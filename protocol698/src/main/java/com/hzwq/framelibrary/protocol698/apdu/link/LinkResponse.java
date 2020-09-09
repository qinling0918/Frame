package com.hzwq.framelibrary.protocol698.apdu.link;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.data.DateTime;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.datahelpers.Result;
import com.hzwq.framelibrary.protocol698.data.enums.LinkRequestTypeEnum;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.string.BitString;

/**
 * Created by qinling on 2019/4/28 19:25
 * Description:
 */
public class LinkResponse implements LinkAPDU.IService {
    private final PIID piid;
    private final Result result;
    private final DateTime requestTime;
    private final DateTime receivedTime;
    private final DateTime responseTime;

    public Builder newBuilder() {
        return new Builder(this);
    }


    private LinkResponse(Builder builder) {
        this.piid = builder.piid;
        this.result = builder.result;
        this.requestTime = builder.requestTime;
        this.receivedTime = builder.receivedTime;
        this.responseTime = builder.responseTime;

    }
    public LinkResponse() {
        this(new Builder());
    }
    public static final class Builder extends APDUBuilder<LinkResponse> {
        private PIID piid;
        private Result result;
        private DateTime requestTime;
        private DateTime receivedTime;
        private DateTime responseTime;

        public Builder() {
            this.piid = new PIID();
            this.result = new Result(true, 0);
            this.requestTime = new DateTime();
            this.receivedTime = new DateTime();
            this.responseTime = new DateTime();
        }



        public Builder(LinkResponse linkResponse) {
            this.piid = linkResponse.piid;
            this.result = linkResponse.result;
            this.requestTime = linkResponse.requestTime;
            this.receivedTime = linkResponse.receivedTime;
            this.responseTime = linkResponse.responseTime;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        /**
         * 时钟可信标志
         * @param clockTrustedMark true 可信， false 不可信
         * @return
         */
        public Builder setClockTrustedMark(boolean clockTrustedMark) {
            result.setClockTrustedMark(clockTrustedMark) ;
            return this;
        }


        /**
         * 1：地址重复，2：非法设备，3：容量不足，其它值：保留。
         * @param status
         * @return
         */
        public Builder setStatus(@IntRange(from = 0, to = 3) int status) {
            result.setStatus(status);
            return this;
        }
        public Builder setPiid(PIID piid) {
            this.piid = checkPIID(piid);
            return this;
        }

        public Builder setRequestTime(DateTime requestTime) {
            this.requestTime = requestTime;
            return this;
        }
        public Builder setReceivedTime(DateTime receivedTime) {
            this.receivedTime = receivedTime;
            return this;
        }
        // todo 有用？
        public Builder setResoponseTime(DateTime responseTime) {
            this.responseTime = responseTime;
            return this;
        }
        /**
         * 重新刷新响应时间
         * todo 需要调用？？？
         * @return
         */
        public Builder refreshResoponseTime() {
            this.responseTime = new DateTime();
            return this;
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + result.toHexString()
                    + requestTime.toHexString()
                    + receivedTime.toHexString()
                    + responseTime.toHexString();
        }

        @Override
        public LinkResponse build() {
            refreshResoponseTime();
            return new LinkResponse(this);
        }
    }

    @Override
    public int classify() {
        return LINK_RESPONSE;
    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    // todo 未完待续
    public static final class Parser extends APDUParser<LinkResponse> {
        public Parser(String apduStr) {
            super(apduStr);
        }

        @Override
        protected ParseResult checkApduStr(String apduStr) {
            return null;
        }

        @Override
        protected String toFormatString() {
            return null;
        }

        @Override
        protected LinkResponse reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        public LinkRequestTypeEnum getLinkRequestTypeEnum() {
            int linkRequestType = Integer.parseInt(apduStr.substring(2, 4), 16);
            return LinkRequestTypeEnum.getLinkRequestTypeEnum(linkRequestType);
        }

        public int getHeartCycle() {
            return new LongUnsigned(apduStr.substring(4, 8)).getValue();
        }

        public DateTime getRequestTime() {
            return new DateTime(apduStr.substring(8, 28));
        }
    }
}
