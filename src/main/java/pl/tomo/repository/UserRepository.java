package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findAll();

	@Query("select u from User u where name = :name")
	User findByName(@Param("name") String name);
	//User findByName(String name);

	@Query("select u from User u where email = :email")
	User finByEmail(@Param("email") String email);

}
