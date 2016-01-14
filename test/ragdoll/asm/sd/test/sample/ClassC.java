package ragdoll.asm.sd.test.sample;

import java.util.Random;

public class ClassC {
	public static void methodC() {
		methodD("Nothing");
	}
	
	public static void methodD(String s) {
		ClassB.methodE();
		new Random();
	}
}
