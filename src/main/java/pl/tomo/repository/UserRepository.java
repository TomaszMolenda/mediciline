package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findAll();

	@Query("select u from User u JOIN FETCH u.diseases d where u.name = :name")
	User findByNameQuery(@Param("name") String name);
	
	User findByName(String name);

	@Query("select u from User u where email = :email")
	User findByEmail(@Param("email") String email);
	
	User findByUniqueID(String uniqueID);

	@Query("select u.demoNo from User u where u.demoNo != 0")
	List<Integer> findAllDemoNo();

	@Query("SELECT u FROM User u LEFT JOIN u.patients LEFT JOIN u.diseases WHERE u.name = :userName")
	List<User> findAllByName(@Param("userName") String userName);
	
	@Query("SELECT u FROM User u LEFT JOIN u.patients WHERE u.name = :name")
	User findByNameWithPatients(@Param("name") String name);

}
