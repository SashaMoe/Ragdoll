package ragdoll.app.pattern;

import ragdoll.code.uml.pattern.IClassInfo;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;

public class DecoratorPattern extends APatternDetector {

	public DecoratorPattern(IClassInfo classInfo) {
		super(classInfo);
		detectPattern();
	}

	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			if (!classInfo.isInterface(className)) {
				List<Pattern> patterns = getDecoratorPatterns(className);
				for (Pattern pattern : patterns) {
					addPattern(pattern);
				}
			}
		}
	}

	public List<Pattern> getDecoratorPatterns(String className) {
		if (className.equals("ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream")) {
//			System.out.println("HIT");
		}
		List<Pattern> results = new ArrayList<>();
		List<String> components = getComponentList(className);
		for (String component : components) {
			boolean isDecorated = true;
			List<IMethod> overriddenMethods = classInfo.getOverriddenMethods(className, component);
			for(IMethod overriddenMethod : overriddenMethods){
				isDecorated = !overriddenMethod.hasSameNameMethodCall();
			}
			if (isDecorated) {
				Pattern pattern = new Pattern();
				pattern.addRole(component, "Target");
				pattern.addRole(className, "Decorator");
				List<String> subClassNames = classInfo.getSubclasses(className);
				for(String subClassName : subClassNames){
					pattern.addRole(subClassName, "Decorator");
				}
				pattern.addRelation(className, component, "decorate");
				results.add(pattern);
			}
		}
		return results;
	}

	private List<String> getComponentList(String className) {
		List<String> components = new ArrayList<>();
		List<String> inheritedClasses = classInfo.getInheritedAncestors(className);
		List<String> paramFromConstructors = classInfo.getClassFromConstructorParameters(className);
		
		for (String param : paramFromConstructors) {
			if (inheritedClasses.contains(param)) {
				components.add(param);
			}
		}	
		return components;
	}
}
