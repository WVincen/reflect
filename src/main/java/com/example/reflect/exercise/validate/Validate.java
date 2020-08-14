package com.example.reflect.exercise.validate;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author vincent
 */
public class Validate {
    public static <T> List<FieldError> check(T t) {
        // 获取类中的所以属性
        List<Field> allFields = getAllFields(t.getClass());
        return allFields.stream()
                .filter(field -> field.isAnnotationPresent(NotBlank.class))
                .peek(field -> field.setAccessible(true))
                .filter(CheckedWrapPredicate.wrap(field -> Objects.isNull(field.get(t)) || Objects.equals(field.get(t), "")
                        || (field.get(t) instanceof List) || (field.get(t) instanceof String && StringUtils.isBlank(field.get(t).toString()))))
                .map(CheckedWrapFunction.wrap(field -> {
                    if (field.getType().isAssignableFrom(List.class) && CollectionUtils.isNotEmpty((List) field.get(t))) {
                        List<?> list = (List) field.get(t);
                        List<FieldError> fieldErrors = list.stream()
                                .map(Validate::check)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(fieldErrors)) {
                            return fieldErrors.get(0);
                        }
                    } else {
                        FieldError fieldError = new FieldError();
                        fieldError.setFieldName(field.getName());
                        NotBlank annotation = field.getAnnotation(NotBlank.class);
                        String message = StringUtils.isEmpty(annotation.message()) ? annotation.resultCode().getMsg() : annotation.message();
                        IntegrationResultCode resultCode = StringUtils.isEmpty(annotation.message()) ? annotation.resultCode() : null;
                        fieldError.setErrorMsg(message);
                        fieldError.setResultCode(resultCode);
                        return fieldError;
                    }
                    return null;
                }))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, Lists.newArrayList());
    }

    private static List<Field> getAllFields(Class<?> clazz, List<Field> fields) {
        List<Field> fieldList = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        fields.addAll(fieldList);
        Class<?> superclass = clazz.getSuperclass();
        if (Objects.isNull(superclass)) {
            return fields;
        } else {
            return getAllFields(superclass, fields);
        }
    }
}
