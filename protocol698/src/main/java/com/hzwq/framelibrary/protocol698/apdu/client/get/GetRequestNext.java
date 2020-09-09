package com.hzwq.framelibrary.protocol698.apdu.client.get;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE1;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class GetRequestNext implements ClientAPDU.GetRequest, FormatAble{
    @Override
    public int type() {
        return GET_REQUEST_NEXT;
    }

    private final PIID piid;
    private final LongUnsigned serialNumber;
    public GetRequestNext() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private GetRequestNext(Builder builder) {
        this.piid = builder.piid;
        this.serialNumber = builder.serialNumber;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<GetRequestNext> {

        private PIID piid;
        private LongUnsigned serialNumber;

        public Builder() {
            this.piid = new PIID();
            // 0000 0000
            this.serialNumber = new LongUnsigned(0);
        }



        public Builder(GetRequestNext getRequestNext) {
            this.piid = getRequestNext.piid;
            this.serialNumber = getRequestNext.serialNumber;
        }
        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }
        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setSerialNumber(@IntRange(from = 0, to = 65535) int serialNumber) {
            return setSerialNumber(new LongUnsigned(serialNumber));
        }
        public Builder setSerialNumber(LongUnsigned serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }
        @Override
        public GetRequestNext build() {
            return new GetRequestNext(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString() + serialNumber.toHexString();
        }
    }


    public static final class Parser extends APDUParser<GetRequestNext> {

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
        protected GetRequestNext reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0,2));
        }

        public OAD getOad() {
            return new OAD(apduStr.substring(2,8));
        }
    }
}