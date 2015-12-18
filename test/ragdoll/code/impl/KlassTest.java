package ragdoll.code.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ragdoll.code.api.IClassDeclaration;
import ragdoll.code.api.IField;
import ragdoll.code.api.IMethod;

public class KlassTest {
	private final String name;
	private final List<IMethod> methodList;
	private final HashMap<String, IField> fieldMap;
	private final IClassDeclaration declaration;
}
