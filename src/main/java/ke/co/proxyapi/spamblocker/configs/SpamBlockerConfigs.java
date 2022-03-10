package ke.co.proxyapi.spamblocker.configs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpamBlockerConfigs
{
	@Value("${app.http.connect-timeout}")
	private Integer connectTimeout;

	@Value("${app.http.read-timeout}")
	private Integer readTimeout;

	@Bean
	public ExecutorService executorService()
	{
		return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory()
	{
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(connectTimeout);
		clientHttpRequestFactory.setReadTimeout(connectTimeout);
		clientHttpRequestFactory.setConnectionRequestTimeout(connectTimeout);
		return clientHttpRequestFactory;
	}

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory)
	{
		return new RestTemplate(clientHttpRequestFactory);
	}

	@Bean
	public ClientHttpConnector factory()
	{
		HttpClient httpClient = HttpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
				.responseTimeout(Duration.ofMillis(connectTimeout))
				.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS))
						.addHandlerLast(new WriteTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS)));

		return new ReactorClientHttpConnector(httpClient);
	}

	@Bean
	public WebClient webClient(ClientHttpConnector connector)
	{
		return WebClient.builder()
				.clientConnector(connector)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@Bean
	public Gson gson()
	{
		return new GsonBuilder()
				.setPrettyPrinting()
				.create();
	}
}
