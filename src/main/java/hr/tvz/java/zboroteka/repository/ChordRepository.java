package hr.tvz.java.zboroteka.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.tvz.java.zboroteka.model.Chord;

public interface ChordRepository extends JpaRepository<Chord, Integer> {

	@Query("SELECT c FROM Chord c WHERE c.name =:name")
	Optional<Chord> findOneByName(@Param("name") String name);
	

}
