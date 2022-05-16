package ke.co.proxyapi.spamblocker.services;

import ke.co.proxyapi.spamblocker.dtos.NewKeywordsDto;
import ke.co.proxyapi.spamblocker.models.Keyword;
import ke.co.proxyapi.spamblocker.repositories.KeywordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class NewKeywordsProcessor implements Processor
{
	@Autowired
	private KeywordsRepository keywordsRepository;

	@Override
	public void process(Exchange exchange)
	{
		NewKeywordsDto keywordsDto = exchange.getMessage().getBody(NewKeywordsDto.class);
		keywordsDto.getKeywords()
				.forEach(word ->
				{
					Optional<Keyword> byWord = keywordsRepository.findByWord(word);
					if (byWord.isEmpty())
					{
						Keyword keyword = new Keyword()
								.setWord(word);
						keywordsRepository.save(keyword);
					}
				});
	}
}
