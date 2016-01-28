package ragdoll.app.pattern;

import ragdoll.code.uml.pattern.IClassInfo;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;

public class SingletonPattern extends APatternDetector {

	public SingletonPattern(IClassInfo classInfo) {
		super(classInfo);
	}

	@Override
	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			if (isSingleton(className)) {
				Pattern pattern = new Pattern();
				pattern.addRole(className, "singleton");
				patterns.add(pattern);
			}
		}
	}
	
	private boolean isSingleton(String className) {
		return !classInfo.isAbstract(className) && !classInfo.isInterface(className)
				&& classInfo.checkHasPrivateConstructor(className) && classInfo.checkHasPrivateFiledOfItself(className)
				&& classInfo.hasGetInstanceMethod(className)
				&& (classInfo.hasLazyGetInstanceMethod(className) || classInfo.hasEagerInit(className));
	}

}
