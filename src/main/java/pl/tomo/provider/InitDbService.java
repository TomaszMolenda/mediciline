package pl.tomo.provider;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import pl.tomo.medicament.api.Processing;



@Service
public class InitDbService {
	
	private Logger logger = Logger.getLogger(InitDbService.class);
	
	@Autowired
	private Processing processing;
	
	@Autowired
	private JdbcTemplate jdbcTemplateMySQL;
	
	@PostConstruct
	public void init(){
		
//		String[] columnsDisease_Medicament = {
//				"info",
//				""
//		
//		};
//		
		try {
			jdbcTemplateMySQL.execute("ALTER TABLE Disease_Medicament ADD id INT NULL DEFAULT NULL AUTO_INCREMENT , ADD PRIMARY KEY (id)");
			//logger.info(");
		} catch (BadSqlGrammarException e) {
			System.out.println("mam blad");
		}
		

		//processing.process();
		
		
		
	}


}
