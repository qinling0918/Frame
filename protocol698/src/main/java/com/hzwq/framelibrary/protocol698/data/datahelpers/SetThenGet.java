package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;


/**
 * 一个设置的对象属性   OAD，
 * 数据                 Data，
 * 一个读取的对象属性   OAD，
 * 延时读取时间         unsigned
 */
public class SetThenGet implements IHex {
    private OAD oadSet;
    private Data data;
    private OAD oadGet;
    private Unsigned time;

    public SetThenGet(OAD oadSet, Data data, OAD oadGet, Unsigned time) {
        this.oadSet = oadSet;
        this.data = data;
        this.oadGet = oadGet;
        this.time = time;
    }
    public SetThenGet(OAD oadSet, Data data, OAD oadGet) {
        this(oadSet,data,oadGet,new Unsigned(0));

    }
    @Override
    public String toHexString() {
        return oadSet.toHexString() + data.toHexString()+oadGet.toHexString()+time.toHexString();
    }
}

