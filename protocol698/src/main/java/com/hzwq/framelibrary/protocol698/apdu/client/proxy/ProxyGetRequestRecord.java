package com.hzwq.framelibrary.protocol698.apdu.client.proxy;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyGet;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

import java.util.ArrayList;


/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ProxyGetRequestRecord implements ClientAPDU.ProxyRequest, FormatAble {
    @Override
    public int type() {
        return PROXY_GET_REQUEST_RECORD;
    }

    private final PIID piid;
    /**
     * 整个代理请求的超时时间   long-unsigned  一般不能为0 ,单位为S
     */
    private final LongUnsigned totalProxyTimeOut;
    private final TSA tsa;
    private final OAD oad;
    private final RSD rsd;
    private final RCSD rcsd;

    public ProxyGetRequestRecord() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ProxyGetRequestRecord(Builder builder) {
        this.piid = builder.piid;
        this.totalProxyTimeOut = builder.totalProxyTimeOut;
        this.tsa = builder.tsa;
        this.oad = builder.oad;
        this.rsd = builder.rsd;
        this.rcsd = builder.rcsd;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<ProxyGetRequestRecord> {
        // 默认三分钟超时时间
        private static final int DEFAULT_OUT_TIME = 180;
        // private static final int MIN_OUT_TIME = 10;
        private PIID piid;
        private LongUnsigned totalProxyTimeOut;
        private TSA tsa;
        private OAD oad;
        private RSD rsd;
        private RCSD rcsd;

        public Builder() {
            this.piid = new PIID();
            this.totalProxyTimeOut = new LongUnsigned(DEFAULT_OUT_TIME);
            this.tsa = new TSA();
            this.oad = new OAD();
            this.rsd = RSD.selectNull();
            this.rcsd = RCSD.newInstance();
        }

        public Builder(ProxyGetRequestRecord service) {
            this.piid = service.piid;
            this.totalProxyTimeOut = service.totalProxyTimeOut;
            this.tsa = service.tsa;
            this.oad = service.oad;
            this.rsd = service.rsd;
            this.rcsd = service.rcsd;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        // 所有的超时时间 不能为0
        public Builder setTotalProxyTimeOut(@IntRange(from = 1, to = LongUnsigned.MAX_VALUE) int totalProxyOutTime) {
            this.totalProxyTimeOut = new LongUnsigned(totalProxyOutTime);
            return this;
        }

        public Builder setTSA(String targetServerAddress) {
            this.tsa = new TSA(targetServerAddress);
            return this;
        }

        public Builder setOAD(String oad) {
            this.oad = new OAD(oad);
            return this;
        }

        public Builder setRCSD(RCSD rcsd) {
            this.rcsd = rcsd;
            return this;
        }

        public Builder setRSD(RSD rsd) {
            this.rsd = rsd;
            return this;
        }

        @Override
        public ProxyGetRequestRecord build() {
            return new ProxyGetRequestRecord(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + totalProxyTimeOut.toHexString()
                    + tsa.toHexString()
                    + oad.toHexString()
                    + rsd.toHexString()
                    + rcsd.toHexString();
        }
    }


    public static final class Parser extends APDUParser<ProxyGetRequestRecord> {

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
        protected ProxyGetRequestRecord reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        public OAD getOad() {
            return new OAD(apduStr.substring(2, 8));
        }
    }
}