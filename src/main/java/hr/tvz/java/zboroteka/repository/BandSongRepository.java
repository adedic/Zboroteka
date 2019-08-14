package hr.tvz.java.zboroteka.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.tvz.java.zboroteka.model.BandSong;

@Repository
public interface BandSongRepository extends JpaRepository<BandSong, Integer> {
	@Modifying
	@Transactional
	@Query("DELETE FROM BandSong bs WHERE bs.songId = :songId")
	void deleteBandSongBySongId(@Param("songId") Integer songId);
}
