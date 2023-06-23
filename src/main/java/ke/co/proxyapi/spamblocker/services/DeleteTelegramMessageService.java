package ke.co.proxyapi.spamblocker.services;

import com.google.gson.Gson;
import ke.co.proxyapi.spamblocker.dtos.DeleteMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeleteTelegramMessageService implements Processor
{
	@Autowired
	private Gson gson;

	@Autowired
	private TelegramApiService telegramApiService;

	@Override
	public void process(Exchange exchange)
	{
		DeleteMessageDto dto = exchange.getMessage().getBody(DeleteMessageDto.class);
		String requestJson = gson.toJson(dto);
		telegramApiService.sendMessage("deleteMessage", requestJson);
	}
}
