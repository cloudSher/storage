package com.eternity.storage.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by cloudsher on 2016/8/10.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Factory {

    String value() default "";
}
