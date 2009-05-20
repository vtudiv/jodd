// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package examples.proxetta.n.proxy;

import jodd.proxetta.MethodSignature;
import jodd.proxetta.ProxyAspect;
import jodd.proxetta.ProxyPointcut;
import examples.proxetta.n.proxy.advice.TxAdvice;
import examples.proxetta.n.proxy.advice.JoAdvice;

/**
 * Proxy definitions holder.
 */
public class Aspects {

	private final ProxyAspect txProxy;
	private final ProxyAspect joProxy;

	public Aspects() {
		txProxy = createTxAspect();
		joProxy = createJoAspect();
	}

	private ProxyAspect createTxAspect() {
		return new ProxyAspect(TxAdvice.class,
				new ProxyPointcut() {
					public boolean apply(MethodSignature msign) {
						return msign.getClassname().endsWith("Service") && msign.getMethodName().startsWith("z");
					}
				});
	}

	private ProxyAspect createJoAspect() {
		return new ProxyAspect(JoAdvice.class,
				new ProxyPointcut() {
					public boolean apply(MethodSignature msign) {
						System.out.println("$$$$$$$$$$ " + msign);
						System.out.println("rrrrrrrrrr " + (msign.getReturnOpcodeType() == MethodSignature.TYPE_VOID));
						return msign.getClassname().endsWith("Service");
					}
				});
	}


	public ProxyAspect getTxAspect() {
		return txProxy;
	}
	public ProxyAspect getJoAspect() {
		return joProxy;
	}
}