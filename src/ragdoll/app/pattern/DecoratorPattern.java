package ragdoll.app.pattern;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;
import ragdoll.framework.RagdollProperties;

public class DecoratorPattern extends APatternDetector {
	private RagdollProperties properties;

	public DecoratorPattern(IClassInfo classInfo) {
		super(classInfo);
		properties = RagdollProperties.getInstance();
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
//		if (className.equals("java.io.FilterInputStream")) {
//			System.out.println("HIT");
//		}
		List<Pattern> results = new ArrayList<>();
		List<String> components = getComponentList(className);
		for (String component : components) {
			int unDecorationCount = 0;
			List<IMethod> overriddenMethods = classInfo.getOverriddenMethods(className, component);
			for(IMethod overriddenMethod : overriddenMethods){
				if (!overriddenMethod.hasSameNameMethodCall()) {
					unDecorationCount++;
				}
			}
			int undecoratedMethodThreshold = Integer.valueOf(properties.getProperty("Decorator-MethodNotDelegatedThreshold", "1"));
			if (unDecorationCount <= undecoratedMethodThreshold) {
				Pattern pattern = new Pattern(className);
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
