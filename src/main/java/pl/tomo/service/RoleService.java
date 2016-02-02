package pl.tomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public void save(Role role)
	{
		roleRepository.save(role);
	}

	public List<Role> findByUser(User user) {
		
		return roleRepository.findByUser(user);
	}

}
