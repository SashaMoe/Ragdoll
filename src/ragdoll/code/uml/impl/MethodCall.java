package ragdoll.code.uml.impl;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.uml.api.IMethodCall;

public class MethodCall implements IMethodCall {
	private String className;
	private String methodName;
	private List<String> paramTypes;

	public MethodCall(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
		this.paramTypes = new ArrayList<>();
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

	public List<String> getParamTypes() {
		return paramTypes;
	}

	public void addParamTypes(String paramType) {
		this.paramTypes.add(paramType);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MethodCall) {
			MethodCall comparedMethodCall = (MethodCall)o;
			List<String> comparedParamTypes = comparedMethodCall.getParamTypes();
			if (paramTypes.size() == comparedParamTypes.size()) {
				for (int i = 0; i < paramTypes.size(); i++) {
					if (!paramTypes.get(i).equals(comparedParamTypes.get(i))) {
						return false;
					}
				}
			}
			return methodName.equals(comparedMethodCall.getMethodName()) && className.equals(comparedMethodCall.getClassName());
		}
		return false;
	}
}
