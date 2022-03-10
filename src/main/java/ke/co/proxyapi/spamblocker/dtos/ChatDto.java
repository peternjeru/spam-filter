package ke.co.proxyapi.spamblocker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ChatDto implements Serializable
{
	@JsonProperty("id")
	private String id;

	@JsonProperty("type")
	private String type;

	@JsonProperty("title")
	private String title;
}
