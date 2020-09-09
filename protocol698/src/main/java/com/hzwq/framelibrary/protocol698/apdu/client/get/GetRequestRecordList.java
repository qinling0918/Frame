package com.hzwq.framelibrary.protocol698.apdu.client.get;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.datahelpers.GetRecord;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class GetRequestRecordList implements ClientAPDU.GetRequest, FormatAble {
    @Override
    public int type() {
        return GET_REQUEST_RECORD_LIST;
    }

    private final PIID piid;

    // todo 用hashmap 去重？？？
    private final ArrayList<GetRecord> getRecords;

    public GetRequestRecordList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private GetRequestRecordList(Builder builder) {
        this.piid = builder.piid;
        this.getRecords = builder.getRecords;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<GetRequestRecordList> {

        private PIID piid;
        ArrayList<GetRecord> getRecords;

        public Builder() {
            this.piid = new PIID();
            this.getRecords = new ArrayList<>();
        }

        public Builder(GetRequestRecordList getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.getRecords = getRequestNormal.getRecords;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder addGetRecord(String oadHexStr, RSD rsd, RCSD rcsd) {
            OAD oad = new OAD(oadHexStr);
            getRecords.add(new GetRecord(oad, rsd, rcsd));
            return this;
        }

        public Builder setRecords(GetRecord... getRecord) {
            getRecords.addAll(Arrays.asList(getRecord));
            return this;
        }

        @Override
        public GetRequestRecordList build() {
            return new GetRequestRecordList(this);
        }

        @Override
        public String toHexString() {
            if (getRecords.isEmpty())
                // return piid.toHexString() + Data.NULL.dataTypeStr() + Data.NULL.toHexString();
                return piid.toHexString() + "00";
            return piid.toHexString() + Data.toString4Array(getRecords);
        }
    }


    public static final class Parser extends APDUParser<GetRequestRecordList> {

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
        protected GetRequestRecordList reBuild() {
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