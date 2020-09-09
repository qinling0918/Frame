package com.hzwq.framelibrary.protocol698.data.data_verification;


import com.hzwq.framelibrary.protocol698.data.MAC;

/**
 * Created by qinling on 2018/5/17 15:24
 * Description: 随机数+数据MAC
 */
public class RN_MAC implements IVerification{
    RN rn;
    MAC mac;

    public RN_MAC(RN rn, MAC mac) {
        this.rn = rn;
        this.mac = mac;
    }

    @Override
    public String toString() {
        return rn.toString()+mac.toString();
    }

    @Override
    public int verificationType() {
        return 2;
    }
}
