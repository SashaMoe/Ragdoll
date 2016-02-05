package ragdoll.code.uml.pattern;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IMethod;

public interface IClassInfo {
	public List<String> getChildren(String className);

	public Set<String> getCompositedClassSet(String className);

	public boolean checkHasPrivateConstructor(String className);

	public boolean checkHasPrivateFiledOfItself(String className);

	public boolean hasLazyGetInstanceMethod(String className);

	public boolean hasGetInstanceMethod(String className);

	public boolean hasEagerInit(String className);

	public void setClasses(Map<String, IClass> iClasses);

	public Map<String, IClass> getClasses();

	public boolean isAbstract(String className);

	public boolean isInterface(String className);

	public List<String> getImplementedInterfaces(String className);

	public List<String> getAggregatedClasses(String className);

	public List<IMethod> getOverriddenMethods(String className, String superName);

	public String getSuperClass(String className);

	public List<String> getInheritedAncestors(String className);

	public List<String> getSubclasses(String className);

	public List<String> getClassFromConstructorParameters(String className);
}