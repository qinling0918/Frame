package com.hzwq.framelibrary.protocol698.apdu.client;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.hzwq.framelibrary.protocol698.apdu.APDU;
import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.data.ConnectMechanismInfo;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.conformance.function.FunctionConformance;
import com.hzwq.framelibrary.protocol698.data.conformance.protocol.ProtocolConformance;
import com.hzwq.framelibrary.protocol698.data.number.DoubleLongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;
import com.hzwq.framelibrary.protocol698.data.string.BitString;

import static com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU.CONNECT_REQUEST;

/**
 * Created by qinling on 2019/4/28 19:22
 * Description:
 */
public  class ConnectRequest implements ClientAPDU.IService,FormatAble {
    public static final int NO_TYPE = 0X00;
    /**
     * @param piid
     * @param protocolVersionCode      期望的应用层协议版本号
     * @param protocolConformance      期望的协议一致性块  bit-string（SIZE（64））
     * @param functionConformance      期望的功能一致性块 bit-string（SIZE（128））
     * @param sendframeMaxSize         客户机发送帧最大尺寸
     * @param reciveframeMaxSize       客户机接收帧最大尺寸
     * @param reciveframeMaxWindowSize 客户机接收帧最大窗口尺寸
     * @param canHandleAPDUSize        客户机最大可处理APDU尺寸
     * @param exceptedConnectTimeOut   期望的应用连接超时时间
     * @param connectMechanismInfo     认证机制信息
     */
    private final PIID piid;
    private final LongUnsigned protocolVersionCode;
    private final BitString protocolConformance;
    private final BitString functionConformance;
    private final LongUnsigned sendframeMaxSize;
    private final LongUnsigned reciveframeMaxSize;
    private final Unsigned reciveframeMaxWindowSize;
    private final LongUnsigned canHandleAPDUSize;
    private final DoubleLongUnsigned exceptedConnectTimeOut;
    private final ConnectMechanismInfo connectMechanismInfo;

    @Override
    public int classify() {
        return APDU.CONNECT_REQUEST;
    }
    @Override
    public int type() {
        return APDU.NO_TYPE;
    }


    public ConnectRequest() {
        this(new Builder());
    }

    public ConnectRequest(ConnectMechanismInfo info) {
        this(new Builder().setConnectMechanismInfo(info));
    }

    public Builder newBuilder() {
        return new Builder(this);
    }


    private ConnectRequest(Builder builder) {
        this.piid = builder.piid;
        this.protocolVersionCode = builder.protocolVersionCode;
        this.protocolConformance = builder.protocolConformance;
        this.functionConformance = builder.functionConformance;
        this.sendframeMaxSize = builder.sendframeMaxSize;
        this.reciveframeMaxSize = builder.reciveframeMaxSize;
        this.reciveframeMaxWindowSize = builder.reciveframeMaxWindowSize;
        this.canHandleAPDUSize = builder.canHandleAPDUSize;
        this.exceptedConnectTimeOut = builder.exceptedConnectTimeOut;
        this.connectMechanismInfo = builder.connectMechanismInfo;
    }



    public static final class Builder extends APDUBuilder<ConnectRequest> {
        private PIID piid;
        private LongUnsigned protocolVersionCode;
        private BitString protocolConformance;
        private BitString functionConformance;
        private LongUnsigned sendframeMaxSize;
        private LongUnsigned reciveframeMaxSize;
        private Unsigned reciveframeMaxWindowSize;
        private LongUnsigned canHandleAPDUSize;
        private DoubleLongUnsigned exceptedConnectTimeOut;
        private ConnectMechanismInfo connectMechanismInfo;

