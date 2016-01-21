package ragdoll.code.uml.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.visitor.api.IUMLVisitor;
import ragdoll.code.visitor.api.IVisitor;
import ragdoll.util.Utilities;

public class Klass implements IClass {

	private Map<String, IClass> iClasses;
	private String name;
	private List<IMethod> methodList;
	private Set<String> useSet;
	private HashMap<String, IField> fieldMap;
	private IClassDeclaration declaration;
	private Set<String> associationTypeSet;
	private boolean hasLazyGetInstanceMethod;
	private boolean hasGetInstanceMethod;
	private boolean hasEagerInit;

	public Klass(String name, Map<String, IClass> iClasses) {
		super();
		this.name = name;
		this.iClasses = iClasses;
		this.methodList = new ArrayList<>();
		this.useSet = new HashSet<String>();
		this.fieldMap = new HashMap<>();
		this.associationTypeSet = new HashSet<>();
		this.hasLazyGetInstanceMethod = false;
	}

	public void setHasLazyGetInstanceMethod(boolean hasLazyGetInstanceMethod) {
		this.hasLazyGetInstanceMethod = hasLazyGetInstanceMethod;
	}
	
	public void setHasGetInstanceMethod(boolean hasGetInstanceMethod) {
		this.hasGetInstanceMethod = hasGetInstanceMethod;
	}

	public void setHasEagerInit(boolean hasEagerInit) {
		this.hasEagerInit = hasEagerInit;
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
		IUMLVisitor newV = (IUMLVisitor) v;
		newV.visit(this);
		declaration.accept(v);
		for (String fieldName : this.fieldMap.keySet()) {
			IField f = this.fieldMap.get(fieldName);
			f.accept(v);
		}
		newV.visit("|");
		for (IMethod m : this.methodList) {
			m.accept(v);
		}

		filterUseSet();
		filterTypeSet();

		newV.postVisit(this);
	}

	public void filterUseSet() {
		Set<String> filteredUseSet = new HashSet<>();
		for (String usedClass : useSet) {
			if (iClasses.containsKey(usedClass)) {
				filteredUseSet.add(usedClass);
			}
		}
		useSet = filteredUseSet;
	}

	public void filterTypeSet() {
		Set<String> filteredTypeSet = new HashSet<>();
		for (String af : fieldMap.keySet()) {
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
					useSet.remove(tt);
				}
			}
		}
		associationTypeSet = filteredTypeSet;
	}

	public String getName() {
		return this.name;
	}

	public Set<String> getUseSet() {
		return useSet;
	}

	public Set<String> getAssociationType() {
		return associationTypeSet;
	}

	public boolean checkHasPrivateConstructor() {
		for (IMethod method : this.methodList) {
			if (method.getAccessLevel().equals("private") && method.getMethodName().equals("<init>")) {
				return true;
			}
		}
		return false;
	}

	public boolean checkHasPrivateFiledOfItself() {
		for (IField field : this.fieldMap.values()) {
			if (field.getAccessLevel().equals("private") && field.getType().equals(this.name)) {
				return true;
			}
		}
		return false;
	}

	public void updateIsSingleton() {
		this.declaration.setIsSingleton(!this.declaration.isAbstract() && !this.declaration.isInterface()
				&& checkHasPrivateConstructor() && checkHasPrivateFiledOfItself() && this.hasGetInstanceMethod &&
				(this.hasLazyGetInstanceMethod || this.hasEagerInit));
	}

}
