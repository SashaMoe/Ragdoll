package ragdoll.util;

import java.util.ArrayList;
import java.util.List;

import ragdoll.framework.RagdollProperties;

public class Utilities {
	public static ArrayList<String> explodeSignature(String signature) {
		if (signature == null) {
			return new ArrayList<>();
		}
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

	public static List<String> getParamTypesFromFullyQualifiedMethodSignature(String signature) {
		String[] tempArr = signature.split("\\(")[1].split("\\)")[0].split("\\,");
		ArrayList<String> types = new ArrayList<>();
		for (int i = 0; i < tempArr.length; i++) {
			types.add(tempArr[i].trim());
		}
		return types;
	}

	public static String getSDInstanceName(String className) {
		return getSDName(className).toLowerCase();
	}
	
	public static String getSDName(String className) {
		return (className + "").replaceAll("\\.", "\\\\.");
	}

	public static String getParamString(List<String> paramTypes) {
		int count = 0;
		String params = "";
		if (paramTypes.size() == 0) {
			return params;
		}
		for (String pType : paramTypes) {
			params += pType.charAt(0) + "" + count + " : " + pType + ", ";
			count++;
		}
		params = params.substring(0, params.length() - 2);
		return params;
	}
	
	public static void printVerbose(String message) {
		RagdollProperties properties = RagdollProperties.getInstance();
		if (properties.getProperty("Verbose", "false").equals("true")) {
			System.out.println(message);
		}
	}
}
