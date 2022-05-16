package ke.co.proxyapi.spamblocker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class NewKeywordsDto
{
	@JsonProperty("keywords")
	private List<String> keywords;
}
