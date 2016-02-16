package ragdoll.app.phase;

import ragdoll.app.pattern.SingletonPattern;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.framework.IPhase;

public class SingletonPatternDetectionPhase implements IPhase {
	private PatternInfo patternInfo;

	public SingletonPatternDetectionPhase() {
		this.patternInfo = PatternInfo.getInstance();
	}

	@Override
	public void execute() {
		IClassInfo classInfo = ClassInfo.getInstance();
		APatternDetector singletonPattern = new SingletonPattern(classInfo);
		singletonPattern.detectPattern();
		patternInfo.storePatternInfo("singleton", singletonPattern.getPatterns());
	}

}
