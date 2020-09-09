package com.hzwq.framelibrary.protocol698.apdu.security;


import com.hzwq.framelibrary.protocol698.apdu.IAPDU;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.MAC;
import com.hzwq.framelibrary.protocol698.data.data_verification.IVerification;
import com.hzwq.framelibrary.protocol698.data.data_verification.RN;
import com.hzwq.framelibrary.protocol698.data.data_verification.RN_MAC;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by qinling on 2018/5/11 14:56
 * Description: 安全传输协议数据单元（SECURITY-APDU）
 */
public class SecurityAPDU implements IAPDU {
    private String requestStr;
    private boolean isPlaintText;//是不是明文应用数据单元
    private IVerification verification;// 数据验证信息

    @Retention(RetentionPolicy.SOURCE)
    public @interface FunctionCode {
    }

    public SecurityAPDU(ISecurityableAPDU SecurityableAPDU) {
        this.requestStr = SecurityableAPDU.toHexString();
        initVerification();
    }


    private void initVerification() {
        verification = new RN_MAC(new RN(), new MAC());
    }

    @Override
    public String toHexString() {
        String isPlaintStr = isPlaintText ? "00" : "01";
        if (requestStr.length() % 2 != 0) {
            // todo 可以返回 Data中的 NULL
            // 表示请求串长度不正确，返回null
            return "00";
        }
        int byteSize = requestStr.length() / 2;
        return isPlaintStr
                + NumberConvert.toHexStr(byteSize, 2)
                + requestStr  // octet-string 明文/密文应用数据单元
                + NumberConvert.toHexStr(verification.verificationType(), 2)
                + verification.toString();
    }

    /**
     * 是不是明文
     *
     * @return
     */
    public boolean isPlaintText() {
        return isPlaintText;
    }

    public SecurityAPDU setIsPlaintText(boolean isPlaintText) {
        this.isPlaintText = isPlaintText;
        return this;
    }

    /**
     * 设置数据验证信息
     *
     * @param verification
     * @return
     */
    public SecurityAPDU setVerification(IVerification verification) {
        this.verification = verification;
        return this;
    }
}