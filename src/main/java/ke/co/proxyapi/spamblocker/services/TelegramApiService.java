package ke.co.proxyapi.spamblocker.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelegramApiService
{
    @Autowired
    private HttpService httpService;

    @Value("${app.telegram.api-url}")
    private String tgApiUrl;

    @Value("${app.telegram.token}")
    private String tgToken;

    public void sendMessage(String method, String body)
    {
        String url = tgApiUrl + "/bot" + tgToken + "/" + method.replaceFirst("/", "");
        try
        {
            httpService.process(url, body);
        }
        catch (RuntimeException exception)
        {
            log.error(exception.getMessage(), exception);
        }
    }
}
