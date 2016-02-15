package ragdoll.app.phase;

import ragdoll.app.pattern.AdapterPattern;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.framework.IPhase;

public class AdapterPatternDetectionPhase implements IPhase {
	private PatternInfo patternInfo;

	public AdapterPatternDetectionPhase(PatternInfo patternInfo) {
		this.patternInfo = patternInfo;
	}
	
	public void execute() {
		IClassInfo classInfo = ClassInfo.getInstance();
		APatternDetector adapterPattern = new AdapterPattern(classInfo);
		adapterPattern.detectPattern();
		patternInfo.storePatternInfo("adapter", adapterPattern.getPatterns());
	}

}
