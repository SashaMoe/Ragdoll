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
import ragdoll.util.ClassFinder;

public class GVOSTextEditDecoratorTest {

	private GVOutputStream gvOS;
	private final Map<String, IClass> iClasses;
	private StringBuffer sb;

	public GVOSTextEditDecoratorTest() throws IOException, ClassNotFoundException {
		String packageName = "ragdoll.asm.uml.test.sample.decorator,java.io.FilterOutputStream,java.io.OutputStream,java.io.FilterInputStream,java.io.InputStream";
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
		PatternInfo patternController = new PatternInfo();
		IClassInfo classInfo = ClassInfo.getInstance();
		classInfo.setClasses(iClasses);

		APatternDetector singletonPattern = new SingletonPattern(classInfo);
		patternController.registerPatternDetector("singleton", singletonPattern);
		APatternDetector adapterPattern = new AdapterPattern(classInfo);
		patternController.registerPatternDetector("adapter", adapterPattern);
		APatternDetector decoratorPattern = new DecoratorPattern(classInfo);
		patternController.registerPatternDetector("decorator", decoratorPattern);

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
	public void testFilterISDecorator(){
		IClass klass = iClasses.get("java.io.FilterInputStream");
		klass.accept(gvOS);
		
		appendBufferLine("\"java.io.FilterInputStream\" [");
		appendBufferLine("color=black");
		appendBufferLine("fillcolor=\"green\"");
		appendBufferLine("style=filled");
		appendBufferLine("label = \"{java.io.FilterInputStream");
		appendBufferLine("«Decorator»\\n|# in : java.io.InputStream\\l|+ read(): int\\l+ read(b0 : byte[]): int\\l+ read(b0 : byte[], i1 : int, i2 : int): int\\l+ skip(l0 : long): long\\l+ available(): int\\l+ close(): void\\l+ mark(i0 : int): void\\l+ reset(): void\\l+ markSupported(): boolean\\l}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("\"java.io.FilterInputStream\" -> \"java.io.InputStream\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \"decorate\"");
		appendBufferLine("]");
		appendBufferLine("\"java.io.FilterInputStream\" -> \"java.io.InputStream\"");
		appendBufferLine("edge [label=\" \"]");

		
		assertEquals(sb.toString(), gvOS.toString());
	}

	@Test
	public void testEncryptionOSDecorator() {
		IClass klass = iClasses.get("ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream");
		klass.accept(gvOS);
		
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream\" [");
		appendBufferLine("color=black");
		appendBufferLine("fillcolor=\"green\"");
		appendBufferLine("style=filled");
		appendBufferLine("label = \"{ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream");
		appendBufferLine("«Decorator»\\n|- encryptor : ragdoll.asm.uml.test.sample.decorator.IEncryption\\l|+ write(i0 : int): void\\l}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream\" -> \"java.io.FilterOutputStream\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream\" -> \"ragdoll.asm.uml.test.sample.decorator.IEncryption\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \"decorate\"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.EncryptionOutputStream\" -> \"java.io.OutputStream\"");
		appendBufferLine("edge [label=\" \"]");
		
		assertEquals(sb.toString(), gvOS.toString());
	}
	
	@Test
	public void testDecryptionISDecorator() {
		IClass klass = iClasses.get("ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream");
		klass.accept(gvOS);
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream\" [");
		appendBufferLine("color=black");
		appendBufferLine("fillcolor=\"green\"");
		appendBufferLine("style=filled");
		appendBufferLine("label = \"{ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream");
		appendBufferLine("«Decorator»\\n|- decryptor : ragdoll.asm.uml.test.sample.decorator.IDecryption\\l|+ read(): int\\l+ read(b0 : byte[], i1 : int, i2 : int): int\\l}\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"empty\"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream\" -> \"java.io.FilterInputStream\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"dashed\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("]");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \" \"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream\" -> \"ragdoll.asm.uml.test.sample.decorator.IDecryption\"");
		appendBufferLine("edge [");
		appendBufferLine("style = \"solid\"");
		appendBufferLine("arrowhead = \"vee\"");
		appendBufferLine("label = \"decorate\"");
		appendBufferLine("]");
		appendBufferLine("\"ragdoll.asm.uml.test.sample.decorator.DecryptionInputStream\" -> \"java.io.InputStream\"");
		appendBufferLine("edge [label=\" \"]");
		assertEquals(sb.toString(), gvOS.toString());
	}
}
