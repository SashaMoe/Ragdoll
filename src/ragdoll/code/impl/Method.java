package ragdoll.code.impl;

import java.util.List;

import ragdoll.code.api.IMethod;
import ragdoll.code.visitor.api.IVisitor;

public class Method implements IMethod {

	private String methodName;
	private String accessLevel;
	private String returnType;
	private List<String> paramTypes;
	private List<String> exceptions;
	
	public Method(String methodName, String accessLevel, String returnType, List<String> paramTypes,
			List<String> exceptions) {
		super();
		this.methodName = methodName;
		this.accessLevel = accessLevel;
		this.returnType = returnType;
		this.paramTypes = paramTypes;
		this.exceptions = exceptions;
	}

	public void accept(IVisitor v) {
		// TODO Auto-generated method stub

	}

}
