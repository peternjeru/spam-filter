package ke.co.proxyapi.spamblocker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Slf4j
public class Main extends SpringBootServletInitializer
{
	public static void main(String args[])
	{
		try
		{
			SpringApplication.run(Main.class, args);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
