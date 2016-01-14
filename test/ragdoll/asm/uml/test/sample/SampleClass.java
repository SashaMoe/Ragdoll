package ragdoll.asm.uml.test.sample;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class SampleClass extends ParentClass implements SampleInterface {
	@SuppressWarnings("unused")
	private int i1;
	public Object o2;
	protected ArrayList<String> a3;
	boolean n4;
	public SampleClass s5;
	public HashMap<String, ParentClass> h6;
	
	public void sampleMethod(int i1, Object o2) throws Exception {
		this.i1 = i1;
		this.o2 = o2;
		throw new Exception(i1 + "");
	}
	
	private SampleInterface sampleMethod2() {
		return new ParentClass();
	}
	
	protected int sampleMethod3() {
		return 233;
	}
	
	boolean sampleMethod4() {
		return false;
	}
	
	void sampleMethod5(UsedClass u) {
		UsedClass2 u2 = new UsedClass2();
	}
}
