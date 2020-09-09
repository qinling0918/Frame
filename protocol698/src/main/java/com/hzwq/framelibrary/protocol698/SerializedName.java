package com.hzwq.framelibrary.protocol698;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE,METHOD,FIELD,CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializedName {

  /**
   * @return the desired name of the field when it is serialized or deserialized
   */
  int value();
  /**
   * @return the alternative names of the field when it is deserialized
   */
  String[] alternate() default {};
}