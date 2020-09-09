package com.hzwq.framelibrary.protocol698.data.string;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;



/**
 * Created by qinling on 2018/5/12 17:45
 * Description: float32	23	octet-string（SIZE（4））
 */
public class Float32 extends Data {
    @Override
    public int dataType() {
        return 23;
    }

    public static final short  MIN_VALUE = 0;
    public static final short  MAX_VALUE = 0xffffffff;

    private final long value;

    public Float32 (String valueHexStr) {
        checkNull(valueHexStr);
        if (valueHexStr.length()!= 8 || !isHexUnsignedStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,需要4字节 16进制字符串");
        }
        long num = Long.parseLong(valueHexStr,16);
        value = num> MAX_VALUE ? MAX_VALUE : num;

    }

    /**
     *  获取 字节数组
     * @return
     */
    public byte[] getBytes() {
        return NumberConvert.hexStringToBytes(toString());
    }

    @Override
    public String toHexString() {
        return NumberConvert.addZeroToStringLeft(Long.toHexString(value),8);
    }

}
