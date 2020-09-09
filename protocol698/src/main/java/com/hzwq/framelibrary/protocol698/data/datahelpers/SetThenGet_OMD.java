package com.hzwq.framelibrary.protocol698.data.datahelpers;/*
package com.sgcc.pda.ui.test.frame698.data;



import com.hzwq.framelibrary.frame698.data.Data;
import com.hzwq.framelibrary.frame698.data.IData;
import com.hzwq.framelibrary.frame698.data.OAD;
import com.hzwq.framelibrary.frame698.data.noUse.OMD;
import com.hzwq.framelibrary.frame698.data.number.Unsigned;

/**
 * 一个设置的对象方法描述符   OMD，
 * 数据                 Data，
 * 一个读取的对象属性   OAD，
 * 延时读取时间         unsigned
 */

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.OMD;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;


public class SetThenGet_OMD implements IHex {
    private OMD omd;
    private Data data;
    private OAD oadGet;
    private Unsigned time;

    public SetThenGet_OMD(OMD omd, Data data, OAD oadGet, Unsigned time) {
        this.omd = omd;
        this.data = data;
        this.oadGet = oadGet;
        this.time = time;
    }

    @Override
    public String toHexString() {
        return omd.toHexString() + data.toHexString()+oadGet.toHexString()+time.toHexString();
    }
}

