package com.hzwq.framelibrary.protocol698.data;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.util.NumberConvert;

import java.util.Objects;


/**
 * Created by qinling on 2018/5/11 9:36
 * Description:
 */
public class OAD extends CSD {
    /**
     * 对象标识符
     */
    private OI oi ;
    /**
     * 表示对象属性编号，取值0…31，
     * 其中0表示整个对象属性，即对象的所有属性；
     */
    private int  attributeTag ; //属性标识
    private String  attributeStr ; //属性标识以及特征
    /**
     * 属性特征是对象同一个属性在不同快照环境下取值模式，
     * 取值0…7，特征含义在具体类属性中描述。
     */
    private int  attributeCharacter ; //属性特征
    /**
     * 00H表示整个属性全部内容。
     * 如果属性是一个结构或数组，01H指向对象属性的第一个元素；
     * 如果属性是一个记录型的存储区，非零值n表示最近第n次的记录。
     */
    private int  elementIndex ; //属性内元素索引
    private String  elementIndexStr ; //属性内元素索引

    public OAD() {
        this("0000",00,00,00);
    }


    /**
     * @param oadHexStr 对象标识
     */
   /* public OAD(String oistr) {
        this(oistr,00,00,00);
    }*/
    public OAD(String oadHexStr) {
        if (oadHexStr.length()!=8 || !NumberConvert.isHexStr(oadHexStr)){
            throw new IllegalArgumentException("参数异常,需要4字节 16进制字符串");
        }
       init(oadHexStr.substring(0,4),oadHexStr.substring(4,6),oadHexStr.substring(6,8));
    }
    /**
     * @param oistr 对象标识 2字节 16进制字符串
     * @param elementIndexHexStr 属性内元素索引   1字节 16进制字符串
     */
    public OAD(String oistr,String elementIndexHexStr) {
        this(oistr,"00",elementIndexHexStr);
    }
    /**
     * @param oistr 对象标识
     * @param attributeHexStr 一字节 16进制 属性标识以及特征
     * @param elementIndexHexStr 属性内元素索引   1字节 16进制字符串
     */
    public OAD(String oistr,String attributeHexStr,String elementIndexHexStr) {
        init(oistr, attributeHexStr, elementIndexHexStr);
    }

    private void init(String oistr, String attributeHexStr, String elementIndexHexStr) {
        if (oistr.length()!=4 || !NumberConvert.isHexStr(oistr)){
            throw new IllegalArgumentException("参数异常,对象标识 需要2字节 16进制字符串");
        }
        if (attributeHexStr.length()!=2 || !NumberConvert.isHexStr(attributeHexStr)){
            throw new IllegalArgumentException("参数异常,属性标识及其特征 需要1字节 16进制字符串");
        }
        if (elementIndexHexStr.length()!=2 || !NumberConvert.isHexStr(elementIndexHexStr)){
            throw new IllegalArgumentException("参数异常,属性内元素索引 需要1字节 16进制字符串");
        }
        String binaryStr = NumberConvert.toBinaryStr(Integer
                .parseInt(attributeHexStr,16),8);

        String attributeTagStr = binaryStr.substring(3,8);
        String attributeCharacterStr = binaryStr.substring(0,2);

        this.elementIndexStr = elementIndexHexStr;
        this.attributeStr = attributeHexStr;

        this.oi = new OI(oistr);
        this.attributeTag = Integer.parseInt(attributeTagStr,16);
        this.attributeCharacter = Integer.parseInt(attributeCharacterStr,16);
        this.elementIndex = Integer.parseInt(elementIndexHexStr,16);
    }

    public OAD(String oistr,
               @IntRange(from = 0,to = 31) int attributeTag,
               @IntRange(from = 0,to = 7) int attributeCharacter,
               @IntRange(from = 0,to = 255) int elementIndex) {
        this(new OI(oistr),attributeTag,attributeCharacter,elementIndex);

    }
    public OAD(OI oi,
               @IntRange(from = 0,to = 31) int attributeTag,
               @IntRange(from = 0,to = 7) int attributeCharacter,
               @IntRange(from = 0,to = 255) int elementIndex) {
        this.oi = oi;
        this.attributeTag = attributeTag;
        this.attributeCharacter = attributeCharacter;
        this.elementIndex = elementIndex;

        this.elementIndexStr = NumberConvert.toHexStr(elementIndex,2);
        String attributeBinaryStr = NumberConvert.toBinaryStr(attributeCharacter,3)
                + NumberConvert.toBinaryStr(attributeTag,5);
        this.attributeStr = NumberConvert.binaryStrToHexStr(attributeBinaryStr);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAD oad = (OAD) o;
        return attributeTag == oad.attributeTag &&
                attributeCharacter == oad.attributeCharacter &&
                elementIndex == oad.elementIndex &&
                Objects.equals(oi, oad.oi) &&
                Objects.equals(attributeStr, oad.attributeStr) &&
                Objects.equals(elementIndexStr, oad.elementIndexStr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(oi, attributeTag, attributeStr, attributeCharacter, elementIndex, elementIndexStr);
    }

    public OI getOi() {
        return oi;
    }
    public String getOIString() {
        return oi.toHexString();
    }

    public int getAttributeTag() {
        return attributeTag;
    }

    public int getAttributeCharacter() {
        return attributeCharacter;
    }

    public int getElementIndex() {
        return elementIndex;
    }


    @Override
    public String toHexString() {
        return getOIString()+attributeStr+elementIndexStr;
    }

    @Override
    protected int csdType() {
        return CHOICE_OAD;
    }

    @Override
    public int dataType() {
        return 81;
    }


}
