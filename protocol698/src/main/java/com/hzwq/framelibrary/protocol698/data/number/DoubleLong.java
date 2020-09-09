package com.hzwq.framelibrary.protocol698.data.number;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/12 17:37
 * Description:  32比特位整数（Integer32）  -2^31…2^31-1
 *
 * 同 Integer
 */
public class DoubleLong extends Data {
    public static final int   MIN_VALUE = 0x80000000;
    public static final int   MAX_VALUE = 0x7fffffff;

    @Override
    public int dataType() {
        return 5;
    }

    private final int value;
    public DoubleLong( int value) {
        this.value = value;
    }

    public DoubleLong(String valueHexStr) {
        checkNull(valueHexStr);
        if (/*valueHexStr.length()!= 8 ||*/ !NumberConvert.isHexStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,对象标识 需要4字节 16进制字符串");
        }
        long num = java.lang.Long.parseLong(valueHexStr,16);
        //value = num> 0x7f ? num - 0xff -1 : num;
        value = (int) num;

    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value,8                                          );
    }

    public int getValue() {
        return value;
    }


}
