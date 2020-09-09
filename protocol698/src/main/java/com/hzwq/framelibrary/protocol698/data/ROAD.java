package com.hzwq.framelibrary.protocol698.data;


import android.support.annotation.NonNull;

import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/11 11:16
 * Description:  记录型对象属性描述符ROAD
 */
public class ROAD extends CSD {
    /**
     * 对象属性描述符
     */
    private OAD oad;
    //private Array<OAD> oadArray = new Array<OAD>
    /**
     * 关联对象属性描述符  SEQUENCE OF OAD
     */
    private OAD[] oadArr;

    public ROAD(OAD oad, OAD... oadArr) {
        this.oad = oad;
        this.oadArr = oadArr;
    }
    public ROAD(String oadHexStr, String... oadHexStrs) {
        OAD[] oads = new OAD[oadHexStrs.length];
        for (int i = 0; i < oadHexStrs.length; i++) {
            oads[i] = new OAD(oadHexStrs[i]);
        }
        this.oad = new OAD(oadHexStr);
        this.oadArr = oads;
    }


    @Override
    public String toHexString() {
        return oad.toHexString()+toString4Array(oadArr) ;
    }

    @Override
    protected int csdType() {
        return CHOICE_ROAD;
    }

    @NonNull
    private String getString(OAD[] oadArr) {
        StringBuffer stringBuffer = new StringBuffer();
        if (oadArr!=null && oadArr.length != 0){
            int len = oadArr.length;
            stringBuffer.append(NumberConvert.toHexStr(len,2));
            for (int i = 0; i < len ; i++) {
                stringBuffer.append(oadArr[i].toString());
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public int dataType() {
        return 82;
    }


}
