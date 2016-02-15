package ragdoll.app.phase;

import ragdoll.app.pattern.DecoratorPattern;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.framework.IPhase;

public class DecoratorPatternDetectionPhase implements IPhase {
	private PatternInfo patternInfo;

	public DecoratorPatternDetectionPhase(PatternInfo patternInfo) {
		this.patternInfo = patternInfo;
	}

	@Override
	public void execute() {
		IClassInfo classInfo = ClassInfo.getInstance();
		APatternDetector decoratorPattern = new DecoratorPattern(classInfo);
		decoratorPattern.detectPattern();
		patternInfo.storePatternInfo("decorator", decoratorPattern.getPatterns());
	}

}
