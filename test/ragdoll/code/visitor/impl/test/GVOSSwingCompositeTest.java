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
import ragdoll.app.pattern.CompositePattern;
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
import ragdoll.code.uml.pattern.Pattern;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.util.ClassFinder;

public class GVOSSwingCompositeTest {

	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;
	private PatternInfo patternController;

	public GVOSSwingCompositeTest() throws IOException, ClassNotFoundException {
		String packageName = "java.awt.Component,java.awt.Container,javax.swing.JComponent,javax.swing.JPanel";
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
		patternController = new PatternInfo();
		IClassInfo classInfo = ClassInfo.getInstance();
		classInfo.setClasses(iClasses);

		APatternDetector singletonPattern = new SingletonPattern(classInfo);
		patternController.registerPatternDetector("singleton", singletonPattern);
		APatternDetector adapterPattern = new AdapterPattern(classInfo);
		patternController.registerPatternDetector("adapter", adapterPattern);
		APatternDetector decoratorPattern = new DecoratorPattern(classInfo);
		patternController.registerPatternDetector("decorator", decoratorPattern);
		APatternDetector compositePattern = new CompositePattern(classInfo);
		patternController.registerPatternDetector("composite", compositePattern);

		IFormatConsumer gvFormatConsumer = GVFormatConsumer.getInstance();
		patternController.registerFormatConsumer(gvFormatConsumer);

		patternController.detectAllPatterns();

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
	public void testComposite() {
		List<Pattern> compositePatterns = patternController.getPatterMap().get("composite");
		Pattern pattern = compositePatterns.get(0);
		assertEquals(1, compositePatterns.size());
		assertEquals("component", pattern.getRoleMap().get("java.awt.Component"));
		assertEquals("composite", pattern.getRoleMap().get("java.awt.Container"));
		assertEquals("composite", pattern.getRoleMap().get("javax.swing.JComponent"));
		assertEquals("composite", pattern.getRoleMap().get("javax.swing.JPanel"));
	}

}
