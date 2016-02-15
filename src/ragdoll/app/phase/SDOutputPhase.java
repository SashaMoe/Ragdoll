package ragdoll.app.phase;

import java.io.PrintWriter;
import java.util.List;

import ragdoll.code.sd.api.INode;
import ragdoll.code.sd.impl.SDInfo;
import ragdoll.code.visitor.impl.SDOutputStream;
import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;

public class SDOutputPhase implements IPhase {

	@Override
	public void execute() throws Exception {
		RagdollProperties properties = RagdollProperties.getInstance();
		String outputFile = properties.getProperty("Output-Directory")+"/"+properties.getProperty("Output-Image-Name")+".sd";
		SDInfo sdInfo = SDInfo.getInstance();
		List<String> classes = sdInfo.getClasses();
		INode startMethod = sdInfo.getStartMethod();
		SDOutputStream sdOS = new SDOutputStream();
		sdOS.visit(classes);
		sdOS.visit("\n");
		startMethod.accept(sdOS);

		try (PrintWriter out = new PrintWriter(outputFile)) {
			out.println(sdOS.toString());
		}

		// System.out.println(sdOS.toString());
	}

}
