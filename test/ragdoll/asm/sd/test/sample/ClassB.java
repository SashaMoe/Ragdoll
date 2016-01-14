package ragdoll.asm.sd.test.sample;

public class ClassB {
	public Integer methodB(int i, int j) {
		ClassC.methodC();
		return i + j;
	}
	
	public static void methodE() {
		Integer.valueOf(1);
	}
}
