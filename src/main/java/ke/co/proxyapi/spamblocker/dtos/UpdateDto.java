package ke.co.proxyapi.spamblocker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UpdateDto implements Serializable
{
	@JsonProperty("update_id")
	private Long updateID;

	@JsonProperty("message")
	private MessageDto message;

	@JsonProperty("edited_message")
	private MessageDto editedMessage;
}
