package ragdoll.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import ragdoll.asm.ClassDeclarationVisitor;
import ragdoll.asm.ClassFieldVisitor;
import ragdoll.asm.ClassMethodVisitor;
import ragdoll.code.api.IClass;
import ragdoll.code.impl.Class;

public class Ragdoll {
	public static void main(String[] args) throws IOException {
		List<IClass> IClasses = new ArrayList<IClass>();
		
		for (String className : args) {
			IClass newClass = new Class(className); 
			ClassReader reader = new ClassReader(className);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
	}
}
