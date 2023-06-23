package ke.co.proxyapi.spamblocker.services;

import com.google.gson.Gson;
import ke.co.proxyapi.spamblocker.dtos.BanUserDto;
import ke.co.proxyapi.spamblocker.dtos.DeleteMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BanTelegramUserService implements Processor
{
	@Autowired
	private Gson gson;

	@Autowired
	private HttpService httpService;

	@Value("${app.telegram.api-url}")
	private String tgApiUrl;

	@Value("${app.telegram.token}")
	private String tgToken;

	@Override
	public void process(Exchange exchange)
	{
		BanUserDto dto = exchange.getMessage().getBody(BanUserDto.class);
		String requestJson = gson.toJson(dto);
		String url = tgApiUrl + "/bot" + tgToken + "/banChatMember";

		try
		{
			httpService.process(url, requestJson);
		}
		catch (RuntimeException exception)
		{
			log.error(exception.getMessage(), exception);
		}
	}
}
