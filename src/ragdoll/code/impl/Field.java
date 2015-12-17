package ragdoll.code.impl;

import ragdoll.code.api.IField;
import ragdoll.code.visitor.api.IVisitor;

public class Field implements IField {

	private String fieldName;
	private String accessLevel;
	private String type;

	public Field(String fieldName, String accessLevel, String type) {
		super();
		this.fieldName = fieldName;
		this.accessLevel = accessLevel;
		this.type = type;
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
