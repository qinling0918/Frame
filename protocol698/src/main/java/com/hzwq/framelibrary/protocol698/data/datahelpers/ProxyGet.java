package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;


import static com.hzwq.framelibrary.protocol698.data.Data.toString4Array;

/**
 * Created by qinling on 2018/5/16 15:32
 * Description:
 * <p>
 * 代理若干个服务器的对象属性读取  SEQUENCE OF
 *      一个目标服务器地址         TSA，
 *      代理一个服务器的超时时间   long-unsigned， —单位：秒，0表示对于某个服务器的代理超时时间由服务器自行控制。
 *      若干个对象属性描述符       SEQUENCE OF OAD
 * }
 */
public class ProxyGet implements IHex {
    private TSA tsa;
    private LongUnsigned time;
    private OAD[] oads;

    public ProxyGet(TSA tsa, LongUnsigned time, OAD...oads) {
        this.tsa = tsa;
        this.time = time;
        this.oads = oads;
    }

    @Override
    public String toHexString() {
        return tsa.toHexString()+time.toHexString()+ toString4Array(oads);
    }
}
