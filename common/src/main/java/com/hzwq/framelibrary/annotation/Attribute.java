package com.hzwq.framelibrary.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by qinling on 2019/6/3 14:42
 * Description:
 */
//@Inherited
@Documented
@Target({FIELD, LOCAL_VARIABLE,METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
    String name() default "";

    int value() default 2;
    //  int dataType();
}