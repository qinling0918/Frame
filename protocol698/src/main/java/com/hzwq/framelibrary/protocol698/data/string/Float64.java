package com.hzwq.framelibrary.protocol698.data.string;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;

import java.math.BigInteger;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description: float64	24	octet-string（SIZE（8））
 */
public class Float64 extends Data {
    @Override
    public int dataType() {
        return 24;
    }

    public static final long MIN_VALUE = 0x0000000000000000L;
    public static final long MAX_VALUE = 0xffffffffffffffffL;
    public static final String MAX_VALUE_STR = "ffffffffffffffff";
    public static final String MIN_VALUE_STR = "0";

    private final BigInteger bigInteger;

    /*public Float64(long value) {
        bigInteger = new BigInteger(Long.toHexString(value),16);
    }*/

    public Float64(String valueHexStr) {
        checkNull(valueHexStr);
        // 判断是否是8字节 ，且是16进制，不带符号（负号）
        if (valueHexStr.length()!= 16 ||!isHexUnsignedStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,需要8字节 16进制字符串");
        }
        BigInteger bigInt = new BigInteger(valueHexStr,16);
        //若该值小于最大值 MAX_VALUE
        boolean isRegion_max = bigInt.compareTo(new BigInteger(MAX_VALUE_STR,16))==-1;
        valueHexStr = isRegion_max ? valueHexStr : MAX_VALUE_STR;

        //若该值小于最小值 MIN_VALUE
        boolean isRegion_min = bigInt.compareTo(new BigInteger(MIN_VALUE_STR,16))==-1;
        valueHexStr = isRegion_min ? MIN_VALUE_STR : valueHexStr;
        bigInteger = new BigInteger(valueHexStr,16);
    }



    @Override
    public String toHexString() {
        return NumberConvert.addZeroToStringLeft(bigInteger.toString(16),16);
    }

    public byte[] getBytes() {
        return NumberConvert.hexStringToBytes(toString());
    }
}
