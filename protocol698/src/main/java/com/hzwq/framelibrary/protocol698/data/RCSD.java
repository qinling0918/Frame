package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.common.util.NumberConvert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by qinling on 2018/5/11 11:40
 * Description:
 * RCSD用于选择记录型对象属性中记录的某列或某几列内容，
 * 即二维记录表的列选择，
 * 例如：事件记录或冻结数据记录中的某关联对象属性数据列。
 * 当无一个OAD时，RCSD=0，即SEQUENCE OF的数据项个数为0，表示“不选择（即全选）”。
 */

// TODO 应该是个抽象类,通过Factory类  根据index 生成对应的子类
public class RCSD extends Array<CSD> {
    private static final String NO_SELECTOR = "00";

    /**
     * 记录列选择描述符RCSD
     */
    //private CSD[] csds;
    private RCSD() {
        //this.csds = csds;
    }

    public static RCSD newInstance() {
        return new RCSD();
    }

    public RCSD addOAD(String... oadHexStrs) {
        CSD[] oads = new OAD[oadHexStrs.length];
        for (int i = 0; i < oadHexStrs.length; i++) {
            oads[i] = new OAD(oadHexStrs[i]);
        }
        this.addAll(Arrays.asList(oads));
        return this;
    }

    public RCSD addROAD(String oadHexStr, String... oadHexStrs) {
        ROAD road = new ROAD(oadHexStr, oadHexStrs);
        this.add(road);
        return this;
    }

    public RCSD addROAD(ROAD... roads) {
        this.addAll(Arrays.asList(roads));
        return this;
    }
 /*   public RCSD(CSD... csds) {
        this.csds = csds;
    }
    public RCSD(CSD[] oads,CSD... roads) {
        this.csds = csds;
    }*/

/*    public static RCSD newInstance(String... oadHexStrs){
        OAD[] oads = new OAD[oadHexStrs.length];
        for (int i = 0; i < oadHexStrs.length; i++) {
            oads[i] = new OAD(oadHexStrs[i]);
        }
        return new RCSD(oads);
    }
    public static RCSD newInstance(String[] oadHexStrs,ROAD... roads){
        OAD[] oads = new OAD[oadHexStrs.length];
        for (int i = 0; i < oadHexStrs.length; i++) {
            oads[i] = new OAD(oadHexStrs[i]);
        }
        return new RCSD(oads);
    }*/

    /*  public RCSD(String... oadHexStrs) {
          OAD[] oads = new OAD[oadHexStrs.length];
          for (int i = 0; i < oadHexStrs.length; i++) {
              oads[i] = new OAD(oadHexStrs[i]);
          }
          this.csds = oads;
      }
      public RCSD(ROAD... csds) {
          this.csds = csds;
      }*/
    @Override
    public String toHexString() {
        //当无一个OAD时，RCSD=0，即SEQUENCE OF的数据项个数为0，表示“不选择（即全选）”。
        if (isEmpty()) return NO_SELECTOR;
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(NumberConvert.toHexStr(size(), 2));
        for (CSD csd : this) {
            stringBuffer.append(csd.csdTypeStr());
            stringBuffer.append(csd.toHexString());
        }
        return stringBuffer.toString().toUpperCase(Locale.CHINA);
    }


    @Override
    public int dataType() {
        return 96;
    }


}
