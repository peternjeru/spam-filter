package ke.co.proxyapi.spamblocker.services;

import ke.co.proxyapi.spamblocker.exceptions.BadRequestException;
import ke.co.proxyapi.spamblocker.exceptions.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class HttpService
{
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RetryTemplate retryTemplate;

	public String process(String uri, String bodyStr)
	{
		return retryTemplate.execute((RetryCallback<String, ServiceUnavailableException>) retryContext ->
		{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<>(bodyStr, headers);

			ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, entity, String.class);
			String body = responseEntity.getBody();

			if (!responseEntity.getStatusCode().isError())
			{
				return body;
			}
			throw new BadRequestException(body);
		});
	}
}
