package com.hzwq.framelibrary.protocol698.data.number;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

import java.lang.Integer;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description:  unsigned	17	8比特位正整数（Unsigned8）	0…255
 */
public class Unsigned extends Data {
    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 255;

    @Override
    public int dataType() {
        return 17;
    }

    private final int value;

    public Unsigned(@IntRange(from = MIN_VALUE, to = MAX_VALUE) int value) {
        this.value = value;
    }

    public Unsigned(String valueHexStr) {
        checkNull(valueHexStr);
        if (valueHexStr.length() != 2 || !NumberConvert.isHexStr(valueHexStr)) {
            throw new IllegalArgumentException("参数异常,对象标识 需要1字节 16进制字符串");
        }
        value = Integer.parseInt(valueHexStr, 16);
    }

    public static String toUnsignedString(int value) {
        value = value % MAX_VALUE + 1;
        return NumberConvert.toHexStr(value, DataType.UNSIGNED.getByteSize()*2);
    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value, DataType.UNSIGNED.getByteSize()*2);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Unsigned{" +
                "value=" + value +
                '}';
    }
}
