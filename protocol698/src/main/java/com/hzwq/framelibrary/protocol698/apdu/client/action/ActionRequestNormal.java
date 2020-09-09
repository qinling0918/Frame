package com.hzwq.framelibrary.protocol698.apdu.client.action;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.OMD;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ActionRequestNormal implements ClientAPDU.ActionRequest, FormatAble {
    @Override
    public int type() {
        return ACTION_REQUEST;
    }

    private final PIID piid;
    private final OMD omd;
    private final Data data;

    public ActionRequestNormal() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ActionRequestNormal(Builder builder) {
        this.piid = builder.piid;
        this.omd = builder.omd;
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

    public static final class Builder extends APDUBuilder<ActionRequestNormal> {

        private PIID piid;
        private OMD omd;
        private Data data;

        public Builder() {
            this.piid = new PIID();
            this.omd = new OMD();
            this.data = Data.NULL;
        }

        public Builder(ActionRequestNormal setRequestNormal) {
            this.piid = setRequestNormal.piid;
            this.omd = setRequestNormal.omd;
            this.data = setRequestNormal.data;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setOMD(String omdHexStr) {
            return setOMD(new OMD(omdHexStr));
        }

        public Builder setOMD(OMD omd) {
            this.omd = omd;
            return this;
        }

        public Builder setData(Data data) {
            this.data = data;
            return this;
        }

        @Override
        public ActionRequestNormal build() {
            return new ActionRequestNormal(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + omd.toHexString()
                    + data.dataTypeStr() + data.toHexString();
        }
    }


    public static final class Parser extends APDUParser<ActionRequestNormal> {

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
        protected ActionRequestNormal reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        public OMD getOMd() {
            return new OMD(apduStr.substring(2, 8));
        }
    }
}