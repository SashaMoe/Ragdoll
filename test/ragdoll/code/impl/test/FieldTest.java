package ragdoll.code.impl.test;

import static org.junit.Assert.*;
import org.junit.Test;

import ragdoll.code.impl.Field;

public class FieldTest {
	private final Field field;
	private final String accessLevel;
	private final String type;
	private final String fieldName;
	private final String signature;
	
	public FieldTest() {
		accessLevel = "private";
		type = "ragdoll.test.Type";
		fieldName = "test";
		signature = null;
		field = new Field(fieldName, accessLevel, type, signature);
	}
	
	@Test
	public void testGetAccessLevel() {
		assertEquals(this.accessLevel, this.field.getAccessLevel());
	}
	
	@Test
	public void testGetType() {
		assertEquals(this.type, this.field.getType());
	}
	
	@Test
	public void testGetFieldName() {
		assertEquals(this.fieldName, this.field.getFieldName());
	}
	
	@Test
	public void testGetSignature() {
		assertEquals(this.signature, this.field.getSignature());
	}
	
}
