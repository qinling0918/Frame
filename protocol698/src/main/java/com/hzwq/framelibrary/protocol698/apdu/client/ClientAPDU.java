package com.hzwq.framelibrary.protocol698.apdu.client;


import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.IAPDUClassify;
import com.hzwq.framelibrary.protocol698.apdu.client.action.ActionRequestNormal;
import com.hzwq.framelibrary.protocol698.apdu.client.action.ActionRequestNormalList;
import com.hzwq.framelibrary.protocol698.apdu.client.action.ActionThenGetRequestNormalList;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestMD5;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestNext;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestNormal;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestNormalList;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestRecord;
import com.hzwq.framelibrary.protocol698.apdu.client.get.GetRequestRecordList;
import com.hzwq.framelibrary.protocol698.apdu.client.set.SetRequestNormal;
import com.hzwq.framelibrary.protocol698.apdu.client.set.SetRequestNormalList;
import com.hzwq.framelibrary.protocol698.apdu.client.set.SetThenGetRequestNormalList;
import com.hzwq.framelibrary.protocol698.apdu.security.ISecurityableAPDU;
import com.hzwq.framelibrary.protocol698.apdu.security.SecurityAPDU;
import com.hzwq.framelibrary.protocol698.data.ConnectMechanismInfo;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.DateTimeS;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.TI;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ActionNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.GetRecord;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet_OMD;
import com.hzwq.framelibrary.protocol698.data.datahelpers.TimeTag;
import com.hzwq.framelibrary.protocol698.data.enums.TimeUnit;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;


import java.util.ArrayList;
import java.util.Arrays;

import static com.hzwq.framelibrary.common.util.NumberConvert.toHexStr;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.NO_TYPE;

/**
 * Created by qinling on 2018/5/11 14:40
 * Description: 客户机应用层协议数据单元（Client-APDU）
 */
public class ClientAPDU implements ISecurityableAPDU {
    public static final int NO_CLASSIFY = 0x00;
    /**
     * 建立应用连接
     */
    public static final int CONNECT_REQUEST = 0x02;
    /**
     * 断开应用链接
     */
    public static final int RELEASE_REQUEST = 0x03;
    /**
     * 读取请求
     */
    public static final int GET_REQUEST = 0x05;
    /**
     * 设置请求
     */
    public static final int SET_REQUEST = 0x06;
    /**
     * 操作请求
     */
    public static final int ACTION_REQUEST = 0x07;
    /**
     * 上报应答
     */
    public static final int REPORT_RESPONSE = 0x08;
    /**
     * 代理请求
     */
    public static final int PROXY_REQUEST = 0x09;
    /**
     * Client  异常响应
     */
    public static final int ERROR_RESPONSE_CLIENT = 0x6E;

    private IService service;
    private TimeTag timeTag;

    private ClientAPDU setTimeTag(TimeTag timeTag) {
        this.timeTag = timeTag == null ? new TimeTag() : timeTag;

        return this;
    }

    /**
     * 设置时效性
     * 时效性判断规则：在时间标签中允许传输延时时间大于零的前提下，
     * 如果接收方的当前时间与时间标签中的开始发送时间之间的时差
     * 大于时间标签中的允许传输延时时间，则放弃处理；反之，则处理
     *
     * @param timeOut  传输延时时间
     * @param timeUnit 传输延时时间单位（s，miniute，hour，day，month，year）
     * @return ClientAPDU
     */
    public ClientAPDU setTimeOut(int timeOut, TimeUnit timeUnit) {
        setTimeTag(new TimeTag(new DateTimeS(), new TI(timeUnit, new LongUnsigned(timeOut))));
        return this;
    }

    @Override
    public SecurityAPDU security() {
        return new SecurityAPDU(this);
    }

    public ClientAPDU(IService service, int timeOut, TimeUnit timeUnit) {
        this.service = service;
        this.timeTag = new TimeTag(new DateTimeS(), new TI(timeUnit, new LongUnsigned(timeOut)));
    }

    public ClientAPDU(IService service) {
        this.service = service;
        this.timeTag = new TimeTag();
    }

