package ragdoll.code.visitor.impl.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.app.pattern.GVFormatConsumer;
import ragdoll.app.pattern.SingletonPattern;
import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.api.IField;
import ragdoll.code.uml.api.IMethod;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.impl.Klass;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.framework.RagdollProperties;
import ragdoll.util.ClassFinder;

public class GVOutputStreamTest {

	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;

	public GVOutputStreamTest() throws IOException, ClassNotFoundException {
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
			if (className.contains(".test.") || className.endsWith("Test") || className.endsWith("Tests")
					|| className.contains("$")) {
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

		for (String className : iClasses.keySet()) {
			IClass klass = iClasses.get(className);
			String superClassName = klass.getDeclaration().getNameOfSuperClass();
			IClass superClass = iClasses.get(superClassName);
			if (superClass != null) {
				superClass.addSubClasses(className);
			}
			List<String> interfaceNames = klass.getDeclaration().getNameOfInterfaces();
			if (interfaceNames != null) {
				for (String interfaceName : interfaceNames) {
					if (iClasses.containsKey(interfaceName)) {
						IClass itf = iClasses.get(interfaceName);
						itf.addSubClasses(className);
					}
				}
			}
		}

		// Pattern Detection
		PatternInfo patternController = PatternInfo.getInstance();
		IClassInfo classInfo = ClassInfo.getInstance();
		classInfo.setClasses(iClasses);

		APatternDetector singletonPattern = new SingletonPattern(classInfo);
		singletonPattern.detectPattern();
		patternController.storePatternInfo("singleton", singletonPattern.getPatterns());

		RagdollProperties properties = RagdollProperties.getInstance();
		properties.init();
		properties.setProperty("Mode", "UML");
		
		IFormatConsumer gvFormatConsumer = GVFormatConsumer.getInstance();
		gvFormatConsumer.parse(patternController.getPatterMap());

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
		appendBufferLine("rankdir=BT;");
		;
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
		appendBufferLine("color=black");
		appendBufferLine("fillcolor=\"white\"");
		appendBufferLine("style=filled");
		appendBuffer("label = \"{");
		assertEquals(sb.toString(), gvOS.toString());
	}

	// Tests use and assoc arrow in abstract factory pattern
	@Test
	public void testPostVisitIClass() {
		// Tests headfirst.factory.pizzafm.ChicagoStyleCheesePizza
		IClass klass = iClasses.get("headfirst.factory.pizzafm.ChicagoStyleCheesePizza");
		klass.filterUseSet();
		klass.filterAssocSet();
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
		appendBufferLine(
				"\"headfirst.factory.pizzafm.ChicagoStyleCheesePizza\" -> \"headfirst.factory.pizzafm.Pizza\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine(
				"\"headfirst.factory.pizzafm.ChicagoStyleCheesePizza\" -> \"headfirst.factory.pizzafm.Pizza\"");
		appendBufferLine("edge [label=\" \"]");

		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testPostVisitIClass2() {
		// Tests headfirst.factory.pizzafm.ChicagoStyleCheesePizza
		IClass klass = iClasses.get("headfirst.factory.pizzaaf.Pizza");
		klass.filterUseSet();
		klass.filterAssocSet();
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
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Clams\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Pepperoni\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Dough\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Cheese\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"headfirst.factory.pizzaaf.Pizza\" -> \"headfirst.factory.pizzaaf.Sauce\"");
		appendBufferLine("edge [label=\" \"]");

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
		List<IMethod> methodList = iClasses.get("headfirst.factory.pizzaaf.ChicagoPizzaIngredientFactory")
				.getMethodList();
		for (IMethod m : methodList) {
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
