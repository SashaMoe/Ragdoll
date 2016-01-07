package ragdoll.code.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.visitor.api.IVisitor;
import ragdoll.util.Utilities;

public class Klass implements IClass {
	private Map<String, IClass> iClasses;
	private String name;
	private List<IMethod> methodList;
	private Set<String> useSet;
	private HashMap<String, IField> fieldMap;
	private IClassDeclaration declaration;
	private Set<String> associationFieldSet;
	private Set<String> associationTypeSet;

	public Klass(String name, Map<String, IClass> iClasses) {
		super();
		this.name = name;
		this.iClasses = iClasses;
		this.methodList = new ArrayList<>();
		this.useSet = new HashSet<String>();
		this.fieldMap = new HashMap<>();
		this.associationFieldSet = new HashSet<String>();
		this.associationTypeSet = new HashSet<>();
	}
	
	public void addAssociationField(String fieldName) {
		this.associationFieldSet.add(fieldName);
	}

	public void addMethod(IMethod method) {
		this.methodList.add(method);
	}
	
	public void addUse(String className) {
		this.useSet.add(className);
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
		Set<String> filteredUseSet = new HashSet<>();
		for (String usedClass : useSet){
			if(iClasses.containsKey(usedClass)){
				filteredUseSet.add(usedClass);
			}
		}
		useSet = filteredUseSet;
		
		Set<String> filteredTypeSet = new HashSet<>();
		for (String af : associationFieldSet) {
			IField field = fieldMap.get(af);
			ArrayList<String> tempTypeArr = new ArrayList<>();
			if (field.getSignature() == null) {
				tempTypeArr.add(field.getType());
			} else {
				tempTypeArr = Utilities.explodeSignature(field.getSignature());
			}
			for (String tt : tempTypeArr) {
				if (iClasses.containsKey(tt)) {
					filteredTypeSet.add(tt);
					filteredUseSet.remove(tt);
				}
			}
			
		}
		associationTypeSet = filteredTypeSet;
		
		v.postVisit(this);
	}

	public String getName() {
		return this.name;
	}

	public Set<String> getUseSet() {
		return useSet;
	}
	
	public Set<String> getAssociationField() {
		return associationFieldSet;
	}
	
	public Set<String> getAssociationType() {
		return associationTypeSet;
	}

}
