package ragdoll.code.uml.api;

public interface IField extends IClassComponent {
	public String getFieldName();

	public String getAccessLevel();

	public String getType();
	
	public String getSignature();
}
