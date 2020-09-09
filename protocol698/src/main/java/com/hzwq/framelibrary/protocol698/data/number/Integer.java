package com.hzwq.framelibrary.protocol698.data.number;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description:  8比特位整数（integer） -128…127
 * java 中的Byte
 */
public class Integer extends Data  {
    @Override
    public int dataType() {
        return 15;
    }
    private final int value;
    public Integer(
            @IntRange(from = -128,to = 127) int value) {
        this.value = value;
    }
    public Integer(byte value) {
        this.value = value;
    }
    public Integer(String valueHexStr) {
        checkNull(valueHexStr);
        if (valueHexStr.length()!= 2 || !NumberConvert.isHexStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,对象标识 需要1字节 16进制字符串");
        }
        int num = java.lang.Integer.parseInt(valueHexStr,16);
        //value = num> 0x7f ? num -0xff-1 : num;
        value = (byte)num;

    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value,2);
    }
    public int getValue() {
        return value;
    }


}
