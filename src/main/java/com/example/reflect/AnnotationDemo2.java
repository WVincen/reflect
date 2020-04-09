package com.example.reflect;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author vincent
 */
public class AnnotationDemo2 {
    @Test
    public void annotationTest() throws Exception {
        example();
    }

    // set values for the annotation
    @Demo(str = "Demo Annotation", val = 100)
    // a method to call in the main
    public static void example() throws Exception {
//        AnnotationDemo2 ob = new AnnotationDemo2();
//        Class c = ob.getClass();
        Class<AnnotationDemo2> c = AnnotationDemo2.class;

        // get the method example
        Method m = c.getMethod("example");

        // get the annotations
        Annotation[] annotation = m.getDeclaredAnnotations();

        // print the annotation
        Arrays.stream(annotation).forEach(System.out::println);

    }
}

// declare a new annotation
@Retention(RetentionPolicy.RUNTIME)
@interface Demo {
    String str();

    int val();
}

