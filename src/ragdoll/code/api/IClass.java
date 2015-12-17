package ragdoll.code.api;

import ragdoll.code.visitor.api.ITraverser;

public interface IClass extends ITraverser {
	public void addMethod(IMethod method);

	public void addField(IField field);

	public void setDeclaration(IClassDeclaration declaration);

	public IClassDeclaration getDeclaration();

	public String getName();
}
