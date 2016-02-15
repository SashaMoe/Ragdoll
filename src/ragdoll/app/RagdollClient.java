package ragdoll.app;

import ragdoll.framework.RagdollProperties;

public class RagdollClient {
	public static void main(String[] args) throws Exception {
		RagdollProperties properties = RagdollProperties.getInstance();
		
		properties.loadProperties("resources/config.properties");
	}
}
