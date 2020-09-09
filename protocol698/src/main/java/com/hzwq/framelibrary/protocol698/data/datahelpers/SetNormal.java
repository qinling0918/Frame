package com.hzwq.framelibrary.protocol698.data.datahelpers;


/**
 * Created by qinling on 2018/5/11 11:39
 * Description:
 */

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;


public class SetNormal implements IHex {
    private OAD oad;
    private Data data;

    public SetNormal(OAD oad, Data data) {
        this.oad = oad;
        this.data = data;
    }
    public SetNormal(String oad, Data data) {
        this.oad = new OAD(oad);
        this.data = data;
    }

    @Override
    public String toHexString() {
        return oad.toHexString()
                + data.dataTypeStr() + data.toHexString();
    }
}

