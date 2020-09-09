package com.hzwq.framelibrary.protocol698.data.number;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

import java.lang.Long;
import java.math.BigInteger;

/**
 * Created by qinling on 2018/5/12 17:45
 * Description:  long64	20	64比特位整数（Integer64）	-2^63…2^63-1
 */
public class Long64 extends Data {
    @Override
    public int dataType() {
        return 20;
    }

    public static final long MIN_VALUE = 0x8000000000000000L;
    public static final long MAX_VALUE = 0x7fffffffffffffffL;

    private final long value;
    private final BigInteger bigInteger;

    public Long64(long value) {
        this.value = value;
        bigInteger = new BigInteger(Long.toHexString(value), 16);
    }

    public Long64(String valueHexStr) {
        checkNull(valueHexStr);
        // todo  没有添加8字节判断
        if (/*valueHexStr.length()!= 16 ||*/ !NumberConvert.isHexStr(valueHexStr)) {
            throw new IllegalArgumentException("参数异常,需要8字节 16进制字符串");
        }
       /* if (valueHexStr.length()!= 16 || !NumberConvert.isHexStr(valueHexStr)){
            throw new IllegalArgumentException("参数异常,需要8字节 16进制字符串");
        }*/
        bigInteger = new BigInteger(valueHexStr, 16);
        value = bigInteger.longValue();
    }

    @Override
    public String toString() {
        return "Long64{" +
                "value=" + value +
                ", bigInteger=" + bigInteger +
                '}';
    }

    public long getValue() {
        return value;
    }

    public BigInteger getBigIntegerValue() {
        return bigInteger;
    }

    @Override
    public String toHexString() {
        return NumberConvert.addZeroToStringLeft(bigInteger.toString(16), 16);
    }
}
