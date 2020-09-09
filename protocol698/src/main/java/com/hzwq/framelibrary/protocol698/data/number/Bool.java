package com.hzwq.framelibrary.protocol698.data.number;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/12 17:36
 * Description:  布尔值（BOOLEAN）
 */
public class Bool extends Data {
    @Override
    public int dataType() {
        return 3;
    }

    private final int value;
    public Bool(@IntRange(from = 0,to = 1) int value) {
        this.value = value;
    }
    public Bool(boolean value) {
        this.value = value ? 1 : 0;
    }

    public Bool(String valueStr) {
        if (valueStr.length()!= 2 || !NumberConvert.isBinaryStr(valueStr)){
            throw new IllegalArgumentException("参数异常,对象标识 需要1字节 2进制字符串");
        }
        value = parseInt(valueStr,16);

    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value,2);
    }



}
