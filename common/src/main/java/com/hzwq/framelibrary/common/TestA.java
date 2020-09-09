package com.hzwq.framelibrary.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by qinling on 2019/4/30 21:32
 * Description:
 */
@Target({TYPE,METHOD,FIELD,CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestA {
    String name();
    int id() default 0;
    Class gid();
}
