package ragdoll.code.impl;

import static org.junit.Assert.*;
import org.junit.Test;

public class FieldTest {
	private final Field field;
	private final String accessLevel;
	private final String type;
	private final String fieldName;
	
	public FieldTest() {
		accessLevel = "private";
		type = "ragdoll.test.Type";
		fieldName = "test";
		field = new Field(fieldName, accessLevel, type);
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
	
}
