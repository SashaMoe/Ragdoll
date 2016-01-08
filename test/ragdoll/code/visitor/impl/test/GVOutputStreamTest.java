package ragdoll.code.visitor.impl.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassDeclarationVisitor;
import ragdoll.asm.ClassFieldVisitor;
import ragdoll.asm.ClassMethodVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;
import ragdoll.code.impl.Klass;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.util.ClassFinder;
import ragdoll.util.Utilities;

public class GVOutputStreamTest {

	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;

	public GVOutputStreamTest() throws IOException {
		// Tests a tricky abstract factory pattern package.
		gvOS = null;
		String packageName = "headfirst";
		List<Class<?>> classes = ClassFinder.find(packageName);
		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}
		iClasses = new HashMap<>();
		
		for (String className : classNames) {
			if (className.contains(".test.") || className.endsWith("Test")
				|| className.endsWith("Tests") || className.contains("$")) {
				continue;
			}
			IClass newClass = new Klass(className, iClasses);
			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, newClass);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, newClass);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, newClass);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			iClasses.put(className, newClass);
		}

	}

	private final void appendBufferLine(String s) {
		sb.append(s + "\n");
	}

	private final void appendBuffer(String s) {
		sb.append(s);
	}

	@Before
	public void setUpGvOS() {
		gvOS = new GVOutputStream();
		sb = new StringBuffer();
	}

	@Test
	public void testInitBuffer() {
		gvOS.initBuffer();

		appendBufferLine("digraph G {");
		appendBufferLine("rankdir=BT;");;
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 12");
		appendBufferLine("node [");
		appendBufferLine("fontname = \"Times New Roman\"");
		appendBufferLine("fontsize = 12");
		appendBufferLine("shape = \"record\"");
		appendBufferLine("]");

		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testEndBuffer() {
		gvOS.endBuffer();
		appendBufferLine("}");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIClass() {
		// Tests headfirst.factory.pizzafm.Pizza
		gvOS.visit(iClasses.get("headfirst.factory.pizzafm.Pizza"));
		appendBufferLine("\"headfirst.factory.pizzafm.Pizza\" [");
		appendBuffer("label = \"{");
		assertEquals(sb.toString(), gvOS.toString());
	}

	// Tests use and assoc arrow in abstract factory pattern
	@Test
	public void testPostVisitIClass() {
		// Tests headfirst.factory.pizzafm.ChicagoStyleCheesePizza
		IClass klass = iClasses.get("headfirst.factory.pizzafm.ChicagoStyleCheesePizza");
		klass.filterUseSet();
		klass.filterTypeSet();
		gvOS.postVisit(klass);

		
		appendBufferLine("}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzafm.ChicagoStyleCheesePizza\" -> \"headfirst.factory.pizzafm.Pizza\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzafm.ChicagoStyleCheesePizza\" -> \"headfirst.factory.pizzafm.Pizza\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		
		assertEquals(sb.toString(), gvOS.toString());
	}
	
	@Test
	public void testPostVisitIClass2() {
		// Tests headfirst.factory.pizzafm.ChicagoStyleCheesePizza
		IClass klass = iClasses.get("headfirst.factory.pizzaaf.Pizza");
		klass.filterUseSet();
		klass.filterTypeSet();
		gvOS.postVisit(klass);

		appendBufferLine("}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Clams\"");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Pepperoni\"");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Dough\"");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Cheese\"");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Sauce\"");

		assertEquals(sb.toString(), gvOS.toString());
	}
	
	

	@Test
	public void testVisitIClassDeclaration() {
		// Tests headfirst.factory.pizzafm.Pizza
		IClassDeclaration classDeclaration = iClasses.get("headfirst.factory.pizzafm.Pizza").getDeclaration();
		classDeclaration.accept(gvOS);
		appendBuffer("«abstract»\\n");
		appendBuffer("headfirst.factory.pizzafm.Pizza|");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIField() {
		// Tests headfirst.factory.pizzaaf.Pizza
		HashMap<String, IField> fieldMap = iClasses.get("headfirst.factory.pizzaaf.Pizza").getFieldMap();
		for (String fieldName : fieldMap.keySet()) {
			IField f = fieldMap.get(fieldName);
			f.accept(gvOS);
		}
		
		appendBuffer("~ name : java.lang.String\\l");
		appendBuffer("~ sauce : headfirst.factory.pizzaaf.Sauce\\l");
		appendBuffer("~ clam : headfirst.factory.pizzaaf.Clams\\l");
		appendBuffer("~ veggies : headfirst.factory.pizzaaf.Veggies[]\\l");
		appendBuffer("~ pepperoni : headfirst.factory.pizzaaf.Pepperoni\\l");
		appendBuffer("~ dough : headfirst.factory.pizzaaf.Dough\\l");
		appendBuffer("~ cheese : headfirst.factory.pizzaaf.Cheese\\l");
		
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitIMethod() {
		// Tests headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory
		List<IMethod> methodList = iClasses.get("headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory").getMethodList();
		for (IMethod m : methodList){
			m.accept(gvOS);
		}
		
		appendBuffer("+ createDough(): Dough\\l");
		appendBuffer("+ createSauce(): Sauce\\l");
		appendBuffer("+ createCheese(): Cheese\\l");
		appendBuffer("+ createVeggies(): Veggies[]\\l");
		appendBuffer("+ createPepperoni(): Pepperoni\\l");
		appendBuffer("+ createClam(): Clams\\l");
		
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testVisitString() {
		gvOS.visit("|");
		appendBuffer("|");
		assertEquals(sb.toString(), gvOS.toString());
	}
}
