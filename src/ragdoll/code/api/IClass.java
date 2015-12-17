package ragdoll.code.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ragdoll.code.visitor.api.ITraverser;
import ragdoll.code.visitor.api.IVisitor;

public interface IClass extends ITraverser {
	public void addMethod(IMethod method);

	public void addField(IField field);

	public void setDeclaration(IClassDeclaration declaration);

	public String getName();
}
