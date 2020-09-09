package com.hzwq.framelibrary.protocol698.apdu.client.report;

import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.PIID;

/**
 * Created by qinling on 2019/4/30 14:22
 * Description:
 */
public class ReportResponseTransData implements ClientAPDU.ReportReponse, FormatAble {
    private PIID piid;
    public ReportResponseTransData(PIID piid) {
        this.piid = piid;
    }

    public ReportResponseTransData(int piid) {
        this.piid = new PIID(piid);
    }

    public ReportResponseTransData(String piid) {
        this.piid = new PIID(piid);
    }

    public PIID getPiid() {
        return piid;
    }

    public ReportResponseTransData setPiid(PIID piid) {
        this.piid = piid;
        return this;
    }

    @Override
    public String format(String formatAbleStr) {
        return piid.toHexString();
    }

    @Override
    public int type() {
        return REPORT_RESPONSE_TRANS_DATA;
    }

    @Override
    public String toHexString() {
        return piid.toHexString();
    }




}
