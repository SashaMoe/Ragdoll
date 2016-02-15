package ragdoll.framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RagdollProperties {
	private volatile static RagdollProperties instance;
	
	public static RagdollProperties getInstance() {
		if (instance == null) {
			synchronized (RagdollProperties.class) {
				if (instance == null) {
					instance = new RagdollProperties();
				}
			}
		}
		return instance;
	}
	
	private InputStream inputStream;
	private String propFilePath;
	private Properties properties;
	
	private RagdollProperties() {
		this.properties = new Properties();
	}
	
	public void loadProperties(String propFilePath) throws IOException {
		this.properties = new Properties();
		this.propFilePath = propFilePath;
		inputStream = new FileInputStream(propFilePath);
		if (inputStream != null) {
			properties.load(inputStream);
		} else {
			throw new FileNotFoundException(propFilePath + " not found!");
		}
		inputStream.close();
	}
	
	public String getProperty(String name, String defaultValue) {
		return properties.getProperty(name, defaultValue);
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
}
