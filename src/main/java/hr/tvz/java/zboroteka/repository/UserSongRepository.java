package hr.tvz.java.zboroteka.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.tvz.java.zboroteka.model.UserSong;

@Repository
public interface UserSongRepository extends JpaRepository<UserSong, Integer> {

	@Modifying
	@Transactional
	@Query("DELETE FROM UserSong us " + " WHERE " + " us.songId = :songId")
	void deleteUserSongBySongId(@Param("songId") Integer songId);
}
