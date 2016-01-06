package ragdoll.util;

public class Utilities {
	public static String packagifyClassName(String className) {
		return (className + "").replaceAll("[/]", ".");
	}
	
	public static String getLastPartOfType(String type) {
		String[] typeParts = type.split("\\.");
		return typeParts[typeParts.length - 1];
	}
}
