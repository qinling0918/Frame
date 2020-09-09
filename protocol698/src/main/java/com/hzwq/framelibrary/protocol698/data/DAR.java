package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/22 11:24
 * Description:
 */
public enum DAR implements IData {
    SUCESS(0, "成功"),
    ERROR1(1, "硬件失效"),
    ERROR2(2, "暂时失效"),
    ERROR3(3, "拒绝读写"),
    ERROR4(4, "对象未定义"),
    ERROR5(5, "对象接口类不符合 "),
    ERROR6(6, "对象不存在"),
    ERROR7(7, "类型不匹配"),
    ERROR8(8, "越界"),
    ERROR9(9, "数据块不可用"),
    ERROR10(10, "分帧传输已取消"),
    ERROR11(11, "不处于分帧传输状态"),
    ERROR12(12, "块写取消"),
    ERROR13(13, "不存在块写状态"),
    ERROR14(14, "数据块序号无效"),
    ERROR15(15, "密码错/未授权"),
    ERROR16(16, "通信速率不能更改"),
    ERROR17(17, "年时区数超"),
    ERROR18(18, "日时段数超 "),
    ERROR19(19, "费率数超 "),
    ERROR20(20, "安全认证不匹配"),
    ERROR21(21, "重复充值"),
    ERROR22(22, "ESAM验证失败 "),
    ERROR23(23, "安全认证失败"),
    ERROR24(24, "客户编号不匹配"),
    ERROR25(25, "充值次数错误"),
    ERROR26(26, "购电超囤积"),
    ERROR27(27, "地址异常"),
    ERROR28(28, "对称解密错误"),
    ERROR29(29, "非对称解密错误 "),
    ERROR30(30, "签名错误"),
    ERROR31(31, "电能表挂起"),
    ERROR32(32, "时间标签无效"),
    ERROR33(33, "请求超时"),
    ERROR34(34, "ESAM的P1P2不正确"),
    ERROR35(35, "ESAM的LC错误"),
    ERROR_ELSE(255, "其它");

    private int value;
    private String statusStr;

    private DAR(int value, String statusStr) {
        this.value = value;
        this.statusStr = statusStr;
    }

    @Override
    public int dataType() {
        return 22;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @Override
    public String toString() {
        return "DAREnum{" +
                "value=" + value +
                ", statusStr='" + statusStr + '\'' +
                '}';
    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value, 2);
    }
}