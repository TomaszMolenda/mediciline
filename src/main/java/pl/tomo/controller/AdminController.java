package pl.tomo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import pl.tomo.entity.Backup;
import pl.tomo.entity.User;
import pl.tomo.provider.GetPropertyValues;
import pl.tomo.service.BackupService;
import pl.tomo.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	private Logger logger = Logger.getLogger(AdminController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BackupService backupService;
	
	@Autowired
	private JdbcTemplate jdbcTemplatePostgres;
	
	@RequestMapping(value = "/info")
	public ModelAndView admin(Principal principal) throws Exception {
		
		
		
		ModelAndView modelAndView = new ModelAndView("admin");
		Set<Backup> backups = backupService.findAll();
		List<Backup> list = new ArrayList<Backup>(backups);
		Collections.sort(list, new Comparator<Backup>() {
		    public int compare(Backup o1, Backup o2) {
		        Integer i1 = o1.getId();
		        Integer i2 = o2.getId();
		        return (i1 > i2 ? -1 : (i1 == i2 ? 0 : 1));
		    }
		});
		List<User> users = userService.findAll();
		modelAndView.addObject("backups", list);
		modelAndView.addObject("users", users);
		logger.info("User " + principal.getName() + " open admin page");
		return modelAndView;
	}
	@RequestMapping(value = "/postgresql/backup")
	public ModelAndView postgresqlBackup() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		
		GetPropertyValues getPropertyValues = new GetPropertyValues("database.properties");
		String databaseNamePostgres = getPropertyValues.getDatabaseNamePostgres();
		
		logger.info("Start backup postgres");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String format = simpleDateFormat.format(date);
		String path = System.getProperty("user.home") + "\\Documents\\" + format + ".backup";
		
		ProcessBuilder backupDb = new ProcessBuilder(	
		    "C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_dump.exe",
		    "--host", "localhost",
		    "--port", "5432",
		    "--username", "postgres",
		    "--no-password",
		    "--format", "custom",
		    "--blobs",
		    "--verbose",
		    "--file", path,
		    databaseNamePostgres
		    );
		
		cmdExecute(backupDb);
		
		File file = new File(path);
		Backup backup = new Backup();
		backup.setPath(path.replaceAll("\\\\", "\\\\\\\\"));
		backup.setName("Postgresql_" + format);
		backup.setDate(date);
		backup.setSize(file.length());
		backup.setType("postgresql");
		if(file.exists() & !file.isDirectory() & file.length() > 0) {
			modelAndView.setViewName("redirect:/admin/info.html?success=true#tab_databases");
			logger.info("Finish backup postgres - successfull");
			backup.setSuccess(true);
		}
		else {
			modelAndView.setViewName("redirect:/admin/info.html?success=false#tab_databases");
			logger.info("Finish backup postgres - error");
			backup.setSuccess(false);
		}
		backupService.save(backup);
		return modelAndView;
	}
	
	@RequestMapping(value = "/postgresql/restore")
	@ResponseStatus(value = HttpStatus.OK)
	public void postgresqlRestore(@RequestHeader("id") int id, HttpServletResponse response) throws Exception {		
		
		Backup backup = backupService.getById(id);
		String path = backup.getPath();
		GetPropertyValues getPropertyValues = new GetPropertyValues("database.properties");
		String databaseNamePostgres = getPropertyValues.getDatabaseNamePostgres();
		logger.info("Start restore postgres, dbname: " + databaseNamePostgres + ", backup: " + backup.toString());
		if(backup.isSuccess()) {
			jdbcTemplatePostgres.execute("SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '" + databaseNamePostgres + "';");
			logger.info("Terminated connecion posgres");
			ProcessBuilder dropDb = new ProcessBuilder(
					"C:\\Program Files\\PostgreSQL\\9.5\\bin\\dropdb.exe",
				    "--host", "localhost",
				    "--port", "5432",
				    "--username", "postgres",
				    "--no-password",
				    "--if-exists",
				    "\"" + databaseNamePostgres + "\""
					);
			
			ProcessBuilder createDb = new ProcessBuilder(
					"C:\\Program Files\\PostgreSQL\\9.5\\bin\\createdb.exe",
				    "--host", "localhost",
				    "--port", "5432",
				    "--username", "postgres",
				    "--no-password",
				    "--encoding=UTF8",
				    "--lc-collate=Polish_Poland.1250",
				    "--lc-ctype=Polish_Poland.1250",
				    "\"" + databaseNamePostgres + "\""
					);
			
			ProcessBuilder restoreDb = new ProcessBuilder(
				"C:\\Program Files\\PostgreSQL\\9.5\\bin\\pg_restore.exe",
			    "--host", "localhost",
			    "--port", "5432",
			    "--username", "postgres",
			    "--no-password",
			    "--dbname", databaseNamePostgres,
			    "--verbose",
			    path
				);
			
			cmdExecute(dropDb);
			cmdExecute(createDb);
			cmdExecute(restoreDb);
			logger.info("restore database posgres complete, dbname: " + databaseNamePostgres);
			response.setHeader("status", "success");
		}
		else{
			logger.info("restore database posgres error, dbname: " + databaseNamePostgres);
			response.setHeader("status", "unsuccess");
		}
	}

	private void cmdExecute(ProcessBuilder processBuilder) throws IOException {
		logger.info("start cmd");
		Runtime r = Runtime.getRuntime();
		Process p;
		r = Runtime.getRuntime();
		processBuilder.redirectErrorStream(true);
		p = processBuilder.start();
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String ll;
		while ((ll = br.readLine()) != null) {
			logger.info(ll);
		}
		logger.info("finish cmd");
	}

}
