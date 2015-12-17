package ragdoll.code.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassComponent;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.visitor.api.ITraverser;
import ragdoll.code.visitor.api.IVisitor;

public class Class implements IClass {
	private String name;
    private List<IMethod> methodList;
    private HashMap<String, IField> fieldMap;
	private IClassDeclaration declaration;
    
	public Class(String name) {
		super();
		this.name = name;
	}

	public void setMethodList(List<IMethod> methodList) {
		this.methodList = methodList;
	}

	public void setFieldMap(HashMap<String, IField> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public void setDeclaration(IClassDeclaration declaration) {
		this.declaration = declaration;
	}

	public void accept(IVisitor v) {
		
	}
	
	public String getName(){
		return this.name;
	}
}
