package com.hzwq.framelibrary.protocol698.apdu.client.base;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.Frame698;
import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestMD5;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestNormal;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description: 逻辑同 GetRequestNormal
 */
public class Base1 implements IHex {

    private final PIID piid;
    private final OAD oad;

    protected Base1(Builder builder) {
        this.piid = builder.piid;
        this.oad = builder.oad;
    }

    public Base1() {
        this(new Builder());
    }

    @Override
    public String toHexString() {
        return new Builder(this).toHexString();
    }


    public static class Builder<APDU extends Base1> extends APDUBuilder<APDU> {
        private PIID piid;
        private OAD oad;

        public Builder() {
            this.piid = new PIID();
            // 0000 0000
            this.oad = new OAD();
        }

        public Builder(Base1 getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.oad = getRequestNormal.oad;
        }

        public Builder<APDU> setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder<APDU> setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder<APDU> setOad(String oadHexStr) {
            return setOad(new OAD(oadHexStr));
        }

        public Builder<APDU> setOad(OAD oad) {
            this.oad = oad;
            return this;
        }

        @Override
        public String toHexString() {
            return piid.toHexString() + oad.toHexString();
        }


        @Override
        public APDU build() {
            return (APDU) new Base1(this);
        }
    }


    public static abstract class Parser<APDU> extends APDUParser<APDU> {

        public Parser(String apduStr) {
            super(apduStr);
        }

        @Override
        protected ParseResult checkApduStr(String apduStr) {
            return null;
        }

        @Override
        public String toFormatString() {
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