package ke.co.proxyapi.spamblocker.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "keywords")
public class Keyword
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "word", nullable = false, columnDefinition = "TEXT", unique = true)
	private String word;
}
