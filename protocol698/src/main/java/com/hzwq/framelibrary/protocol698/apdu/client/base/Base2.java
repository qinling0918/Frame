package com.hzwq.framelibrary.protocol698.apdu.client.base;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.apdu.APDU;
import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.report.ReportResponseRecordList;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;

import java.util.ArrayList;
import java.util.Arrays;

import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE2;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class Base2 implements IHex {

    private final PIID piid;
    // todo 用hashmap
    private final ArrayList<OAD> oads;


    protected Base2(Builder builder) {
        this.piid = builder.piid;
        this.oads = builder.oads;

    }

    public Base2() {
        this(new Builder());
    }

    @Override
    public String toHexString() {
        return new Builder(this).toHexString();
    }

    public static class Builder<APDU extends Base2> extends APDUBuilder<APDU> {

        private PIID piid;
        private ArrayList<OAD> oads;

        public Builder() {
            this.piid = new PIID();
            this.oads = new ArrayList<>();
        }

        public Builder(Base2 getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.oads = getRequestNormal.oads;
        }

        public Builder<APDU> setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder<APDU> setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder<APDU> setOads(String... oadHexStrs) {
            OAD[] oadArr = new OAD[oadHexStrs.length];
            for (int i = 0; i < oadHexStrs.length; i++) {
                oadArr[i] = new OAD(oadHexStrs[i]);
            }
            oads.addAll(Arrays.asList(oadArr));
            return this;
        }

        public Builder<APDU> setOads(OAD... oad) {
            oads.addAll(Arrays.asList(oad));
            return this;
        }


        @Override
        public String toHexString() {
            if (oads.isEmpty())
                // return piid.toHexString() + Data.NULL.dataTypeStr() + Data.NULL.toHexString();
                return piid.toHexString() + "00";

            return piid.toHexString() + Data.toString4Array(oads);
        }

        @Override
        public APDU build() {
            return (APDU) new Base2(this);
        }
    }


    public abstract static class Parser<APDU> extends APDUParser<APDU> {

        public Parser(String apduStr) {
            super(apduStr);
        }

        @Override
        public ParseResult checkApduStr(String apduStr) {
            return null;
        }

        @Override
        public String toFormatString() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        public OAD[] getOads() {
            return null;
        }
    }
}