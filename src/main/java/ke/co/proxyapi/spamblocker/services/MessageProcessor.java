package ke.co.proxyapi.spamblocker.services;

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
import java.util.regex.Pattern;

@Service
@Slf4j
public class MessageProcessor implements Processor
{
	@Autowired
	private KeywordsRepository keywordsRepository;

	@Autowired
	private ProducerTemplate template;

	@Value("${app.leeway}")
	private Integer leeway;

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

		String normalized = Normalizer.normalize(StringUtils.stripAccents(messageDto.getText()), Normalizer.Form.NFKC);
		String ascii = NON_ASCII_PATTERN.matcher(normalized).replaceAll("");
		List<Integer> allMatches = keywordsRepository.findAllMatches(ascii);
		if (allMatches.size() >= leeway)
		{
			log.info("Matches: " + allMatches.size());
			DeleteMessageDto deleteMessageDto = new DeleteMessageDto(messageDto.getChat().getId(), messageDto.getMessageID());
			template.asyncSendBody("direct:telegram", deleteMessageDto);
		}
	}
}
