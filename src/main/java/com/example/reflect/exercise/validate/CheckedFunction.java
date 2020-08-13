package com.example.reflect.exercise.validate;

/**
 * @author vincent
 */
@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
