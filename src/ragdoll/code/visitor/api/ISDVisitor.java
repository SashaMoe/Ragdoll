package ragdoll.code.visitor.api;

import java.util.List;
import java.util.Set;

import ragdoll.code.sd.impl.INode;

public interface ISDVisitor extends IVisitor {
	void visit(List<String> classes, Set<String> createdClasses);
	void visit(INode node);
}
