package ragdoll.code.uml.impl;

import java.util.List;

import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.visitor.api.IUMLVisitor;
import ragdoll.code.visitor.api.IVisitor;

public class ClassDeclaration implements IClassDeclaration {

	private boolean isAbstract;
	private boolean isInterface;
	private String className;
	private String nameOfSuperClass;
	private List<String> nameOfInterfaces;

	public ClassDeclaration(boolean isAbstract, boolean isInterface, String className, String nameOfSuperClass,
			List<String> nameOfInterfaces) {
		super();
		this.isAbstract = isAbstract;
		this.isInterface = isInterface;
		this.className = className;
		this.nameOfSuperClass = nameOfSuperClass;
		this.nameOfInterfaces = nameOfInterfaces;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public String getClassName() {
		return className;
	}

	public String getNameOfSuperClass() {
		return nameOfSuperClass;
	}

	public List<String> getNameOfInterfaces() {
		return nameOfInterfaces;
	}

	public void accept(IVisitor v) {
		((IUMLVisitor) v).visit(this);
	}

}
