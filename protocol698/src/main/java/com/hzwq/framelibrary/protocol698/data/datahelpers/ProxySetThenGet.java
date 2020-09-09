package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;


/**
 * Created by qinling on 2018/5/16 15:57
 * Description:
 *
 * 代理若干个服务器的对象属性设置后读取  SEQUENCE OF
 {
 一个目标服务器地址          TSA，
 代理一个服务器的超时时间    long-unsigned，
 若干个对象属性的设置后读取  SEQUENCE OF
 {
 设置的对象属性描述符   OAD，
 及其设置数值           Data，
 读取的对象属性描述符   OAD，
 及其延时读取时间       unsigned
 }
 }
 */
public class ProxySetThenGet implements IHex {
    private TSA tsa;
    private LongUnsigned outTime;
    private SetThenGet[] setThenGets;

    public ProxySetThenGet(TSA tsa, LongUnsigned outTime, SetThenGet... setThenGets) {
        this.tsa = tsa;
        this.outTime = outTime;
        this.setThenGets = setThenGets;
    }

    @Override
    public String toHexString() {
        return tsa.toHexString()+outTime.toHexString()+ Data.toString4Array(setThenGets);
    }
}
