package ragdoll.asm;

import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.TraceClassVisitor;

public class FirstASM {

	public static void main(String[] args) throws IOException {
		ClassReader reader = new ClassReader("ragdoll.asm.FirstASM");
		ClassVisitor visitor = new TraceClassVisitor(new PrintWriter(System.out));
		reader.accept(visitor, ClassReader.EXPAND_FRAMES);

	}

}
