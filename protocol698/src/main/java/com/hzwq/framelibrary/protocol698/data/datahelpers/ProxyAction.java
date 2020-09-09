package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;


/**
 * Created by qinling on 2018/5/16 15:32
 * Description:

 * {
 * 一个目标服务器地址            TSA，
 * 代理一个服务器的超时时间      long-unsigned，
 * 若干个对象方法描述符及其参数  SEQUENCE OF
 * {
 * 对象方法描述符  OMD，
 * 及其方法参数    Data
 * }
 */
public class ProxyAction implements IHex {
    private TSA tsa;
    private LongUnsigned time;
    private ActionNormal[] actionNormals;

    public ProxyAction(TSA tsa, LongUnsigned time, ActionNormal... actionNormals) {
        this.tsa = tsa;
        this.time = time;
        this.actionNormals = actionNormals;
    }

    @Override
    public String toHexString() {
        return tsa.toHexString() + time.toHexString() + Data.toString4Array(actionNormals);
    }
}
