package com.hzwq.framelibrary.protocol698.data;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OI;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;


/**
 * Created by qinling on 2018/5/11 9:36
 * Description:  对象方法描述符OMD（Object Method Descriptor）
 */
public class OMD extends Data {
    /**
     * 对象标识符
     */
    private OI oi;

    /**
     * 方法标识，取值1…255，
     * 对象方法编号
     */
    private Unsigned methodTag;
    /**
     * //操作模式值默认为0
     */
    private Unsigned operateMode;


    public OMD() {
        this("00001000");
    }


    /**
     * @param omdHexStr 对象标识
     */
    public OMD(String omdHexStr) {
        if (omdHexStr.length() != 8 || !NumberConvert.isHexStr(omdHexStr)) {
            throw new IllegalArgumentException("参数异常,需要4字节 16进制字符串");
        }
        init(omdHexStr.substring(0, 4), omdHexStr.substring(4, 6), omdHexStr.substring(6, 8));
    }

    private void init(String oistr, String methodTag, String operateMode) {
        if (oistr.length() != 4 || !NumberConvert.isHexStr(oistr)) {
            throw new IllegalArgumentException("参数异常,对象标识 需要2字节 16进制字符串");
        }
        if (methodTag.length() != 2 || !NumberConvert.isHexStr(methodTag)) {
            throw new IllegalArgumentException("参数异常,方法标识及其特征 需要1字节 16进制字符串");
        }
        if (operateMode.equals("00")) {
            throw new IllegalArgumentException("参数异常,操作模式通常为0");
        }

        this.oi = new OI(oistr);
        this.methodTag = new Unsigned(methodTag);
        this.operateMode = new Unsigned(0);

    }

    public OMD(String oistr, @IntRange(from = 1, to = 255) int methodTag) {
        this.oi = new OI(oistr);
        this.methodTag = new Unsigned(methodTag);
        this.operateMode = new Unsigned(0);
    }


    public OI getOi() {
        return oi;
    }

    public void setOi(OI oi) {
        this.oi = oi;
    }

    public int getMethodTag() {
        return methodTag.getValue();
    }

    public void setMethodTag(@IntRange(from = 1, to = 255) int methodTag) {
        setMethodTag(new Unsigned(methodTag));
    }

    public void setMethodTag(Unsigned methodTag) {
        this.methodTag = methodTag;
    }

    @Override
    public int dataType() {
        return 83;
    }

    @Override
    public String toHexString() {
        return oi.toHexString() + methodTag.toHexString() + operateMode.toHexString();
    }
}
