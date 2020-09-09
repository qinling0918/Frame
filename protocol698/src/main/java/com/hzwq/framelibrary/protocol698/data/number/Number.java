package com.hzwq.framelibrary.protocol698.data.number;

import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2019/4/26 18:48
 * Description:
 */
public abstract class Number extends Data implements java.io.Serializable {


    public abstract String toLongStr();

    public abstract String toUnsignedStr();

    public abstract String toLongUnsignedStr();

    public abstract String toLong64UnsignedStr();

    public abstract String toLong64();


    public abstract String doubleValue();





}
