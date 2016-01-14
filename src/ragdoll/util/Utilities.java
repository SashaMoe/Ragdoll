package ragdoll.util;

import java.util.ArrayList;
import java.util.List;

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

	public static String getClassNameFromFullyQualifiedMethodSignature(String signature) {
		String[] tempArr = signature.split("\\(")[0].split("\\.");
		String className = "";
		for (int i = 0; i < tempArr.length - 1; i++) {
			className += tempArr[i] + ((i == tempArr.length - 2) ? "" : ".");
		}
		return className;
	}
	
	public static String getMethodNameFromFullyQualifiedMethodSignature(String signature) {
		String[] tempArr = signature.split("\\(")[0].split("\\.");
		String methodName = tempArr[tempArr.length - 1].split("\\(")[0];
		return methodName;
	}
	
	public static List<String> getMethodTypesFromFullyQualifiedMethodSignature(String signature) {
		String[] tempArr = signature.split("\\(")[1].split("\\)")[0].split("\\,");
		ArrayList<String> types = new ArrayList<>();
		for (int i = 0; i < tempArr.length; i++) {
			types.add(tempArr[i].trim());
		}
		return types;
	}
}
