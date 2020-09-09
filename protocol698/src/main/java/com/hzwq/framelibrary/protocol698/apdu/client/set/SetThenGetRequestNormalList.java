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
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class SetThenGetRequestNormalList implements ClientAPDU.SetRequest, FormatAble {
    @Override
    public int type() {
        return SET_THEN_GET_REQUEST_NORMAL_LIST;
    }

    private final PIID piid;
    private final ArrayList<SetThenGet> setThenGets;

    public SetThenGetRequestNormalList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private SetThenGetRequestNormalList(Builder builder) {
        this.piid = builder.piid;
        this.setThenGets = builder.setThenGets;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<SetThenGetRequestNormalList> {

        private PIID piid;
        private ArrayList<SetThenGet> setThenGets;

        public Builder() {
            this.piid = new PIID();
            this.setThenGets = new ArrayList<>();
        }

        public Builder(SetThenGetRequestNormalList setRequestNormal) {
            this.piid = setRequestNormal.piid;
            this.setThenGets = setRequestNormal.setThenGets;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        /**
         * @param oadSet      一个设置的对象属性
         * @param data        数据
         * @param oadGet      一个读取的对象属性
         * @param delayedTime 延时读取时间    单位：秒，0表示采用服务器默认的延时时间。
         * @return
         */
        // 这里Data 并不明确。需要根据OAD 来判断
        public Builder setOadAndData(String oadSet, Data data, String oadGet, int delayedTime) {
            return setOadAndData(new OAD(oadSet), data, new OAD(oadGet), new Unsigned(delayedTime));
        }

        public Builder setOadAndData(String oadSet, Data data, String oadGet) {
            return setOadAndData(new OAD(oadSet), data, new OAD(oadGet));
        }

        public Builder setOadAndData(OAD oadSet, Data data, OAD oadGet, Unsigned time) {
            SetThenGet setNormal = new SetThenGet(oadSet, data, oadGet, time);
            setThenGets.add(setNormal);
            return this;
        }

        public Builder setOadAndData(OAD oadSet, Data data, OAD oadGet) {
            SetThenGet setNormal = new SetThenGet(oadSet, data, oadGet, new Unsigned(0));
            setThenGets.add(setNormal);
            return this;
        }

        public Builder addSetThenGet(SetThenGet... setNormal) {
            setThenGets.addAll(Arrays.asList(setNormal));
            return this;
        }

        @Override
        public SetThenGetRequestNormalList build() {
            return new SetThenGetRequestNormalList(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + Data.toString4Array(setThenGets);
        }
    }


    public static final class Parser extends APDUParser<SetThenGetRequestNormalList> {

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
        protected SetThenGetRequestNormalList reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        // todo 获取长度，获取个数，
        public SetNormal[] getSetNormal() {
            int num = Integer.parseInt(apduStr.substring(2, 4));
            SetNormal[] setNormals = new SetNormal[num];
            int startIndex = 4;
            for (int i = 0; i < num; i++) {
                String oadStr = apduStr.substring(startIndex, startIndex + 8);
                String dataType = apduStr.substring(startIndex + 8, startIndex + 10);
                int dataByteSize = DataManager.getInstance().getDataByteSize(dataType);
                String dataStr = apduStr.substring(startIndex + 10, startIndex + 10 + dataByteSize * 2);
                startIndex = startIndex + 10 + dataByteSize * 2;
                OAD oad = new OAD(oadStr);
                // todo 反射，现在先用NULL 替代 对应的数据
                setNormals[i] = new SetNormal(oad, Data.NULL);
            }
            return setNormals;
            // return new OAD(apduStr.substring(2, 8));
        }
    }
}