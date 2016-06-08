package pl.tomo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.repository.RoleRepository;
import pl.tomo.repository.UserRepository;
import pl.tomo.repository.UserRepositoryEntityGraph;

@Service
public class UserService implements UserDetailsService {
	
	private Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRepositoryEntityGraph userRepositoryEntityGraph;
	
	@Autowired
	private RequestService requestService;

	public void save(User user) {
		userRepository.save(user);
		logger.info("User " + user.getName() + " has been saved");
	}

	public User findByName(String name) {
		User user = userRepository.findByName(name);
		if(user == null)
			logger.info("Try to find user " + name + " - no success");
		else
			logger.info("Try to find user " + name + " - success");
		return user;
	}

	public Role findRoleByName(String roleName) {
		Role role = roleRepository.findRoleByName(roleName);
		if(role == null)
			logger.info("Try to find user " + roleName + " - no success");
		else
			logger.info("Try to find user " + roleName + " - success");
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
		String logUsers = "";
		for (User user : returnList) {
			logUsers += user.getName() + "; ";
		}
		logger.info("Find all users: " + logUsers);
		return returnList;
	}

	public List<String> findAllEmail() {
		List<User> users = userRepository.findAll();
		List<String> emails = new ArrayList<String>();
		for (User user : users) {
			emails.add(user.getEmail());
		}
		String logEmails = "";
		for (String string : emails) {
			logEmails += string + "; ";
		}
		logger.info("Find all emails: " + logEmails);
		return emails;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("get user deatils by email: " + email);
		return (UserDetails) userRepository.findByEmail(email);
	}

	public User findByEmail(String email) {
		logger.info("get user by email: " + email);
		return userRepository.findByEmail(email);
	}

	public User findByUniqueID(String uniqueID) {
		logger.info("get user by UniqueID: " + uniqueID);
		return userRepository.findByUniqueID(uniqueID);
	}

	public List<String> findAllName() {
		List<User> users = userRepository.findAll();
		List<String> names = new ArrayList<String>();
		for (User user : users) {
			names.add(user.getName());
		}
		logger.info("get list users");
		return names;
	}

	public List<User> findAllByName(String userName) {
		logger.info("get list users by name: " + userName);
		return userRepository.findAllByName(userName);
	}

	public void delete(User user) {
		logger.info("delete user: " + user.getName());
		userRepository.delete(user);
		
	}

	public User findByRequest(HttpServletRequest request) {
		String authCookie = requestService.getAuthCookie(request);
		logger.info("get user by auth: " + authCookie);
		return userRepository.findByAuth(authCookie);
	}

	public List<User> findAllByEmail(String email) {
		logger.info("get list users by email: " + email);
		return userRepository.findAllByEmail(email);
	}

}
