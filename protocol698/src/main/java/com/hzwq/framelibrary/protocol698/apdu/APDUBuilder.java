package com.hzwq.framelibrary.protocol698.apdu;

import android.support.annotation.NonNull;

import com.hzwq.framelibrary.protocol698.data.PIID;

/**
 * Created by qinling on 2019/4/28 19:40
 * Description:
 */
public abstract class APDUBuilder<APDU> implements IAPDU {
    public abstract APDU build();

    @NonNull
    protected PIID checkPIID(PIID piid) {
        return null == piid ? new PIID() : piid;
    }

}
