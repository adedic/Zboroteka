package hr.tvz.java.zboroteka.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.tvz.java.zboroteka.model.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Integer> {

	Optional<Band> findByCreatorId(Integer creatorId);
}
