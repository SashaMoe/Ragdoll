package ragdoll.code.visitor.api;

import java.util.List;

import ragdoll.code.sd.impl.INode;

public interface ISDVisitor extends IVisitor {
	void visit(List<String> classes);
	void visit(INode node);
}
