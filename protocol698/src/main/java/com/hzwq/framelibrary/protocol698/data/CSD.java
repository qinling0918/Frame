package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/11 11:40
 * Description:
 */
public abstract class CSD extends Data {
    public static final int CHOICE_OAD = 0x00;
    public static final int CHOICE_ROAD = 0x01;

    // public static final String CHOICE_OAD = "00";
   // public static final String CHOICE_ROAD = "01";
    @Override
    public int dataType() {
        return 91;
    }

    public static OAD[] createOAD(String... oadHexStrs){
        OAD[] oads = new OAD[oadHexStrs.length];
        for (int i = 0; i < oadHexStrs.length; i++) {
            oads[i] = new OAD(oadHexStrs[i]);
        }
       return oads;
    }
    public static OAD[] createROAD(String... oadHexStrs){
        OAD[] oads = new OAD[oadHexStrs.length];
        for (int i = 0; i < oadHexStrs.length; i++) {
            oads[i] = new OAD(oadHexStrs[i]);
        }
        return oads;
    }
    public String csdTypeStr() {
        return NumberConvert.toHexStr(csdType(),2);
    }
    protected abstract int csdType();
}
