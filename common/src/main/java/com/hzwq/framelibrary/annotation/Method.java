package com.hzwq.framelibrary.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by qinling on 2019/6/3 14:42
 * Description:
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {

    int method() default 1;

}