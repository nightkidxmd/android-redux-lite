package com.dreamland.redux.annotation;


import com.dreamland.redux.DummyReducer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by XMD on 2016/6/23.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ReducerClass {
     Class<?>[] value() default {DummyReducer.class};
}
