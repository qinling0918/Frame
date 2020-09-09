package com.hzwq.framelibrary.protocol698.data.data_verification;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.number.DoubleLongUnsigned;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;


/**
 * Created by qinling on 2018/5/11 18:22
 * Description: 安全标识
 * 标识       double-long-unsigned，
 * 附加数据   octet-string
 */
public class SID extends Data implements IVerification {

    @Override
    public int dataType() {
        return 93;
    }

    @Override
    public int verificationType() {
        return 3;
    }

    private DoubleLongUnsigned flag;
    private OctetString data;

    public SID(DoubleLongUnsigned flag, OctetString data) {
        this.flag = flag;
        this.data = data;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
