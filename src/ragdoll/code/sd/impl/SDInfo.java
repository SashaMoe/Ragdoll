package ragdoll.code.sd.impl;

import java.util.ArrayList;
import java.util.List;

import ragdoll.code.sd.api.INode;
import ragdoll.code.uml.impl.ClassInfo;

public class SDInfo {
	private volatile static SDInfo instance;
	private List<String> classes;
	private INode startMethod;

	private SDInfo() {
		this.classes = new ArrayList<>();
	}

	public static SDInfo getInstance() {
		if (instance == null) {
			synchronized (ClassInfo.class) {
				if (instance == null) {
					instance = new SDInfo();
				}
			}
		}
		return instance;
	}

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
	}

	public INode getStartMethod() {
		return startMethod;
	}

	public void setStartMethod(INode startMethod) {
		this.startMethod = startMethod;
	}
}
