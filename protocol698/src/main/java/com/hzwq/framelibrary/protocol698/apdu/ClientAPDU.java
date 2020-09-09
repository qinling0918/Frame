package com.hzwq.framelibrary.protocol698.apdu;


import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.apdu.security.ISecurityableAPDU;
import com.hzwq.framelibrary.protocol698.apdu.security.SecurityAPDU;
import com.hzwq.framelibrary.protocol698.data.COMDCB;
import com.hzwq.framelibrary.protocol698.data.ConnectMechanismInfo;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.DateTimeS;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.TI;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.conformance.function.FunctionConformance;
import com.hzwq.framelibrary.protocol698.data.conformance.protocol.ProtocolConformance;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ActionNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.GetRecord;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyAction;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyActionThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxySet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxySetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet_OMD;
import com.hzwq.framelibrary.protocol698.data.datahelpers.TimeTag;
import com.hzwq.framelibrary.protocol698.data.enums.TimeUnit;
import com.hzwq.framelibrary.protocol698.data.OMD;
import com.hzwq.framelibrary.protocol698.data.number.DoubleLongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;
import com.hzwq.framelibrary.protocol698.data.string.BitString;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;

import static com.hzwq.framelibrary.protocol698.apdu.APDU.ACTION_REQUEST;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.CONNECT_REQUEST;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.GET_REQUEST;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.NO_TYPE;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.PROXY_REQUEST;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.RELEASE_REQUEST;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.REPORT_RESPONSE;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.SET_REQUEST;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE1;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE2;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE3;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE4;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE5;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE6;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE7;

/**
 * Created by qinling on 2018/5/11 14:40
 * Description: 客户机应用层协议数据单元（Client-APDU）
 */
public class ClientAPDU implements ISecurityableAPDU {
    private int classify;
    private int type;
    private String requestStr;
    private TimeTag timeTag;


    private ClientAPDU(int classify, int type, String requestStr, TimeTag timeTag) {
        this.classify = classify;
        this.type = type;
        this.requestStr = requestStr;
        this.timeTag = timeTag;
    }

    private ClientAPDU(int classify, int type, String requestStr) {
        this(classify, type, requestStr, new TimeTag());
    }

    private ClientAPDU(int classify, String requestStr) {
        this(classify, NO_TYPE, requestStr, new TimeTag());
    }

    private ClientAPDU setTimeTag(TimeTag timeTag) {
        this.timeTag = timeTag == null ? new TimeTag() : timeTag;
        return this;
    }

    /**
     *  设置时效性
     *  时效性判断规则：在时间标签中允许传输延时时间大于零的前提下，
     *  如果接收方的当前时间与时间标签中的开始发送时间之间的时差
     *  大于时间标签中的允许传输延时时间，则放弃处理；反之，则处理
     * @param timeOut  传输延时时间
     * @param timeUnit 传输延时时间单位（s，miniute，hour，day，month，year）
     * @return ClientAPDU
     */
    public ClientAPDU setTimeOut(int timeOut, TimeUnit timeUnit) {
        setTimeTag(new TimeTag(new DateTimeS(),new TI(timeUnit,new LongUnsigned(timeOut))));
        return this;
    }

    @Override
    public String toHexString() {

        return NumberConvert.toHexStr(classify, 2)
                + (type != NO_TYPE ? NumberConvert.toHexStr(type, 2) : "")
                + requestStr + timeTag.toHexString();
    }

    @Override
    public SecurityAPDU security() {
        return new SecurityAPDU(this);
    }


    public static GetRequest getRequest() {
        //  return new GetRequest();
        return SingleTon.GET_REQUEST;
    }

    public static ActionRequest actionRequest() {
        // return new ActionRequest();
        return SingleTon.ACTION_REQUEST;
    }

    public static ConnectRequest connectRequest() {
        //  return new ConnectRequest();
        return SingleTon.CONNECT_REQUEST;
    }

    public static ProxyRequest proxyRequest() {
        // return new ProxyRequest();
        return SingleTon.PROXY_REQUEST;
    }

