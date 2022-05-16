package ke.co.proxyapi.spamblocker.repositories;

import ke.co.proxyapi.spamblocker.models.Keyword;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordsRepository extends PagingAndSortingRepository<Keyword, Integer>
{
	@Query(value = "SELECT id FROM keywords" +
			" WHERE to_tsvector( :text ) @@ to_tsquery(word)", nativeQuery = true)
	List<Integer> findAllMatches(@Param("text") String text);

	Optional<Keyword> findByWord(String word);
}
