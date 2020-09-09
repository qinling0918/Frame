package com.hzwq.framelibrary.protocol698.data.manager;

import android.util.SparseArray;

import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2018/5/22 14:06
 * Description:
 */
public class DarManager {
    private SparseArray<String> dars = new SparseArray<>();

    private static class SingleTon {
        private static DarManager instance = new DarManager();
    }
    public static DarManager getInstance(/*int type*/) {
        return SingleTon.instance;
    }
    private DarManager() {
        init();
    }
    public  String getStatusStr(int type) {
        return dars.get(type);
    }
    public  String getStatusStr(String errorTypeHexStr) {
        if (errorTypeHexStr.length()!=2 || !NumberConvert.isHexStr(errorTypeHexStr)){
            throw new IllegalArgumentException("参数异常,需要1字节 16进制字符串");
        }
        return dars.get(Integer.parseInt(errorTypeHexStr,16));
    }

    private void init() {
        dars.put(0, "成功");
        dars.put(1, "硬件失效");
        dars.put(2, "暂时失效");
        dars.put(3, "拒绝读写");
        dars.put(4, "对象未定义");
        dars.put(5, "对象接口类不符合 ");
        dars.put(6, "对象不存在");
        dars.put(7, "类型不匹配");
        dars.put(8, "越界");
        dars.put(9, "数据块不可用");
        dars.put(10, "分帧传输已取消");
        dars.put(11, "不处于分帧传输状态");
        dars.put(12, "块写取消");
        dars.put(13, "不存在块写状态");
        dars.put(14, "数据块序号无效");
        dars.put(15, "密码错/未授权");
        dars.put(16, "通信速率不能更改");
        dars.put(17, "年时区数超");
        dars.put(18, "日时段数超 ");
        dars.put(19, "费率数超 ");
        dars.put(20, "安全认证不匹配");
        dars.put(21, "重复充值");
        dars.put(22, "ESAM验证失败 ");
        dars.put(23, "安全认证失败");
        dars.put(24, "客户编号不匹配");
        dars.put(25, "充值次数错误");
        dars.put(26, "购电超囤积");
        dars.put(27, "地址异常");
        dars.put(28, "对称解密错误");
        dars.put(29, "非对称解密错误 ");
        dars.put(30, "签名错误");
        dars.put(31, "电能表挂起");
        dars.put(32, "时间标签无效");
        dars.put(33, "请求超时");
        dars.put(34, "ESAM的P1P2不正确");
        dars.put(35, "ESAM的LC错误");
        dars.put(255, "其它");

    }
}
