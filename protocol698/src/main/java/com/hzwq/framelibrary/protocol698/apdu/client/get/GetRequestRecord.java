package com.hzwq.framelibrary.protocol698.apdu.client.get;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.datahelpers.GetRecord;

import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE1;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE3;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class GetRequestRecord implements ClientAPDU.GetRequest, FormatAble {
    @Override
    public int type() {
        return GET_REQUEST_RECORD;
    }

    private final PIID piid;
    private final GetRecord getRecord;

    public GetRequestRecord() {
        this(new Builder());
    }

    public Builder newBuilder() {
        return new Builder(this);
    }


    private GetRequestRecord(Builder builder) {
        this.piid = builder.piid;
        this.getRecord = builder.getRecord;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<GetRequestRecord> {

        private PIID piid;
        private GetRecord getRecord;

        public Builder() {
            this.piid = new PIID();
            // 0000 0000
            this.getRecord = new GetRecord(new OAD(), RSD.selectNull(), RCSD.newInstance());
        }

        public Builder(GetRequestRecord getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.getRecord = getRequestNormal.getRecord;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setOAD(String oadHexStr) {

            this.getRecord.setOad(new OAD(oadHexStr));
            return this;
        }

        public Builder setRSD(RSD rsd) {
            this.getRecord.setRsd(rsd);
            return this;
        }

        public Builder setRSCD(RCSD rcsd) {
            this.getRecord.setRcsd(rcsd);
            return this;
        }

        public Builder setRecord(String oadHexStr, RSD rsd, RCSD rcsd) {
            return setRecord(new GetRecord(new OAD(oadHexStr), rsd, rcsd));
        }

        public Builder setRecord(GetRecord getRecord) {
            this.getRecord = getRecord;
            return this;
        }

        @Override
        public GetRequestRecord build() {
            return new GetRequestRecord(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString() + getRecord.toHexString();
        }
    }


    public static final class Parser extends APDUParser<GetRequestRecord> {

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
        protected GetRequestRecord reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        // todo
        public GetRecord getRecord() {
            //   return new OAD(apduStr.substring(2, 8));
            return null;
        }
    }


   /* public static class GetRecord implements IData {
        private OAD oad;
        private RSD rsd;
        private RCSD rcsd;

        public GetRecord(OAD oad, RSD rsd, RCSD rcsd) {
       *//* this.oad = null== oad ?new OAD() : oad;
        this.rsd = null== rsd ?new RSD() : rsd;
        this.rcsd = null== rcsd ?new RCSD() : rcsd;*//*
            this.oad = oad;
            this.rsd = rsd;
            this.rcsd = rcsd;
        }

        @Override
        public String toHexString() {
            return oad.toHexString()+rsd.toHexString()+rcsd.toHexString();
        }
    }*/
}