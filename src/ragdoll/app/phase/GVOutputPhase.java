package ragdoll.app.phase;

import java.io.PrintWriter;
import java.util.Map;

import ragdoll.app.pattern.GVFormatConsumer;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;

public class GVOutputPhase implements IPhase {
	private PatternInfo patternInfo;

	public GVOutputPhase() {
		this.patternInfo = PatternInfo.getInstance();
	}

	public void execute() throws Exception {
		GVFormatConsumer gvFormatConsumer = GVFormatConsumer.getInstance();
		gvFormatConsumer.init();
		gvFormatConsumer.parse(patternInfo.getPatterMap());

		IClassInfo classInfo = ClassInfo.getInstance();
		Map<String, IClass> iClasses = classInfo.getClasses();
		GVOutputStream gvOS = new GVOutputStream();
		gvOS.initBuffer();
		for (String c : iClasses.keySet()) {
			if (RagdollProperties.getInstance().getProperty("Mode").equals("UML") ||
				patternInfo.getInvolvedClasses().contains(c)) {
				iClasses.get(c).accept(gvOS);
			}
			
		}
		gvOS.endBuffer();

		RagdollProperties properties = RagdollProperties.getInstance();
		String outputFile = properties.getProperty("Output-Directory") + "/"
				+ properties.getProperty("Output-Image-Name") + ".gv";
		try (PrintWriter out = new PrintWriter(outputFile)) {
			out.println(gvOS.toString());
		}
	}

}