    public static ReleaseRequest releaseRequest() {
        // return new ReleaseRequest();
        return SingleTon.RELEASE_REQUEST;
    }

    public static ReportReponse ReportReponse() {
        // return new ReportReponse();
        return SingleTon.REPORT_REPONSE;
    }

    public static SetRequest setRequest() {
        // return new SetRequest();
        return SingleTon.SET_REQUEST;
    }

    private static class SingleTon {
        private static final GetRequest GET_REQUEST = new GetRequest();
        private static final ActionRequest ACTION_REQUEST = new ActionRequest();
        private static final ConnectRequest CONNECT_REQUEST = new ConnectRequest();
        private static final ProxyRequest PROXY_REQUEST = new ProxyRequest();
        private static final ReleaseRequest RELEASE_REQUEST = new ReleaseRequest();
        private static final ReportReponse REPORT_REPONSE = new ReportReponse();
        private static final SetRequest SET_REQUEST = new SetRequest();
    }


    public static class GetRequest {

        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(GET_REQUEST, type, resultStr);
        }

        /**
         * 读取一个对象属性的MD5值
         * <p>
         * 服务序号-优先级                PIID，
         * 一个对象属性描述符             OAD
         */
        public ClientAPDU getRequestMD5(PIID piid, OAD oad) {
            piid = checkPIID(piid);
            return build(TYPE6, piid.toHexString() + oad.toHexString());
        }

        /**
         * 读取分帧响应的下一个数据块请求
         * <p>
         * 服务序号-优先级                PIID，
         * 正确接收的最近一次数据块序号   long-unsigned  16比特位正整数（Unsigned16） 0-65535
         */
        public ClientAPDU getRequestNext(PIID piid, @IntRange(from = 0, to = 65535) int serialNumber) {
            piid = checkPIID(piid);
            return build(TYPE5, piid.toHexString() + NumberConvert.toHexStr(serialNumber, 4));
        }
        public ClientAPDU getRequestNext(int piid, @IntRange(from = 0, to = 65535) int serialNumber) {
            return getRequestNext(new PIID(piid),serialNumber);
        }
        public ClientAPDU getRequestNext(PIID piid, String serialNumberHexStr) {
            piid = checkPIID(piid);
            if (serialNumberHexStr.length() != 4 || !NumberConvert.isHexStr(serialNumberHexStr)) {
                throw new IllegalArgumentException("参数异常,属性标识及其特征 需要2字节 16进制字符串");
            }

            return build(TYPE5, piid.toHexString() + serialNumberHexStr);
        }

        /**
         * 读取若干个记录型对象属性请求
         * <p>
         * 服务序号-优先级         PIID，
         * 读取若干个记录型对象属性  SEQUENCE OF GetRecord
         * <p>
         * GetRecord∷=SEQUENCE
         * {
         * 对象属性描述符     OAD，
         * 记录选择描述符     RSD，
         * 记录列选择描述符   RCSD
         * }
         *
         * @return
         */
        public ClientAPDU getRequestRecordList(PIID piid, GetRecord... getRecords) {
            piid = checkPIID(piid);
            return build(TYPE4, piid.toHexString() + Data.toString4Array(getRecords));
        }

        /**
         * 读取一个记录型对象属性请求
         *
         * @return 服务序号-优先级  PIID，
         * 读取一个记录型对象属性  GetRecord
         * <p>
         * GetRecord∷=SEQUENCE
         * {
         * 对象属性描述符     OAD，
         * 记录选择描述符     RSD，
         * 记录列选择描述符   RCSD
         * }
         */
        public ClientAPDU getRequestRecord(int piid, String oad, RSD rsd, RCSD rcsd) {
            return getRequestRecord(new PIID(piid), new GetRecord(new OAD(oad), rsd, rcsd));
        }
        public ClientAPDU getRequestRecord(OAD oad, RSD rsd, RCSD rcsd) {
            return getRequestRecord(new PIID(), new GetRecord(oad, rsd, rcsd));
        }
        public ClientAPDU getRequestRecord(PIID piid, OAD oad, RSD rsd, RCSD rcsd) {
            return getRequestRecord(piid, new GetRecord(oad, rsd, rcsd));
        }

