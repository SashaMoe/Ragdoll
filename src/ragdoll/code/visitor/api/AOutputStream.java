package ragdoll.code.visitor.api;

public abstract class AOutputStream {
	protected StringBuffer sb = new StringBuffer();
	
	public void initBuffer() {
		
	}
	public void endBuffer() {
		
	}
	protected void appendBufferLine(String s) {
		this.sb.append(s + "\n");
	}

	public String toString() {
		return this.sb.toString();
	}
}
