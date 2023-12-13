package edu.hw10.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    boolean persist() default false;
    String cacheName() default "";
    int maxSize() default 100;
    String strategy() default "LRU";
}
