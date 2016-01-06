package ragdoll.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import ragdoll.code.api.IClass;
import ragdoll.code.api.IField;
import ragdoll.code.impl.Field;

public class ClassFieldVisitor extends ClassVisitor {

	private IClass c;

	public ClassFieldVisitor(int arg0, IClass c) {
		super(arg0);
		this.c = c;
	}

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, IClass c) {
		super(arg0, arg1);
		this.c = c;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		String level = "";
		if ((access & Opcodes.ACC_PUBLIC) != 0) {
			level = "public";
		} else if ((access & Opcodes.ACC_PROTECTED) != 0) {
			level = "protected";
		} else if ((access & Opcodes.ACC_PRIVATE) != 0) {
			level = "private";
		} else {
			level = "default";
		}

		IField field = new Field(name, level, type);
		this.c.addField(field);
		return toDecorate;
	}
}
