package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description: 目标服务器地址TSA（Target Server Address）
 */
public class TSA extends OctetString {
    public TSA() {
        this("0000");
        // System.out.println(hexStr);
    }
    public TSA(String hexStr) {
        this(NumberConvert.hexStringToBytes(hexStr));
       // System.out.println(hexStr);
    }

    public TSA(byte[] bytes) {
        super(bytes);
        if (bytes.length < 2 || bytes.length > 17) {
            throw new StringIndexOutOfBoundsException("TSA 为2到17字节");
        }
    }

    @Override
    public int dataType() {
        return 85;
    }

  /*  @Override
    public String toHexString() {
        return super.toHexString();
        // todo  数据类型为 OctetString,不确定时候需要体现出来
       // return tsa.dataTypeStr()+ tsa.toHexString();
    }*/
}