    public static ClientAPDU connectRequest(ConnectMechanismInfo info) {
        return new ClientAPDU(new ConnectRequest.Builder().setConnectMechanismInfo(info).build());
    }

    public static ClientAPDU connectRequest(ConnectRequest.Builder builder) {
        return new ClientAPDU(builder.build());
    }

    public static ClientAPDU getRequestNormal(String oadHexStr) {
        return getRequestNormal(0, oadHexStr);
    }

    public static ClientAPDU getRequestNormal(@IntRange(from = 0, to = 255) int piid, String oadHexStr) {
        return getRequestNormal((GetRequestNormal) new GetRequestNormal.Builder()
                .setPiid(piid)
                .setOad(oadHexStr).build());
    }

    public static ClientAPDU getRequestNormal(GetRequestNormal service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU getRequestNormalList(String... oadHexStr) {
        return getRequestNormalList(0, oadHexStr);
    }

    public static ClientAPDU getRequestNormalList(@IntRange(from = 0, to = 255) int piid, String... oadHexStr) {
        return getRequestNormalList(new GetRequestNormalList.Builder()
                .setPiid(piid)
                .setOads(oadHexStr).build());
    }

    public static ClientAPDU getRequestNormalList(GetRequestNormalList service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU getRequestRecord(String oadHexStr, RSD rsd, RCSD rcsd) {
        return getRequestRecord(0, oadHexStr, rsd, rcsd);
    }

    public static ClientAPDU getRequestRecord(@IntRange(from = 0, to = 255) int piid, String oadHexStr, RSD rsd, RCSD rcsd) {
        return getRequestRecord(new GetRequestRecord().newBuilder()
                .setPiid(piid)
                .setRecord(oadHexStr, rsd, rcsd).build());
    }

    public static ClientAPDU getRequestRecord(GetRequestRecord service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU getRequestRecordList(GetRecord... getRecord) {
        return getRequestRecordList(0, getRecord);
    }

    public static ClientAPDU getRequestRecordList(@IntRange(from = 0, to = 255) int piid, GetRecord... getRecord) {
        return getRequestRecordList(new GetRequestRecordList.Builder()
                .setPiid(piid)
                .setRecords(getRecord).build());
    }

    public static ClientAPDU getRequestRecordList(GetRequestRecordList service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU getRequestNext(@IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int lastBlockNum) {
        return getRequestNext(0, lastBlockNum);
    }

    public static ClientAPDU getRequestNext(@IntRange(from = 0, to = 255) int piid, @IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int lastBlockNum) {
        return getRequestNext(new GetRequestNext().newBuilder()
                .setPiid(piid)
                .setSerialNumber(lastBlockNum).build());
    }

    public static ClientAPDU getRequestNext(GetRequestNext service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU getRequestMD5(String oadHexStr) {
        return getRequestMD5(0, oadHexStr);
    }

    public static ClientAPDU getRequestMD5(@IntRange(from = 0, to = 255) int piid, String oadHexStr) {
        return getRequestMD5(new GetRequestMD5.Builder()
                .setPiid(piid)
                .setOad(oadHexStr).build());
    }

    public static ClientAPDU getRequestMD5(GetRequestMD5 service) {
        return new ClientAPDU(service);
    }

    /***  设置请求*/

    public static ClientAPDU setRequestNormal(String oadHexStr, Data data) {
        return setRequestNormal(0, oadHexStr, data);
    }

    public static ClientAPDU setRequestNormal(@IntRange(from = 0, to = 255) int piid, String oadHexStr, Data data) {
        return setRequestNormal(new SetRequestNormal().newBuilder().
                setPiid(piid).
                setOad(oadHexStr).
                setData(data).build());
    }

    public static ClientAPDU setRequestNormal(SetRequestNormal service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU setRequestNormalList(SetNormal... setNormals) {
        return setRequestNormalList(0, setNormals);
    }

    public static ClientAPDU setRequestNormalList(@IntRange(from = 0, to = 255) int piid, SetNormal... setNormals) {
        return setRequestNormalList(new SetRequestNormalList().newBuilder()
                .setPiid(piid)
                .setNormals(setNormals).build());
    }

    public static ClientAPDU setRequestNormalList(SetRequestNormalList service) {
        return new ClientAPDU(service);
    }

    public static ClientAPDU setThenGetRequestNormalList(SetThenGet... setNormal) {
        return setThenGetRequestNormalList(0, setNormal);
    }

    public static ClientAPDU setThenGetRequestNormalList(@IntRange(from = 0, to = 255) int piid, SetThenGet... setNormal) {
        return setThenGetRequestNormalList(new SetThenGetRequestNormalList().newBuilder()
                .setPiid(piid)
                .addSetThenGet(setNormal).build());
    }

    public static ClientAPDU setThenGetRequestNormalList(SetThenGetRequestNormalList service) {
        return new ClientAPDU(service);
    }


    /**
     * 操作请求
     */

    public static ClientAPDU actionRequestNormal(String omdHexStr, Data data) {
        return setRequestNormal(0, omdHexStr, data);
    }

    public static ClientAPDU actionRequestNormal(@IntRange(from = 0, to = 255) int piid, String omdHexStr, Data data) {
        return actionRequestNormal(new ActionRequestNormal().newBuilder().
                setPiid(piid).
                setOMD(omdHexStr).
                setData(data).build());
    }

    public static ClientAPDU actionRequestNormal(ActionRequestNormal service) {
        return new ClientAPDU(service);
    }


    public static ClientAPDU actionRequestNormalList(ActionNormal... actionNormals) {
        return actionRequestNormalList(0, actionNormals);
    }

    public static ClientAPDU actionRequestNormalList(@IntRange(from = 0, to = 255) int piid, ActionNormal... actionNormals) {
        return actionRequestNormalList(new ActionRequestNormalList().newBuilder()
                .setPiid(piid)
                .setActionNormals(actionNormals).build());
    }

    public static ClientAPDU actionRequestNormalList(ActionRequestNormalList service) {
        return new ClientAPDU(service);
    }


    public static ClientAPDU actionThenGetRequestNormalList(SetThenGet_OMD... setNormal) {
        return actionThenGetRequestNormalList(0, setNormal);
    }

    public static ClientAPDU actionThenGetRequestNormalList(@IntRange(from = 0, to = 255) int piid, SetThenGet_OMD... setThenGet_omds) {
        return actionThenGetRequestNormalList(new ActionThenGetRequestNormalList().newBuilder()
                .setPiid(piid)
                .addSetThenGet(setThenGet_omds).build());
    }

    public static ClientAPDU actionThenGetRequestNormalList(ActionThenGetRequestNormalList service) {
        return new ClientAPDU(service);
    }


    @Override
    public String toHexString() {
        return service.classifyStr() + service.typeStr() + service.toHexString()
                + timeTag.toHexString();
    }

    public interface IService extends IHex{
        int classify();

        int type();

        default String classifyStr() {
            return classify() == NO_CLASSIFY ? "" : toHexStr(classify(), 2);
        }

        default String typeStr() {
            return type() == NO_TYPE ? "" : toHexStr(type(), 2);
        }
    }


    private abstract static class ReleaseRequest implements IService {
        @Override
        public int classify() {
            return RELEASE_REQUEST;
        }
    }


    public interface GetRequest extends IService {
        public static final int GET_REQUEST_NORMAL = 0x01;
        public static final int GET_REQUEST_NORMAL_LIST = 0x02;
        public static final int GET_REQUEST_RECORD = 0x03;
        public static final int GET_REQUEST_RECORD_LIST = 0x04;
        public static final int GET_REQUEST_NEXT = 0x05;
        public static final int GET_REQUEST_MD5 = 0x06;

        @Override
        default int classify() {
            return GET_REQUEST;
        }

    }


    public interface SetRequest extends IService {
        int SET_REQUEST_NORMAL = 0x01;
        int SET_REQUEST_NORMAL_LIST = 0x02;
        int SET_THEN_GET_REQUEST_NORMAL_LIST = 0x03;

        @Override
        default int classify() {
            return SET_REQUEST;
        }

    }

    public interface ActionRequest extends IService {
        public static final int ACTION_REQUEST = 0x01;
        public static final int ACTION_REQUEST_LIST = 0x02;
        public static final int ACTION_THEN_GET_REQUEST_NORMAL_LIST = 0x03;

        @Override
        default int classify() {
            return ACTION_REQUEST;
        }

    }

    public interface ReportReponse extends IService {
        /**
         * 上报若干个对象属性的响应      	 [1] ReportResponseList，
         * 上报若干个记录型对象属性的响应	 [2] ReportResponseRecordList，
         * 上报透明数据的响应              [3] ReportResponseTransData
         */
        public static final int REPORT_RESPONSE_LIST = 0x01;
        public static final int REPORT_RESPONSE_RECORD_LIST = 0x02;
        public static final int REPORT_RESPONSE_TRANS_DATA = 0x03;

        @Override
        default int classify() {
            return REPORT_RESPONSE;
        }

    }

    public interface ProxyRequest extends IService {
        int PROXY_GET_REQUEST_LIST = 0x01;
        int PROXY_GET_REQUEST_RECORD = 0x02;
        int PROXY_SET_REQUEST_LIST = 0x03;
        int PROXY_SET_THEH_GET_REQUEST_LIST = 0x04;
        int PROXY_ACTION_REQUEST_LIST = 0x05;
        int PROXY_ACTION_THEN_GET_REQUEST_LIST = 0x06;
        int PROXY_TRANS_COMMAND_REQUEST = 0x07;

        @Override
        default int classify() {
            return PROXY_REQUEST;
        }

    }

    public interface ErrorResponse extends IService {
        @Override
        default int classify() {
            return ERROR_RESPONSE_CLIENT;
        }
    }

    public static class Base implements ClientAPDU.IService, FormatAble {
        private int classify;
        private int type;

        private final PIID piid;

        // todo 用hashmap 去重？？？
        private final ArrayList<OAD> oads;

        public Base(int classify, int type) {
            this(new Builder());
        }


        public Builder newBuilder() {
            return new Builder(this);
        }


        private Base(Builder builder) {
            this.classify = builder.classify;
            this.type = builder.type;
            this.piid = builder.piid;
            this.oads = builder.oads;

        }

        @Override
        public String toHexString() {
            return newBuilder().toHexString();
        }

        @Override
        public String format(String apduStr) {
            return new Parser(apduStr).toFormatString();
        }

        @Override
        public int classify() {
            return classify;
        }

        @Override
        public int type() {
            return type;
        }

        public static final class Builder extends APDUBuilder<Base> {
            private int classify;
            private int type;

            private PIID piid;
            private ArrayList<OAD> oads;

            public Builder() {
                this.piid = new PIID();
                this.oads = new ArrayList<>();
            }

            public Builder(Base getRequestNormal) {
                this.classify = getRequestNormal.classify;
                this.type = getRequestNormal.type;

                this.piid = getRequestNormal.piid;
                this.oads = getRequestNormal.oads;
            }

            public Builder setPiid(int piid) {
                return setPiid(new PIID(piid));
            }

            public Builder setPiid(PIID piid) {
                this.piid = piid;
                return this;
            }

            public Builder setOads(String... oadHexStrs) {
                OAD[] oadArr = new OAD[oadHexStrs.length];
                for (int i = 0; i < oadHexStrs.length; i++) {
                    oadArr[i] = new OAD(oadHexStrs[i]);
                }
                oads.addAll(Arrays.asList(oadArr));
                return this;
            }

            public Builder setOads(OAD... oad) {
                oads.addAll(Arrays.asList(oad));
                return this;
            }

            @Override
            public Base build() {
                return new Base(this);
            }

            @Override
            public String toHexString() {
                if (oads.isEmpty())
                    // return piid.toHexString() + Data.NULL.dataTypeStr() + Data.NULL.toHexString();
                    return piid.toHexString() + "00";
                return piid.toHexString() + Data.toString4Array(oads);
            }
        }


        public static final class Parser extends APDUParser<Base> {

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
            protected Base reBuild() {
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
}
