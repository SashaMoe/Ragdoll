package ragdoll.code.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.visitor.api.IVisitor;

public class Klass implements IClass {
	private String name;
	private List<IMethod> methodList;
	private HashMap<String, IField> fieldMap;
	private IClassDeclaration declaration;

	public Klass(String name) {
		super();
		this.name = name;
		this.methodList = new ArrayList<>();
		this.fieldMap = new HashMap<>();
	}

	public void addMethod(IMethod method) {
		this.methodList.add(method);
	}

	public void addField(IField field) {
		this.fieldMap.put(field.getFieldName(), field);
	}

	public void setDeclaration(IClassDeclaration declaration) {
		this.declaration = declaration;
	}

	public IClassDeclaration getDeclaration() {
		return declaration;
	}

	public List<IMethod> getMethodList() {
		return methodList;
	}

	public HashMap<String, IField> getFieldMap() {
		return fieldMap;
	}

	public void accept(IVisitor v) {
		
		v.visit(this);
		declaration.accept(v);
		for (String fieldName : this.fieldMap.keySet()) {
			IField f = this.fieldMap.get(fieldName);
			f.accept(v);
		}
		v.visit("|");
		for (IMethod m : this.methodList){
			m.accept(v);
		}
		v.postVisit(this);
	}

	public String getName() {
		return this.name;
	}
}
