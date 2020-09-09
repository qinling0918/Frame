package com.hzwq.framelibrary.protocol698.apdu.client.action;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.DataManager;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ActionNormal;
import com.hzwq.framelibrary.protocol698.data.OMD;

import java.util.ArrayList;
import java.util.Arrays;

import static com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU.ActionRequest.ACTION_REQUEST_LIST;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ActionRequestNormalList  implements ClientAPDU.ActionRequest, FormatAble {
    @Override
    public int type() {
        return ACTION_REQUEST_LIST;
    }

    private final PIID piid;
    private final ArrayList<ActionNormal> actionNormals;

    public ActionRequestNormalList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ActionRequestNormalList(Builder builder) {
        this.piid = builder.piid;
        this.actionNormals = builder.actionNormals;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<ActionRequestNormalList> {

        private PIID piid;
        private  ArrayList<ActionNormal> actionNormals;

        public Builder() {
            this.piid = new PIID();
            this.actionNormals = new ArrayList<>();
        }

        public Builder(ActionRequestNormalList setRequestNormal) {
            this.piid = setRequestNormal.piid;
            this.actionNormals = setRequestNormal.actionNormals;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        public Builder setOadAndData(String oadHexStr,Data data) {
            return setOadAndData(new OMD(oadHexStr),data);
        }

        public Builder setOadAndData(OMD omd,Data data) {
            ActionNormal actionNormal = new ActionNormal(omd,data);
            actionNormals.add(actionNormal);
            return this;
        }
        public Builder setActionNormals(ActionNormal... actionNormal) {
            actionNormals.addAll(Arrays.asList(actionNormal));
            return this;
        }
        @Override
        public ActionRequestNormalList build() {
            return new ActionRequestNormalList(this);
        }

        @Override
        public String toHexString() {
            return piid.toHexString()
                    + Data.toString4Array(actionNormals);
        }
    }


    public static final class Parser extends APDUParser<ActionRequestNormalList> {

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
        protected ActionRequestNormalList reBuild() {
            return null;
        }

        public PIID getPiid() {
            return new PIID(apduStr.substring(0, 2));
        }

        // todo 获取长度，获取个数，
        public ActionNormal[] getSetNormal() {
            int num = Integer.parseInt(apduStr.substring(2, 4));
            ActionNormal[] actionNormals = new  ActionNormal[num];
            int startIndex = 4;
            for (int i = 0; i < num; i++) {
                String oadStr = apduStr.substring(startIndex,startIndex+8);
                String dataType = apduStr.substring(startIndex+8,startIndex+10);
                int dataByteSize = DataManager.getInstance().getDataByteSize(dataType);
                String dataStr = apduStr.substring(startIndex+10,startIndex+10+dataByteSize*2);
                startIndex = startIndex+10+dataByteSize*2;
                OMD omd = new OMD(oadStr);
                // todo 反射，现在先用NULL 替代 对应的数据
                actionNormals[i] = new ActionNormal(omd,Data.NULL);
            }
            return actionNormals;
           // return new OMD(apduStr.substring(2, 8));
        }
    }
}