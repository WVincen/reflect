package com.example.reflect.exercise.validate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author vincent
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {
    String message() default "";

    IntegrationResultCode resultCode() default IntegrationResultCode.UNKNOWN;
}
