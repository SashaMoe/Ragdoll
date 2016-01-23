package ragdoll.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Finds the classes given package name. Solution from
 * http://stackoverflow.com/questions/15519626/how-to-get-all-classes-names-in-a
 * -package
 */
public class ClassFinder {

	private static final char DOT = '.';
	private static final char SLASH = '/';
	private static final String CLASS_SUFFIX = ".class";
	
	public static List<Class<?>> find(String scannedPackage) throws ClassNotFoundException {
		String[] scannedPathArr = scannedPackage.split(",");
		System.out.println(scannedPathArr.length);

		List<Class<?>> classes = new ArrayList<>();
		for (String name : scannedPathArr) {
			String fullName = name.trim();
			String scannedPath = fullName.replace(DOT, SLASH);
			URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
			if (scannedUrl == null) {
				classes.add(Class.forName(fullName));
				continue;
			}
			File scannedDir = new File(scannedUrl.getFile());
			for (File file : scannedDir.listFiles()) {
				classes.addAll(find(file, scannedPath));
			}
		}
		return classes;
	}

	private static List<Class<?>> find(File file, String scannedPackage) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String resource = scannedPackage + DOT + file.getName();
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				classes.addAll(find(child, resource));
			}
		} else if (resource.endsWith(CLASS_SUFFIX)) {
			int endIndex = resource.length() - CLASS_SUFFIX.length();
			String className = resource.substring(0, endIndex);
			try {
				classes.add(Class.forName(className));
			} catch (ClassNotFoundException ignore) {
			}
		}
		return classes;
	}

}