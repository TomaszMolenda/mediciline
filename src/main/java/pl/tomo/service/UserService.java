package pl.tomo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.repository.RoleRepository;
import pl.tomo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired

	private RoleRepository roleRepository;

	public void save(User user) {
		userRepository.save(user);
	}

	public User findByName(String name) {

		return userRepository.findByName(name);
	}

	public Role findRoleByName(String roleName) {

		return roleRepository.findRoleByName(roleName);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public List<String> findAllEmail() {
		List<User> users = userRepository.findAll();
		List<String> emails = new ArrayList<String>();
		for (User user : users) {
			emails.add(user.getEmail());
		}
		return emails;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return (UserDetails) userRepository.findByEmail(email);
	}

	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	public User findByUniqueID(String uniqueID) {
		return userRepository.findByUniqueID(uniqueID);
	}

}
