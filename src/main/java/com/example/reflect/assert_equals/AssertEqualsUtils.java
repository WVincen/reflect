package com.example.reflect.assert_equals;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author vincent
 */
public class AssertEqualsUtils {

    public static <T> Boolean assertEqualsList(Collection<T> expected, Collection<T> actual) {
        if (expected == null && actual == null) {
            return true;
        }
        if (expected == null) {
            return false;
        }
        if (actual == null) {
            return false;
        }
        if (expected.size() == 0 && actual.size() == 0) {
            return true;
        }
        if (expected.size() != actual.size()) {
            return false;
        }
        Iterator<T> iterator1 = expected.iterator();
        Iterator<T> iterator2 = actual.iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            T next1 = iterator1.next();
            T next2 = iterator2.next();
            if (!assertEqualsObject(next1, next2)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Boolean assertEqualsObject(T expected, T actual) {
        if (Objects.isNull(expected) && Objects.isNull(actual)) {
            return true;
        }
        if (Objects.isNull(expected)) {
            return false;
        }
        if (Objects.isNull(actual)) {
            return false;
        }
        return Arrays.stream(FieldUtils.getAllFields(expected.getClass()))
                .allMatch(f -> {
                    f.setAccessible(true);
                    Class<?> type = f.getType();
                    Object o1 = get(f, expected);
                    Object o2 = get(f, actual);
                    if (Collection.class.isAssignableFrom(type)) {
                        return assertEqualsList(cast(o1), cast(o2));
                    } else if (ClassUtils.isPrimitiveOrWrapper(type) || type.getClassLoader() == null) {
                        return Objects.equals(o1, o2);
                    }
                    return assertEqualsObject(o1, o2);
                });
    }

    @SneakyThrows
    private static Object get(Field f, Object obj) {
        return f.get(obj);
    }

    @SuppressWarnings("unchecked")
    private static <T> T cast(Object obj) {
        return (T) obj;
    }
}
