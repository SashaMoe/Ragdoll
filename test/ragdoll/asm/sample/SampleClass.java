package ragdoll.asm.sample;

import java.io.IOException;

public abstract class SampleClass extends ParentClass implements SampleInterface {
	private int i1;
	public Object o2;
	
	public void sampleMethod(int i1, Object o2) throws Exception {
		this.i1 = i1;
		this.o2 = o2;
		throw new Exception();
	}
}
