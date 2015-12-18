package ragdoll.code.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;


import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;

public class KlassTest {
	private final String name;
	private final List<IMethod> methodList;
	private final HashMap<String, IField> fieldMap;
	private final IClassDeclaration declaration;
	private final IClass klass; 
	
	public KlassTest(){
		name = "Mewo";
		methodList = new ArrayList<IMethod>();
		fieldMap = new HashMap<>();
		
		IField f1 = new Field("F1", "public", "T1");
		IField f2 = new Field("F2", "priavte", "T2");
		fieldMap.put("F1", f1);
		fieldMap.put("F2", f2);
		
		List<String> nameOfInterfaces = new ArrayList<>();
		nameOfInterfaces.add("IC1");
		nameOfInterfaces.add("IC2");
		
		declaration = new ClassDeclaration(false, true, "C1", "SC1", nameOfInterfaces);
		
		List<String> exceptions = new ArrayList<>();
		exceptions.add("e1");
		exceptions.add("e2");
		List<String> paramTypes = new ArrayList<>();
		paramTypes.add("t1");
		paramTypes.add("t2");
		
		IMethod method1 = new Method("method","private","T3",paramTypes,exceptions);
		
		methodList.add(method1);
		
		klass = new Klass(name);
		klass.addField(f1);
		klass.addField(f2);
		klass.addMethod(method1);
		klass.setDeclaration(declaration);
	}

	@Test
	public void testGetName() {
		assertEquals(this.name, this.klass.getName());
	}
	
	@Test
	public void testGetDeclaration() {
		assertEquals(this.declaration, this.klass.getDeclaration());
	}
}
