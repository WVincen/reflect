package com.example.reflect.exercise.validate;

import java.util.function.Function;

/**
 * @author vincent
 */
public class CheckedWrapFunction {
    static <T, R> Function<T, R> wrap(CheckedFunction<T, R> checkedFunction) {
        return t -> {
            try {
                return checkedFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
