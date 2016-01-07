package ragdoll.util;

import java.util.ArrayList;

public class Utilities {
	public static ArrayList<String> explodeSignature(String signature) {
		String[] tempArr = signature.split("<|>|;");
		ArrayList<String> result = new ArrayList<>();
		for (String s : tempArr) {
			if (!s.isEmpty()) {
				if (s.startsWith("L")) {
					result.add(packagifyClassName(s.substring(1)));
				} else {
					result.add(packagifyClassName(s));
				}
			}
		}
		return result;
	}
	
	public static String packagifyClassName(String className) {
		return (className + "").replaceAll("[/]", ".");
	}
	
	public static String getLastPartOfType(String type) {
		String[] typeParts = type.split("\\.");
		return typeParts[typeParts.length - 1];
	}
}
