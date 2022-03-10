package ke.co.proxyapi.spamblocker.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteMessageDto
{
	@SerializedName("chat_id")
	private String chatID;

	@SerializedName("message_id")
	private Long messageID;
}
