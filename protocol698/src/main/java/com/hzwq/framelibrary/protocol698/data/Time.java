package com.hzwq.framelibrary.protocol698.data;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.util.NumberConvert;

import java.util.Locale;

/**
 * Created by qinling on 2018/5/12 12:12
 * Description:
 */
public class Time extends Data {

    //8比特位正整数（Unsigned8）
    private int hour;
    private int minute;
    private int second;

    /**
     * hour、minute、second=FFH表示无效。
     * 若 输入 -1，则表示无效
     */
    public Time(String hexTimeStr) {
        if (hexTimeStr.length() != 6 || !NumberConvert.isHexStr(hexTimeStr)) {
            throw new IllegalArgumentException("参数异常,属性标识及其特征 需要3字节 16进制字符串");
        }
        String hourStr = hexTimeStr.substring(0, 2);
        String minuteStr = hexTimeStr.substring(2, 4);
        String secondStr = hexTimeStr.substring(4, 6);
        hour = getTime(hourStr);
        minute = getTime(minuteStr);
        second = getTime(secondStr);
    }

    private int getTime(String hourStr) {
        return Integer.parseInt(hourStr, 16);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public Time(@IntRange(from = -1, to = 23) int hour,
                @IntRange(from = -1, to = 59) int minute,
                @IntRange(from = -1, to = 59) int second) {
        this.hour = (hour + 0x100) % 0x100;
        this.minute = (minute + 0x100) % 0x100;
        this.second = (second + 0x100) % 0x100;
    }

    @Override
    public String toHexString() {
       /* String str = NumberConvert.toHexStr(hour, 2) +
                NumberConvert.toHexStr(minute, 2) +
                NumberConvert.toHexStr(second, 2);*/
        String str = String.format(Locale.CHINA,"%02x%02x%02x",hour,minute,second);
        return str.toUpperCase(Locale.CHINA);
    }

    @Override
    public int dataType() {
        return 27;
    }


}
