package com.hzwq.framelibrary.protocol698.apdu.client.report;

import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.base.Base2;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ReportResponseList extends Base2 implements ClientAPDU.ReportReponse, FormatAble {

    @Override
    public int type() {
        return REPORT_RESPONSE_LIST;
    }

    @Override
    public String format(String formatAbleStr) {
        return new Parser(formatAbleStr).toFormatString();
    }

    private ReportResponseList(Builder builder) {
        super(builder);
    }

    public ReportResponseList() {
        super(new Builder());
    }

    public Builder newBulider() {
        return new Builder(this);
    }

    public static final class Builder extends Base2.Builder<ReportResponseList> {

        public Builder(ReportResponseList reportResponseRecordList) {
            super(reportResponseRecordList);
        }

        public Builder() {
            super();
        }

        @Override
        public ReportResponseList build() {
            return new ReportResponseList(this);
        }
    }


    public static final class Parser extends Base2.Parser<ReportResponseList> {
        public Parser(String apduStr) {
            super(apduStr);
        }

        @Override
        protected ReportResponseList reBuild() {
            return new ReportResponseList().newBulider()
                    .setOads(getOads())
                    .setPiid(getPiid())
                    .build();
        }
    }
}