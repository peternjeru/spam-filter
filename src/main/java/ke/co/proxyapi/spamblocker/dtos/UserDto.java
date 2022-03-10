package ke.co.proxyapi.spamblocker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserDto implements Serializable
{
	@JsonProperty("id")
	private Long id;

	@JsonProperty("is_bot")
	private Boolean isBot;

	@JsonProperty("username")
	private String username;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String LastName;
}
