package hr.tvz.java.zboroteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.java.zboroteka.model.SongKey;

public interface SongKeyRepository extends JpaRepository<SongKey, Integer> {

}
