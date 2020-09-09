package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OMD;


public class ActionNormal implements IHex {
    private OMD omd;
    private Data data;

    public ActionNormal(OMD oad, Data data) {
        this.omd = oad;
        this.data = data;
    }

    @Override
    public String toHexString() {
        return omd.toHexString()
                + data.dataTypeStr() + data.toHexString();
    }
}