        public ClientAPDU getRequestRecord(PIID piid, @NonNull GetRecord getRecord) {
            piid = checkPIID(piid);
            return build(TYPE3, piid.toHexString() + getRecord.toHexString());
        }

        /**
         * 读取若干个对象属性请求
         * <p>
         * 服务序号-优先级       PIID，
         * 若干个对象属性描述符  SEQUENCE OF OAD
         */
        public ClientAPDU getRequestNormalList(PIID piid, OAD... oads) {
            piid = checkPIID(piid);
            if (oads == null || oads.length == 0)
                throw new IllegalArgumentException("参数异常,OAD 不能为null");
            return build(TYPE2, piid.toHexString() + Data.toString4Array(oads));
        }

        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }


        /**
         * type = 1.读取一个对象属性请求的数据类型
         * type = 2.读取一个对象属性的MD5值
         * <p>
         * 服务序号-优先级     PIID，
         * 一个对象属性描述符  OAD
         */

        public ClientAPDU getRequestNormal(PIID piid, OAD oad) {
            piid = checkPIID(piid);
            return build(TYPE1, piid.toHexString() + oad.toHexString());
        }

        public ClientAPDU getRequestNormal(int piid, String oadHexStr) {
            return getRequestNormal(new PIID(piid), new OAD(oadHexStr));
        }
    }

    public static class ActionRequest {
        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(ACTION_REQUEST, type, resultStr);
        }


        /**
         * 设置后读取若干个对象属性请求
         * 服务序号-优先级  PIID，
         * 若干个设置后读取对象属性  SEQUENCE OF
         * {
         * 一个设置的对象属性   OAD，
         * 数据                 Data，
         * 一个读取的对象属性   OAD，
         * 延时读取时间         unsigned
         * }
         *
         * @param piid
         * @param setThenGets
         * @return
         */
        public ClientAPDU actionThenGetRequestNormalList(PIID piid, @NonNull SetThenGet_OMD... setThenGets) {
            piid = checkPIID(piid);
            return build(TYPE3, piid.toHexString() + Data.toString4Array(setThenGets));
        }

        /**
         * 设置若干个对象属性请求
         * <p>
         * 服务序号-优先级  PIID，
         * 若干个对象属性 ActionNormal   SEQUENCE OF
         * {
         * 一个对象方法描述符  OMD，
         * 方法参数            Data
         * }
         */
        public ClientAPDU actionRequestList(PIID piid, ActionNormal... oads) {
            piid = checkPIID(piid);
            if (oads == null || oads.length == 0)
                throw new IllegalArgumentException("参数异常,ActionNormal 不能为null");
            return build(TYPE2, piid.toHexString() + Data.toString4Array(oads));
        }

        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }


        /**
         * 操作一个对象方法请求
         * <p>
         * 服务序号-优先级      PIID，
         * 一个对象方法描述符   OMD，
         * 方法参数             Data
         */

        public ClientAPDU actionRequest(PIID piid, OMD omd, Data data) {
            piid = checkPIID(piid);
            return build(TYPE1, piid.toHexString() + omd.toHexString() + data.dataTypeStr() + data.toHexString());
        }
        public ClientAPDU actionRequest(int piid, String omd, Data data) {
            return actionRequest(new PIID(piid),new OMD(omd),data);
        }

    }

    public static class ConnectRequest {
        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(CONNECT_REQUEST, type, resultStr);
        }

        /**
         * 四种建立连接的方式。
         *
         * @param connectMechanismInfo
         * @return
         */
        public ClientAPDU connectRequest(ConnectMechanismInfo connectMechanismInfo) {
            return connectRequest(new PIID("00"),
                    new LongUnsigned("0010"),
                    new ProtocolConformance(),
                    new FunctionConformance(),
                    new LongUnsigned("0400"),
                    new LongUnsigned("0400"),
                    new Unsigned("01"),
                    new LongUnsigned("0400"),
                    new DoubleLongUnsigned("00000064"),
                    connectMechanismInfo);
        }

        /**
         * 以对称加密 通过m1，s1 进行建立连接。
         *
         * @param m1
         * @param s1
         * @return
         */
        public ClientAPDU connectRequest(String m1, String s1) {
            return connectRequest(ConnectMechanismInfo.symmetrySecurity(m1,s1));
        }

        public ClientAPDU connectRequest(PIID piid,
                                         @IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int protocolVersionCode,
                                         @IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int sendframeMaxSize,
                                         @IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int reciveframeMaxSize,
                                         @IntRange(from = Unsigned.MIN_VALUE, to = Unsigned.MAX_VALUE) int reciveframeMaxWindowSize,
                                         @IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int canHandleAPDUSize,
                                         long exceptedConnectTimeOut,
                                         ConnectMechanismInfo connectMechanismInfo
        ) {
            return connectRequest(piid,
                    new LongUnsigned(protocolVersionCode),
                    new ProtocolConformance(),
                    new FunctionConformance(),
                    new LongUnsigned(sendframeMaxSize),
                    new LongUnsigned(reciveframeMaxSize),
                    new Unsigned(reciveframeMaxWindowSize),
                    new LongUnsigned(canHandleAPDUSize),
                    new DoubleLongUnsigned(exceptedConnectTimeOut),
                    connectMechanismInfo);
        }

        /**
         * 建立应用连接请求
         *
         * @param piid
         * @param protocolVersionCode      期望的应用层协议版本号
         * @param protocolConformance      期望的协议一致性块  bit-string（SIZE（64））
         * @param functionConformance      期望的功能一致性块 bit-string（SIZE（128））
         * @param sendframeMaxSize         客户机发送帧最大尺寸
         * @param reciveframeMaxSize       客户机接收帧最大尺寸
         * @param reciveframeMaxWindowSize 客户机接收帧最大窗口尺寸
         * @param canHandleAPDUSize        客户机最大可处理APDU尺寸
         * @param exceptedConnectTimeOut   期望的应用连接超时时间
         * @param connectMechanismInfo     认证机制信息
         * @return
         */
        public ClientAPDU connectRequest(PIID piid,
                                         LongUnsigned protocolVersionCode,
                                         BitString protocolConformance,
                                         BitString functionConformance,
                                         LongUnsigned sendframeMaxSize,
                                         LongUnsigned reciveframeMaxSize,
                                         Unsigned reciveframeMaxWindowSize,
                                         LongUnsigned canHandleAPDUSize,
                                         DoubleLongUnsigned exceptedConnectTimeOut,
                                         ConnectMechanismInfo connectMechanismInfo
        ) {
            piid = checkPIID(piid);

            return build(NO_TYPE, piid.toHexString()
                    + protocolVersionCode.toHexString()
                    + protocolConformance.toHexString()
                    + functionConformance.toHexString()
                    + sendframeMaxSize.toHexString()
                    + reciveframeMaxSize.toHexString()
                    + reciveframeMaxWindowSize.toHexString()
                    + canHandleAPDUSize.toHexString()
                    + exceptedConnectTimeOut.toHexString()
                    + connectMechanismInfo.toHexString()
            );
        }

        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }

    }

    public static class ProxyRequest {
        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(PROXY_REQUEST, type, resultStr);
        }


        /**
         * 代理读取若干个服务器的若干个对象属性请求
         *
         * @param piid      服务序号-优先级
         * @param time      整个代理请求的超时时间——单位：秒，一般不能为0
         * @param proxyGets 代理若干个服务器的对象属性
         * @return
         */
        public ClientAPDU proxyGetRequestList(PIID piid, LongUnsigned time, ProxyGet... proxyGets) {
            piid = checkPIID(piid);
            if (proxyGets == null || proxyGets.length == 0)
                throw new IllegalArgumentException("参数异常,ProxyGet 不能为null");
            return build(TYPE1, piid.toHexString() + Data.toString4Array(proxyGets));
        }

        /**
         * 代理读取一个服务器的一个记录型对象属性请求
         *
         * @param piid    服务序号-优先级
         * @param outTime 代理请求的超时时间
         * @param tsa     目标服务器地址
         * @param oad     对象属性描述符
         * @param rsd     记录选择描述符
         * @param rcsd    记录列选择描述符
         * @return
         */
        public ClientAPDU proxyGetRequestRecord(PIID piid, LongUnsigned outTime, TSA tsa, OAD oad, RSD rsd, RCSD rcsd) {
            piid = checkPIID(piid);
            return build(TYPE2, piid.toHexString()
                    + outTime.toHexString()
                    + tsa.toHexString()
                    + oad.toHexString()
                    + rsd.toHexString()
                    + rcsd.toHexString());
        }

        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }


        /**
         * 代理设置后读取若干个服务器的若干个对象属性请求
         *
         * @param piid      服务序号-优先级
         * @param outTime   整个代理请求的超时时间
         * @param proxySets 代理若干个服务器的对象属性设置
         * @return
         */
        public ClientAPDU proxySetRequestList(PIID piid, LongUnsigned outTime, ProxySet... proxySets) {
            piid = checkPIID(piid);
            return build(TYPE3, piid.toHexString() + outTime.toHexString() + Data.toString4Array(proxySets));
        }

        /**
         * 代理设置后读取若干个服务器的若干个对象属性请求
         *
         * @param piid
         * @param outTime
         * @param proxySetThenGets
         * @return
         */
        public ClientAPDU proxySetThenGetRequestList(PIID piid, LongUnsigned outTime, ProxySetThenGet... proxySetThenGets) {
            piid = checkPIID(piid);
            return build(TYPE4, piid.toHexString() + outTime.toHexString() + Data.toString4Array(proxySetThenGets));
        }

        /**
         * 服务序号-优先级         PIID，
         * 整个代理请求的超时时间  long-unsigned，
         * 代理若干个服务器的对象方法操作  SEQUENCE OF
         * {
         * 一个目标服务器地址            TSA，
         * 代理一个服务器的超时时间      long-unsigned，
         * 若干个对象方法描述符及其参数  SEQUENCE OF
         * {
         * 对象方法描述符  OMD，
         * 及其方法参数    Data
         * }
         * }
         *
         * @param piid
         * @param outTime
         * @param proxyActions
         * @return
         */
        public ClientAPDU proxyActionRequestList(PIID piid, LongUnsigned outTime, ProxyAction... proxyActions) {
            piid = checkPIID(piid);
            return build(TYPE5, piid.toHexString() + outTime.toHexString() + Data.toString4Array(proxyActions));
        }

        /**
         * 代理操作后读取若干个服务器的若干个对象方法和属性请求
         *
         * @param piid
         * @param outTime
         * @param proxyActionThenGets
         * @return
         */
        public ClientAPDU proxyActionThenGetRequestList(PIID piid, LongUnsigned outTime, ProxyActionThenGet... proxyActionThenGets) {
            piid = checkPIID(piid);
            return build(TYPE6, piid.toHexString() + outTime.toHexString() + Data.toString4Array(proxyActionThenGets));
        }

        /**
         * 代理操作透明转发请求命令
         *
         * @param piid                服务序号-优先级
         * @param oad                 数据转发端口
         * @param comdcb              端口通信控制块
         * @param outTime_second      接收等待报文超时时间（秒）
         * @param outTime_millisecond 接收等待字节超时时间（毫秒）
         * @param command             透明转发命令      octet-string
         * @return
         */
        public ClientAPDU proxyTransCommandRequest(PIID piid, OAD oad, COMDCB comdcb, LongUnsigned outTime_second, LongUnsigned outTime_millisecond, OctetString command) {
            piid = checkPIID(piid);
            return build(TYPE7, piid.toHexString()
                    + oad.toHexString()
                    + comdcb.toHexString()
                    + outTime_second.toHexString()
                    + outTime_millisecond.toHexString()
                    + command.toHexString());
        }
    }

    public static class ReleaseRequest {
        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(RELEASE_REQUEST, type, resultStr);
        }

        /**
         * 断开应用连接请求的数据类型
         *
         * @param piid 服务序号-优先级
         * @return
         */
        public ClientAPDU ReportResponseList(PIID piid) {
            piid = checkPIID(piid);
            return build(TYPE1, piid.toHexString());
        }


        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }
    }

    public static class ReportReponse {
        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(REPORT_RESPONSE, type, resultStr);
        }

        /**
         * 上报若干个对象属性的响应
         * <p>
         * 服务序号-优先级                 PIID，
         * 对应上报的若干个对象属性描述符  SEQUENCE OF OAD
         *
         * @param piid
         * @param oads
         * @return
         */
        public ClientAPDU ReportResponseList(PIID piid, OAD... oads) {
            piid = checkPIID(piid);
            return build(TYPE1, piid.toHexString() + Data.toString4Array(oads));
        }

        /**
         * 上报若干个记录型对象属性的响应
         *
         * @param piid 服务序号-优先级
         * @param oads 对应上报的若干个对象属性描述符
         *             //todo : 猜测  此处应该是 ROAD
         * @return
         */
        public ClientAPDU ReportResponseRecordList(PIID piid, OAD... oads) {
            piid = checkPIID(piid);
            return build(TYPE2, piid.toHexString() + Data.toString4Array(oads));
        }

        /**
         * 上报透明数据的响应的数据类型
         * 对应上报透明数据，用于向服务器表明接收确认
         *
         * @param piid
         * @return
         */
        public ClientAPDU reportResponseTransData(PIID piid) {
            piid = checkPIID(piid);
            return build(TYPE3, piid.toHexString());
        }

        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }


    }

    public static class SetRequest {
        private ClientAPDU build(int type, String resultStr) {
            return new ClientAPDU(SET_REQUEST, type, resultStr);
        }


        /**
         * 设置后读取若干个对象属性请求
         * 服务序号-优先级  PIID，
         * 若干个设置后读取对象属性  SEQUENCE OF
         * {
         * 一个设置的对象属性   OAD，
         * 数据                 Data，
         * 一个读取的对象属性   OAD，
         * 延时读取时间         unsigned
         * }
         *
         * @param piid
         * @param setThenGets
         * @return
         */
        public ClientAPDU setThenGetRequestNormalList(PIID piid, @NonNull SetThenGet... setThenGets) {
            piid = checkPIID(piid);
            return build(TYPE3, piid.toHexString() + Data.toString4Array(setThenGets));
        }

        /**
         * 设置若干个对象属性请求
         * <p>
         * 服务序号-优先级  PIID，
         * 若干个对象属性 SetNormal   SEQUENCE OF
         * {
         * 一个对象属性描述符  OAD，
         * 数据                Data
         * }
         */
        public ClientAPDU setRequestNormalList(PIID piid, SetNormal... oads) {
            piid = checkPIID(piid);
            if (oads == null || oads.length == 0)
                throw new IllegalArgumentException("参数异常,OAD 不能为null");
            return build(TYPE2, piid.toHexString() + Data.toString4Array(oads));
        }

        @NonNull
        private PIID checkPIID(PIID piid) {
            return null == piid ? new PIID("00") : piid;
        }


        /**
         * 设置一个对象属性请求
         * <p>
         * 服务序号-优先级      PIID，
         * 一个对象属性描述符   OAD，
         * 数据                 Data
         */

        public ClientAPDU setRequestNormal(PIID piid, OAD oad, Data data) {
            piid = checkPIID(piid);
            return build(TYPE1, piid.toHexString() + oad.toHexString() + data.dataTypeStr() + data.toHexString());
        }
    }
}
