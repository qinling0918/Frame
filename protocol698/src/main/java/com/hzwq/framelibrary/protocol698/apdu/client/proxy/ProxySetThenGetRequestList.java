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
import com.hzwq.framelibrary.protocol698.data.datahelpers.ProxySetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.datahelpers.SetThenGet;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;

import java.util.ArrayList;


/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class ProxySetThenGetRequestList implements ClientAPDU.ProxyRequest, FormatAble {
    @Override
    public int type() {
        return PROXY_SET_THEH_GET_REQUEST_LIST;
    }

    private final PIID piid;
    /**
     * 整个代理请求的超时时间   long-unsigned  一般不能为0 ,单位为S
     */
    private final LongUnsigned totalProxyTimeOut;
    private final ArrayList<ProxySetThenGet> proxySetThenGets;

    public ProxySetThenGetRequestList() {
        this(new Builder());
    }


    public Builder newBuilder() {
        return new Builder(this);
    }


    private ProxySetThenGetRequestList(Builder builder) {
        this.piid = builder.piid;
        this.totalProxyTimeOut = builder.totalProxyTimeOut;
        this.proxySetThenGets = builder.proxySetThenGets;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends APDUBuilder<ProxySetThenGetRequestList> {
        // 默认三分钟超时时间
        private static final int DEFAULT_OUT_TIME = 180;
        private static final int MIN_OUT_TIME = 10;
        private PIID piid;
        private LongUnsigned totalProxyTimeOut;
        private ArrayList<ProxySetThenGet> proxySetThenGets;

        public Builder() {
            this.piid = new PIID();
            this.totalProxyTimeOut = new LongUnsigned(DEFAULT_OUT_TIME);
            this.proxySetThenGets = new ArrayList<>();
        }

        public Builder(ProxySetThenGetRequestList getRequestNormal) {
            this.piid = getRequestNormal.piid;
            this.totalProxyTimeOut = getRequestNormal.totalProxyTimeOut;
            this.proxySetThenGets = getRequestNormal.proxySetThenGets;
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

        public Builder addSetThenGets(String tsa, SetThenGet... proxySetThenGets) {
            return addSetThenGets(tsa, 0, proxySetThenGets);
        }

        /**
         * @param tsa              一个目标服务器地址
         * @param timeOut          代理一个服务器的超时时间 单位：秒，0表示对于某个服务器的代理超时时间由服务器自行控制。
         * @param setThenGets  若干个对象属性的设置后读取
         * @return
         */
        private Builder addSetThenGets(String tsa, @IntRange(from = 0, to = LongUnsigned.MAX_VALUE) int timeOut, SetThenGet... setThenGets) {
            /*SetThenGet[] setNormalArr = new SetThenGet[proxySetThenGets.length];
            for (int i = 0; i < proxySetThenGets.length; i++) {
                setNormalArr[i] = new SetThenGet(proxySetThenGets[i]);
            }*/
            this.proxySetThenGets.add(new ProxySetThenGet(new TSA(tsa), new LongUnsigned(timeOut), setThenGets));
            return this;
        }


        @Override
        public ProxySetThenGetRequestList build() {
            return new ProxySetThenGetRequestList(this);
        }

        @Override
        public String toHexString() {
            String proxyGetsHexStr = proxySetThenGets.isEmpty() ? "00" : Data.toString4Array(proxySetThenGets);
            return piid.toHexString()
                    + totalProxyTimeOut.toHexString()
                    + proxyGetsHexStr;
        }
    }


    public static final class Parser extends APDUParser<ProxySetThenGetRequestList> {

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
        protected ProxySetThenGetRequestList reBuild() {
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