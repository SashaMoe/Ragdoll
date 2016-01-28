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

import ragdoll.app.pattern.SingletonPattern;
import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassDeclaration;
import ragdoll.code.uml.impl.Klass;
import ragdoll.code.uml.pattern.PatternController;
import ragdoll.code.visitor.impl.GVOutputStream;
import ragdoll.util.ClassFinder;

public class GVOSChocolateSingletonTest {
	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;

	public GVOSChocolateSingletonTest() throws IOException, ClassNotFoundException {
		// Tests a tricky abstract factory pattern package.
		gvOS = null;
		String packageName = "ragdoll.asm.uml.test.sample";
		List<Class<?>> classes = ClassFinder.find(packageName);
		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}
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

		// pattern detection
		PatternController patternDetector = PatternController.getInstance();
		patternDetector.setClasses(iClasses);
		SingletonPattern singletonPattern = new SingletonPattern(patternDetector);
		patternDetector.addPattern("singletonPattern", singletonPattern);
		patternDetector.detectAllPatterns();

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
	public void testLazySingleton() {
		IClassDeclaration classDeclaration = iClasses.get("ragdoll.asm.uml.test.sample.ChocolateBoiler")
				.getDeclaration();
		classDeclaration.accept(gvOS);
		appendBufferLine("ragdoll.asm.uml.test.sample.ChocolateBoiler");
		appendBuffer("«singleton»\\n|");
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testEagerSingleton() {
		IClassDeclaration classDeclaration = iClasses.get("ragdoll.asm.uml.test.sample.ChocolateBoilerEager")
				.getDeclaration();
		classDeclaration.accept(gvOS);
		appendBufferLine("ragdoll.asm.uml.test.sample.ChocolateBoilerEager");
		appendBuffer("«singleton»\\n|");
		assertEquals(sb.toString(), gvOS.toString());
	}
}
