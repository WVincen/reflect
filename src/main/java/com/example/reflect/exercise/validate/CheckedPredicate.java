package com.example.reflect.exercise.validate;

/**
 * @author vincent
 */
@FunctionalInterface
public interface CheckedPredicate<T> {
    boolean test(T t) throws Exception;
}
