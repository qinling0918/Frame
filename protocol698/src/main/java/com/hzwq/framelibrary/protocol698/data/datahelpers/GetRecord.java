package com.hzwq.framelibrary.protocol698.data.datahelpers;/*
package com.sgcc.pda.ui.test.frame698.data;


/**
 * Created by qinling on 2018/5/11 11:39
 * Description:
 */

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;

public class GetRecord implements IHex {


    private OAD oad;
    private RSD rsd;
    private RCSD rcsd;

    public GetRecord(OAD oad, RSD rsd, RCSD rcsd) {
       /* this.oad = null== oad ?new OAD() : oad;
        this.rsd = null== rsd ?new RSD() : rsd;
        this.rcsd = null== rcsd ?new RCSD() : rcsd;*/
        this.oad = oad;
        this.rsd = rsd;
        this.rcsd = rcsd;
    }

    public void setOad(OAD oad) {
        this.oad = oad;
    }

    public void setRsd(RSD rsd) {
        this.rsd = rsd;
    }

    public void setRcsd(RCSD rcsd) {
        this.rcsd = rcsd;
    }
    @Override
    public String toHexString() {
        return oad.toHexString()+rsd.toHexString()+rcsd.toHexString();
    }
}

