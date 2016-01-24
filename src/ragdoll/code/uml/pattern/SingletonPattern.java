package ragdoll.code.uml.pattern;

import java.util.HashSet;
import java.util.Set;

public class SingletonPattern implements IPattern {
	private IClassInfo classInfo;
	private Set<String> singletonSet;

	public SingletonPattern(IClassInfo classInfo) {
		this.classInfo = classInfo;
		this.singletonSet = new HashSet<>();
	}

	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			if (isSingleton(className)) {
				singletonSet.add(className);
			}
		}
	}

	private boolean isSingleton(String className) {
		return !classInfo.isAbstract(className) && !classInfo.isInterface(className)
				&& classInfo.checkHasPrivateConstructor(className) && classInfo.checkHasPrivateFiledOfItself(className)
				&& classInfo.hasGetInstanceMethod(className)
				&& (classInfo.hasLazyGetInstanceMethod(className) || classInfo.hasEagerInit(className));
	}

	public void getClassNodeAttribute(String className, NodeAttrinute nodeAttrinute) {
		boolean isSingleton = singletonSet.contains(className);
		if(isSingleton){
			nodeAttrinute.setBorderColor("blue");
			nodeAttrinute.setPatternName("«singleton»");
		}
	}

}
