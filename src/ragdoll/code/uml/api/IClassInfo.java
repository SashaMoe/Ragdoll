package ragdoll.code.uml.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IClassInfo {
	/**
	 * Gets all the children (descendants) of the given class
	 * 
	 * @param className
	 * @return A list of class names.
	 */
	public List<String> getChildren(String className);// TODO: Check me!

	/**
	 * Gets the "composited" classes of the given class.
	 * 
	 * By "composited", it means the classes that are used as inner types
	 * of a field.
	 * 
	 * For instance, say class A has two fields:
	 *   private List\<B\> listOfB;
	 *   private E\<C, List\<D\>\> mapOfCToDs;
	 * Then B and C and D are all "composited" class of A.
	 * 
	 * @param className
	 * @return A list of class names.
	 */
	public Set<String> getCompositedClassSet(String className);

	/**
	 * Checks if the class has a private constructor.
	 * 
	 * @param className
	 * @return
	 */
	public boolean checkHasPrivateConstructor(String className);

	/**
	 * Checks if the class has any private field of itself.
	 * 
	 * @param className
	 * @return
	 */
	public boolean checkHasPrivateFiledOfItself(String className);

	/**
	 * Checks if the class has a method that lazily creates or gets
	 * a instance of the class itself.
	 * 
	 * @param className
	 * @return
	 */
	public boolean hasLazyGetInstanceMethod(String className);

	/**
	 * Checks if the class has a method that creates or gets
	 * a instance of the class itself.
	 * 
	 * NOTE: This doesn't necessarily require a method
	 *       named getInstance()!
	 * 
	 * @param className
	 * @return
	 */
	public boolean hasGetInstanceMethod(String className);

	/**
	 * Checks if the class initialize itself during the class loads.
	 * i.e. in \<clinit\>, the class itself gets initialized.
	 * 
	 * @param className
	 * @return
	 */
	public boolean hasEagerInit(String className);

	/**
	 * Sets the classes in the scope
	 * 
	 * @param iClasses
	 */
	public void setClasses(Map<String, IClass> iClasses);

	/**
	 * Gets all the classes.
	 * 
	 * @return
	 */
	public Map<String, IClass> getClasses();

	/**
	 * Checks if a class is abstract.
	 * 
	 * @param className
	 * @return
	 */
	public boolean isAbstract(String className);

	/**
	 * Checks if a class is an interface.
	 * 
	 * @param className
	 * @return
	 */
	public boolean isInterface(String className);

	/**
	 * Returns a class by class name.
	 * 
	 * @param className
	 * @return
	 */
	public IClass getClassByName(String className);
	
	/**
	 * Gets all directly implemented interfaces of a class.
	 * 
	 * @param className
	 * @return A list of class names.
	 */
	public List<String> getImplementedInterfaces(String className);

	/**
	 * Gets all the classes that are aggregated by the given class.
	 * i.e. They are passed in as parameters to the constructor of given class,
	 *      AND is stored as a field in the given class.
	 * 
	 * @param className
	 * @return A list of class names.
	 */
	public List<String> getAggregatedClasses(String className);

	/**
	 * Gets all the given super class's methods
	 * that are overridden by the given class.
	 * 
	 * @param className
	 * @param superName
	 * @return 
	 */
	public List<IMethod> getOverriddenMethods(String className, String superName);

	/**
	 * Gets the direct super class of given class. 
	 * 
	 * @param className
	 * @return The class name.
	 */
	public String getSuperClass(String className);

	/**
	 * Gets all the ancestors of the given class,
	 * including interfaces, abstract classes, and concrete classes.
	 * 
	 * @param className
	 * @returnA list of class names.
	 */
	public List<String> getInheritedAncestors(String className);// TODO: Check
																// me!

	/**
	 * Gets all the classes that directly implements or extends the given class.
	 * 
	 * @param className
	 * @return A list of class names.
	 */
	public List<String> getSubclasses(String className);// TODO: Check me!

	/**
	 * Gets all classes that are passed in
	 * as parameters to the constructor of given class,
	 * 
	 * @param className
	 * @return A list of class names.
	 */
	public List<String> getClassFromConstructorParameters(String className);

	/**
	 * Gets all fields of the given class.
	 * 
	 * @param className
	 * @return
	 */
	public Map<String, IField> getFieldsByClass(String className);

	/**
	 * Gets all methods of the given class.
	 * 
	 * @param className
	 * @return
	 */
	public List<IMethod> getMethodsByClass(String className);

	/**
	 * Gets all method calls that appears in a given method from a given class.
	 * 
	 * @param className
	 * @param methodName The method name from the given class.
	 * @param paramTypes The parameters to identify the method in case of method overload.
	 * @return
	 */
	public List<IMethodCall> getMethodCallsByClassAndMethod(String className, String methodName,
			List<String> paramTypes);// Maybe Fix later

	/**
	 * Gets all the classes in a given package.
	 * 
	 * @param packageName
	 * @return
	 */
	public List<IClass> getClassesByPackage(String packageName);

	/**
	 * Gets all constructor methods in a given class.
	 * 
	 * @param className
	 * @return
	 */
	public List<IMethod> getConstructors(String className);
}