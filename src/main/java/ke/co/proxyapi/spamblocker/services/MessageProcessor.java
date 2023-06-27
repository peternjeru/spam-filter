package ke.co.proxyapi.spamblocker.services;

import com.ibm.icu.text.Transliterator;
import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import ke.co.proxyapi.spamblocker.dtos.BanUserDto;
import ke.co.proxyapi.spamblocker.dtos.DeleteMessageDto;
import ke.co.proxyapi.spamblocker.dtos.MessageDto;
import ke.co.proxyapi.spamblocker.dtos.UpdateDto;
import ke.co.proxyapi.spamblocker.repositories.KeywordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MessageProcessor implements Processor
{
	@Autowired
	private KeywordsRepository keywordsRepository;

	@Autowired
	private ProducerTemplate template;

	private final Transliterator transliterator = Transliterator.getInstance("Any-Latin/BGN");

	@Value("${app.leeway-min}")
	private Integer leewayMin;

	@Value("${app.leeway-max}")
	private Integer leewayMax;

	@Value("${app.spamwords}")
	private String[] spamWords;

	@Override
	public void process(Exchange exchange)
	{
		UpdateDto updateDto = exchange.getMessage().getBody(UpdateDto.class);

		MessageDto messageDto;
		if (updateDto.getEditedMessage() != null)
		{
			messageDto = updateDto.getEditedMessage();
		}
		else
		{
			messageDto = updateDto.getMessage();
		}

		String text = null;
		if (messageDto.getCaption() != null && !messageDto.getCaption().isEmpty())
		{
			text = messageDto.getCaption();
		}
		if (messageDto.getText() != null && !messageDto.getText().isEmpty())
		{
			text = messageDto.getText();
		}

		if (text == null)
		{
			return;
		}

		String normalizedText = transliterator.transliterate(text);
		log.info("Normalized Text:\n" + normalizedText);

		List<Integer> allMatches = keywordsRepository.findAllMatches(normalizedText);
		if ((allMatches.size() >= leewayMin && hasTgLink(normalizedText)) || allMatches.size() >= leewayMax)
		{
			log.info("Matches: " + allMatches.size());
			DeleteMessageDto deleteMessageDto = new DeleteMessageDto(messageDto.getChat().getId(), messageDto.getMessageID());
			template.asyncSendBody("direct:telegram:deleteMessage", deleteMessageDto);
		}

		String lowerCase = normalizedText.toLowerCase();
		for (String spamWord : spamWords)
		{
			Pattern pattern = Pattern.compile(spamWord, Pattern.CASE_INSENSITIVE);
			if (pattern.matcher(lowerCase).results().count() >= 2)
			{
				log.info("Found spam word '" + spamWord + "' in message");
				DeleteMessageDto deleteMessageDto = new DeleteMessageDto(messageDto.getChat().getId(), messageDto.getMessageID());
				template.asyncSendBody("direct:telegram:deleteMessage", deleteMessageDto);

				BanUserDto banUserDto = new BanUserDto(messageDto.getChat().getId(), messageDto.getUser().getId(), false);
				template.asyncSendBody("direct:telegram:banUser", banUserDto);
			}
		}
	}

	private boolean hasTgLink(String message)
	{
		Set<String> tgDomains = Set.of("t.me", "telegram.me","telegram.dog");

		UrlDetector parser = new UrlDetector(message, UrlDetectorOptions.HTML);
		List<Url> urlList = parser.detect();

		for (Url url: urlList)
		{
			if (tgDomains.contains(url.getHost()))
			{
				return true;
			}
		}
		return false;
	}
}
