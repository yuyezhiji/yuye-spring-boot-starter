package com.yuye.base.common.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;

public class ReflectAsmTool implements ReflectTool {
	MethodAccess methodAccess = null;
	int index;

//	public ReflectAsmTool(Class target, String fieldName) {
//		init(target, buildGetterName(fieldName));
//	}

	public ReflectAsmTool(Class target, String methodName) {
		init(target, methodName);
	}

	public void init(Class target, String methodName) {
		methodAccess = MethodAccess.get(target);
		index = methodAccess.getIndex(methodName);
	}

	@Override
	public Object getValue(Object target, String attr) {
		return methodAccess.invoke(target, index, attr);
	}


}
