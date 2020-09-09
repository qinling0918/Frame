package com.hzwq.framelibrary.protocol698.apdu.client.proxy;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.CSD;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.RCSD;
import com.hzwq.framelibrary.protocol698.data.RSD;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.datahelpers.GetRecord;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyGet;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ProxyGetRequestList implements ClientAPDU.ProxyRequest, FormatAble {
    @Override
    public int type() {
        return PROXY_GET_REQUEST_LIST;
    }

    private final PIID piid;
    /**
     * 整个代理请求的超时时间   long-unsigned  一般不能为0 ,单位为S
     */
    private final LongUnsigned totalProxyTimeOut;
    private final ArrayList<ProxyGet> proxyGets;

    public ProxyGetRequestList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ProxyGetRequestList(Builder builder) {
        this.piid = builder.piid;
        this.totalProxyTimeOut = builder.totalProxyTimeOut;
        this.proxyGets = builder.proxyGets;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<ProxyGetRequestList> {
        // 默认三分钟超时时间
        private static final int DEFAULT_OUT_TIME = 180;
        private static final int MIN_OUT_TIME = 10;
        private PIID piid;
        private LongUnsigned totalProxyTimeOut;
        private ArrayList<ProxyGet> proxyGets;

        public Builder() {
            this.piid = new PIID();
            this.totalProxyTimeOut = new LongUnsigned(DEFAULT_OUT_TIME);
            this.proxyGets = new ArrayList<>();
        }

        public Builder(ProxyGetRequestList getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.totalProxyTimeOut = getRequestNormal.totalProxyTimeOut;
            this.proxyGets = getRequestNormal.proxyGets;
        }

        public Builder setPiid(int piid) {
            return setPiid(new PIID(piid));
        }

        public Builder setPiid(PIID piid) {
            this.piid = piid;
            return this;
        }

        // 所有的超时时间 不能为0
        public Builder setTotalProxyTimeOut(@IntRange(from = 1, to = LongUnsigned.MAX_VALUE) int totalProxyOutTime) {
            this.totalProxyTimeOut = new LongUnsigned(totalProxyOutTime);
            return this;
        }

        public Builder addProxyGets(String tsa, String... oads) {
            return addProxyGets(tsa, 0, oads);
        }

        /**
         * @param tsa     一个目标服务器地址
         * @param timeOut 代理一个服务器的超时时间 单位：秒，0表示对于某个服务器的代理超时时间由服务器自行控制。
         * @param oads    若干个对象属性描述符
         * @return
         */
        private Builder addProxyGets(String tsa, @IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int timeOut, String... oads) {
            OAD[] oadArr = new OAD[oads.length];
            for (int i = 0; i < oads.length; i++) {
                oadArr[i] = new OAD(oads[i]);
            }
            this.proxyGets.add(new ProxyGet(new TSA(tsa), new LongUnsigned(timeOut), oadArr));
            return this;
        }


        @Override
        public ProxyGetRequestList build() {
            return new ProxyGetRequestList(this);
        }

        @Override
        public String toHexString() {
            String proxyGetsHexStr = proxyGets.isEmpty() ? "00" : Data.toString4Array(proxyGets);
            return piid.toHexString()
                    + totalProxyTimeOut.toHexString()
                    + proxyGetsHexStr;
        }
    }


    public static final class Parser extends APDUParser<ProxyGetRequestList> {

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
        protected ProxyGetRequestList reBuild() {
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