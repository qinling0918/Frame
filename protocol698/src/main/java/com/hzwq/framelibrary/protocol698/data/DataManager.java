package com.hzwq.framelibrary.protocol698.data;

import android.util.SparseArray;
import android.util.SparseIntArray;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.data_verification.RN;
import com.hzwq.framelibrary.protocol698.data.data_verification.SID;
import com.hzwq.framelibrary.protocol698.data.data_verification.SID_MAC;
import com.hzwq.framelibrary.protocol698.data.number.Bool;
import com.hzwq.framelibrary.protocol698.data.noUse.NULL;
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


/**
 * Created by qinling on 2018/5/22 15:07
 * Description:
 */
public class DataManager {
    public static void main(String[] args) {
       // System.out.println(APDUFactory.connect().connectRequest(new ConnectMechanismInfo(new ConnectMechanismInfo.NullSecurity())).toString());
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
     *  choice(02) +  Selector1(?)
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

    private SparseArray<Class> dataClazz = new SparseArray<>();
    private SparseIntArray dataStrLength = new SparseIntArray();

    private static class SingleTon {
        private static DataManager instance = new DataManager();
    }

    public static DataManager getInstance(/*int type*/) {
        return SingleTon.instance;
    }

    private DataManager() {
        initClass();
        initLengths();
    }

    /**
     * 每种数据类型的长度  （是字符串长度，不是字节数）
     *
     * -1 长度不定,在数据类型后紧接着获取的就是长度
     * -2 在数据类型后紧接着获取的不是长度，而是元素个数，然后再每一个去读取每个元素的长度
     * -3 组合数据类型，类似与结构体（-2），无法从后续字节读取元素个数，但该类型本身就有元素个数 例如 SID（元素个数=2）,SID_MAC（3），
     * -4 bitString。需要随后的数据长度/8
     * -5 ROAD       OAD(08) +   SEQUENCE OF OAD  (02) + (N*8)
     * -6 Region  ENUM(02) + DATA(?) + DATA(?)
     * -7 Rsd       choice(02) +  Selector1(?)
     * -8 csd       choice(02)  OAD(08)/ROAD(?)
     * -9 MS       choice(02)  + ?

     */
    private void initLengths() {
        dataStrLength.put(0, 0);// NULL
        dataStrLength.put(1, LEN_ELEMENT);//array
        dataStrLength.put(2, LEN_ELEMENT);//structure
        dataStrLength.put(3, 1); //bool
        dataStrLength.put(4, LEN_BIT_STRING);//bit-string
        dataStrLength.put(5, 4);//double-long
        dataStrLength.put(6, 4);// double-long-unsigned
        dataStrLength.put(9, LEN_FOLLOW);//octet-string
        dataStrLength.put(10, LEN_FOLLOW);//VisibleString.class
        dataStrLength.put(12, LEN_FOLLOW);//UTF8String.class
        dataStrLength.put(15, 1);//Integer.class
        dataStrLength.put(16, 2);//Long.class
        dataStrLength.put(17, 1);//Unsigned.class
        dataStrLength.put(18, 2);//LongUnsigned.class
        dataStrLength.put(20, 8);//Long64.class
        dataStrLength.put(21, 8);//Long64Unsigned.class
        dataStrLength.put(22, 1);//Enum.class
        dataStrLength.put(23, 4);//Float32.class
        dataStrLength.put(24, 8);//Float64.class
        dataStrLength.put(25, 10);//DateTime.class
        dataStrLength.put(26, 5);//DateTime.class
        dataStrLength.put(27, 3);//Time.class
        dataStrLength.put(28, 7);//DateTimeS.class
        dataStrLength.put(80, 2);//OI.class
        dataStrLength.put(81, 4);//OAD.class

        dataStrLength.put(82, LEN_ROAD);//ROAD.class
        dataStrLength.put(83, 4);//OMD.class
        dataStrLength.put(84, 3);//TI.class
        dataStrLength.put(85, LEN_FOLLOW);//TSA.class
        dataStrLength.put(86, LEN_FOLLOW);//MAC.class
        dataStrLength.put(87, LEN_FOLLOW);//RN.class
        dataStrLength.put(88, LEN_REGION);//Region.class
        dataStrLength.put(89, 2);//ScalerUnit.class   integer（2）+enum（2）

        dataStrLength.put(90, LEN_RSD);//RSD.class
        dataStrLength.put(91, LEN_CSD);//CSD.class
        dataStrLength.put(92, LEN_MS);//MS.class
        dataStrLength.put(93, LEN_SID);//SID.class
        dataStrLength.put(94, LEN_SID_MAC);//SID_MAC.class
        dataStrLength.put(95, 5);//COMDCB.class
        dataStrLength.put(96, LEN_RCSD);//RCSD.class
    }

    private void initClass() {
        dataClazz.put(0, NULL.class);
        dataClazz.put(1, Array.class);
        dataClazz.put(2, Structure.class);
        dataClazz.put(3, Bool.class);
        dataClazz.put(4, BitString.class);
        dataClazz.put(5, DoubleLong.class);
        dataClazz.put(6, DoubleLongUnsigned.class);
        dataClazz.put(9, OctetString.class);
        dataClazz.put(10, VisibleString.class);// 0X0A
        dataClazz.put(12, UTF8String.class);//0X0C
        dataClazz.put(15, Integer.class);//0X0F
        dataClazz.put(16, Long.class);//0X10
        dataClazz.put(17, Unsigned.class);//0X11
        dataClazz.put(18, LongUnsigned.class);//0X12
        dataClazz.put(20, Long64.class);//0X14
        dataClazz.put(21, Long64Unsigned.class);//0X15
        dataClazz.put(22, Enum.class);//0X16
        dataClazz.put(23, Float32.class);//0X17
        dataClazz.put(24, Float64.class);//0X18
        dataClazz.put(25, DateTime.class);//0X19
        dataClazz.put(26, Date.class);//0X1A
        dataClazz.put(27, Time.class);//0X1B
        dataClazz.put(28, DateTimeS.class);//0X1C
        dataClazz.put(80, OI.class);//0X50
        dataClazz.put(81, OAD.class);//0X51

        dataClazz.put(82, ROAD.class);//0X52
        dataClazz.put(83, OMD.class);//0X53
        dataClazz.put(84, TI.class);//0X54
        dataClazz.put(85, TSA.class);//0X55
        dataClazz.put(86, MAC.class);//0X56
        dataClazz.put(87, RN.class);//0X57
        dataClazz.put(88, Region.class);//0X58
        dataClazz.put(89, ScalerUnit.class);//0X59

        dataClazz.put(90, RSD.class);//0X5A
        dataClazz.put(91, CSD.class);//0X5B
        dataClazz.put(92, MS.class);//0X5C
        dataClazz.put(93, SID.class);//0X5D
        dataClazz.put(94, SID_MAC.class);//0X5E
        dataClazz.put(95, COMDCB.class);//0X5F
        dataClazz.put(96, RCSD.class);//0X60
    }
    public enum DataType{
        NULL(0, 0,NULL.class),// NULL
        ARRAY(1, LEN_ELEMENT,Array.class),//array
        STRUCTURE(2, LEN_ELEMENT,Structure.class),//structure
        BOOL(3, 1,Bool.class), //bool
        BIT_STRING(4, LEN_BIT_STRING,BitString.class),//bit-string
        DOUBLE_LONG(5, 4,DoubleLong.class),//double-long
        DOUBLE_LONG_UNSIGNED(6, 4,DoubleLongUnsigned.class),// double-long-unsigned
        OCTET_STRING(9, LEN_FOLLOW,OctetString.class),//octet-string
        VISIBLE_STRING(10, LEN_FOLLOW,VisibleString.class),//VisibleString.class
        UTF8_STRING(12, LEN_FOLLOW,UTF8String.class),//UTF8String.class
        INTEGER(15, 1, Integer.class),//Integer.class
        LONG(16, 2, Long.class),//Long.class
        UNSIGNED(17, 1,Unsigned.class),//Unsigned.class
        LONG_UNSIGNED(18, 2,LongUnsigned.class),//LongUnsigned.class
        LONG64(20, 8,Long64.class),//Long64.class
        LONG64_UNSIGNED(21, 8,Long64Unsigned.class),//Long64Unsigned.class
        ENUM(22, 1, Enum.class),//Enum.class
        FLOAT32(23, 4,Float32.class),//Float32.class
        FLOAT64(24, 8,Float64.class),
        DATE_TIME(25, 10,DateTime.class),//DateTime.class
        DATE(26, 5,Date.class),//date.class
        TIME(27, 3,Time.class),//Time.class
        DATE_TIME_S(28, 7,DateTimeS.class),//DateTimeS.class
        OI(80, 2,OI.class),//OI.class
        OAD(81, 4,OAD.class),//OAD.class

        ROAD(82, LEN_ROAD,ROAD.class),//ROAD.class
        OMD(83, 4,OMD.class),//OMD.class
        TI(84, 3,TI.class),//TI.class
        TSA(85, LEN_FOLLOW,TSA.class),//TSA.class
        MAC(86, LEN_FOLLOW,MAC.class),//MAC.class
        RN(87, LEN_FOLLOW,RN.class),//RN.class
        REGION(88, LEN_REGION,Region.class),//Region.class
        SCALER_UNIT(89, 2,ScalerUnit.class),//ScalerUnit.class   integer（2）+enum（2）

        RSD(90, LEN_RSD,RSD.class),//RSD.class
        CSD(91, LEN_CSD,CSD.class),//CSD.class
        MS(92, LEN_MS,MS.class),//MS.class
        SID(93, LEN_SID,SID.class),//SID.class
        SID_MAC(94, LEN_SID_MAC,SID_MAC.class),//SID_MAC.class
        COMDCB(95, 5,COMDCB.class),//COMDCB.class
        RCSD(96, LEN_RCSD,RCSD.class);//RCSD.class

        int dateType;
        int byteSize;
        Class calzz;

        DataType(int dateType, int byteSize,Class calzz) {
        }
    }
    public Class getDataClass(int dataType) {
        return dataClazz.get(dataType);
    }

    public Class getDataClass(String dataTypeHexStr) {
        if (dataTypeHexStr.length() != 2 || !NumberConvert.isHexStr(dataTypeHexStr)) {
            throw new IllegalArgumentException("参数异常,需要1字节 16进制字符串");
        }
        return dataClazz.get(Integer.parseInt(dataTypeHexStr, 16));
    }

    public int getDataByteSize(int dataType) {
        return dataStrLength.get(dataType);
    }

    private Integer getDataByteSize(int dataType, String byteAfterDataTypeHexStr) {
        int size = getDataByteSize(dataType);
        return size < 0 ? getSpecialDataByteSize(dataType,size, byteAfterDataTypeHexStr) : size;
    }

    /**
     * 根据 16进制的数据类型 获取相对的数据长度
     *
     * @param dataTypeHexStr
     * @return
     */

    public Integer getDataByteSize(String dataTypeHexStr) {
        if (dataTypeHexStr.length() < 2 || !NumberConvert.isHexStr(dataTypeHexStr)) {
            throw new IllegalArgumentException("参数异常,只少需要1字节 16进制字符串");
        }
        return getDataByteSize(Integer.parseInt(dataTypeHexStr.substring(0, 2), 16), dataTypeHexStr.substring(2));
    }

    /**
     *
     *
     * @param dataType
     * @param size
     * @param dataTypeHexStr
     * @return >0 值正确， null 或者小于0 异常，获取失败
     */
    private Integer getSpecialDataByteSize(int dataType, int size, String dataTypeHexStr) {
        Integer byteSize = size;
        String hexStr = dataTypeHexStr;
        switch (size) {
            case LEN_FOLLOW:
                byteSize=  Integer.parseInt(hexStr.substring(0, 2));
                break;
            case LEN_ELEMENT:
                // 元素个数
                int elementNum = Integer.parseInt(hexStr.substring(0, 2));
                hexStr = hexStr.substring(2);
                for (int i = 0; i < elementNum; i++) {
                    int bytes = getDataByteSize(hexStr);
                    hexStr = hexStr.substring(bytes*2);
                    byteSize += bytes;
                }
                break;

            case LEN_SID: //  标识 (double-long-unsigned 6) + 附加数据( octet-string )
                int flagSize = getDataByteSize(6);
                hexStr = hexStr.substring(flagSize*2);
                byteSize = flagSize +  getDataByteSize(9);
                break;
            case LEN_SID_MAC:

                break;
            case LEN_BIT_STRING:
                break;
            case LEN_ROAD:
                break;
            case LEN_REGION:
                break;
            case LEN_RSD:
                break;
            case LEN_CSD:
                break;
            case LEN_RCSD:
                break;
            case LEN_MS:
                break;
            default:
                return byteSize;
        }
        return byteSize;
    }
    /** 第一个数据标识开始到最后
     * 例如:8501 00 400C0200 01 0205110411081108110411100000
     * 此处应该是     0205110411081108110411100000*/
    /**
     * getDataStrLength("02","0205110411081108110411100000")
     *
     * @param dataTypeHexStr          数据标识
     * @param byteAfterDataTypeHexStr 第一个数据标识开始到最后
     * @return 对应数据类型的长度  xiao
     */
    public int getDataByteSize(String dataTypeHexStr, String byteAfterDataTypeHexStr) {
        return dataStrLength.get(Integer.parseInt(dataTypeHexStr, 16));
    }


    public static class Item {
        private String hexStr;
        private IData data;
        private Class clazz;
        // Optional
    }
}
