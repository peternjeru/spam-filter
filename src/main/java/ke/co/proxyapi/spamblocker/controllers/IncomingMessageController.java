package ke.co.proxyapi.spamblocker.controllers;

import ke.co.proxyapi.spamblocker.dtos.UpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class IncomingMessageController
{
	@Autowired
	private ProducerTemplate template;

	@PostMapping(value = "/incoming")
	public ResponseEntity<Void> incomingMessage(@RequestBody UpdateDto body)
	{
		template.asyncSendBody("direct:incomingMessage", body);
		return ResponseEntity.ok().build();
	}
}
