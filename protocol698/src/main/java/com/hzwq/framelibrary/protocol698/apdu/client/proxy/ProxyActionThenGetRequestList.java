package com.hzwq.framelibrary.protocol698.apdu.client.proxy;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.apdu.APDUBuilder;
import com.hzwq.framelibrary.protocol698.apdu.APDUParser;
import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.data.PIID;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxyActionThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet_OMD;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

import java.util.ArrayList;


/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ProxyActionThenGetRequestList implements ClientAPDU.ProxyRequest, FormatAble {
    @Override
    public int type() {
        return PROXY_ACTION_THEN_GET_REQUEST_LIST;
    }

    private final PIID piid;
    /**
     * 整个代理请求的超时时间   long-unsigned  一般不能为0 ,单位为S
     */
    private final LongUnsigned totalProxyTimeOut;
    private final ArrayList<ProxyActionThenGet> proxyActionThenGets;

    public ProxyActionThenGetRequestList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ProxyActionThenGetRequestList(Builder builder) {
        this.piid = builder.piid;
        this.totalProxyTimeOut = builder.totalProxyTimeOut;
        this.proxyActionThenGets = builder.proxyActionThenGets;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<ProxyActionThenGetRequestList> {
        // 默认三分钟超时时间
        private static final int DEFAULT_OUT_TIME = 180;
        private static final int MIN_OUT_TIME = 10;
        private PIID piid;
        private LongUnsigned totalProxyTimeOut;
        private ArrayList<ProxyActionThenGet> proxyActionThenGets;

        public Builder() {
            this.piid = new PIID();
            this.totalProxyTimeOut = new LongUnsigned(DEFAULT_OUT_TIME);
            this.proxyActionThenGets = new ArrayList<>();
        }

        public Builder(ProxyActionThenGetRequestList getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.totalProxyTimeOut = getRequestNormal.totalProxyTimeOut;
            this.proxyActionThenGets = getRequestNormal.proxyActionThenGets;
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

        public Builder addProxyAction(String tsa, SetThenGet_OMD... oads) {
            return addProxyAction(tsa, 0, oads);
        }

        /**
         * @param tsa     一个目标服务器地址
         * @param timeOut 代理一个服务器的超时时间 单位：秒，0表示对于某个服务器的代理超时时间由服务器自行控制。
         * @param setThenGets_OMD   若干个对象属性描述符及其数据
         * @return
         */
        private Builder addProxyAction(String tsa, @IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int timeOut, SetThenGet_OMD... setThenGets_OMD) {
            /*SetThenGet[] setNormalArr = new SetThenGet[setThenGets_OMD.length];
            for (int i = 0; i < setThenGets_OMD.length; i++) {
                setNormalArr[i] = new SetThenGet(setThenGets_OMD[i]);
            }*/
            this.proxyActionThenGets.add(new ProxyActionThenGet(new TSA(tsa), new LongUnsigned(timeOut), setThenGets_OMD));
            return this;
        }


        @Override
        public ProxyActionThenGetRequestList build() {
            return new ProxyActionThenGetRequestList(this);
        }

        @Override
        public String toHexString() {
            String proxyGetsHexStr = proxyActionThenGets.isEmpty() ? "00" : Data.toString4Array(proxyActionThenGets);
            return piid.toHexString()
                    + totalProxyTimeOut.toHexString()
                    + proxyGetsHexStr;
        }
    }


    public static final class Parser extends APDUParser<ProxyActionThenGetRequestList> {

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
        protected ProxyActionThenGetRequestList reBuild() {
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