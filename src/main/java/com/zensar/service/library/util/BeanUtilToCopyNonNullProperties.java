package com.zensar.service.library.util;

import java.lang.reflect.Field;
import java.util.Collection;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author Nailesh
 * @apiNote This class is used to copy non null properties from source class to
 *          destination class except collection and properties name should be
 *          same in source and target object
 * 
 */
public class BeanUtilToCopyNonNullProperties<T> {

	/**
	 * @param target      as source object and contains non null properties.
	 * @param destination as destination object contains null properties.
	 * @return Destination object.
	 */
	public T copyNonNullProperties(T target, T destination) {
		if (destination == null || target == null || target.getClass() != destination.getClass())
			return null;

		final BeanWrapper src = new BeanWrapperImpl(destination);
		final BeanWrapper trg = new BeanWrapperImpl(target);

		for (final Field property : target.getClass().getDeclaredFields()) {
			Object providedObject = src.getPropertyValue(property.getName());
			if (providedObject != null && !(providedObject instanceof Collection<?>)) {
				trg.setPropertyValue(property.getName(), providedObject);
			}
		}
		return target;
	}
}
