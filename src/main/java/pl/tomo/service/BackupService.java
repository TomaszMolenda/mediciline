package pl.tomo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Backup;
import pl.tomo.provider.GetPropertyValues;
import pl.tomo.repository.BackupRepository;

@Service
public class BackupService {
	
	@Autowired
	private BackupRepository backupRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplatePostgres;

	public List<Backup> findAll() {
		Set<Backup> backups = new HashSet<Backup>(backupRepository.findAll());
		List<Backup> list = new ArrayList<Backup>(backups);
		Collections.sort(list, new Comparator<Backup>() {
		    public int compare(Backup o1, Backup o2) {
		        Integer i1 = o1.getId();
		        Integer i2 = o2.getId();
		        return (i1 > i2 ? -1 : (i1 == i2 ? 0 : 1));
		    }
		});
		return list;
	}

	public void backupPostgres() throws IOException {
		GetPropertyValues getPropertyValues = new GetPropertyValues("database.properties");
		String databaseNamePostgres = getPropertyValues.getDatabaseNamePostgres();
		
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
			backup.setSuccess(true);
			save(backup);
		} else {
			backup.setSuccess(false);
			save(backup);
			throw new IOException();
		}
		
	}
	
	private void cmdExecute(ProcessBuilder processBuilder) throws IOException {
		Runtime r = Runtime.getRuntime();
		Process p;
		r = Runtime.getRuntime();
		processBuilder.redirectErrorStream(true);
		p = processBuilder.start();
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String ll;
	}
	
	private void save(Backup backup) {
		backupRepository.save(backup);
	}

	public void restorePostgres(int id) throws IOException {
		Backup backup = getById(id);
		String path = backup.getPath();
		GetPropertyValues getPropertyValues = new GetPropertyValues("database.properties");
		String databaseNamePostgres = getPropertyValues.getDatabaseNamePostgres();
		if(backup.isSuccess()) {
			jdbcTemplatePostgres.execute("SELECT pg_terminate_backend (pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '" + databaseNamePostgres + "';");
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
			return;
			}
		throw new IOException();
		
	}
	private Backup getById(int id) {
		Backup backup = backupRepository.findOne(id);
		return backup;
	}
	
}
