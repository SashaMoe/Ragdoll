package ragdoll.asm.sd.test.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ClassC {
	public static void methodC() {
		methodD("Nothing");
	}
	
	public static void methodD(String s) {
		ClassB.methodE();
		Collections.shuffle(new ArrayList<>());
	}
}
