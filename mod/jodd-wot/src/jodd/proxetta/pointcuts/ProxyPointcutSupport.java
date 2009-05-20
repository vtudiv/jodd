// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.proxetta.pointcuts;

import jodd.proxetta.ProxyPointcut;
import jodd.proxetta.MethodSignature;
import jodd.proxetta.AnnotationData;
import jodd.util.Wildcard;

import java.util.List;

/**
 * {@link jodd.proxetta.ProxyPointcut} support methods.
 */
public abstract class ProxyPointcutSupport implements ProxyPointcut {

	/**
	 * Returns <code>true</code> if method is public.
	 */
	public boolean isPublic(MethodSignature msign) {
		return (msign.getAccessFlags() & MethodSignature.ACC_PUBLIC) != 0;
	}

	/**
	 * Returns <code>true</code> if method is annotated with provided annotation.
	 */
	public boolean hasAnnotation(MethodSignature msign, String annotationName) {
		List<AnnotationData> anns = msign.getAnnotations();
		for (AnnotationData a : anns) {
			if (annotationName.equals(a.declaration)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if method has no arguments.
	 */
	public boolean hasNoArguments(MethodSignature msign) {
		return msign.getArgumentsCount() == 0;
	}

	/**
	 * Returns <code>true</code> if method has only one argument.
	 */
	public boolean hasOneArgument(MethodSignature msign) {
		return msign.getArgumentsCount() == 1;
	}

	/**
	 * Returns <code>true</code> if method is a top-level method.
	 */
	public boolean isTopLevelMethod(MethodSignature msign) {
		return msign.isTopLevelMethod();
	}


	// ---------------------------------------------------------------- wildcards

	/**
	 * Match method name to provided {@link jodd.util.Wildcard} pattern.
	 */
	public boolean matchMethodName(MethodSignature msing, String wildcard) {
		return Wildcard.match(msing.getMethodName(), wildcard);
	}

	/**
	 * Match class name to provided {@link jodd.util.Wildcard} pattern.
	 */
	public boolean matchClassName(MethodSignature msing, String wildcard) {
		return Wildcard.match(msing.getClassname(), wildcard);
	}


	// ---------------------------------------------------------------- return


	/**
	 * Returns <code>true</code> if method's return type is <code>void</code>.
	 */
	public boolean hasNoReturnValue(MethodSignature msign) {
		return msign.getReturnOpcodeType() == MethodSignature.TYPE_VOID;
	}

	/**
	 * Returns <code>true</code> if method has a return type.
	 */
	public boolean hasReturnValue(MethodSignature msign) {
		return msign.getReturnOpcodeType() != MethodSignature.TYPE_VOID;
	}



	// ---------------------------------------------------------------- logical joins

	/**
	 * Returns <code>true</code> if both pointcuts can be applied on the method..
	 */
	public boolean and(MethodSignature msign, ProxyPointcut p1, ProxyPointcut p2) {
		return p1.apply(msign) && p2.apply(msign);
	}

	/**
	 * Returns <code>true</code> if at least one pointcuts can be applied on the method..
	 */
	public boolean or(MethodSignature msign, ProxyPointcut p1, ProxyPointcut p2) {
		return p1.apply(msign) || p2.apply(msign);
	}


}