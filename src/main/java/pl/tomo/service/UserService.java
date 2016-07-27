package pl.tomo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.tomo.controller.exception.UserValidationException;
import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.provider.EmailService;
import pl.tomo.repository.RoleRepository;
import pl.tomo.repository.UserRepository;
import pl.tomo.repository.UserRepositoryEntityGraph;
import pl.tomo.validator.UserValidator;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepositoryEntityGraph userRepositoryEntityGraph;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserValidator userValidator;

	public User save(User user) throws ConstraintViolationException, UserValidationException {
		userValidator.validate(user);
		user = prepare(user);
		user = userRepository.save(user);
		HashMap<String, String> email = prepareEmail(user);
		emailService.sendEmail(email.get("subject"), email.get("text"), email.get("email"));
		return user;
	}

	private HashMap<String, String> prepareEmail(User user) {
		HashMap<String, String> email = new HashMap<>();
		email.put("subject", "Rejestracja w serwisie Mediciline");
		email.put("email", user.getEmail());
		String link = "http://212.244.79.82:8085/confirm/" + user.getUniqueID();
		String text = "Witaj " + user.getName() + "!,<br><br>Kliknij w poniższy link aby aktywować konto<br><br>"
				+ "<a href=\"" + link + "\">Aktywacja konta</a>";
		email.put("text", text);
		return email;
	}

	public User findByName(String name) {
		User user = userRepository.findByName(name);
		return user;
	}

	public Role findRoleByName(String roleName) {
		Role role = roleRepository.findRoleByName(roleName);
		return role;
	}

	public List<User> findAll() {
		List<User> users = userRepositoryEntityGraph.getAll("select u from User u", "roles", "medicaments", "diseases", "patients");
		Set<User> list = new HashSet<User>(users);
		List<User> returnList = new ArrayList<User>(list);
		Collections.sort(returnList, new Comparator<User>() {
			@Override
			public int compare(User u1, User u2) {
				int i1 = u1.getId();
				int i2 = u2.getId();
				return (i1 > i2 ? -1 : (i1 == i2 ? 0 : 1));
			}
		});
		return returnList;
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

	public List<String> findAllNameLowerCase() {
		List<User> users = userRepository.findAll();
		List<String> names = new ArrayList<String>();
		for (User user : users) {
			names.add(user.getName().toLowerCase());
		}
		return names;
	}

	public List<User> findAllByName(String userName) {
		return userRepository.findAllByName(userName);
	}

	public void delete(User user) {
		userRepository.delete(user);
		
	}
	
	public User findByRequestOnlyUser(HttpServletRequest request) {
		String auth = requestService.getAuthCookie(request);
		return userRepositoryEntityGraph.findByRequestOnlyUser(auth);
	}
	
	public User findWithMedicaments(HttpServletRequest request) {
		String auth = requestService.getAuthCookie(request);
		return userRepositoryEntityGraph.findWithMedicaments(auth);
	}

	public List<User> findAllByEmail(String email) {
		return userRepository.findAllByEmail(email);
	}

	public boolean isAdmin(User user) {
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			if(role.getName().equals("ROLE_ADMIN"))
				return true;
		}
		return false;
	}
	
	private User prepare(User user) {
		user.setEmail(user.getEmail().toLowerCase());
		Role role = findRoleByName("ROLE_USER");
		user.getRoles().add(role);
		user.setUniqueID(UUID.randomUUID().toString());
		user.setAuth(UUID.randomUUID().toString());
		return user;
	}

	public User confimr(String uniqueID) {
		User user = findByUniqueID(uniqueID);
		user.setActive(true);
		return userRepository.save(user);
	}

	public User getAllData(HttpServletRequest request) {
		String auth = requestService.getAuthCookie(request);
		User returnUser = userRepositoryEntityGraph.getAllData(auth);
		return returnUser;
	}


	

}
