package hr.tvz.java.zboroteka.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.java.zboroteka.model.SongKey;

public interface SongKeyRepository extends JpaRepository<SongKey, Integer> {

	Optional<SongKey> findOneByNameOrOtherName(String keyName, String keyName1);

}
