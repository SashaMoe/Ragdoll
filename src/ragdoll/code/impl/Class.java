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

    private List<IMethod> methodList;
    private HashMap<String, IField> fieldMap;
	private IClassDeclaration declaration;
    
	public void accept(IVisitor v) {
		
	}
}
