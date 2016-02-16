package ragdoll.app.phase;

import ragdoll.app.pattern.CompositePattern;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.framework.IPhase;

public class CompositePatternDetectionPhase implements IPhase {
	private PatternInfo patternInfo;

	public CompositePatternDetectionPhase() {
		this.patternInfo = PatternInfo.getInstance();
	}

	@Override
	public void execute() {
		IClassInfo classInfo = ClassInfo.getInstance();
		APatternDetector compositePattern = new CompositePattern(classInfo);
		compositePattern.detectPattern();
		patternInfo.storePatternInfo("composite", compositePattern.getPatterns());
	}

}
