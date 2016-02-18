package ragdoll.framework;

import java.io.FileInputStream;
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
	
	private Properties properties;
	
	private RagdollProperties() {
		init();
	}
	
	public void init() {
		this.properties = new Properties();
	}
	
	public void loadProperties(String propFilePath) throws IOException {
		InputStream inputStream = new FileInputStream(propFilePath);
		properties.load(inputStream);
		inputStream.close();
	}
	
	public String getProperty(String name, String defaultValue) {
		return properties.getProperty(name, defaultValue);
	}
	
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
	
}