        public Builder() {
            piid = new PIID();
            protocolVersionCode = new LongUnsigned("0010");
            protocolConformance = new ProtocolConformance();
            functionConformance = new FunctionConformance();
            sendframeMaxSize = new LongUnsigned("0400");
            reciveframeMaxSize = new LongUnsigned("0400");
            reciveframeMaxWindowSize = new Unsigned("01");
            canHandleAPDUSize = new LongUnsigned("0400");
            exceptedConnectTimeOut = new DoubleLongUnsigned("00000064");
            connectMechanismInfo = ConnectMechanismInfo.nullSecurity();
        }

        public Builder(ConnectRequest connectRequest) {
            this.piid = connectRequest.piid;
            this.protocolVersionCode = connectRequest.protocolVersionCode;
            this.protocolConformance = connectRequest.protocolConformance;
            this.functionConformance = connectRequest.functionConformance;
            this.sendframeMaxSize = connectRequest.sendframeMaxSize;
            this.reciveframeMaxSize = connectRequest.reciveframeMaxSize;
            this.reciveframeMaxWindowSize = connectRequest.reciveframeMaxWindowSize;
            this.canHandleAPDUSize = connectRequest.canHandleAPDUSize;
            this.exceptedConnectTimeOut = connectRequest.exceptedConnectTimeOut;
            this.connectMechanismInfo = connectRequest.connectMechanismInfo;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            piid = checkPIID(piid);
            this.piid = piid;
            return this;
        }

        public Builder setProtocolConformance(BitString protocolConformance) {
            this.protocolConformance = protocolConformance;
            return this;
        }

        public Builder setFunctionConformance(BitString functionConformance) {
            this.functionConformance = functionConformance;
            return this;
        }

        public Builder setSendframeMaxSize(@IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int sendframeMaxSize) {
            return setSendframeMaxSize(new LongUnsigned(sendframeMaxSize));
        }

        private Builder setSendframeMaxSize(LongUnsigned sendframeMaxSize) {
            this.sendframeMaxSize = sendframeMaxSize;
            return this;
        }

        public Builder setReciveframeMaxSize(@IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int protocolVersionCode) {
            return setReciveframeMaxSize(new LongUnsigned(protocolVersionCode));
        }

        private Builder setReciveframeMaxSize(LongUnsigned reciveframeMaxSize) {
            this.reciveframeMaxSize = reciveframeMaxSize;
            return this;
        }

        public Builder setReciveframeMaxWindowSize(@IntRange(from = Unsigned.MIN_VALUE, to = Unsigned.MAX_VALUE) int reciveframeMaxWindowSize) {
            return setReciveframeMaxWindowSize(new Unsigned(reciveframeMaxWindowSize));
        }

        private Builder setReciveframeMaxWindowSize(Unsigned reciveframeMaxWindowSize) {
            this.reciveframeMaxWindowSize = reciveframeMaxWindowSize;
            return this;
        }

        public Builder setCanHandleAPDUSize(@IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int canHandleAPDUSize) {
            return setCanHandleAPDUSize(new LongUnsigned(canHandleAPDUSize));
        }

        public Builder setCanHandleAPDUSize(LongUnsigned canHandleAPDUSize) {
            this.canHandleAPDUSize = canHandleAPDUSize;
            return this;
        }

        public Builder setExceptedConnectTimeOut(@IntRange(from = DoubleLongUnsigned.MIN_VALUE, to = DoubleLongUnsigned.MAX_VALUE) int exceptedConnectTimeOut) {
            return setExceptedConnectTimeOut(new DoubleLongUnsigned(exceptedConnectTimeOut));
        }

        private Builder setExceptedConnectTimeOut(DoubleLongUnsigned exceptedConnectTimeOut) {
            this.exceptedConnectTimeOut = exceptedConnectTimeOut;
            return this;
        }

        public Builder setConnectMechanismInfo(ConnectMechanismInfo connectMechanismInfo) {
            this.connectMechanismInfo = connectMechanismInfo;
            return this;
        }

