package ragdoll.app.phase;

import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;

public class GenerateSDImagePhase implements IPhase {

	// java -jar lib/sdedit-4.01.jar -o demo/test.png -t png demo/test.sd

	@Override
	public void execute() throws Exception {
		RagdollProperties properties = RagdollProperties.getInstance();
		String inputFile = properties.getProperty("Output-Directory") + properties.getProperty("Output-Image-Name")
				+ ".sd";
		String outputImage = properties.getProperty("Output-Directory") + properties.getProperty("Output-Image-Name")
				+ "." + properties.getProperty("Output-Image-Type");
		Runtime runtime = Runtime.getRuntime();
		String cmd = "java -jar " + properties.getProperty("SDEdit-Jar-Path") + " -o " + outputImage + " -t "
				+ properties.getProperty("Output-Image-Type") + " " + inputFile;
		runtime.exec(cmd);
	}

}
