package ragdoll.app.phase;

import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;

public class GenerateDotImagePhase implements IPhase {

	// dot demo/temppp.gv -Tpng -o demo/temppp.png

	@Override
	public void execute() throws Exception {
		RagdollProperties properties = RagdollProperties.getInstance();
		String inputFile = properties.getProperty("Output-Directory") + properties.getProperty("Output-Image-Name")
				+ ".gv";
		String outputImage = properties.getProperty("Output-Directory") + properties.getProperty("Output-Image-Name")
				+ "." + properties.getProperty("Output-Image-Type");
		Runtime runtime = Runtime.getRuntime();
		String cmd = properties.getProperty("Dot-Path") + " " + inputFile + " -Tpng -o " + outputImage;
		System.out.println(cmd);
		runtime.exec(cmd);
	}

}
