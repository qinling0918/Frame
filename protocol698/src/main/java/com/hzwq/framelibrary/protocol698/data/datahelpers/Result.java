package com.hzwq.framelibrary.protocol698.data.datahelpers;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.TSA;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;
import com.hzwq.framelibrary.protocol698.data.string.BitString;

/**
 * Created by qinling on 2018/5/16 15:48
 * Description:  代理若干个服务器的对象属性
 * 一个目标服务器地址        TSA，
 * 代理一个服务器的超时时间  long-unsigned，
 * 若干个对象属性描述符及其数据  SEQUENCE OF
 * <p>
 * SetNormal
 * {
 * 对象属性描述符  OAD，
 * 及其数据        Data
 * }
 */

public class Result implements IHex {

    /**
     * 时钟可信标志
     */
    private boolean clockTrustedMark;
    /**
     * 0：成功，1：地址重复，2：非法设备，3：容量不足，其它值：保留。
     */
    private int status;


    public Result(boolean clockTrustedMark, @IntRange(from = 0, to = 3) int status) {
        //  super(caculateValue(clockTrustedMark, status));
        this.clockTrustedMark = clockTrustedMark;
        this.status = status;
    }

    private int caculateValue() {
        return (status % 0x80 + (clockTrustedMark ? 0x80 : 0x00)) % 0x100;
    }


    public boolean isClockTrustedMark() {
        return clockTrustedMark;
    }

    public int getStatus() {
        return status % 0x80;
    }

    public Result setClockTrustedMark(boolean clockTrustedMark) {
        this.clockTrustedMark = clockTrustedMark;
        return this;
    }

    public Result setStatus(@IntRange(from = 0, to = 3) int status) {
        this.status = status;
        return this;
    }

    public String getStatusStr() {
        switch (getStatus()) {
            case 0:
                return "成功";
            case 1:
                return "地址重复";
            case 2:
                return "非法设备";
            case 3:
                return "容量不足";
            default:
                return "未知状态（保留）";

        }
    }

    @Override
    public String toHexString() {
        return new Unsigned(caculateValue()).toHexString();
    }
}
