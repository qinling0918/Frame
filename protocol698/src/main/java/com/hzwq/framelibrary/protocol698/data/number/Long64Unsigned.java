package com.hzwq.framelibrary.protocol698.data.number;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

import java.lang.Long;
import java.math.BigInteger;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description: long64-unsigned 21	64比特位正整数（Unsigned64）	0…2^64-1
 */
public class Long64Unsigned extends Data {
    @Override
    public int dataType() {
        return 21;
    }

    public static final long MIN_VALUE = 0x0000000000000000L;
    public static final long MAX_VALUE = 0xffffffffffffffffL;
    public static final String MAX_VALUE_STR = "ffffffffffffffff";
    public static final String MIN_VALUE_STR = "0";

    private final BigInteger bigInteger;
    public Long64Unsigned(long value) {
        bigInteger = new BigInteger(Long.toHexString(value),16);
    }

    public Long64Unsigned(String valueHexStr) {
        checkNull(valueHexStr);
        // todo  没有添加8字节判断
        if (/*valueHexStr.length()!= 16 ||*/!NumberConvert.isHexStr(valueHexStr)){
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
    public String toString() {
        return NumberConvert.addZeroToStringLeft(bigInteger.toString(16),16);
    }

    public BigInteger getValue() {
        return bigInteger;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
