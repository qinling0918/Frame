package com.hzwq.framelibrary.protocol698.data.string;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/12 17:39
 * Description: 8比特位位组（字节）串（OCTET STRING）
 */
public class OctetString extends Data implements CharSequence {
    @Override
    public int dataType() {
        return 9;
    }

  //  public static final short MIN_VALUE = 0;
   // public static final short MAX_VALUE = 0xff; // 0b11111111

    //private final byte[] bytes;
    private final String string;

    public OctetString(String hexStr) {
        checkNull(hexStr);
        if (!NumberConvert.isHexUnsignedStr(hexStr)) {
            throw new IllegalArgumentException("参数异常,需要八位一组的 2进制字符串");
        }
        this.string = hexStr;
    }


    public OctetString(byte[] bytes) {
        //this.bytes = bytes;
        this.string = NumberConvert.bytesToBinaryString(bytes);
       // System.out.println(" --  "+ string);
    }

    /**
     *  获取 字节数组
     * @return
     */
    /**
     * public byte[] getBytes() {
     * return bytes;
     * }
     */


    @Override
    public int length() {
        return string.length();
    }

    @Override
    public char charAt(int index) {
        return string.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return string.substring(start, end);
    }

    @Override
    public String toHexString() {
       // String hexStr = NumberConvert.binaryStrToHexStr(string);
        return NumberConvert.toHexStr(string.length()/2, 2)
                + string;
    }
}
