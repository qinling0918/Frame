package com.hzwq.framelibrary.protocol698.apdu.client.proxy;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.COMDCB;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ActionNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyAction;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;

import java.util.ArrayList;


/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ProxyTransCommandRequest implements ClientAPDU.ProxyRequest, FormatAble {
    @Override
    public int type() {
        return PROXY_TRANS_COMMAND_REQUEST;
    }

    private final PIID piid;
    /**
     * 数据转发端口
     */
    private final OAD oad;
    /**
     * 端口通信控制块
     */
    private final COMDCB comdcb;
    /**
     * 接收等待报文超时时间（秒）
     */
    private final LongUnsigned reciveFrameTimeOut;
    /**
     * 接收等待字节超时时间（毫秒） long-unsigned
     */
    private final LongUnsigned reciveByteTimeOut;
    /**
     * 透明转发命令                 octet-string
     */
    private final OctetString commandStr;

    public ProxyTransCommandRequest() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ProxyTransCommandRequest(Builder builder) {
        this.piid = builder.piid;
        this.oad = builder.oad;
        this.comdcb = builder.comdcb;
        this.reciveFrameTimeOut = builder.reciveFrameTimeOut;
        this.reciveByteTimeOut = builder.reciveByteTimeOut;
        this.commandStr = builder.commandStr;
    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<ProxyTransCommandRequest> {
        // 默认三分钟超时时间
        private static final int DEFAULT_OUT_TIME = 180;
        private static final int MIN_OUT_TIME = 10;
        private PIID piid;
        private OAD oad;
        private COMDCB comdcb;
        private LongUnsigned reciveFrameTimeOut;
        private LongUnsigned reciveByteTimeOut;
        private OctetString commandStr;

        public Builder() {
            this.piid = new PIID();
            this.oad = new OAD();
            this.comdcb = COMDCB.DEFAULT;
            this.reciveFrameTimeOut = new LongUnsigned(60);
            this.reciveByteTimeOut = new LongUnsigned(60);
            this.commandStr = new OctetString("00".getBytes());
        }


        public Builder(ProxyTransCommandRequest getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.oad = getRequestNormal.oad;
            this.comdcb = getRequestNormal.comdcb;
            this.reciveFrameTimeOut = getRequestNormal.reciveFrameTimeOut;
            this.reciveByteTimeOut = getRequestNormal.reciveByteTimeOut;
            this.commandStr = getRequestNormal.commandStr;

        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setOAD(String oad) {
            return setOAD(new OAD(oad));
        }

        public Builder setOAD(OAD oad) {
            this.oad = oad;
            return this;
        }

        public Builder setCOMDCB(COMDCB comdcb) {
            this.comdcb = comdcb;
            return this;
        }

        public Builder setReciveFrameTimeOut(@IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int reciveFrameTimeOut) {
            return setReciveFrameTimeOut(reciveFrameTimeOut);
        }

        private Builder setReciveFrameTimeOut(LongUnsigned reciveFrameTimeOut) {
            this.reciveFrameTimeOut = reciveFrameTimeOut;
            return this;
        }

        public Builder setReciveByteTimeOut(@IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int reciveByteTimeOut) {
            return setReciveByteTimeOut(reciveByteTimeOut);
        }

        private Builder setReciveByteTimeOut(LongUnsigned reciveByteTimeOut) {
            this.reciveByteTimeOut = reciveByteTimeOut;
            return this;
        }

        public void setCommandStr(String commandStr) {
            this.commandStr = new OctetString(commandStr.getBytes());
        }


        @Override
        public ProxyTransCommandRequest build() {
            return new ProxyTransCommandRequest(this);
        }


        @Override
        public String toHexString() {
            return piid.toHexString()
                    + oad.toHexString()
                    + comdcb.toHexString()
                    + reciveFrameTimeOut.toHexString()
                    + reciveByteTimeOut.toHexString()
                    + commandStr.toHexString();
        }
    }


    public static final class Parser extends APDUParser<ProxyTransCommandRequest> {

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
        protected ProxyTransCommandRequest reBuild() {
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