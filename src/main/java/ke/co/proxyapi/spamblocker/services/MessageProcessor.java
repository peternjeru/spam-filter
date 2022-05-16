package ke.co.proxyapi.spamblocker.services;

import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import ke.co.proxyapi.spamblocker.dtos.DeleteMessageDto;
import ke.co.proxyapi.spamblocker.dtos.MessageDto;
import ke.co.proxyapi.spamblocker.dtos.UpdateDto;
import ke.co.proxyapi.spamblocker.repositories.KeywordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
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

	@Value("${app.leeway-min}")
	private Integer leewayMin;

	@Value("${app.leeway-max}")
	private Integer leewayMax;

	private final Pattern NON_ASCII_PATTERN = Pattern.compile("\\P{ASCII}");

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

		String normalized = Normalizer.normalize(StringUtils.stripAccents(text), Normalizer.Form.NFKC);
		String ascii = NON_ASCII_PATTERN.matcher(normalized).replaceAll("");
		log.info("Normalized Txt:\n\n" + ascii);

		List<Integer> allMatches = keywordsRepository.findAllMatches(ascii);
		log.info("Matches: " + allMatches.size());

		if ((allMatches.size() >= leewayMin && hasTgLink(ascii)) || allMatches.size() >= leewayMax)
		{
			DeleteMessageDto deleteMessageDto = new DeleteMessageDto(messageDto.getChat().getId(), messageDto.getMessageID());
			template.asyncSendBody("direct:telegram", deleteMessageDto);
		}
	}

	private boolean hasTgLink(String message)
	{
		Set<String> tgDomains = Set.of("t.me", "telegram.me");

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
