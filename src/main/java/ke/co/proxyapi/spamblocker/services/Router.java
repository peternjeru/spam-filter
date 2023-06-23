package ke.co.proxyapi.spamblocker.services;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Router extends RouteBuilder
{
	@Autowired
	private DeleteTelegramMessageService deleteTelegramMessageService;

	@Autowired
	private BanTelegramUserService banTelegramUserService;

	@Autowired
	private MessageProcessor messageProcessor;

	@Autowired
	private NewKeywordsProcessor newKeywordsProcessor;

	@Override
	public void configure()
	{
		from("direct:incomingMessage")
				.process(messageProcessor)
				.end();

		from("direct:keywords")
				.process(newKeywordsProcessor)
				.end();

		from("direct:telegram:deleteMessage")
				.throttle(20)
				.timePeriodMillis(1000)
				.process(deleteTelegramMessageService)
				.end();

		from("direct:telegram:banUser")
				.throttle(20)
				.timePeriodMillis(1000)
				.process(banTelegramUserService)
				.end();
	}
}
