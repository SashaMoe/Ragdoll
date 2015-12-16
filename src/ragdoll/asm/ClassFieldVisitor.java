package ragdoll.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {

	public ClassFieldVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ClassFieldVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		System.out.println("	" + type + "	" + name);
		return toDecorate;
	}
}
