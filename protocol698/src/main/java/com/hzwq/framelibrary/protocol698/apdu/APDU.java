package com.hzwq.framelibrary.protocol698.apdu;

public  class APDU implements IAPDU {
    public static final int NO_CLASSIFY = 0x00;

    /**
     * 预连接请求
     */
    public static final int LINK_REQUEST = 0x01;
    /**
     * 预连接响应
     */

    public static final int LINK_RESPONSE = 0x81;

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

    /**
     * 建立应用连接响应
     */
    public static final int CONNECT_RESPONSE = 0x82;
    /**
     * 断开应用连接响应
     */
    public static final int RELEASE_RESPONSE = 0x83;
    /**
     * 断开应用连接通知
     */
    public static final int RELEASE_NOTIFICATION = 0x84;
    /**
     * 读取响应
     */
    public static final int GET_RESPONSE = 0x85;
    /**
     * 设置响应
     */
    public static final int SET_RESPONSE = 0x86;
    /**
     * 操作响应
     */
    public static final int ACTION_RESPONSE = 0x87;
    /**
     * 上报通知
     */
    public static final int REPORT_NOTIFICATION = 0x88;
    /**
     * 代理响应
     */
    public static final int PROXY_RESPONSE = 0x89;
    /**
     * Client  异常响应
     */
    public static final int ERROR_RESPONSE_SERVER = 0xEE;

    /******* SECURITY_APDU  ******************/

    public static final int SECURITY_REQUEST = 0x10; //0x10 == 16
    public static final int SECURITY_RESPONSE = 0x90; //0X90 == 144

    //TYPE
    // 读取,操作,代理等(請求/响应)的数据类型,与mode
    public static final int NO_TYPE = 0x00;
    public static final int TYPE1 = 0x01;
    public static final int TYPE2 = 0x02;
    public static final int TYPE3 = 0x03;
    public static final int TYPE4 = 0x04;
    public static final int TYPE5 = 0x05;
    public static final int TYPE6 = 0x06;
    public static final int TYPE7 = 0x07;

    @Override
    public String toHexString() {
        return null;
    }

    public int type = NO_CLASSIFY;
    public int classify = NO_TYPE;

   /* public static IAPDUParser parse(String apduStr) {
        return createApduParser(apduStr);
    }

    public static IAPDUParser parse(String apduStr, boolean isSeparate) {
        return createApduParser(apduStr, isSeparate);
    }

    public static LinkAPDUParser parseLink(String apduStr) {
        return (LinkAPDUParser) parse(apduStr);
    }

    public static ConnectAPDUParser parseConnect(String apduStr) {
        return (ConnectAPDUParser) parse(apduStr);
    }

    public static GetAPDUParser parseGet(String apduStr) {
        return (GetAPDUParser) parse(apduStr);
    }

    public static SetAPDUParser parseSet(String apduStr) {
        return (SetAPDUParser) parse(apduStr);

    }

    public static ActionAPDUParser parseAction(String apduStr) {
        return (ActionAPDUParser) parse(apduStr);
    }

    public static ReportAPDUParser parseReport(String apduStr) {
        return (ReportAPDUParser) parse(apduStr);
    }
    public static ProxyAPDUParser parseProxy(String apduStr) {
        return (ProxyAPDUParser) parse(apduStr);
    }*/
    /*public static ErrorAPDUParser parseError(String apduStr) {
        return (ErrorAPDUParser) parse(apduStr);
    }*/

    /**
     * 选择相应的解析器
     *
     * @param apduStr
     * @return
     */
    // todo 分帧相应逻辑未完成
 /*   private static IAPDUParser createApduParser(String apduStr, boolean isSeparate) {
        IAPDUParser DEFAULT_PARSER = APDUParser.DEFAULT_PARSER(apduStr);

        if (TextUtils.isEmpty(apduStr) || !NumberConvert.isHexStr(apduStr)) return DEFAULT_PARSER;
        int len = apduStr.length();

        if (len >= 2) {
            int classify = Integer.parseInt(apduStr.substring(0, 2), 16);
            switch (classify) {
                case APDU.LINK_RESPONSE:
                    return new LinkAPDUParser(apduStr);
                case APDU.CONNECT_RESPONSE:
                    return new ConnectAPDUParser(apduStr);
                case APDU.RELEASE_RESPONSE:
                case APDU.RELEASE_NOTIFICATION:
                case APDU.GET_RESPONSE:
                    return new GetAPDUParser(apduStr);
                case APDU.SET_RESPONSE:
                case APDU.ACTION_RESPONSE:
                case APDU.REPORT_NOTIFICATION:
                case APDU.PROXY_RESPONSE:
                    return new GetAPDUParser(apduStr);
                case APDU.ERROR_RESPONSE_SERVER:
                    return DEFAULT_PARSER;
                case APDU.SECURITY_RESPONSE:
                    return new SecurityAPDUParser(apduStr, isSeparate);
                default:
                    return DEFAULT_PARSER;
            }
        }
        return DEFAULT_PARSER;

    }*/


}