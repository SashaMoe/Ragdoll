package ragdoll.code.uml.api;

import java.util.List;

public interface IMethod extends IClassComponent {
	public void addCallees(IMethodCall callee);

	public List<IMethodCall> getCallees();

	public String getMethodName();

	public String getAccessLevel();

	public String getReturnType();

	public List<String> getParamTypes();

	public List<String> getExceptions();

	public boolean compareToMethod(IMethod method);
}
