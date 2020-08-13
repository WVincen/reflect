package com.example.reflect.exercise.validate;

import java.util.function.Predicate;

/**
 * @author vincent
 */
public class CheckedWrapPredicate {
    static <T> Predicate<T> wrap(CheckedPredicate<T> checkedPredicate) {
        return t -> {
            try {
                return checkedPredicate.test(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
