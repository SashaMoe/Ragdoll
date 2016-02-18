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

import ragdoll.app.pattern.AdapterPattern;
import ragdoll.app.pattern.DecoratorPattern;
import ragdoll.app.pattern.GVFormatConsumer;
import ragdoll.app.pattern.SingletonPattern;
import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.impl.Klass;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.framework.RagdollProperties;
import ragdoll.util.ClassFinder;

public class GVOSItrToEnmAdapterTest {

	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;

	public GVOSItrToEnmAdapterTest() throws IOException, ClassNotFoundException {
		String packageName = "ragdoll.asm.uml.test.sample.adapter,java.util.Enumeration,java.util.Iterator";
		List<Class<?>> classes = ClassFinder.find(packageName);
		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}

		GVOutputStream gvOS = new GVOutputStream();
		iClasses = new HashMap<>();

		for (String className : classNames) {
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
		APatternDetector adapterPattern = new AdapterPattern(classInfo);
		adapterPattern.detectPattern();
		APatternDetector decoratorPattern = new DecoratorPattern(classInfo);
		decoratorPattern.detectPattern();
		patternController.storePatternInfo("singleton", singletonPattern.getPatterns());
		patternController.storePatternInfo("adapter", adapterPattern.getPatterns());
		patternController.storePatternInfo("decorator", decoratorPattern.getPatterns());

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
	public void testItrToEnmAdapter() {
		IClass klass = iClasses.get("ragdoll.asm.uml.test.sample.adapter.IteratorToEnumerationAdapter");
		klass.accept(gvOS);

		appendBufferLine("\"ragdoll.asm.uml.test.sample.adapter.IteratorToEnumerationAdapter\" [");
		appendBufferLine("color=black");
		appendBufferLine("fillcolor=\"#A32B2E\"");
		appendBufferLine("style=filled");
		appendBufferLine("label = \"{ragdoll.asm.uml.test.sample.adapter.IteratorToEnumerationAdapter");
		appendBufferLine(
				"«Adapter»|- itr : java.util.Iterator\\l|+ hasMoreElements(): boolean\\l+ nextElement(): Object\\l}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine(
				"\"ragdoll.asm.uml.test.sample.adapter.IteratorToEnumerationAdapter\" -> \"java.util.Enumeration\"");
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
		appendBufferLine("label = \"adapts\"");
		appendBufferLine("]");
		appendBufferLine(
				"\"ragdoll.asm.uml.test.sample.adapter.IteratorToEnumerationAdapter\" -> \"java.util.Iterator\"");
		appendBufferLine("edge [label=\" \"]");

		assertEquals(sb.toString(), gvOS.toString());
	}

}
