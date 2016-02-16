package ragdoll.app.pattern;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.api.IMethodCall;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;
import ragdoll.framework.RagdollProperties;

public class AdapterPattern extends APatternDetector {
	private RagdollProperties properties;

	public AdapterPattern(IClassInfo classInfo) {
		super(classInfo);
		properties = RagdollProperties.getInstance();
	}

	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			if (!classInfo.isAbstract(className) || !classInfo.isInterface(className)) {
				List<Pattern> patterns = getAdapterPatterns(className);
				for (Pattern pattern : patterns) {
					addPattern(pattern);
				}
			}
		}
	}

	public List<Pattern> getAdapterPatterns(String className) {
		List<String> implementedIterfaces = classInfo.getImplementedInterfaces(className);
		List<String> aggregatedClasses = classInfo.getAggregatedClasses(className);

		List<Pattern> results = new ArrayList<>();

		if (implementedIterfaces != null) {
			for (String implementedInterface : implementedIterfaces) {
				for (String aggregatedClass : aggregatedClasses) {
					if (!classInfo.getClasses().containsKey(implementedInterface)
							|| !classInfo.getClasses().containsKey(aggregatedClass)) {
						continue;
					}
					List<IMethod> overriddenMethods = classInfo.getOverriddenMethods(className, implementedInterface);
					int implementedCount = 0;
					for (IMethod overriddenMathod : overriddenMethods) {
						List<IMethodCall> callees = overriddenMathod.getCallees();
						for (IMethodCall callee : callees) {
							String calleeType = callee.getClassName();
							if (calleeType.equals(aggregatedClass)) {
								implementedCount++;
								break;
							}
						}
					}
					int adapterMethodThreshold = Integer.valueOf(properties.getProperty("Adapter-MethodDelegationThreshold", "2"));
					if (implementedCount >= adapterMethodThreshold) {
						Pattern pattern = new Pattern(className);
						pattern.addRole(implementedInterface, "Target");
						pattern.addRole(aggregatedClass, "Adaptee");
						pattern.addRole(className, "Adapter");
						pattern.addRelation(className, aggregatedClass, "adapts");
						results.add(pattern);
					}
				}
			}
		}
		return results;
	}
}
