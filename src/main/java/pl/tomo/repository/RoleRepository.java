package pl.tomo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	List<Role> findAll();

	@Query("SELECT r FROM Role r WHERE r.name = :role")
	Role findRoleByName(@Param("role") String roleName);

	@Query("select r from Role r inner join r.users user where user = :user")
	List<Role> findByUser(@Param("user") User user);

}
