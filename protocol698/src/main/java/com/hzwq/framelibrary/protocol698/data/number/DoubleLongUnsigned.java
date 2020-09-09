package com.hzwq.framelibrary.protocol698.data.number;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by qinling on 2018/5/12 17:38
 * Description: 32比特位正整数（double-long-unsigned）	0…2^32 -1
 */
public class DoubleLongUnsigned  extends Data  {
    @Override
    public int dataType() {
        return 6;
    }
    public static final int  MIN_VALUE = 0;
    public static final long  MAX_VALUE = 0xffffffffL;



    private final long value;
    public DoubleLongUnsigned(long value) {
        this.value = value;
    }

    public DoubleLongUnsigned(String valueHexStr) {
        checkNull(valueHexStr);
        if (/*valueHexStr.length()!= 8 ||*/ !NumberConvert.isHexStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,对象标识 需要4字节 16进制字符串");
        }
        long num = java.lang.Long.parseLong(valueHexStr,16);
        num = num>MAX_VALUE ? MAX_VALUE : num;
        value = num<MIN_VALUE ? MIN_VALUE : num;

    }

    @Override
    public String toHexString() {
        return NumberConvert.addZeroToStringLeft(
                BigInteger.valueOf(value).toString(16),8);
    }

    public long getValue() {
        return value;
    }
}
