package pl.tomo.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.repository.RoleRepository;

@Service
public class RoleService {
	
	private Logger logger = Logger.getLogger(RoleService.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	public void save(Role role) {
		roleRepository.save(role);
		logger.info("save role: " + role.getName());
	}

	public List<Role> findByUser(User user) {
		if(user != null) {
			logger.info("get list roles, by user: " + user.getName());
		}
		
		return roleRepository.findByUser(user);
	}

	public Role findByName(String roleName) {
		logger.info("get role by name: " + roleName);
		return roleRepository.findRoleByName(roleName);
	}

}
