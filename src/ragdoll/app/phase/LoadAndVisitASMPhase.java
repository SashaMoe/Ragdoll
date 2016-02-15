package ragdoll.app.phase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.uml.ClassDeclarationVisitor;
import ragdoll.asm.uml.ClassFieldVisitor;
import ragdoll.asm.uml.ClassMethodVisitor;
import ragdoll.code.uml.api.IClass;
import ragdoll.code.uml.api.IClassInfo;
import ragdoll.code.uml.impl.ClassInfo;
import ragdoll.code.uml.impl.Klass;
import ragdoll.framework.IPhase;
import ragdoll.framework.RagdollProperties;
import ragdoll.util.ClassFinder;

public class LoadAndVisitASMPhase implements IPhase {

	@Override
	public void execute() throws Exception {
		RagdollProperties properties = RagdollProperties.getInstance();
		String classesString = properties.getProperty("Input");
		List<Class<?>> classes = ClassFinder.find(classesString);
		List<String> classNames = new ArrayList<>();
		for (Class<?> c : classes) {
			classNames.add(c.getName());
		}
		Map<String, IClass> iClasses = new HashMap<>();

		for (String className : classNames) {
			if (!properties.getProperty("UML-Include-Test-Classes", "false").equals("true")) {
				if (className.contains(".test.") || className.endsWith("Test") || className.endsWith("Tests")
						|| className.contains("$")) {
					continue;
				}
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
		IClassInfo classInfo = ClassInfo.getInstance();
		classInfo.setClasses(iClasses);
	}

}
