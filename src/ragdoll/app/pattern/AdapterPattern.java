package ragdoll.app.pattern;

import ragdoll.code.uml.pattern.IClassInfo;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.api.IMethodCall;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;

public class AdapterPattern extends APatternDetector {

	private static final int ADAPTER_METHOD_THRESHOLD = 1;

	public AdapterPattern(IClassInfo classInfo) {
		super(classInfo);
		detectPattern();
	}

	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			List<Pattern> patterns = getAdapterPatterns(className);
			for (Pattern pattern : patterns) {
				addPattern(pattern);
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
					if (implementedCount >= ADAPTER_METHOD_THRESHOLD) {
						Pattern pattern = new Pattern();
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
