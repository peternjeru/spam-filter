package ke.co.proxyapi.spamblocker.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BanUserDto
{
	@SerializedName("chat_id")
	private String chatID;

	@SerializedName("user_id")
	private Long userID;

	@SerializedName("revoke_messages")
	private Boolean revokeMessages;
}
