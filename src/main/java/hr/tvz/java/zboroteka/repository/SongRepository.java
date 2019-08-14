package hr.tvz.java.zboroteka.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.tvz.java.zboroteka.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

	//All songs created by current user filtered by query string
	@Query("SELECT s FROM Song s "
			+ " WHERE "
			+ " (upper(s.name) LIKE %:query% "
			+ " OR upper(s.songText) LIKE %:query% "
			+ " OR upper(s.description) LIKE %:query% "
			+ " OR upper(s.author) LIKE %:query% "
			+ " OR upper(s.usage) LIKE %:query% ) "
			+ " AND "
			+ "s.creatorId = :creatorId"
			)
	Optional<List<Song>> findAllByQueryAndCreator(@Param("query") String query, @Param("creatorId") Integer creatorId);

	Optional<List<Song>> findAllByCreatorId(Integer creatorId);

	

	@Modifying
	@Transactional
	@Query("DELETE FROM Song s WHERE s.id = :songId")
	void deleteSongById(@Param("songId") Integer songId);

}
