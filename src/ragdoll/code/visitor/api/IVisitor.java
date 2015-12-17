package ragdoll.code.visitor.api;

import java.util.HashMap;
import java.util.List;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;

public interface IVisitor {
	void visit(IClass c);
	public void postVisit(IClass c);
	public void visit(HashMap<String, IField> fs);
	void visit(List<IMethod> ms);
	void visit(IClassDeclaration cd);
}
