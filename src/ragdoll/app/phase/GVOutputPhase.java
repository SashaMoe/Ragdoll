package ragdoll.app.phase;

import java.io.PrintWriter;
import java.util.Map;

import ragdoll.app.pattern.GVFormatConsumer;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;

public class GVOutputPhase implements IPhase {
	private PatternInfo patternInfo;

	public GVOutputPhase(PatternInfo patternInfo) {
		this.patternInfo = patternInfo;
	}

	public void execute() throws Exception {
		IFormatConsumer gvFormatConsumer = GVFormatConsumer.getInstance();
		gvFormatConsumer.parse(patternInfo.getPatterMap());

		IClassInfo classInfo = ClassInfo.getInstance();
		Map<String, IClass> iClasses = classInfo.getClasses();
		GVOutputStream gvOS = new GVOutputStream();
		gvOS.initBuffer();
		for (String c : iClasses.keySet()) {
			iClasses.get(c).accept(gvOS);
		}
		gvOS.endBuffer();

		RagdollProperties properties = RagdollProperties.getInstance();
		String outputFile = properties.getProperty("Output-Directory") + "/"
				+ properties.getProperty("Output-Image-Name") + ".gv";
		try (PrintWriter out = new PrintWriter(outputFile)) {
			out.println(gvOS.toString());
		}

		// System.out.println(gvOS.toString());
	}

}
