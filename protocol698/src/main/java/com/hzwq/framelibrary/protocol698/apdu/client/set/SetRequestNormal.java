package com.hzwq.framelibrary.protocol698.apdu.client.set;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class SetRequestNormal implements ClientAPDU.SetRequest, FormatAble {
    @Override
    public int type() {
        return SET_REQUEST_NORMAL;
    }

    private final PIID piid;
    private final OAD oad;
    private final Data data;

    public SetRequestNormal() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private SetRequestNormal(Builder builder) {
        this.piid = builder.piid;
        this.oad = builder.oad;
        this.data = builder.data;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<SetRequestNormal> {

        private PIID piid;
        private OAD oad;
        private Data data;

        public Builder() {
            this.piid = new PIID();
            this.oad = new OAD();
            this.data = Data.NULL;
        }

        public Builder(SetRequestNormal setRequestNormal) {
            this.piid = setRequestNormal.piid;
            this.oad = setRequestNormal.oad;
            this.data = setRequestNormal.data;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setOad(String oadHexStr) {
            return setOad(new OAD(oadHexStr));
        }

        public Builder setOad(OAD oad) {
            this.oad = oad;
            return this;
        }

        public Builder setData(Data data) {
            this.data = data;
            return this;
        }

        @Override
        public SetRequestNormal build() {
            return new SetRequestNormal(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + oad.toHexString()
                    + data.dataTypeStr() + data.toHexString();
        }
    }


    public static final class Parser extends APDUParser<SetRequestNormal> {

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
        protected SetRequestNormal reBuild() {
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