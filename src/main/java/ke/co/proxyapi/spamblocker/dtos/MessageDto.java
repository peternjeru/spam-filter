package ke.co.proxyapi.spamblocker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MessageDto implements Serializable
{
	@JsonProperty("message_id")
	private Long messageID;

	@JsonProperty("date")
	private Long date;

	@JsonProperty("text")
	private String text;

	@JsonProperty("from")
	private UserDto user;

	@JsonProperty("chat")
	private ChatDto chat;
}
