package com.hzwq.framelibrary.protocol698.data.number;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description:  long-unsigned	18	16比特位正整数（Unsigned16）	0…65535
 */
public class LongUnsigned extends Data {
    @Override
    public int dataType() {
        return 18;
    }


    public static final int  MIN_VALUE = 0;
    public static final int  MAX_VALUE = 65535;

    private final int value;
    public LongUnsigned(@IntRange(from = MIN_VALUE,to = MAX_VALUE) int value) {
        this.value = value;
    }

    public LongUnsigned(String valueHexStr) {
        checkNull(valueHexStr);
        // todo 未判断2字节
        if (/*valueHexStr.length()!= 4 || */!NumberConvert.isHexStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,需要2字节 16进制字符串");
        }
        int num = java.lang.Integer.parseInt(valueHexStr,16);
        num = num> MAX_VALUE ? MAX_VALUE : num;
        value = num< MIN_VALUE ? MIN_VALUE : num;

    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value,4                                          );
    }

    public int getValue() {
        return value;
    }
}
