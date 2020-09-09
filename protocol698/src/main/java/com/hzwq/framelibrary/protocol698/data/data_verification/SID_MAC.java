package com.hzwq.framelibrary.protocol698.data.data_verification;


import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description: 数据验证码
 */
public class SID_MAC extends Data implements IVerification{

    @Override
    public int dataType() {
        return 94;
    }

    @Override
    public int verificationType() {
        return 0;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
