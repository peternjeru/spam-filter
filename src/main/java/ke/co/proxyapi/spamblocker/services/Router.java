package ke.co.proxyapi.spamblocker.services;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Router extends RouteBuilder
{
	@Autowired
	private TelegramService telegramService;

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

		from("direct:telegram")
				.throttle(20)
				.timePeriodMillis(1000)
				.process(telegramService)
				.end();
	}
}
