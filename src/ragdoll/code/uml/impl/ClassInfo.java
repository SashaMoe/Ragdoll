package ragdoll.code.uml.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.api.IMethodCall;

public class ClassInfo implements IClassInfo {
	private Map<String, IClass> classes;
	private volatile static ClassInfo instance;

	private ClassInfo() {
		this.classes = new HashMap<>();
	}

	public static ClassInfo getInstance() {
		if (instance == null) {
			synchronized (ClassInfo.class) {
				if (instance == null) {
					instance = new ClassInfo();
				}
			}
		}
		return instance;
	}

	public Map<String, IClass> getClasses() {
		return classes;
	}

	public void setClasses(Map<String, IClass> iClasses) {
		classes = iClasses;
	}

	//NOTE: lots of following methods will through null pointer execptions if claases does not have claaName;
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

	public Set<String> getCompositedClassSet(String className) {
		return classes.get(className).getCompositedClassSet();
	}

	public Map<String, IField> getFieldsByClass(String className) {
		return classes.get(className).getFieldMap();
	}

	public List<IMethod> getMethodsByClass(String className) {
		return classes.get(className).getMethodList();
	}

	public List<IMethodCall> getMethodCallsByClassAndMethod(String className, String methodName,
			List<String> paramTypes) {
		IMethod stubMethod = new Method(methodName, "meow", "meow", paramTypes, new ArrayList<>());
		if (classes.containsKey(className)) {
			for (IMethod method : classes.get(className).getMethodList()) {
				if (method.equals(stubMethod)) {
					return method.getCallees();
				}
			}
		}
		return new ArrayList<>();
	}

	public List<IClass> getClassesByPackage(String packageName) {
		List<IClass> aPackage = new ArrayList<>();
		for (String className : classes.keySet()) {
			if (className.startsWith(packageName)) {
				aPackage.add(classes.get(className));
			}
		}
		return aPackage;
	}

	public List<IMethod> getConstructors(String className) {
		List<IMethod> constructors = new ArrayList<>();
		if (classes.containsKey(className)) {
			for (IMethod method : classes.get(className).getMethodList()) {
				if (method.getMethodName().equals("<init>")) {
					constructors.add(method);
				}
			}
		}
		return constructors;
	}

	@Override
	public IClass getClassByName(String className) {
		return classes.get(className);
	}
}
