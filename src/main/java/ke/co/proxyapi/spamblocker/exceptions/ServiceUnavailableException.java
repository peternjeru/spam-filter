package ke.co.proxyapi.spamblocker.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RuntimeException
{
	public ServiceUnavailableException(String body)
	{
		super(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase() + ": " + body);
	}
}
