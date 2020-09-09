package com.hzwq.framelibrary.protocol698;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hzwq.framelibrary.common.IFrame;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.apdu.IAPDU;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;


/**
 * Created by qinling on 2018/5/8 8:53
 * Description: 698协议报文解析组装类
 * 分帧部分参见 Frame698Separator
 */
@SuppressWarnings("ALL")
public class Frame698 implements Cloneable, IFrame {

    @IntDef({FUNCTION_CODE_1_LINK_MANAGER, FUNCTION_CODE_3_USER_DATA,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FunctionCode {
    }

    //功能码 目前仅有1和3有用
    // 链路管理	链路连接管理（登录，心跳，退出登录）
    public static final int FUNCTION_CODE_1_LINK_MANAGER = 0b001;
    // 用户数据	应用连接管理及数据交换服务
    public static final int FUNCTION_CODE_3_USER_DATA = 0b011;

    public static final int ADDRESS_TYPE_SINGLE = 0b00;
    public static final int ADDRESS_TYPE_GROUP = 0b10;
    public static final int ADDRESS_TYPE_BROADCAST = 0b11;
    public static final int ADDRESS_TYPE_MATCH = 0b01;

    public static final int FRAME_MAX_LENGTH = 0b0011111111111111;
    public static final int FRAME_SEPARATE = 0x1800; // 6144   0b0001 1000 0000 0000
    @SuppressWarnings("Annotator")
    public static final String REGEX_SINGLE_GROUP = "^[0-9]+$";
    @SuppressWarnings("Annotator")
    public static final String REGEX_MATCH = "^[A-Aa-a0-9]+$";

    // 传输方向位DIR和启动标志位PRM组合意义
    // 00	客户机对服务器上报的响应
    // 01	客户机发起的请求
    // 10	服务器发起的上报
    // 11	服务器对客户机请求的响应
    private final int dir_prm;
    // 是否分帧  int frameSeparateFlag = isFrameSeparate ? 1: 0
    private final boolean isFrameSeparate;//0：不请求，1：请求
    // 扰码标志 int sc = isScrambling ? 1:0;
    private final boolean isScrambling;//0：不请求，1：请求
    // 功能码  1	链路管理	链路连接管理（登录，心跳，退出登录）
    //3	用户数据	应用连接管理及数据交换服务
    private final int functionCode;//1：链路管理，3：用户数据
    // 地址
    private final String serverAddress;
    // 地址类型
    private final int serverAddressType;
    // 逻辑地址
    private final int logicAddress;
    // 客户机地址
    private final int clientAddress;
    // 分帧序号
       /* bit15=0，bit14=0：表示分帧传输数据起始帧；
        bit15=1，bit14=0：表示分帧传输确认帧（确认帧不包含IAPDU片段域）；
        bit15=0，bit14=1：表示分帧传输最后帧；
        bit15=1，bit14=1：表示分帧传输中间帧。*/

    private final int frameSeparateNumber;
    private final int frameSeparateStatus;
    // 链路层数据
    private final String linkDataStr;

    private final IAPDU linkData;

    //private final String frame698Str;
    //private final int parseCode;

    public Frame698() {
        this(new Builder());
    }

    private Frame698(Builder builder) {

        this.dir_prm = builder.dir_prm;
        this.isFrameSeparate = builder.isFrameSeparate;
        this.isScrambling = builder.isScrambling;
        this.functionCode = builder.functionCode;
        this.serverAddress = builder.serverAddress;
        this.serverAddressType = builder.serverAddressType;
        this.logicAddress = builder.logicAddress;
        this.clientAddress = builder.clientAddress;

        this.frameSeparateNumber = builder.frameSeparateNumber;
        this.frameSeparateStatus = builder.frameSeparateStatus;

        this.linkDataStr = builder.linkDataStr;
        this.linkData = builder.linkData;

    }

    @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }


    @Override
    public String toString() {
        return "Frame698{" +
                "dir_prm=" + dir_prm +
                ", isFrameSeparate=" + isFrameSeparate +
                ", isScrambling=" + isScrambling +
                ", functionCode=" + functionCode +
                ", serverAddress='" + serverAddress + '\'' +
                ", serverAddressType=" + serverAddressType +
                ", logicAddress=" + logicAddress +
                ", clientAddress=" + clientAddress +
                ", frameSeparateNumber=" + frameSeparateNumber +
                ", frameSeparateStatus=" + frameSeparateStatus +
                ", linkDataStr='" + linkDataStr + '\'' +
                ", linkData=" + linkData +
                '}';
    }


    public Builder newBuilder() {
        return new Builder(this);
    }

    public static final class Builder {
        private int dir_prm;
        private boolean isFrameSeparate;//0：不分帧，1：分帧
        private boolean isScrambling;//0：不干扰，1：干扰
        private int functionCode;//1：链路管理，3：用户数据
        //地址
        private String serverAddress;

        //地址类型
        private int serverAddressType;
        //逻辑地址
        private int logicAddress;
        //客户机地址
        private int clientAddress;


        // 分帧序号  表示分帧传输过程的帧序号，取值范围0…4095，循环使用；
        private int frameSeparateNumber;
        /* bit15=0，bit14=0：表示分帧传输数据起始帧；
              bit15=1，bit14=0：表示分帧传输确认帧（确认帧不包含APDU片段域）；
              bit15=0，bit14=1：表示分帧传输最后帧；
              bit15=1，bit14=1：表示分帧传输中间帧。*/
        private int frameSeparateStatus;


        private String linkDataStr;
        private IAPDU linkData;

        public Builder() {
            dir_prm = 0b01; //默认是客户机的请求
            isFrameSeparate = false;
            isScrambling = false;
            functionCode = FUNCTION_CODE_3_USER_DATA;
            serverAddress = "AA";//默认设置为广播地址
            serverAddressType = ADDRESS_TYPE_BROADCAST;//默认设置为广播地址
            logicAddress = 0b00;
            clientAddress = 0;

            frameSeparateNumber = 0;
            frameSeparateStatus = 0b00;

            linkDataStr = "";
            //linkData = IAPDU.get().getRequestNormal(new PIID(0),new OAD());
            linkData = null;

        }

        public Builder(Frame698 frame698) {
            this.dir_prm = frame698.dir_prm;
            this.isFrameSeparate = frame698.isFrameSeparate;
            this.isScrambling = frame698.isScrambling;
            this.functionCode = frame698.functionCode;
            this.serverAddress = frame698.serverAddress;
            this.serverAddressType = frame698.serverAddressType;
            this.logicAddress = frame698.logicAddress;
            this.clientAddress = frame698.clientAddress;
            this.linkDataStr = frame698.linkDataStr;

            this.frameSeparateNumber = frame698.frameSeparateNumber;
            this.frameSeparateStatus = frame698.frameSeparateStatus;
            this.linkData = frame698.linkData;

        }

        /**
         * 传输方向位DIR和启动标志位PRM组合意义
         * 00	客户机对服务器上报的响应
         * 01	客户机发起的请求
         * 10	服务器发起的上报
         * 11	服务器对客户机请求的响应
         *
         * @param dir_prm 传输方向位DIR和启动标志位PRM
         * @return Builder
         */
        public Builder setDirPrm(@IntRange(from = 0, to = 3) int dir_prm) {
            this.dir_prm = dir_prm;
            return this;
        }

        /**
         * 是否分帧
         *
         * @param isFrameSeparate true，false
         * @return Builder
         */
        public Builder setFrameSeparate(boolean isFrameSeparate) {
            this.isFrameSeparate = isFrameSeparate;
            return this;
        }

        /**
         * 是否有干扰码
         *
         * @param isScrambling 是否有干扰码
         * @return Builder
         */
        public Builder setScrambling(boolean isScrambling) {
            this.isScrambling = isScrambling;
            return this;
        }

        /**
         * @param functionCode 功能码
         *                     1	链路管理	链路连接管理（登录，心跳，退出登录）
         *                     3	用户数据	应用连接管理及数据交换服务
         */
        public Builder setFunctionCode(@FunctionCode int functionCode) {
            this.functionCode = functionCode;
            return this;
        }

        /**
         * 设置服务端地址,当地址格式不正确时,
         * 则会将地址类型转成广播地址,
         * 并且将所输入的地址转成广播地址"AA"
         *
         * @param address 服务端地址
         * @return Builder
         */
        private Builder setServerAddress(@NonNull String address) {
            this.serverAddress = checkOrDefault(address, "AA");
            //当是单地址或者组地址时,不可能为"AA", 所以默认转成广播地址
            if (isSingleOrGroup() && this.serverAddress.equalsIgnoreCase("AA")) {
                setServerAddressType(ADDRESS_TYPE_BROADCAST);
            }
            return this;
        }

        public Builder setServerAddress(@NonNull String address, @IntRange(from = 0, to = 3) int addressType) {
            setServerAddressType(addressType);
            setServerAddress(address);
            return this;
        }

        /**
         * @param addressType 0 单地址  1组地址 2 通配地址  3 广播地址
         * @return Builder
         */
        private Builder setServerAddressType(@IntRange(from = 0, to = 3) int addressType) {
            this.serverAddressType = addressType;
            return this;
        }

        public Builder setLogicAddress(@IntRange(from = 0, to = 3) int logicAddress) {
            this.logicAddress = logicAddress;
            return this;
        }

        public Builder setClientAddress(@IntRange(from = 0, to = 255) int clientAddress) {
            this.clientAddress = clientAddress;
            return this;
        }

        public Builder setLinkDataStr(String linkDataStr) {
            this.linkDataStr = linkDataStr;
            return this;
        }

        public Builder setLinkData(IAPDU linkData) {
            this.linkData = linkData;
            return this;
        }

        public Builder setFrameSeparateNumber(@IntRange(from = 0, to = 4095) int frameSeparateNumber) {
            this.frameSeparateNumber = frameSeparateNumber;
            setFrameSeparate(true);
            return this;
        }

        public Builder setFrameSeparateStatus(@IntRange(from = 0, to = 3) int frameSeparateStatus) {
            this.frameSeparateStatus = frameSeparateStatus;
            setFrameSeparate(true);
            return this;
        }

        private String len, ctrl, add, hcs, data, fcs;

        public Frame698 build() {
            return new Frame698(this);
        }

        public String toHexString() {
            /*68H L C A HCS DATA FCS 16H*/
            add = getAddress();
            len = getLength();
            ctrl = getControl();
            hcs = getHCS();
            data = getLinkData();
            fcs = getFCS();
            return String.format(Locale.CHINA, "68%s%s%s%s%s%s16",
                    len, ctrl, add, hcs, data, fcs);

        }

        @Override
        public String toString() {
            return toHexString();
        }

        /**
         * 获取链路数据层16进制字符串
         *
         * @return String 链路数据层16进制字符串
         */
        private String getLinkData() {

            if (null != data && data.length() > 0) return data;

            //判断 链路数据层来源，若是linkDataStr 为空则检查linkData的值，若是二者皆是空，则返回 “”
            linkDataStr = TextUtils.isEmpty(linkDataStr)
                    ? linkData == null ? "" : linkData.toHexString()
                    : linkDataStr;

            String linkDataString = linkDataStr;
            if (isFrameSeparate) {
                linkDataString = getFrameSeparate(linkDataString);
            }

            //如果是扰码,则需要每个字节加33H
            if (isScrambling) {
                return scramblingLinkDataWith(linkDataString);
            }
            return linkDataString;
        }

        /**
         * 获取 链路数据层分帧时 分帧格式域 + IAPDU 的数据
         *
         * @param linkDataString IAPDU 字符串
         * @return 分帧格式域 + IAPDU
         * 分帧格式域 2字节 16bit位
         * // 疑似需要高低位反转
         * bit0…bit11 ：表示分帧传输过程的帧序号，取值范围0…4095，循环使用；
         * bit15-bit14 ：分帧传输数据起始帧（00）确认帧（10），最后帧（01），中间帧（11）
         * 其中确认帧不包含IAPDU片段域
         */
        private String getFrameSeparate(String linkDataString) {

            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append(NumberConvert.toBinaryStr(frameSeparateStatus, 2))
                    .append("00")
                    .append(NumberConvert.toBinaryStr(frameSeparateNumber, 12));
            String frameSeparate = NumberConvert.toHexStr(
                    Integer.parseInt(stringBuffer.toString(), 2), 4);
            // 疑似需要高低位反转
            frameSeparate = NumberConvert.hexStrReverse(frameSeparate);
            linkDataString = frameSeparateStatus == 0b10  //分帧传输确认帧
                    ? frameSeparate : frameSeparate + linkDataString;
            /**
             * 未完， 可能需要linkData中的某些数据对分帧格式域 组建有影响
             */
            // 由服务器或客户机启动传输的数据分帧传输时
              /*  if (dir_prm==0b10 || dir_prm == 0b01){
                    linkDataString = frameSeparateStatus ==  0b10
                        ? frameSeparate : frameSeparate + linkDataString;
                }else{
                }*/
            return linkDataString;
        }

        /*private boolean isEmpty(String str) {
            return null == str || str.length()<=0;
        }*/

        /**
         * 获取经过 干扰运算 的链路数据
         *
         * @return 经过 干扰运算 的链路数据
         */
        // TODO: 疑问: 若是按照每字节 +33H ,则某个字节大于CCH时 ,干扰后为了保证8bit位1字节的长度,或出现补位溢出情况
        // 例如 FF + 33H = 132H  ---结果取值--->  32H
        @NonNull
        private String scramblingLinkDataWith(String linkDataStr) {
            StringBuilder stringBuffer = new StringBuilder();
            byte[] bytes = NumberConvert.hexStringToBytes(linkDataStr);
            System.out.println(bytes[0]);
            int len = bytes.length;
            for (int i = 0; i < len; i++) {
                bytes[i] += 0x33;
            }
            stringBuffer.append(NumberConvert.bytesToHexString(bytes));
            return stringBuffer.toString().toUpperCase(Locale.CHINA);
        }


        /**
         * 获取帧长度域  字节高低位翻转
         *
         * @return 获取帧长度, 以2字节16进制表示
         * 出现错误:
         * 返回"0000"  帧字符串长度为奇数
         * 返回"FFFF"  帧长度超出最大限制
         */
        private String getLength() {
            if (null != len && len.length() > 0) return len;
            // System.out.println("len: " + len);
            int length = 4 +  // 长度本身两个字节
                    getControl().length() +
                    getAddress().length() +
                    4 + // HCS 两个字节
                    getLinkData().length() +
                    4; // FCS 两个字节
            if (length % 2 != 0) { //字符串长度不是偶数,也就是说 字节组成出现问题
                return "0000";
            }
            length /= 2; //获取字节数
            if (length > FRAME_MAX_LENGTH) {
                return "FFFF";
                //throw new IndexOutOfBoundsException("超出了协议目前现有的长度 (2^14) (16383) (3FFFH)");
            }
            String lenStr = NumberConvert.toHexStr(length, 4);
            return NumberConvert.hexStrReverse(lenStr, 0, lenStr.length());
        }

        /**
         * 获取控制域
         *
         * @return 控制域
         */
        private String getControl() {
            if (null != ctrl && ctrl.length() > 0) return ctrl;
            // 是否分帧
            int frameSeparateFlag = isFrameSeparate ? 1 : 0;
            // 扰码标志
            int sc = isScrambling ? 1 : 0;
            // 功能码 1：链路管理，3：用户数据
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append(NumberConvert.toBinaryStr(dir_prm, 2))
                    .append(frameSeparateFlag)
                    .append(0)
                    .append(sc)
                    .append(NumberConvert.toBinaryStr(functionCode, 3));

            return NumberConvert.binaryStrToHexStr(stringBuffer.toString()).toUpperCase(Locale.CHINA);

        }

        /**
         * 地址类型	逻辑地址	地址长度N 服务器地址  客户机地址
         * 服务器地址 高低字节位 翻转
         *
         * @return 逻辑地址 服务器地址
         */
        private String getAddress() {
            if (null != add && add.length() > 0) return add;
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer
                    .append(getAddressFirstByte(serverAddressType, logicAddress, serverAddress))
                    .append(NumberConvert.hexStrReverse(serverAddress, 0, serverAddress.length()))
                    .append(NumberConvert.toHexStr(clientAddress, 2));
            return stringBuffer.toString().toUpperCase(Locale.CHINA);
        }

        /**
         * 获取SA 的第一个字节
         *
         * @param serverAddressType 服务端地址类型
         * @param logicAddress      逻辑地址
         * @param serverAddress     服务端地址
         * @return SA 的第一个字节
         */
        private String getAddressFirstByte(int serverAddressType, int logicAddress, String serverAddress) {
            String binStr = NumberConvert.toBinaryStr(serverAddressType, 2)
                    + (NumberConvert.toBinaryStr(logicAddress, 2))
                    + (NumberConvert.toBinaryStr(serverAddress.length() / 2 - 1, 4));

            return NumberConvert.binaryStrToHexStr(binStr).toUpperCase(Locale.CHINA);
        }

        /**
         * 获取帧头校验HCS
         *
         * @return 2字节字符串
         */
        private String getHCS() {
            if (null != hcs && hcs.length() > 0) return hcs;
            String strHCS = getLength() + getControl() + getAddress();
            return NumberConvert.getFcsOrHcs(strHCS).toUpperCase(Locale.CHINA);
        }


        /**
         * 获取帧头校验FCS
         *
         * @return 2字节字符串
         */
        private String getFCS() {
            if (null != fcs && fcs.length() > 0) return fcs;
            String strFCS = getLength() + getControl() + getAddress() + getHCS() + getLinkData();
            return NumberConvert.getFcsOrHcs(strFCS).toUpperCase(Locale.CHINA);
        }


        /**
         * 是不是单地址或者组地址
         *
         * @return true 是单地址或者组地址
         */
        private boolean isSingleOrGroup() {
            return serverAddressType == ADDRESS_TYPE_SINGLE || serverAddressType == ADDRESS_TYPE_GROUP;
        }

        /**
         * 检查服务器地址是否符合格式
         *
         * @param address  单地址/组地址 包括 纯数字(10进制)的地址字符串, 含有F 的占位的奇数长度的 10进制数字字符串
         *                 通配地址  0- 9 以及a或者A 组成的字符串,含有F的占位符,
         *                 广播地址  "AA"
         * @param defValue 默认AA
         */
        private String checkOrDefault(String address, String defValue) {
            String address_copy = address;
            String address_endCase = address.substring(address.length() - 1);
            if (address_endCase.equalsIgnoreCase("F")) {
                address = address.substring(0, address.length() - 1);
            }
            if (null == address || TextUtils.isEmpty(address)) {
                return defValue.toUpperCase();
            }
            if (serverAddressType == ADDRESS_TYPE_BROADCAST) {
                return "AA";
            }

            String regex = REGEX_SINGLE_GROUP;
            if (isSingleOrGroup()) {
                regex = REGEX_SINGLE_GROUP;
            } else if (serverAddressType == ADDRESS_TYPE_MATCH) {
                regex = REGEX_MATCH;
            }
            address = address.matches(regex)
                    ? address : defValue;
            address = address.length() % 2 == 0 ? address : address + "F";
            if (address_copy.equalsIgnoreCase(address)) {
                return address_copy;
            }
            return address;
        }
    }

    /**
     * 输出报文解析结果
     *
     * @param frame698Str 报文
     * @return String 报文解析结果
     */
    public String format(String frame698Str) {
        return parse(frame698Str).toFormatString();
    }

    public Parser parse(String frame698Str) {
        return new Parser(frame698Str);
    }

    public static final class Parser {
        private final String frame698Str;
        /**
         * 0 正常
         * -1 null或不是16进制
         * -2 长度不对，最少也要12字节，或者字符串长度为奇数
         * -3 帧起始码、帧结束码 格式不对
         * -4 长度域值与 该帧字符串不符合
         * -5 校验值不符
         * -6 命令帧无法获取状态码
         */
        public final int parseCode;

        enum ParseResult {
            /**
             * 帧解析结果
             **/
            SUCCESS(0, "解析成功"),
            ERROR_STRING_FORMAT(-1, "所输入字符串格式不对，null或不是16进制"),
            ERROR_STRING_LENGTH(-2, "帧字符串长度不足，或者长度为奇数"),
            ERROR_FRAME_FORMAT(-3, "帧起始码、帧结束码 格式不对"),
            ERROR_FRAME_LENGTH(-4, "长度域值与 该帧字符长度不符合"),
            ERROR_FRAME_HCS(-5, "帧头校验HCS 不符"),
            ERROR_Frame_Fcs(-6, "帧校验FCS 不符"),
            ERROR_UNKNOW(0xff, "未知错误"),;

            public int getCode() {
                return code;
            }

            public String getErrMsg() {
                return errMsg;
            }

            // 错误码
            private int code;
            // 错误信息
            private String errMsg;

            ParseResult(int code, String errMsg) {
                this.code = code;
                this.errMsg = errMsg;
            }
        }

        /**
         * 根据解析码获取错误信息
         *
         * @param parseCode
         * @return
         */
        public String getErrorStr() {
            for (ParseResult error : ParseResult.values()) {
                if (parseCode == error.getCode()) {
                    return error.getErrMsg();
                }
            }
            return ParseResult.ERROR_UNKNOW.getErrMsg();
        }

        //a)采用串行通信方式实现本地数据传输时，在发送数据时，在有效数据帧前加4个FEH作为前导码。
        public Parser(String frame698Str) {
            /*if (!TextUtils.isEmpty(frame698Str) && frame698Str.length() >= 10 && frame698Str.substring(0, 10).equalsIgnoreCase("FEFEFEFE68")) {
                frame698Str = frame698Str.substring(8); //将 “fefefefe” 去掉.
            }*/
            if (!TextUtils.isEmpty(frame698Str) && frame698Str.toUpperCase(Locale.CHINA).startsWith("FEFEFEFE68")) {
                frame698Str = frame698Str.substring(8);
            }
            this.frame698Str = frame698Str;
            parseCode = checkFrame698(frame698Str);
        }


        /**
         * 校验是不是  698协议帧格式  最少12字节
         *
         * @param frame698Str 698协议帧
         * @return 0 正常
         * -1 null或不是16进制
         * -2 长度不对，最少也要12字节，或者字符串长度为奇数
         * -3 帧起始码、帧结束码 格式不对
         * -4 长度域值与 该帧字符串不符合
         * -5 帧头校验HCS 不符
         * -6 帧校验FCS 不符
         */

        private int checkFrame698(String frame698Str) {
            return checkFrame(frame698Str).getCode();
        }

        private ParseResult checkFrame(String frame698Str) {
            if (TextUtils.isEmpty(frame698Str) || !NumberConvert.isHexStr(frame698Str))
                return ParseResult.ERROR_STRING_FORMAT;
            int len = frame698Str.length();


            if (len < 24 || len % 2 != 0) return ParseResult.ERROR_STRING_LENGTH;
            if ((!frame698Str.substring(0, 2).equalsIgnoreCase("68") || !frame698Str.substring(len - 2, len).equalsIgnoreCase("16")))
                return ParseResult.ERROR_FRAME_FORMAT;

            if (!getLengthStr(frame698Str).equalsIgnoreCase(NumberConvert.toHexStr((len - 4) / 2, 4)))
                return ParseResult.ERROR_FRAME_LENGTH;  //去除头尾字符两个字节长度

            // 地址域第一个字节是帧的 第5字节  即 8 + （服务地址长度字节数（serverAddressLength+1））*2  +客户端长度 2
            int addressIndexEnd = getAddressIndexEnd(frame698Str);
            String frame_head = frame698Str.substring(2, addressIndexEnd);
            String hcs = frame698Str.substring(addressIndexEnd, addressIndexEnd + 4);
/*
            if (!NumberConvert.getFcsOrHcs(frame_head).equalsIgnoreCase(hcs))
                return ParseResult.ERROR_FRAME_HCS;
            if (!NumberConvert.getFcsOrHcs(frame698Str.substring(2, len - 6)).equalsIgnoreCase(frame698Str.substring(len - 6, len - 2)))
                return ParseResult.ERROR_Frame_Fcs;*/

            return ParseResult.SUCCESS;
        }

        /**
         * 获取长度域字符串
         *
         * @param frame698Str 698帧报文
         * @return String 长度域字符串
         */
        private String getLengthStr(String frame698Str) {
            frame698Str = NumberConvert.hexStrReverse(frame698Str.substring(2, 6), 0, 4);
            return null == frame698Str ? "" : frame698Str;
        }

        /**
         * 获取服务地址第一字节 字符串
         *
         * @param frame698Str 698帧报文
         * @return 服务地址第一字节 字符串
         */
        private String getAddressFirstByteStr(String frame698Str) {
            return frame698Str.substring(8, 10);
        }

        /**
         * 获取地址域第一个字节的2进制字符串
         *
         * @param frame698Str 698帧报文
         * @return String 地址域第一个字节的2进制字符串
         */
        private String getAddressFirstByteBinaryStr(String frame698Str) {
            return NumberConvert.toBinaryStr(Integer.parseInt(getAddressFirstByteStr(frame698Str), 16), 8);
        }

        private int getServerAddressLength(String frame698Str) {
            String binaryStr = getAddressFirstByteBinaryStr(frame698Str);
            return Integer.parseInt(binaryStr.substring(4, binaryStr.length()), 2);
        }

        /**
         * 地址最后一个字节在字符串中的index
         * int addressStart = 8
         * SA = firstByte（2） + ServerAddress（（getServerAddressLength（）+1）* 2）
         * CA = 2
         * <p>
         * 即  8+ 2+ （getServerAddressLength（）+1）* 2 +2
         * // 地址域第一个字节是帧的 第5字节  即 8 + 第一个字节长度 + （服务地址长度字节数（serverAddressLength+1））*2  +客户端长度 2
         *
         * @param frame698Str 698帧报文
         * @return 地址最后一个字节在字符串中的index
         */
        protected int getAddressIndexEnd(String frame698Str) {
            return 8 + 2 + (getServerAddressLength(frame698Str) + 1) * 2 + 2;
        }

        /**
         * 传输方向位DIR和启动标志位PRM组合意义
         * 00	客户机对服务器上报的响应
         * 01	客户机发起的请求
         * 10	服务器发起的上报
         * 11	服务器对客户机请求的响应
         *
         * @return int 传输方向位DIR和启动标志位PRM组合意义
         */
        public int getDirPrm() {
            if (parseCode != 0) return parseCode;
            String binaryStr = getControlBinaryStr();
            return Integer.parseInt(binaryStr.substring(0, 2), 2);
        }

        private String getControlBinaryStr() {
            return NumberConvert.toBinaryStr(Integer.parseInt(getControl(), 16), 8);
        }


        /**
         * 是否分帧
         * 0 不分帧， 1 分帧
         *
         * @return int 是否分帧
         */
        public int getFrameSeparateCode() {
            if (parseCode != 0) return parseCode;
            String binaryStr = getControlBinaryStr();
            return Integer.parseInt(binaryStr.substring(2, 3), 2);
        }

        public boolean isFrameSeparate() {
            return getFrameSeparateCode() == 1;
        }

        /**
         * 是否有干扰码
         *
         * @return 正常   0  不干扰   或 1 干扰
         * 异常   <0  根据parseCode 说明
         */
        public int getFrameIsScrambling() {
            if (parseCode != 0) return parseCode;
            String binaryStr = getControlBinaryStr();
            return Integer.parseInt(binaryStr.substring(4, 5), 2);
        }

        /**
         * 功能码
         * 1	链路管理	链路连接管理（登录，心跳，退出登录）
         * 3	用户数据	应用连接管理及数据交换服务
         */
        public int getFunctionCode() {
            if (parseCode != 0) return parseCode;
            String binaryStr = getControlBinaryStr();
            return Integer.parseInt(binaryStr.substring(5, 8), 2);
        }

        /**
         * @return 服务地址经过高低位反转
         */
        public String getServerAddress() {
            String serverAddress = getServerAddressStr();
            return NumberConvert.hexStrReverse(serverAddress, 0, serverAddress.length());
        }

        /**
         * 获取服务地址,高低位字节未反转
         *
         * @return 服务地址
         */
        private String getServerAddressStr() {
            if (parseCode != 0) return parseCode + "";
            return frame698Str.substring(10, getAddressIndexEnd(frame698Str) - 2);
        }

        public String getServerAddressWithFirstByte() {
            if (parseCode != 0) return parseCode + "";
            return getAddressFirstByteStr(frame698Str) + getServerAddress();
        }

        /**
         * 服务端地址类型 0 单地址  1组地址 2 通配地址  3 广播地址
         *
         * @return int 服务端地址类型
         */

        public int getServerAddressType() {
            if (parseCode != 0) return parseCode;
            String binaryStr = getAddressFirstByteBinaryStr(frame698Str);
            return Integer.parseInt(binaryStr.substring(0, 2), 2);
        }

        /**
         * @return int 逻辑地址
         */
        public int getLogicAddress() {
            if (parseCode != 0) return parseCode;
            String binaryStr = getAddressFirstByteBinaryStr(frame698Str);
            return Integer.parseInt(binaryStr.substring(2, 4), 2);
        }

        /**
         * @return int 客户端地址
         */
        public int getClientAddress() {
            if (parseCode != 0) return parseCode;
            return Integer.parseInt(getClientAddressStr(), 16);
        }

        public String getClientAddressStr() {
            if (parseCode != 0) return parseCode + "";
            int start = getAddressIndexEnd(frame698Str) - 2;
            int end = getAddressIndexEnd(frame698Str);
            return frame698Str.substring(start, end);
        }

        /**
         * 链路数据层
         *
         * @return String 链路数据层
         */
        public String getLinkDataStr() {
            if (parseCode != 0) return parseCode + "";
            // (1字节 帧结束字符 + 2字节帧校验FCS ) * 2
            String linkDataStr = getLinkDataStrNoSeparate();

            if (getFrameIsScrambling() == 1) {
                linkDataStr = unScramblingLinkData(linkDataStr);
            }
            // 若是分帧 则前两个字节是分帧格式
            if (getFrameSeparateCode() == 1) {
                linkDataStr = linkDataStr.substring(4, linkDataStr.length());
            }

            // todo 应该在checkFrame 中 检查是不是分帧 格式，若是则再判断， 分帧格式中 序列号以及   启动，确认，传输，最后四种帧
            return linkDataStr;
        }

        /**
         * 不分帧情况下获取链路数据
         *
         * @return String
         */
        @NonNull
        private String getLinkDataStrNoSeparate() {
            int end = frame698Str.length() - 6;
            //8 + 服务地址长度（serverAddressLength+1）+1字节客户端长度 2 + 2字节帧头校验HCS
            int start = getAddressIndexEnd(frame698Str) + 4;
            return frame698Str.substring(start, end);
        }


        /**
         * 将经过干扰运算 的链路数据转为  未干扰的数据
         *
         * @return String
         */

        @NonNull
        private String unScramblingLinkData(String scramblingStr) {
            StringBuilder stringBuffer = new StringBuilder();
            byte[] bytes = NumberConvert.hexStringToBytes(scramblingStr);
            int len = bytes.length;
            for (int i = 0; i < len; i++) {
                int minuend = bytes[i] < 0x33 ? 0x33 - 0x100 : 0x33;
                bytes[i] -= minuend;
            }
            stringBuffer.append(NumberConvert.bytesToHexString(bytes));
            return stringBuffer.toString().toUpperCase(Locale.CHINA);
        }

        /**
         * 获取控制域
         *
         * @return String 控制码
         */
        public String getControl() {
            if (parseCode != 0) return parseCode + "";
            return frame698Str.substring(6, 8);

        }

        /**
         * 根据698字符串重新 建造出 698对象
         *
         * @return 698协议帧对象
         */
        public Frame698 reBuild() {
            if (parseCode != 0) return new Frame698().newBuilder().setLinkDataStr("解析失败").build();
            return new Frame698().newBuilder()
                    .setDirPrm(getDirPrm())
                    .setScrambling(getFrameIsScrambling() == 1)
                    .setFunctionCode(getFunctionCode())
                    .setServerAddress(getServerAddress(), getServerAddressType())
                    .setLogicAddress(getLogicAddress())
                    .setClientAddress(getClientAddress())
                    .setLinkDataStr(getLinkDataStr())
                    .setFrameSeparateStatus(getFrameSeparateStatus())
                    .setFrameSeparateNumber(getFrameSeparateNumber())
                    .setFrameSeparate(getFrameSeparateCode() == 1)
                    .build();
        }

        /**
         * 获取分帧标志位
         *
         * @return int
         */
        public int getFrameSeparateNumber() {
            if (parseCode != 0) return parseCode;
            return getInfoFromFrameSeparateAreaStr(4, 16);
        }

        //68c600e3050100000000000083ae0000900082021c850100401
        //0000900082021c8501......

        /**
         * 分帧状态码
         *
         * @return int
         */
        public int getFrameSeparateStatus() {
            if (parseCode != 0) return parseCode;
            return getInfoFromFrameSeparateAreaStr(0, 2);
        }

        /**
         * 获取分帧标志位字符串，其中 bit15，bit14 为分帧标识（0，2）
         * bit12-bit0 标识分帧序号（4，16）
         *
         * @param start 起始位
         * @param end   结束位
         * @return 分帧标志位
         */
        private int getInfoFromFrameSeparateAreaStr(int start, int end) {
            String separateFrameAreaStr = getFrameSeparateStr();
            if (getFrameSeparateCode() != 1) return 0;
            String separateFrameAreaBinaryStr = NumberConvert.
                    toBinaryStr(Integer.parseInt(separateFrameAreaStr, 16), 16);
            //frame698Str.substring(6, 8);
            return Integer.parseInt(separateFrameAreaBinaryStr.substring(start, end), 2);
        }


        private boolean isFrameSeparateAreaEmpty(String separateFrameAreaStr) {
            return TextUtils.isEmpty(separateFrameAreaStr) || separateFrameAreaStr.length() == 0;
        }

        /**
         * 获取 分帧格式域  高低位转换
         *
         * @return 分帧格式域
         */
        private String getFrameSeparateStr() {
            if (parseCode != 0) return parseCode + "";
            if (getFrameSeparateCode() != 1) return "";
            return NumberConvert.hexStrReverse(getLinkDataStrNoSeparate().substring(0, 4), 0, 4);
        }

      /*  public IIAPDUParser parseIAPDU() {
            if (parseCode != 0) return null;
            return IAPDU.parse(getLinkDataStr());
        }*/

        /**
         * 用来帮助解析
         *
         * @return String
         */
        public String toFormatString() {
            //String frame698Str = toHexString();
            // Frame698.Parser parser = new Frame698.Parser(frame698Str);
            if (parseCode != 0) return "解析错误，错误码：" + parseCode + " " + getErrorStr();
            String lenStr = getLengthStr(frame698Str);
            String[] dir_prmStrs = new String[]{
                    "客户机对服务器上报的响应",
                    "客户机发起的请求",
                    "服务器发起的上报",
                    "服务器对客户机请求的响应",
            };
            String[] serverAddressType = new String[]{
                    "单地址",
                    "组地址",
                    "通配地址",
                    "广播地址",
            };
            String[] separateStatus = new String[]{
                    "起始帧",// 0b00
                    "最后帧",// 0b01
                    "确认帧",// 0b10
                    "中间帧",// 0b11
            };
            return "698报文帧: " + frame698Str + "\n" +
                    frame698Str.substring(0, 2) + " 帧头\n" +
                    frame698Str.substring(2, 6) + " 帧长度L:" + Integer.parseInt(lenStr, 16) + "字节（10进制）\n" +
                    getControl() + " 控制码C " + dir_prmStrs[getDirPrm()] + " "
                    + (getFrameSeparateCode() == 1 ? " " : " 不") + "分帧"
                    + (getFrameIsScrambling() == 1 ? " " : " 不") + "扰码  "
                    + getFuctionStr(getFunctionCode()) + "\n" +
                    getAddressFirstByteStr(frame698Str) + " 服务器地址SA第一个字节\n"
                    + getServerAddressStr() + " 服务器地址SA: "
                    + getServerAddress() + "  地址类型："
                    + serverAddressType[getServerAddressType()] + "\n" +
                    getClientAddressStr() + " 客户端地址CA: " + getClientAddress() + "\n" +
                    frame698Str.substring(getAddressIndexEnd(frame698Str), getAddressIndexEnd(frame698Str) + 4) + " 帧头校验HCS\n" +
                    (getFrameSeparateCode() == 1 ? getFrameSeparateStr() + " 分帧传输" + separateStatus[getFrameSeparateStatus()] + " 分帧序号： " + getFrameSeparateNumber() + "\n" : "") +
                    getLinkDataStr() + " 链路用户数据\n" +
                    frame698Str.substring(frame698Str.length() - 6, frame698Str.length() - 2) + " 帧校验\n" +
                    frame698Str.substring(frame698Str.length() - 2, frame698Str.length()) + " 帧尾";

        }

        /**
         * 根据功能码获取 状态解释
         *
         * @param functionCode 功能码
         * @return String 功能码对应的解释
         */
        private String getFuctionStr(int functionCode) {
            switch (functionCode) {
                case 1:
                    return "链路连接管理（登录，心跳，退出登录）";
                case 3:
                    return "用户数据(应用连接管理及数据交换服务)";
                default:
                    return "保留";
            }
        }
    }
}
