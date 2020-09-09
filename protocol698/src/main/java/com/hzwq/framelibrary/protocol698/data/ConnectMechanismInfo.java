package com.hzwq.framelibrary.protocol698.data;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.string.OctetString;
import com.hzwq.framelibrary.protocol698.data.string.VisibleString;

/**
 * Created by qinling on 2018/5/21 15:07
 * Description: 应用连接请求认证的机制信息 ConnectMechanismInfo
 */
public abstract class ConnectMechanismInfo implements IHex {
    /**
     * 公共连接    [0] NullSecurity，
     * 一般密码    [1] PasswordSecurity，
     * 对称加密    [2] SymmetrySecurity，
     * 数字签名    [3] SignatureSecurity
     */
    private static final String NULL_SECURITY = "00";
    private static final String PASSWORD_SECURITY = "01";
    private static final String SYMMETRY_SECURITY = "02";
    private static final String SIGNATURE_SECURITY = "03";

    public static NullSecurity nullSecurity(){
        return new NullSecurity();
    }
    public static PasswordSecurity passwordSecurity(String password){
        return new PasswordSecurity(password);
    }
    public static SymmetrySecurity symmetrySecurity(String s1,String m1){
        return new SymmetrySecurity(s1,m1);
    }
    public static SignatureSecurity signatureSecurity(String s2,String m2){
        return new SignatureSecurity(s2,m2);
    }

    private static class NullSecurity extends ConnectMechanismInfo {
        @Override
        public String toHexString() {
            return NULL_SECURITY;
        }
    }

    /**
     * 一般密码
     */
    private static class PasswordSecurity extends ConnectMechanismInfo {
        private String password;

        public PasswordSecurity(String password) {
            // todo 做格式校验  16进制，或者字符串转
            this.password = password;
        }
        @Override
        public String toHexString() {
            return PASSWORD_SECURITY + new VisibleString(password).toHexString();
        }
    }

    /**
     * 对称加密
     */
    private static class SymmetrySecurity extends ConnectMechanismInfo {
        private OctetString m1;// 密文1为对客户机产生的随机数加密得到的密文。
        private OctetString s1;

        public SymmetrySecurity(String m1, String s1) {
            // todo 做格式校验  16进制，或者字符串转
            this.m1 = new OctetString(NumberConvert.hexStringToBytes(m1));
            this.s1 = new OctetString(NumberConvert.hexStringToBytes(s1));
        }


        @Override
        public String toHexString() {
            return SYMMETRY_SECURITY +m1.toHexString() + s1.toHexString();
        }
    }

    /**
     * 数字签名
     */
    private static class SignatureSecurity extends ConnectMechanismInfo {
        private OctetString m2;// 密文2为客户机（主站）对服务器（终端）产生的主站证书等数据加密信息。
        private OctetString s2;// 客户机签名2为客户机对密文2的签名

        public SignatureSecurity(String m2, String s2) {
            // todo 做格式校验  16进制，或者字符串转
            this.m2 = new OctetString(NumberConvert.hexStringToBytes(m2));
            this.s2 = new OctetString(NumberConvert.hexStringToBytes(s2));
        }



        @Override
        public String toHexString() {
            return SIGNATURE_SECURITY + m2.toHexString() + s2.toHexString();
        }
    }
}
