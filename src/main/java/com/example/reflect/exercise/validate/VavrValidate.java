package com.example.reflect.exercise.validate;

import io.vavr.CheckedFunction1;
import io.vavr.CheckedPredicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author vincent
 */
public class VavrValidate {
    public static <T> List<FieldError> check(T t) {
        Field[] allFields = FieldUtils.getAllFields(t.getClass());
        return Arrays.stream(allFields)
                .filter(field -> field.isAnnotationPresent(NotBlank.class))
                .peek(field -> field.setAccessible(true))
                .filter(CheckedPredicate.<Field>of(
                        field -> Objects.isNull(field.get(t)) || Objects.equals(field.get(t), "") || (field.get(t) instanceof List) ||
                                (field.get(t) instanceof String && StringUtils.isBlank(field.get(t).toString()))
                        ).unchecked()
                )
                .map(CheckedFunction1.<Field, FieldError>of(
                        field -> {
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
                        })
                        .unchecked()
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
