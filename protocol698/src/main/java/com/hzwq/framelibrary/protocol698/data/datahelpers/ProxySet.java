package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

/**
 * Created by qinling on 2018/5/16 15:48
 * Description:  代理若干个服务器的对象属性
 * 一个目标服务器地址        TSA，
 代理一个服务器的超时时间  long-unsigned，
 若干个对象属性描述符及其数据  SEQUENCE OF

 SetNormal
 {
 对象属性描述符  OAD，
 及其数据        Data
 }

 */

public class ProxySet implements IHex {
    //一个目标服务器地址
    private TSA tsa;
    //代理一个服务器的超时时间
    private LongUnsigned outTimme;
    private SetNormal[] setNormals;

    public ProxySet(TSA tsa, LongUnsigned outTimme, SetNormal... setNormals) {
        this.tsa = tsa;
        this.outTimme = outTimme;
        this.setNormals = setNormals;
    }

    @Override
    public String toHexString() {
        return tsa.toHexString()+outTimme.toHexString()+ Data.toString4Array(setNormals);
    }
}
