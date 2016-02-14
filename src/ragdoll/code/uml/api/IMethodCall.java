package ragdoll.code.uml.api;

import java.util.List;

public interface IMethodCall {

	public String getClassName();

	public void setClassName(String className);

	public String getMethodName();

	public void setMethodName(String methodName);

	public List<String> getParamTypes();

	public void addParamTypes(String paramType);

}
