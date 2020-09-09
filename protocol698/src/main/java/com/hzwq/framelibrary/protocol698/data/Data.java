package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.number.Bool;
import com.hzwq.framelibrary.protocol698.data.noUse.Structure;
import com.hzwq.framelibrary.protocol698.data.string.UTF8String;
import com.hzwq.framelibrary.protocol698.data.number.DoubleLong;
import com.hzwq.framelibrary.protocol698.data.number.DoubleLongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.Long64;
import com.hzwq.framelibrary.protocol698.data.number.Long64Unsigned;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;
import com.hzwq.framelibrary.protocol698.data.string.BitString;
import com.hzwq.framelibrary.protocol698.data.string.Float32;
import com.hzwq.framelibrary.protocol698.data.string.Float64;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;
import com.hzwq.framelibrary.protocol698.data.string.VisibleString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by qinling on 2018/5/14 9:51
 * Description:
 */
public abstract class Data implements IData {
    public static final Data NULL = com.hzwq.framelibrary.protocol698.data.noUse.NULL.getInstance();

    public abstract int dataType();

    public String dataTypeStr() {
        return dataType() == -1 ? "" : NumberConvert.toHexStr(dataType(), 2);
    }

    protected void checkNull(String binString) {
        if (null == binString || binString.length() == 0) {
            throw new NullPointerException("参数异常,不能为null");
        }
    }

    protected Integer parseInt(String numStr, int radix) {
        try {
            return Integer.parseInt(numStr, radix);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected boolean isHexUnsignedStr(String valueHexStr) {
        return NumberConvert.isHexUnsignedStr(valueHexStr);
    }

    protected int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }

    protected byte[] hexStringToBytes(String hexString) {
        return NumberConvert.hexStringToBytes(hexString);
    }

    public static String toString4Array(IHex[] datas) {
        return toString4Array(Arrays.asList(datas));
    }

    public static String toString4Array(Collection<? extends IHex> datas) {
        StringBuffer stringBuffer = new StringBuffer();
        if (datas != null && datas.size() != 0) {
            int len = datas.size();
            stringBuffer.append(NumberConvert.toHexStr(len, 2));
            for (IHex data : datas) {
                stringBuffer.append(data.toHexString());
            }
        }
        return stringBuffer.toString().toUpperCase(Locale.CHINA);
    }

    /**
     * 长度不定,在数据类型后紧接着获取的就是字节数
     */
    public static final int LEN_FOLLOW = -1;
    /**
     * 在数据类型后紧接着获取的不是长度，而是元素个数，然后再每一个去读取每个元素的长度
     */
    public static final int LEN_ELEMENT = -2;
    /**
     * 组合数据类型，类似与结构体（-2），无法从后续字节读取元素个数，但该类型本身就有元素个数 例如 SID（元素个数=2）,SID_MAC（3），
     */
    public static final int LEN_SID = -3;
    public static final int LEN_SID_MAC = -11;
    /**
     * 需要随后的数据长度/8
     */
    public static final int LEN_BIT_STRING = -4;
    /**
     * ROAD       OAD(08) +   SEQUENCE OF OAD  (02) + (N*8)
     */
    public static final int LEN_ROAD = -5;
    /**
     * Region  ENUM(02) + DATA(?) + DATA(?)
     */
    public static final int LEN_REGION = -6;
    /**
     * choice(02) +  Selector1(?)
     */
    public static final int LEN_RSD = -7;
    /**
     * csd       choice(02)  OAD(08)/ROAD(?)
     */
    public static final int LEN_CSD = -8;
    /**
     * RCSD       SEQUENCE OF csd
     */
    public static final int LEN_RCSD = -9;
    /**
     * MS       choice(02)  + ?
     */
    public static final int LEN_MS = -10;

    public enum DataType {

        NULL(0, 0, com.hzwq.framelibrary.protocol698.data.noUse.NULL.class),// NULL
        ARRAY(1, LEN_ELEMENT, Array.class),//array
        STRUCTURE(2, LEN_ELEMENT, Structure.class),//structure
        BOOL(3, 1, Bool.class), //bool
        BIT_STRING(4, LEN_BIT_STRING, BitString.class),//bit-string
        DOUBLE_LONG(5, 4, DoubleLong.class),//double-long
        DOUBLE_LONG_UNSIGNED(6, 4, DoubleLongUnsigned.class),// double-long-unsigned
        OCTET_STRING(9, LEN_FOLLOW, OctetString.class),//octet-string
        VISIBLE_STRING(10, LEN_FOLLOW, VisibleString.class),//VisibleString.class
        UTF8_STRING(12, LEN_FOLLOW, UTF8String.class),//UTF8String.class
        INTEGER(15, 1, Integer.class),//Integer.class
        LONG(16, 2, Long.class),//Long.class
        UNSIGNED(17, 1, Unsigned.class),//Unsigned.class
        LONG_UNSIGNED(18, 2, LongUnsigned.class),//LongUnsigned.class
        LONG64(20, 8, Long64.class),//Long64.class
        LONG64_UNSIGNED(21, 8, Long64Unsigned.class),//Long64Unsigned.class
        ENUM(22, 1, Enum.class),//Enum.class
        FLOAT32(23, 4, Float32.class),//Float32.class
        FLOAT64(24, 8, Float64.class),
        DATE_TIME(25, 10, DateTime.class),//DateTime.class
        DATE(26, 5, Date.class),//date.class
        TIME(27, 3, Time.class),//Time.class
        DATE_TIME_S(28, 7, DateTimeS.class),//DateTimeS.class
        OI(80, 2, OI.class),//OI.class
        OAD(81, 4, OAD.class),//OAD.class

        ROAD(82, LEN_ROAD, ROAD.class),//ROAD.class
        OMD(83, 4, com.hzwq.framelibrary.protocol698.data.OMD.class),//OMD.class
        TI(84, 3, TI.class),//TI.class
        TSA(85, LEN_FOLLOW, TSA.class),//TSA.class
        MAC(86, LEN_FOLLOW, MAC.class),//MAC.class
        RN(87, LEN_FOLLOW, com.hzwq.framelibrary.protocol698.data.data_verification.RN.class),//RN.class
        REGION(88, LEN_REGION, Region.class),//Region.class
        SCALER_UNIT(89, 2, ScalerUnit.class),//ScalerUnit.class   integer（2）+enum（2）

        RSD(90, LEN_RSD, RSD.class),//RSD.class
        CSD(91, LEN_CSD, CSD.class),//CSD.class
        MS(92, LEN_MS, MS.class),//MS.class
        SID(93, LEN_SID, com.hzwq.framelibrary.protocol698.data.data_verification.SID.class),//SID.class
        SID_MAC(94, LEN_SID_MAC, com.hzwq.framelibrary.protocol698.data.data_verification.SID_MAC.class),//SID_MAC.class
        COMDCB(95, 5, COMDCB.class),//COMDCB.class
        RCSD(96, LEN_RCSD, RCSD.class);//RCSD.class

        int dateType;
        int byteSize;
        Class calzz;

        public int getDateType() {
            return dateType;
        }

        public int getByteSize() {
            return byteSize;
        }

        public Class getCalzz() {
            return calzz;
        }

        DataType(int dateType, int byteSize, Class calzz) {
        }
    }
}
