package ragdoll.app.pattern;

import ragdoll.code.uml.pattern.IClassInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.Pattern;

public class CompositePattern extends APatternDetector {
	
	
	private Map<String, Map<String, Set<String>>> componentMap;
	private static final String COMPOSITE_KEY = "composite";
	private static final String LEAF_KEY = "leaf";
	private static final String CAST_COMPONENT = "cast-component";

	public CompositePattern(IClassInfo classInfo) {
		super(classInfo);
		this.componentMap = new HashMap<>();
		detectPattern();
	}

	@Override
	public void detectPattern() {
		for (String className : classInfo.getClasses().keySet()) {
			List<String> inheritedClases = classInfo.getInheritedAncestors(className);
			for (String superClass : inheritedClases) {
				if (isComposite(className, superClass)) {
					if (componentMap.containsKey(superClass)) {
						addAsRole(superClass, className, COMPOSITE_KEY);
					} else {
						addComponent(superClass);
						addAsRole(superClass, className, COMPOSITE_KEY);
					}
				} else {
					if (componentMap.containsKey(superClass)) {
						addAsRole(superClass, className, LEAF_KEY);
					} else {
						addComponent(superClass);
						addAsRole(superClass, className, LEAF_KEY);
					}
				}
			}
		}

		for (String componentClass : componentMap.keySet()) {
			Map<String, Set<String>> roleMap = componentMap.get(componentClass);
			Set<String> compositeSet = roleMap.get(COMPOSITE_KEY);
			Set<String> leafSet = roleMap.get(LEAF_KEY);
			Set<String> castComponentSet = roleMap.get(CAST_COMPONENT);
			if (!compositeSet.isEmpty()) {
				Set<String> leafToRemove = new HashSet<>();
				for (String leafClass : leafSet) {
					boolean done = false;
					
					List<String> ancesters = classInfo.getInheritedAncestors(leafClass);
					for (String ancester : ancesters) {
						if (compositeSet.contains(ancester)) {
							compositeSet.add(leafClass);
							leafToRemove.add(leafClass);
							done = true;
							break;
						}
					}
					
					if (done) {
						continue;
					}
					
					List<String> children = classInfo.getChildren(leafClass);
					for (String childClass : children) {
						if (compositeSet.contains(childClass)) {
							castComponentSet.add(leafClass);
							leafToRemove.add(leafClass);
							break;
						}
					}
				}
				
				for (String toRemove : leafToRemove) {
					leafSet.remove(toRemove);
				}

				Pattern pattern = new Pattern();
				pattern.addRole(componentClass, "component");
				for (String castComponentClass : castComponentSet) {
					pattern.addRole(castComponentClass, "component");
				}
				for (String compositeClass : compositeSet) {
					pattern.addRole(compositeClass, "composite");
				}
				for (String leafClass : leafSet) {
					pattern.addRole(leafClass, "leaf");
				}
				addPattern(pattern);
			}
		}
	}

	private void addAsRole(String superClass, String className, String role) {
		Map<String, Set<String>> roleMap = componentMap.get(superClass);
		Set<String> roleSet = roleMap.get(role);
		roleSet.add(className);
		roleMap.put(role, roleSet);
		componentMap.put(superClass, roleMap);
	}

	private void addComponent(String superClass) {
		Map<String, Set<String>> roleMap = new HashMap<>();
		roleMap.put(COMPOSITE_KEY, new HashSet<String>());
		roleMap.put(LEAF_KEY, new HashSet<String>());
		roleMap.put(CAST_COMPONENT, new HashSet<String>());
		componentMap.put(superClass, roleMap);
	}

	private boolean isComposite(String className, String superClass) {
		return classInfo.getCompositedClassSet(className).contains(superClass);
	}

}
