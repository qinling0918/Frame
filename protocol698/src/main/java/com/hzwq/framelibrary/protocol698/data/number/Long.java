package com.hzwq.framelibrary.protocol698.data.number;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description:  long	16	16比特位整数（long）	-32768…32767
 * 2字节
 */
public class Long extends Data {
    @Override
    public int dataType() {
        return 16;
    }

    public static final short MIN_VALUE = -32768;
    public static final short MAX_VALUE = 32767;

    private final int value;

    public Long(short value) {
        this.value = value;
    }

    public Long(@IntRange(from = MIN_VALUE, to = MAX_VALUE) int value) {
        this.value = value;
    }

    public Long(String valueHexStr) {
        checkNull(valueHexStr);
        if (valueHexStr.length() != 4 || !NumberConvert.isHexStr(valueHexStr)) {
            throw new IllegalArgumentException("参数异常,需要2字节 16进制字符串");
        }
        long num = java.lang.Long.parseLong(valueHexStr, 16);
        value = (short) num;

    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value, 4);
    }

    public int getValue() {
        return value;
    }
}
