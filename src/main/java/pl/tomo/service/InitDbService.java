package pl.tomo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitDbService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@PostConstruct
	public void init() {
		
//		Role roleUser = new Role();
//		roleUser.setName("ROLE_USER");
//		roleService.save(roleUser);
//		
//		Role roleAdmin = new Role();
//		roleAdmin.setName("ROLE_ADMIN");
//		roleService.save(roleAdmin);
//		
//		User user = new User();
//		user.setName("tomotomo");
//		user.setPassword("tomotomo");
//		List<Role> roles = new ArrayList<Role>();
//		roles.add(roleAdmin);
//		roles.add(roleUser);
//		user.setRoles(roles);
//		userService.save(user);
		

		
		
	
		System.out.println("poszlo!");
		
		
	}

}
