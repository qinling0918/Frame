package com.hzwq.framelibrary.protocol698.apdu.client.set;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.DataManager;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetNormal;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class SetRequestNormalList  implements ClientAPDU.SetRequest , FormatAble {
    @Override
    public int type() {
        return SET_REQUEST_NORMAL_LIST;
    }

    private final PIID piid;
    private final ArrayList<SetNormal> setNormals;

    public SetRequestNormalList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private SetRequestNormalList(Builder builder) {
        this.piid = builder.piid;
        this.setNormals = builder.setNormals;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<SetRequestNormalList> {

        private PIID piid;
        private  ArrayList<SetNormal> setNormals;

        public Builder() {
            this.piid = new PIID();
            this.setNormals = new ArrayList<>();
        }

        public Builder(SetRequestNormalList setRequestNormal) {
            this.piid = setRequestNormal.piid;
            this.setNormals = setRequestNormal.setNormals;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setOadAndData(String oadHexStr,Data data) {
            return setOadAndData(new OAD(oadHexStr),data);
        }

        public Builder setOadAndData(OAD oad,Data data) {
            SetNormal setNormal = new SetNormal(oad,data);
            setNormals.add(setNormal);
            return this;
        }
        public Builder setNormals(SetNormal... setNormal) {
            setNormals.addAll(Arrays.asList(setNormal));
            return this;
        }
        public Builder addNormal(SetNormal setNormal) {
            setNormals.add(setNormal);
            return this;
        }
        @Override
        public SetRequestNormalList build() {
            return new SetRequestNormalList(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + Data.toString4Array(setNormals);
        }
    }


    public static final class Parser extends APDUParser<SetRequestNormalList> {

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
        protected SetRequestNormalList reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        // todo 获取长度，获取个数，
        public SetNormal[] getSetNormal() {
            int num = Integer.parseInt(apduStr.substring(2, 4));
            SetNormal[] setNormals = new  SetNormal[num];
            int startIndex = 4;
            for (int i = 0; i < num; i++) {
                String oadStr = apduStr.substring(startIndex,startIndex+8);
                String dataType = apduStr.substring(startIndex+8,startIndex+10);
                int dataByteSize = DataManager.getInstance().getDataByteSize(dataType);
                String dataStr = apduStr.substring(startIndex+10,startIndex+10+dataByteSize*2);
                startIndex = startIndex+10+dataByteSize*2;
                OAD oad = new OAD(oadStr);
                // todo 反射，现在先用NULL 替代 对应的数据
                setNormals[i] = new SetNormal(oad,Data.NULL);
            }
            return setNormals;
           // return new OAD(apduStr.substring(2, 8));
        }
    }
}