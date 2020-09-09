package com.hzwq.framelibrary.protocol698.apdu.server;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.apdu.security.ISecurityableAPDU;
import com.hzwq.framelibrary.protocol698.apdu.security.SecurityAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.datahelpers.FollowReport;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.datahelpers.GetRecord;
import com.hzwq.framelibrary.protocol698.data.datahelpers.TimeTag;
import com.hzwq.framelibrary.protocol698.data.datahelpers.parser.AResultNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.parser.AResultRecord;

import java.util.Calendar;

import static com.hzwq.framelibrary.protocol698.apdu.APDU.GET_RESPONSE;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.NO_TYPE;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE1;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE2;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE3;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE4;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE5;
import static com.hzwq.framelibrary.protocol698.apdu.APDU.TYPE6;

/**
 * Created by qinling on 2018/9/21 9:21
 * Description: 服务器应用层协议数据单元（Server-APDU）
 */
public class ServerAPDU implements ISecurityableAPDU {
    private int classify;
    private int type;
    private String requestStr;
    private TimeTag timeTag;
    private FollowReport followReport;

    private ServerAPDU(int classify, int type, String requestStr, TimeTag timeTag, FollowReport followReport) {
        this.classify = classify;
        this.type = type;
        this.requestStr = requestStr;
        this.timeTag = timeTag;
        this.followReport = followReport;
    }

    private ServerAPDU(int classify, int type, String requestStr) {
        this(classify, type, requestStr, new TimeTag(), new FollowReport());
    }

    private ServerAPDU(int classify, String requestStr) {
        this(classify, NO_TYPE, requestStr, new TimeTag(), new FollowReport());
    }

    public ServerAPDU setTimeTag(TimeTag timeTag) {
        this.timeTag = timeTag == null ? new TimeTag() : timeTag;
        return this;
    }

    public ServerAPDU setTimeTagStr(String timeTagStr) {
        setTimeTag(new TimeTag(timeTagStr));
        return this;
    }

    private ServerAPDU setFollowReport(FollowReport followReport) {
        this.followReport = followReport == null ? new FollowReport() : followReport;
        return this;
    }

    public ServerAPDU setFollowReport(AResultRecord... resultRecords) {
        return setFollowReport(new FollowReport(resultRecords));
    }

    public ServerAPDU setFollowReport(AResultNormal... resultNormals) {
        return setFollowReport(new FollowReport(resultNormals));
    }

