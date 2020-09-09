package com.hzwq.framelibrary.protocol698.data.data_verification;


import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description: RN∷=octet-string	ESAM生成用于加密的信息串。
 */
public class RN extends Data implements IVerification {

    @Override
    public int dataType() {
        return 87;
    }
    @Override
    public int verificationType() {
        return 1;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