        @Override
        public ConnectRequest build() {
            return new ConnectRequest(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + protocolVersionCode.toHexString()
                    + protocolConformance.toHexString()
                    + functionConformance.toHexString()
                    + sendframeMaxSize.toHexString()
                    + reciveframeMaxSize.toHexString()
                    + reciveframeMaxWindowSize.toHexString()
                    + canHandleAPDUSize.toHexString()
                    + exceptedConnectTimeOut.toHexString()
                    + connectMechanismInfo.toHexString();
        }
    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    /**
     * @param apduStr 链路层 建立应用连接 APDU
     * @return String 报文解析结果
     */
    @Override
    public String format(String apduStr) {
        return parse(apduStr).toFormatString();
    }

    private Parser parse(String apduStr) {
        return new Parser(apduStr);
    }

    public static final class Parser extends APDUParser<ConnectRequest> {

        private final String apduStr;
        private final int parseCode;
        private final int MIN_BYTE_SIZE = 41;

        public Parser(String apduStr) {
            super(apduStr);
            parseCode = checkApduStr(apduStr).getCode();
            if (parseCode != 0) {
                this.apduStr = apduStr;
                return;
            }
            // 把APDU数据第一个字节去掉，第一个字节表示某一种Apdu
            this.apduStr = apduStr.substring(2);
        }

        @Override
        protected ParseResult checkApduStr(String apduStr) {
            if (apduStr == null || apduStr.length() < 2) {
                return ParseResult.APDU_ERR_LENGTH;
            }
            if (Integer.parseInt(apduStr.substring(0, 2), 16) != APDU.CONNECT_REQUEST) {
                return ParseResult.APDU_ERR_LENGTH;
            }
            if (apduStr.length() < MIN_BYTE_SIZE * 2) {
                return ParseResult.APDU_ERR_LENGTH;
            }
            return ParseResult.SUCCESS;
        }


        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        public int getProtocolVersionCode() {
            return new LongUnsigned(apduStr.substring(2, 6)).getValue();
        }

        public String getProtocolConformance() {
            return apduStr.substring(6, 22);
        }

        public String getFunctionConformance() {
            return apduStr.substring(22, 54);
        }

        public int getSendframeMaxSize() {
            return new LongUnsigned(apduStr.substring(54, 58)).getValue();
        }

        public int getReciveframeMaxSize() {
            return new LongUnsigned(apduStr.substring(58, 62)).getValue();
        }

        public int getReciveframeMaxWindowSize() {
            return new Unsigned(apduStr.substring(62, 64)).getValue();
        }

        public int getCanHandleAPDUSize() {
            return new LongUnsigned(apduStr.substring(64, 68)).getValue();
        }

        public long getExceptedConnectTimeOut() {
            return new DoubleLongUnsigned(apduStr.substring(68, 76)).getValue();
        }

        public ConnectMechanismInfo getConnectMechanismInfo() {
            String securityType = apduStr.substring(76, 78);
            // switch ()
            // TODO 需要按照情景分析。根据加密类型 对字符串进行处理
            return ConnectMechanismInfo.nullSecurity();
        }

        @Override
        public String toFormatString() {
            return "";
        }

        @Override
        protected ConnectRequest reBuild() {
            return new Builder().setPiid(getPiid())
                    .setExceptedConnectTimeOut((int) getExceptedConnectTimeOut())
                    .setProtocolConformance(new BitString(getProtocolConformance()))
                    .setFunctionConformance(new BitString(getFunctionConformance()))
                    .setSendframeMaxSize(getSendframeMaxSize())
                    .setReciveframeMaxSize(getReciveframeMaxSize())
                    .setReciveframeMaxWindowSize(getReciveframeMaxWindowSize())
                    .setCanHandleAPDUSize(getCanHandleAPDUSize())
                    .setExceptedConnectTimeOut((int) getExceptedConnectTimeOut())
                    .setConnectMechanismInfo(getConnectMechanismInfo())
                    .build();
        }
    }
}
