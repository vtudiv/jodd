// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.petite.resolver;

import jodd.petite.meta.PetiteInitMethod;
import jodd.petite.PetiteException;
import jodd.petite.InitMethodPoint;
import jodd.introspector.ClassDescriptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resolver for bean init methods.
 */
public class InitMethodResolver {

	public InitMethodPoint[] resolve(Object bean) {
		Class<?> type = bean.getClass();

		// lookup methods
		List<InitMethodPoint> list = new ArrayList<InitMethodPoint>();
		ClassDescriptor cd = new ClassDescriptor(type, false);
		Method[] allMethods = cd.getAllMethods(true);

		for (Method method : allMethods) {
			PetiteInitMethod petiteInitMethod = method.getAnnotation(PetiteInitMethod.class);
			if (petiteInitMethod == null) {
				continue;
			}
			if (method.getParameterTypes().length > 0) {
				throw new PetiteException("Arguments are not allowed for Petite init method: " + type.getName() + '#' + method.getName());
			}
			int order = petiteInitMethod.order();
			list.add(new InitMethodPoint(method, order, petiteInitMethod.invoke()));
		}

		InitMethodPoint[] methods;

		if (list.isEmpty()) {
			methods = InitMethodPoint.EMPTY;
		} else {
			Collections.sort(list);
			methods = list.toArray(new InitMethodPoint[list.size()]);
		}

		return methods;
	}

}