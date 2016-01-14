package ragdoll.code.uml.api;

import java.util.List;

public interface IMethod extends IClassComponent {
	public String getMethodName();

	public String getAccessLevel();

	public String getReturnType();

	public List<String> getParamTypes();

	public List<String> getExceptions();
}
