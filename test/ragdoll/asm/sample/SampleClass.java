package ragdoll.asm.sample;

public abstract class SampleClass extends ParentClass implements SampleInterface {
	private int i1;
	public Object o2;
	
	public void sampleMethod(int i1, Object o2) {
		this.i1 = i1;
		this.o2 = o2;
	}
}
