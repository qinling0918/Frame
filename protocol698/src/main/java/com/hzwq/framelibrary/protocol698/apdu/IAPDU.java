package com.hzwq.framelibrary.protocol698.apdu;


import com.hzwq.framelibrary.common.IHex;

/**
 * Created by qinling on 2018/5/18 10:56
 * Description: 数据域
 */
public interface IAPDU extends IHex {



   // String format(String apduStr);

   /* default String toString4Array(IData... datas){
        StringBuffer stringBuffer = new StringBuffer();
        if (datas != null && datas.length != 0) {
            int len = datas.length;
            stringBuffer.append(NumberConvert.toHexStr(len, 2));
            for (int i = 0; i < len; i++) {
                stringBuffer.append(datas[i].toString());
            }
        }
        return stringBuffer.toString().toUpperCase(Locale.CHINA);
    }*/
}
