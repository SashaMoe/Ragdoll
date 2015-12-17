package ragdoll.code.api;

import java.util.List;

public interface IClassDeclaration extends IClassComponent {
	public boolean isAbstract();

	public boolean isInterface();

	public String getClassName();

	public String getNameOfSuperClass();

	public List<String> getNameOfInterfaces();
}
