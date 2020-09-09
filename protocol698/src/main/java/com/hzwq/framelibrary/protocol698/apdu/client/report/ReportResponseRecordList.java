package com.hzwq.framelibrary.protocol698.apdu.client.report;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.base.Base2;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;

import java.util.ArrayList;
import java.util.Arrays;

import static com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU.REPORT_RESPONSE;
import static com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU.ReportReponse.REPORT_RESPONSE_RECORD_LIST;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ReportResponseRecordList extends Base2 implements ClientAPDU.ReportReponse, FormatAble {

    @Override
    public int type() {
        return REPORT_RESPONSE_RECORD_LIST;
    }

    @Override
    public String format(String formatAbleStr) {
        return new Parser(formatAbleStr).toFormatString();
    }

    private ReportResponseRecordList(Builder builder) {
        super(builder);
    }

    public ReportResponseRecordList() {
        super(new Builder());
    }

    public Builder newBulider() {
        return new Builder(this);
    }

    public static final class Builder extends Base2.Builder<ReportResponseRecordList> {

        public Builder(ReportResponseRecordList reportResponseRecordList) {
            super(reportResponseRecordList);
        }

        public Builder() {
            super();
        }

        @Override
        public ReportResponseRecordList build() {
            return new ReportResponseRecordList(this);
        }
    }


    public static final class Parser extends Base2.Parser<ReportResponseRecordList> {
        public Parser(String apduStr) {
            super(apduStr);
        }

        @Override
        protected ReportResponseRecordList reBuild() {
            return new ReportResponseRecordList().newBulider()
                    .setOads(getOads())
                    .setPiid(getPiid())
                    .build();
        }
    }
}