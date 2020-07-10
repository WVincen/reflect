package com.example.reflect.exercise;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author vincent
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TransferETC {
    String name();

    boolean required() default true;
}
