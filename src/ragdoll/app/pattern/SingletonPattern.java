package ragdoll.app.pattern;

import java.util.List;

import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;
import ragdoll.framework.RagdollProperties;

public class SingletonPattern extends APatternDetector {
	private RagdollProperties properties;
	
	public SingletonPattern(IClassInfo classInfo) {
		super(classInfo);
		properties = RagdollProperties.getInstance();
	}

	@Override
	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			if (isSingleton(className)) {
				Pattern pattern = new Pattern(className);
				pattern.addRole(className, "singleton");
				addPattern(pattern);
			}
		}
	}
	
	private boolean isSingleton(String className) {
		boolean requireGetInstanceMethod = Boolean.valueOf(properties.getProperty("Singleton-RequireGetInstance", "false"));
		if (requireGetInstanceMethod) {
			List<IMethod> methods = classInfo.getMethodsByClass(className);
			boolean hasGetInstanceMethod = false;
			for (IMethod method : methods) {
				if (method.getMethodName().equals("getInstance")) {
					hasGetInstanceMethod = true;
					break;
				}
			}
			if (!hasGetInstanceMethod) {
				return false;
			}
		}
		
		return !classInfo.isAbstract(className) && !classInfo.isInterface(className)
				&& classInfo.checkHasPrivateConstructor(className) && classInfo.checkHasPrivateFiledOfItself(className)
				&& classInfo.hasGetInstanceMethod(className)
				&& (classInfo.hasLazyGetInstanceMethod(className) || classInfo.hasEagerInit(className));
	}

}
