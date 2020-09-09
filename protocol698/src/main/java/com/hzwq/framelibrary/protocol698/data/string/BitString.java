package com.hzwq.framelibrary.protocol698.data.string;


import android.support.annotation.NonNull;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;

import java.util.BitSet;


/**
 * Created by qinling on 2018/5/12 17:37
 * Description:  比特位串（BIT STRING）
 * // 紧跟着的16进制数 就是比特串的bit 数， 所以应该/8
 */
public class BitString extends Data implements CharSequence {
    @Override
    public int dataType() {
        return 4;
    }

    private final BitSet bitSet;
    protected final Integer value;
    private final String valueStr;

    public boolean isBinary() {
        return isBinary;
    }

    private final boolean isBinary;

    public BitString(int value) {
        this.value = value;
        this.valueStr = Integer.toBinaryString(value);
        isBinary = true;
        bitSet = new BitSet(value);
    }

    public BitString(String valueStr) {
        checkNull(valueStr);
        isBinary = NumberConvert.isBinaryUnsignedStr(valueStr);
        int radix = isBinary ? 2 : 16;

        value = parseInt(valueStr, radix);
        this.valueStr = valueStr;
        bitSet = new BitSet(value);
    }

    @Override
    public int length() {
        return valueStr.length();
    }

    @Override
    public char charAt(int index) {
        return valueStr.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return valueStr.substring(start, end);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String toBinaryString() {
        return isBinary ? valueStr : NumberConvert.hexStrToBinaryStr(valueStr);
    }

    /**
     * 转成 字节单位 的16进制
     *
     * @return 16进制字符串
     */
    @Override
    public String toHexString() {

        return NumberConvert.toHexStr(valueStr.length() / 2, 2) +
                (isBinary ? NumberConvert.binaryStrToHexStrWithFormat(valueStr)
                        : NumberConvert.isHexUnsignedStr(valueStr)
                        ? valueStr
                        : "格式有误" + valueStr
                ) ;
    }
}
