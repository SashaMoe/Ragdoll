package ragdoll.code.uml.pattern;

import java.util.List;
import java.util.Map;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IMethod;

public interface IClassInfo {
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
}