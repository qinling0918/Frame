package com.hzwq.framelibrary.protocol698.apdu.link;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.data.DateTime;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.enums.LinkRequestTypeEnum;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

/**
 * Created by qinling on 2019/4/28 19:25
 * Description:
 */
public class LinkRequest implements LinkAPDU.IService {
    private final PIID piid;
    private final LinkRequestTypeEnum linkRequestTypeEnum;
    private final LongUnsigned heartCycle;
    private final DateTime requestTime;

    public Builder newBuilder() {
        return new Builder(this);
    }

    public LinkRequest() {
        this(new Builder());
    }

    private LinkRequest(Builder builder) {
        this.piid = builder.piid;
        this.linkRequestTypeEnum = builder.linkRequestTypeEnum;
        this.heartCycle = builder.heartCycle;
        this.requestTime = builder.requestTime;

    }

    public static final class Builder extends APDUBuilder<LinkRequest> {
        private PIID piid;
        private LinkRequestTypeEnum linkRequestTypeEnum;
        private LongUnsigned heartCycle;
        private DateTime requestTime;

        public Builder() {
            this.piid = new PIID();
            this.linkRequestTypeEnum = LinkRequestTypeEnum.LOGIN;
            this.heartCycle = new LongUnsigned("00B4");
            this.requestTime = new DateTime();
        }

        public Builder(LinkRequest linkRequest) {
            this.piid = linkRequest.piid;
            this.linkRequestTypeEnum = linkRequest.linkRequestTypeEnum;
            this.heartCycle = linkRequest.heartCycle;
            this.requestTime = linkRequest.requestTime;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = checkPIID(piid);
            return this;
        }

        public Builder setLinkRequestType(LinkRequestTypeEnum linkRequestTypeEnum) {
            this.linkRequestTypeEnum = linkRequestTypeEnum;
            return this;
        }

        public Builder setHeartCycle(@IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int heartCycle) {
            this.heartCycle = new LongUnsigned(heartCycle);
            return this;
        }

        // fixme 感觉貌似没有必要
        public void setRequestTime(DateTime requestTime) {
            this.requestTime = requestTime;
        }

        /**
         * 每一次设置之后，重新刷新时间
         *
         * @return
         */
        public Builder updateTime() {
            this.requestTime = new DateTime();
            return this;
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + linkRequestTypeEnum.toHexString()
                    + heartCycle.toHexString()
                    + requestTime.toHexString();
        }

        @Override
        public LinkRequest build() {
            updateTime();
            return new LinkRequest(this);
        }
    }

    @Override
    public int classify() {
        return LINK_REQUEST;
    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    // todo 未完待续
    public static final class Parser extends APDUParser<LinkRequest> {
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
        protected LinkRequest reBuild() {
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
