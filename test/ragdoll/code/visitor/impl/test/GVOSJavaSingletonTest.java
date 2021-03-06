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
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.impl.Klass;
import ragdoll.code.uml.pattern.APatternDetector;
import ragdoll.code.uml.pattern.IFormatConsumer;
import ragdoll.code.uml.pattern.PatternInfo;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.framework.RagdollProperties;
import ragdoll.util.ClassFinder;

public class GVOSJavaSingletonTest {
	private static final String RUNTIME_CLASS_NAME = "java.lang.Runtime";
	private static final String CALENDAR_CALSS_NAME = "java.util.Calendar";
	private static final String DESKTOP_CLASS_NAME = "java.awt.Desktop";
	private static final String FILTERINPUTSTREAM_CLASS_NAME = "java.io.FilterInputStream";
	private static final String DELIMINATOR = ",";

	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;

	public GVOSJavaSingletonTest() throws IOException, ClassNotFoundException {
		// Tests a tricky abstract factory pattern package.
		gvOS = null;
		String packageName = RUNTIME_CLASS_NAME + DELIMINATOR + CALENDAR_CALSS_NAME + DELIMINATOR + DESKTOP_CLASS_NAME
				+ DELIMINATOR + FILTERINPUTSTREAM_CLASS_NAME;
		List<Class<?>> classes = ClassFinder.find(packageName);
		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}
		iClasses = new HashMap<>();

		for (String className : classNames) {
			// if (className.contains(".test.") || className.endsWith("Test")
			// || className.endsWith("Tests") || className.contains("$")) {
			// continue;
			// }
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
	public void testRuntimeSingleton() {
		IClassDeclaration classDeclaration = iClasses.get(RUNTIME_CLASS_NAME).getDeclaration();
		classDeclaration.accept(gvOS);
		appendBufferLine(RUNTIME_CLASS_NAME);
		appendBuffer("«singleton»|");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testCalenderSingleton() {
		IClassDeclaration classDeclaration = iClasses.get(CALENDAR_CALSS_NAME).getDeclaration();
		classDeclaration.accept(gvOS);
		appendBufferLine(CALENDAR_CALSS_NAME);
		appendBuffer("«singleton»|");
		assertNotEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testDesktopSingleton() {
		// This is indeed a multiton.
		// FYI:
		// http://stackoverflow.com/questions/3061328/singleton-class-in-java-api

		IClassDeclaration classDeclaration = iClasses.get(DESKTOP_CLASS_NAME).getDeclaration();
		classDeclaration.accept(gvOS);
		appendBufferLine(DESKTOP_CLASS_NAME);
		appendBuffer("«singleton»|");
		assertNotEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testFilterInputStreamSingleton() {
		IClassDeclaration classDeclaration = iClasses.get(FILTERINPUTSTREAM_CLASS_NAME).getDeclaration();
		classDeclaration.accept(gvOS);
		appendBufferLine(FILTERINPUTSTREAM_CLASS_NAME);
		appendBuffer("«singleton»|");
		assertNotEquals(sb.toString(), gvOS.toString());
	}
}
