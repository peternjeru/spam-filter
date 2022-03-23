package ke.co.proxyapi.spamblocker.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException
{
	public BadRequestException(String body)
	{
		super(HttpStatus.BAD_REQUEST.getReasonPhrase() + ": " + body);
	}
}
