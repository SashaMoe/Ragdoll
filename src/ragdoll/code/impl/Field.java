package ragdoll.code.impl;

import ragdoll.code.api.IField;
import ragdoll.code.visitor.api.IVisitor;

public class Field implements IField {

	private String fieldName;
	private String accessLevel;
	private String type;
	private String signature;

	public Field(String fieldName, String accessLevel, String type, String signature) {
		super();
		this.fieldName = fieldName;
		this.accessLevel = accessLevel;
		this.type = type;
		this.signature = signature;
	}
	
	public String getSignature() {
		return signature;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public String getType() {
		return type;
	}

	public void accept(IVisitor v) {
		v.visit(this);
	}

}
