package com.hzwq.framelibrary.protocol698.apdu;

/**
 * Created by qinling on 2019/4/28 18:13
 * Description:
 */
public abstract class APDUParser<APDU> {
    protected final String apduStr;
    protected final int parseCode;

    public APDUParser(String apduStr) {
        this.apduStr = apduStr;
        parseCode = checkApduStr(apduStr).getCode();
    }

    protected abstract ParseResult checkApduStr(String apduStr);

    protected abstract String toFormatString();
    protected abstract APDU reBuild();


    public enum ParseResult {
        /**
         * 帧解析结果
         **/
        SUCCESS(0, "解析成功"),
        APDU_ERR_LENGTH(-7, "字符串长度不足，或者长度为奇数"),
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
}
