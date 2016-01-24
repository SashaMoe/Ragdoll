package ragdoll.code.uml.pattern;

import java.util.HashMap;
import java.util.Map;

import ragdoll.code.uml.api.IClass;

public class PatternDetector implements IClassInfo {
	private Map<String, IClass> classes;
	private Map<String, IPattern> patterns;
	private volatile static PatternDetector instance;

	private PatternDetector() {
		this.classes = new HashMap<>();
		this.patterns = new HashMap<>();
	}

	public static PatternDetector getInstance() {
		if (instance == null) {
			synchronized (PatternDetector.class) {
				if (instance == null) {
					instance = new PatternDetector();
				}
			}
		}
		return instance;
	}
	
	public void addPattern(String name, IPattern pattern){
		this.patterns.put(name, pattern);
	}

	public void detectAllPatterns(){
		for(String key: this.patterns.keySet()){
			this.patterns.get(key).detectPattern();
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

}
