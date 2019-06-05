package hr.tvz.java.zboroteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.tvz.java.zboroteka.model.Band;

@Repository
public interface BandRepository extends JpaRepository<Band, Integer> {

}
