package ragdoll.code.uml.impl;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.api.IMethodCall;
import ragdoll.code.visitor.api.IUMLVisitor;
import ragdoll.code.visitor.api.IVisitor;

public class Method implements IMethod {

	private String methodName;
	private String accessLevel;
	private String returnType;
	private List<String> paramTypes;
	private List<String> exceptions;
	private List<IMethodCall> callees;

	public Method(String methodName, String accessLevel, String returnType, List<String> paramTypes,
			List<String> exceptions) {
		super();
		this.methodName = methodName;
		this.accessLevel = accessLevel;
		this.returnType = returnType;
		this.paramTypes = paramTypes;
		this.exceptions = exceptions;
		this.callees = new ArrayList<>();
	}

	public void addCallees(IMethodCall callee) {
		this.callees.add(callee);
	}

	public List<IMethodCall> getCallees() {
		return callees;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public String getReturnType() {
		return returnType;
	}

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void accept(IVisitor v) {
		((IUMLVisitor) v).visit(this);
	}

	public boolean compareToMethod(IMethod method) {
		List<String> comparedParamTypes = method.getParamTypes();
		if (paramTypes.size() == comparedParamTypes.size()) {
			for (int i = 0; i < paramTypes.size(); i++) {
				if (!paramTypes.get(i).equals(comparedParamTypes.get(i))) {
					return false;
				}
			}
		}
		return methodName.equals(method.getMethodName());
	}

	public boolean hasSameNameMethodCall() {
		for (IMethodCall callee : callees) {
			if (callee.getMethodName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Method) {
			Method comparedMethod = (Method) o;
			return compareToMethod(comparedMethod);
		}
		return false;
	}

}
