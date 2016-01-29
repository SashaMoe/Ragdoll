package ragdoll.code.uml.pattern;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ragdoll.app.pattern.IFormatConsumer;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IMethod;

public class PatternController implements IClassInfo {
	private Map<String, IClass> classes;
	private Map<String, Class<? extends APatternDetector>> patternDetectorClasses;
	private Map<String, APatternDetector> patternDetectors;
	private Map<String, List<Pattern>> patternMap;
	private List<IFormatConsumer> consumers;

	public PatternController() {
		this.classes = new HashMap<>();
		this.patternDetectors = new HashMap<>();
		this.patternDetectorClasses = new HashMap<>();
		this.patternMap = new HashMap<>();
		this.consumers = new ArrayList<>();
	}

	public void registerPatternDetector(String patternType, APatternDetector pattern) {
		this.patternDetectors.put(patternType, pattern);
	}

	public void registerFormatConsumer(IFormatConsumer consumer) {
		this.consumers.add(consumer);
	}

	public void detectAllPatterns() {
		for (String patternType : patternDetectorClasses.keySet()) {
			Class<? extends APatternDetector> patternDetector = patternDetectorClasses.get(patternType);
			Constructor<?> ctor = patternDetector.getConstructors()[0];
			try {
				APatternDetector tempDetector = (APatternDetector) ctor.newInstance(this);
				patternDetectors.put(patternType, tempDetector);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String patternType : patternDetectors.keySet()) {
			APatternDetector patternDetector = patternDetectors.get(patternType);
			patternMap.put(patternType, patternDetector.getPatterns());
		}
		for (IFormatConsumer consumer : consumers) {
			consumer.parse(patternMap);
		}
	}

	public Map<String, IClass> getClasses() {
		return classes;
	}

	public void setClasses(Map<String, IClass> iClasses) {
		classes = iClasses;
	}

	public boolean checkHasPrivateConstructor(String className) {
		return classes.get(className).checkHasPrivateConstructor();
	}

	public boolean checkHasPrivateFiledOfItself(String className) {
		return classes.get(className).checkHasPrivateFiledOfItself();
	}

	public boolean hasLazyGetInstanceMethod(String className) {
		return classes.get(className).hasLazyGetInstanceMethod();
	}

	public boolean hasGetInstanceMethod(String className) {
		return classes.get(className).hasGetInstanceMethod();
	}

	public boolean hasEagerInit(String className) {
		return classes.get(className).hasEagerInit();
	}

	public boolean isAbstract(String className) {
		return classes.get(className).getDeclaration().isAbstract();
	}

	public boolean isInterface(String className) {
		return classes.get(className).getDeclaration().isInterface();
	}

	public List<String> getImplementedInterfaces(String className) {
		return classes.get(className).getDeclaration().getNameOfInterfaces();
	}

	public List<String> getAggregatedClasses(String className) {
		return classes.get(className).getAggregatedClasses();
	}

	public List<IMethod> getOverriddenMethods(String className, String superName) {
		if ((classes.get(className).getDeclaration().getNameOfSuperClass().equals(superName)
				|| classes.get(className).getDeclaration().getNameOfInterfaces().contains(superName))
				&& classes.containsKey(superName)) {
			IClass superClass = classes.get(superName);
			return classes.get(className).getOverriddenMethods(superClass);
		}
		return new ArrayList<>();
	}

	public String getSuperClass(String className) {
		return classes.get(className).getDeclaration().getNameOfSuperClass();
	}

	public List<String> getInheritedAncestors(String className) {
		List<String> ancestors = new ArrayList<>();
		ancestors.addAll(getInheritedAncestorsHelper(className));
		ancestors.remove(className);
		return ancestors;
	}
	
	private List<String> getInheritedAncestorsHelper(String className) {
		if (!classes.containsKey(className)) {
			return new ArrayList<>();
		}
		List<String> ancestors = new ArrayList<>();
		ancestors.add(className);
		ancestors.addAll(getInheritedAncestorsHelper(getSuperClass(className)));
		for (String itf : getImplementedInterfaces(className)) {
			ancestors.addAll(getInheritedAncestorsHelper(itf));
		}
		return ancestors;
	}
	
	public List<String> getSubclasses(String className) {
		return classes.get(className).getSubClasses();
	}
	
	public List<String> getChildren(String className) {
		if (!classes.containsKey(className)) {
			return new ArrayList<>();
		}
		List<String> children = new ArrayList<>();
		for (String subclass : getSubclasses(className)) {
			children.add(subclass);
			children.addAll(getChildren(subclass));
		}
		return children;
	}
	
	public List<String> getClassFromConstructorParameters(String className) {
		return classes.get(className).getClassFromConstructorParameters();
	}
}
