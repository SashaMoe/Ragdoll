package ragdoll.code.uml.pattern;

import java.util.Map;

import ragdoll.code.uml.api.IClass;

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


}
