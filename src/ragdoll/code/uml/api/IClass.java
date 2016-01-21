package ragdoll.code.uml.api;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ragdoll.code.visitor.api.ITraverser;

public interface IClass extends ITraverser {
	public void addMethod(IMethod method);

	public void addField(IField field);
	
	public void addUse(String className); // M2

	public void setDeclaration(IClassDeclaration declaration);

	public IClassDeclaration getDeclaration();

	public List<IMethod> getMethodList();

	public HashMap<String, IField> getFieldMap();
	
	public Set<String> getUseSet(); //M2
	
	public Set<String> getAssociationType(); // M2
	
	public void filterUseSet(); // M2
	
	public void filterTypeSet(); // M2

	public String getName();
	
	public void setHasGetInstanceMethod(boolean hasGetInstanceMethod);

	public void updateIsSingleton();
}
