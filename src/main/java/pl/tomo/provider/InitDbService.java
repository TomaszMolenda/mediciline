package pl.tomo.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import pl.tomo.entity.Role;
import pl.tomo.entity.User;
import pl.tomo.medicament.api.Processing;
import pl.tomo.service.RoleService;
import pl.tomo.service.UserService;



@Service
public class InitDbService {
	
	private Logger logger = Logger.getLogger(InitDbService.class);
	
	@Autowired
	private Processing processing;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
		
	@PostConstruct
	public void init(){
/*
		try {
			jdbcTemplateMySQL.execute("ALTER TABLE Disease_Medicament ADD id INT NULL DEFAULT NULL AUTO_INCREMENT , ADD PRIMARY KEY (id)");
			//logger.info(");
		} catch (BadSqlGrammarException e) {
			System.out.println("mam blad1");
			try {
				String sql = "SELECT count(*) from disease_medicament";
				int count = jdbcTemplateMySQL.queryForObject(sql, Integer.class).intValue();
				if(count==0) {
					jdbcTemplateMySQL.execute("drop table Disease_Medicament");
					jdbcTemplateMySQL.execute("create table Disease_Medicament (id integer not null AUTO_INCREMENT, disease_id integer not null, medicaments_id integer not null, primary key (id, disease_id, medicaments_id))");
					jdbcTemplateMySQL.execute("alter table Disease_Medicament add constraint FKl1ismrlm60755iy4rjgx17qtg foreign key (medicaments_id) references Medicament (id)");
					jdbcTemplateMySQL.execute("alter table Disease_Medicament add constraint FKnye1jlloob2ilb0h3h352mmn0 foreign key (disease_id) references Disease (id)");
				}

			} catch (BadSqlGrammarException e1) {
				System.out.println("mam blad2");
			}
		}
*/		
		try {
			jdbcTemplateMySQL.execute("UPDATE User SET JSESSIONID=null WHERE 1");
		} catch (BadSqlGrammarException e) {
			System.out.println("Nie udało się czyszczenie jsessionid");
		}
		

		
		User user = userService.findByName("pina");
		if(user==null) {
			Role roleUser = roleService.findByName("ROLE_USER");
			if(roleUser==null) {
				roleUser = new Role();
				roleUser.setName("ROLE_USER");
				roleService.save(roleUser);
			}
			Role roleAdmin = roleService.findByName("ROLE_ADMIN");
			if(roleAdmin==null) {
				roleAdmin = new Role();
				roleAdmin.setName("ROLE_ADMIN");
				roleService.save(roleAdmin);
			}
			
			User newUser = new User();
			newUser.setName("pina");
			newUser.setEmail("pina@tomo.pl");
			newUser.setPassword("11111");
			newUser.setActive(true);
			newUser.setAuth("5742453c-4e32-45c0-99fc-fdd39c066253");
			newUser.getRoles().add(roleAdmin);
			newUser.getRoles().add(roleUser);


			userService.save(newUser);
		}
		User userAdmin = userService.findByName("nimda");
		if(userAdmin==null) {
			Role roleUser = roleService.findByName("ROLE_USER");
			if(roleUser==null) {
				roleUser = new Role();
				roleUser.setName("ROLE_USER");
				roleService.save(roleUser);
			}
			Role roleAdmin = roleService.findByName("ROLE_ADMIN");
			if(roleAdmin==null) {
				roleAdmin = new Role();
				roleAdmin.setName("ROLE_ADMIN");
				roleService.save(roleAdmin);
			}
			
			User newUser = new User();
			newUser.setName("nimda");
			newUser.setEmail("nimda@o2.pl");
			newUser.setPassword("demo");
			newUser.setActive(true);
			newUser.setAuth("7add864d-a0e1-433a-87eb-27eb598d4a52");
			
			newUser.getRoles().add(roleAdmin);
			newUser.getRoles().add(roleUser);
			
			userService.save(newUser);
		}

		
		
		
		List<User> demoUsers = userService.findAllByEmail("demo@demo.demo");
		for (User demoUserToDelete : demoUsers) {
			userService.delete(demoUserToDelete);
		}
		

		//processing.process();
		
		
		
	}


}
