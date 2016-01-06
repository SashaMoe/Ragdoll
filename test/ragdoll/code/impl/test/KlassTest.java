package ragdoll.code.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.impl.ClassDeclaration;
import ragdoll.code.impl.Field;
import ragdoll.code.impl.Klass;
import ragdoll.code.impl.Method;

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
		

		Map<String, IClass> iClasses = new HashMap<>();
		klass = new Klass(name, iClasses);
		iClasses.put(name, klass);
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
	
	@Test
	public void testGetFieldMap() {
		HashMap<String, IField> fieldMap = this.klass.getFieldMap();
		for (String f : fieldMap.keySet()) {
			assertEquals(true, fieldMap.containsKey(f));
			assertEquals(fieldMap.get(f), this.fieldMap.get(f));
		}
	}
	
	@Test
	public void testGetMethodList() {
		List<IMethod> methodList = this.klass.getMethodList();
		for (int i = 0; i < this.methodList.size(); i++) {
			assertEquals(this.methodList.get(i), methodList.get(i));
		}
	}
}
