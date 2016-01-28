package ragdoll.code.uml.impl;

import ragdoll.code.uml.api.IMethodCall;

public class MethodCall implements IMethodCall {
	private String className;
	private String methodName;
	
	public MethodCall(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}