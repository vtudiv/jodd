// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.interceptor;

import jodd.madvoc.ActionRequest;
import jodd.madvoc.ScopeType;
import jodd.madvoc.injector.RequestScopeInjector;
import jodd.madvoc.injector.SessionScopeInjector;
import jodd.madvoc.meta.In;
import jodd.madvoc.component.MadvocConfig;
import jodd.madvoc.component.ScopeDataManager;
import jodd.madvoc.component.ContextInjector;
import jodd.servlet.ServletUtil;
import jodd.servlet.upload.MultipartRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Configures actions and applies some servlet configuration prior action execution.
 * This interceptor does the following:
 * <ul>
 * <li>sets character encoding
 * <li>uses multi-part request if needed
 * <li>performs the injection (using either default or specified injector)
 * <li>invokes the action
 * <li>performs the outjection.
 * </ul>
 */
public class ServletConfigInterceptor extends ActionInterceptor {

	@In(scope = ScopeType.CONTEXT)
	protected MadvocConfig madvocConfig;

	@In(scope = ScopeType.CONTEXT)
	protected ScopeDataManager scopeDataManager;

	@In(scope = ScopeType.CONTEXT)
	protected ContextInjector contextInjector;

	protected RequestScopeInjector requestInjector;
	protected SessionScopeInjector sessionInjector;

	@Override
	public void init() {
		requestInjector = new RequestScopeInjector(scopeDataManager);
		sessionInjector = new SessionScopeInjector(scopeDataManager);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object intercept(ActionRequest actionRequest) throws Exception {
		HttpServletRequest servletRequest = actionRequest.getHttpServletRequest();
		HttpServletResponse serlvetResponse = actionRequest.getHttpServletResponse();

		servletRequest = prepare(actionRequest, servletRequest, serlvetResponse);

		// get injector
		Object target = actionRequest.getAction();
		inject(target, servletRequest, serlvetResponse);
		Object result = actionRequest.invoke();
		outject(target, servletRequest);
		return result;
	}

	/**
	 * Prepares servlet request and response. Returns HTTP servlet request that might be wrapped for multiparts.
	 */
	protected HttpServletRequest prepare(ActionRequest actionRequest, HttpServletRequest servletRequest, HttpServletResponse serlvetResponse) throws IOException {
		// set character encoding
		servletRequest.setCharacterEncoding(madvocConfig.getEncoding());
		serlvetResponse.setCharacterEncoding(madvocConfig.getEncoding());

		// multi-part
		if (ServletUtil.isMultipartRequest(servletRequest)) {
			servletRequest = new MultipartRequestWrapper(servletRequest, madvocConfig.getFileUploadFactory(), madvocConfig.getEncoding());
			actionRequest.setHttpServletRequest(servletRequest);
		}
		return servletRequest;
	}

	/**
	 * Performs injection.
	 */
	protected void inject(Object target, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		contextInjector.inject(target, servletRequest, servletResponse);
		sessionInjector.inject(target, servletRequest);
		requestInjector.inject(target, servletRequest);
	}

	/**
	 * Performs outjection.
	 */
	protected void outject(Object target, HttpServletRequest servletRequest) {
		contextInjector.outject(target, servletRequest);
		sessionInjector.outject(target, servletRequest);
		requestInjector.outject(target, servletRequest);
	}

}