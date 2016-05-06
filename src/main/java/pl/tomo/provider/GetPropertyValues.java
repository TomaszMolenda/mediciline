package pl.tomo.provider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GetPropertyValues {
	
	private Logger logger = Logger.getLogger(GetPropertyValues.class);

	private InputStream inputStream;
	private Properties properties;
	
	public GetPropertyValues(String propFileName) throws IOException{
		properties = new Properties();
		propFileName = "/properties/" + propFileName;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			
			if (inputStream != null) {
				properties.load(inputStream);
				logger.info("load property from file '" + propFileName);
			} else {
				logger.info("property file '" + propFileName + "' not found in the classpath");
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}

	public String getDatabaseNamePostgres() throws IOException {
		String property = properties.getProperty("database.postgres.url");
		return property.substring(property.lastIndexOf("/") + 1);
	}
}
