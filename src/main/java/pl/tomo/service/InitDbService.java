package pl.tomo.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.medicament.api.Processing;



@Service
public class InitDbService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private Processing processing;
	
	@PostConstruct
	public void init() throws Exception{

		//processing.process();
		//taskExecutorExample.printMessages();
		
//		Role roleUser = new Role();
//		roleUser.setName("ROLE_USER");
//		roleService.save(roleUser);
//		
//		Role roleAdmin = new Role();
//		roleAdmin.setName("ROLE_ADMIN");
//		roleService.save(roleAdmin);
//		
//		User user = new User();
//		user.setName("tomo");
//		user.setEmail("dupa@o2.pl");
//		user.setPassword("dupa");
//		List<Role> roles = new ArrayList<Role>();
//		roles.add(roleAdmin);
//		roles.add(roleUser);
//		user.setRoles(roles);
//		userService.save(user);
		

		
		
	
		System.out.println("poszlo!");
		
		
	}
	

	


}
