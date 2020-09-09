package com.hzwq.framelibrary.protocol698.data.enums;

import com.hzwq.framelibrary.common.util.NumberConvert;



/**
 * Created by qinling on 2018/5/22 11:24
 * Description:
 */
public class DAREnum extends Enum {
    @Override
    public int enumType() {
        return DAR;
    }
    public static DAREnum SUCESS = new DAREnum(0,"成功");
    public static DAREnum ERROR1 = new DAREnum(1, "硬件失效");
    public static DAREnum ERROR2 = new DAREnum(2, "暂时失效");
    public static DAREnum ERROR3 = new DAREnum(3, "拒绝读写");
    public static DAREnum ERROR4 = new DAREnum(4, "对象未定义");
    public static DAREnum ERROR5 = new DAREnum(5, "对象接口类不符合 ");
    public static DAREnum ERROR6 = new DAREnum(6, "对象不存在");
    public static DAREnum ERROR7 = new DAREnum(7, "类型不匹配");
    public static DAREnum ERROR8 = new DAREnum(8, "越界");
    public static DAREnum ERROR9 = new DAREnum(9, "数据块不可用");
    public static DAREnum ERROR10 = new DAREnum(10, "分帧传输已取消");
    public static DAREnum ERROR11 = new DAREnum(11, "不处于分帧传输状态");
    public static DAREnum ERROR12 = new DAREnum(12, "块写取消");
    public static DAREnum ERROR13 = new DAREnum(13, "不存在块写状态");
    public static DAREnum ERROR14 = new DAREnum(14, "数据块序号无效");
    public static DAREnum ERROR15 = new DAREnum(15, "密码错/未授权");
    public static DAREnum ERROR16 = new DAREnum(16, "通信速率不能更改");
    public static DAREnum ERROR17 = new DAREnum(17, "年时区数超");
    public static DAREnum ERROR18 = new DAREnum(18, "日时段数超 ");
    public static DAREnum ERROR19 = new DAREnum(19, "费率数超 ");
    public static DAREnum ERROR20 = new DAREnum(20, "安全认证不匹配");
    public static DAREnum ERROR21 = new DAREnum(21, "重复充值");
    public static DAREnum ERROR22 = new DAREnum(22, "ESAM验证失败 ");
    public static DAREnum ERROR23 = new DAREnum(23, "安全认证失败");
    public static DAREnum ERROR24 = new DAREnum(24, "客户编号不匹配");
    public static DAREnum ERROR25 = new DAREnum(25, "充值次数错误");
    public static DAREnum ERROR26 = new DAREnum(26, "购电超囤积");
    public static DAREnum ERROR27 = new DAREnum(27, "地址异常");
    public static DAREnum ERROR28 = new DAREnum(28, "对称解密错误");
    public static DAREnum ERROR29 = new DAREnum(29, "非对称解密错误 ");
    public static DAREnum ERROR30 = new DAREnum(30, "签名错误");
    public static DAREnum ERROR31 = new DAREnum(31, "电能表挂起");
    public static DAREnum ERROR32 = new DAREnum(32, "时间标签无效");
    public static DAREnum ERROR33 = new DAREnum(33, "请求超时");
    public static DAREnum ERROR34 = new DAREnum(34, "ESAM的P1P2不正确");
    public static DAREnum ERROR35 = new DAREnum(35, "ESAM的LC错误");
    public static DAREnum ERROR_ELSE= new DAREnum(255, "其它");

    private String statusStr;

    private DAREnum(int value, String statusStr) {
        super(value);
        this.statusStr = statusStr;
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


}
