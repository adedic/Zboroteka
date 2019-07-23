package hr.tvz.java.zboroteka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.java.zboroteka.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