    @Override
    public String toHexString() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        return NumberConvert.toHexStr(classify, 2)
                + (type != NO_TYPE ? NumberConvert.toHexStr(type, 2) : "")
                + requestStr
                + timeTag.toHexString()
                + followReport.toHexString();
    }

    @Override
    public SecurityAPDU security() {
        return null;
    }
    public static ConnectResponse connectResponse() {
        return SingleTon.CONNECT_RESPONSE;
    }
    public static GetResponse GetResponse() {
        return SingleTon.GET_RESPONSE;
    }
    public static ActionResponse actionResponse() {
        return SingleTon.ACTION_RESPONSE;
    }
    public static ProxyResponse proxyResponse() {
        return SingleTon.PROXY_RESPONSE;
    }
    public static ReleaseNotification releaseNotification() {
        return SingleTon.RELEASE_NOTIFICATION;
    }
    public static ReleaseResponse releaseResponse() {
        return SingleTon.RELEASE_RESPONSE;
    }
    public static ReportNotification reportNotification() {
        return SingleTon.REPORT_NOTIFICATION;
    }
    public static SetResponse setResponse() {
        return SingleTon.SET_RESPONSE;
    }
    private ServerAPDU() {
    }
    private static class SingleTon {
        private static final ConnectResponse CONNECT_RESPONSE = new ConnectResponse();
        private static final GetResponse GET_RESPONSE = new GetResponse();
        private static final ActionResponse ACTION_RESPONSE = new ActionResponse();
        private static final ProxyResponse PROXY_RESPONSE = new ProxyResponse();
        private static final ReleaseNotification RELEASE_NOTIFICATION = new ReleaseNotification();
        private static final ReleaseResponse RELEASE_RESPONSE = new ReleaseResponse();
        private static final ReportNotification REPORT_NOTIFICATION = new ReportNotification();
        private static final SetResponse SET_RESPONSE = new SetResponse();
    }

    private static class GetResponse {
        private ServerAPDU build(int type, String resultStr) {
            return new ServerAPDU(GET_RESPONSE, type, resultStr);
        }

        /**
         * 读取一个对象属性的MD5值
         * <p>
         * 服务序号-优先级                PIID，
         * 一个对象属性描述符             OAD
         */
        public ServerAPDU getResponseMD5(PIID piid, OAD oad) {
            piid = checkPIID(piid);
            return build(TYPE6, piid.toString() + oad.toString());
        }

        /**
         * 读取分帧响应的下一个数据块请求
         * <p>
         * 服务序号-优先级                PIID，
         * 正确接收的最近一次数据块序号   long-unsigned  16比特位正整数（Unsigned16） 0-65535
         */
        public ServerAPDU getResponseNext(PIID piid, @IntRange(from = 0, to = 65535) int serialNumber) {
            piid = checkPIID(piid);
            return build(TYPE5, piid.toString() + NumberConvert.toHexStr(serialNumber, 4));
        }

        public ServerAPDU getResponseNext(PIID piid, String serialNumberHexStr) {
            piid = checkPIID(piid);
            if (serialNumberHexStr.length() != 4 || !NumberConvert.isHexStr(serialNumberHexStr)) {
                throw new IllegalArgumentException("参数异常,属性标识及其特征 需要2字节 16进制字符串");
            }
            return build(TYPE5, piid.toString() + serialNumberHexStr);
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
        public ServerAPDU getResponseRecordList(PIID piid, GetRecord... getRecords) {
            piid = checkPIID(piid);
            return build(TYPE4, piid.toString() + Data.toString4Array(getRecords));
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
   /* public ClientAPDU getResponseRecord(PIID piid, OAD oad, RSD rsd, RCSD rcsd) {
        return getResponseRecord(piid,new GetRecord(oad,rsd,rcsd));
    }
*/
        public ServerAPDU getResponseRecord(PIID piid, @NonNull AResultRecord resultRecord) {
            piid = checkPIID(piid);
            return build(TYPE3, piid.toString() + resultRecord.toHexString());
        }

        /**
         * 读取若干个对象属性请求
         * <p>
         * 服务序号-优先级       PIID，
         * 若干个对象属性描述符  SEQUENCE OF AResultNormal
         */
        public ServerAPDU getResponseNormalList(PIID piid, AResultNormal... aResultNormal) {
            piid = checkPIID(piid);
            if (aResultNormal == null || aResultNormal.length == 0)
                throw new IllegalArgumentException("参数异常,OAD 不能为null");
            return build(TYPE2, piid.toString() + Data.toString4Array(aResultNormal));
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

        public ServerAPDU getResponseNormal(PIID piid, AResultNormal aResultNormal) {
            piid = checkPIID(piid);
            return build(TYPE1, piid.toString() + aResultNormal.toString());
        }
    }
    private static class ActionResponse {
        // 操作响应  	[135]
    }
    private static class ConnectResponse {

     //    建立应用连接响应	[130]
        /**
         * 82 00 54 4F 50 53 30 31 30 32 31 36 30 37 33 31 30 31 30 32 31 36 30 37 33 31 00 00 00 00 00 00 00 00 00 10 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 04 00 04 00 01 04 00 00 00 00 64 00 00 00 00
         82 —— [130] CONNECT-Response
         00 —— PIID-ACD
         54 4F 50 53 30 31 30 32 31 36 30 37 33 31 30 31 30 32 31 36 30 37 33 31 00 00 00 00 00 00 00 00 —— 厂商版本信息：厂商代码（size(4)）+ 软件版本号（size(4)）+软件版本日期（size(6)）+硬件版本号（size(4)）+硬件版本日期（size(6)）+厂家扩展信息（size(8)）
         00 10 —— 期望的应用层协议版本号
         FF FF FF FF FF FF FF FF —— ProtocolConformance
         FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF —— FunctionConformance
         04 00 —— 服务器发送帧最大尺寸
         04 00 —— 服务器接收帧最大尺寸
         01    —— 服务器接收帧最大窗口尺寸
         04 00 —— 服务器最大可处理APDU尺寸
         00 00 00 64 —— 期望的应用连接超时时间
         00 —— 连接响应对象   允许建立应用连接     （0）
         00 —— 认证附加信息   OPTIONAL=0 表示没有
         00 —— FollowReport  OPTIONAL=0表示没有上报信息
         00 —— 没有时间标签
         */


    }
    private static class ProxyResponse {
        // 代理响应          	[137]
    }
    private static class ReleaseNotification {
        // 断开应用连接通知	[132]
    }
    private static class ReleaseResponse {
        // 断开应用连接响应	[131]
    }
    private static class ReportNotification {
        // 上报通知	[136]
    }
    private static class SetResponse {
        // 设置响应  	[134]
    }
}
