package com.hzwq.framelibrary.protocol698.data.datahelpers;

import android.text.TextUtils;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.DateTimeS;
import com.hzwq.framelibrary.protocol698.data.TI;


/**
 * Created by qinling on 2018/5/11 18:18
 * Description: 可选，若不存在则 用00 表示。
 * // todo  未完成
 */
public class TimeTag implements IHex {
    private DateTimeS dateTimeS;
    private TI ti;
    private String timeTagStr;
    public TimeTag(String timeTagStr) {
        this.timeTagStr = timeTagStr;
    }
    public TimeTag() {
        this.timeTagStr = "";
    }
    public TimeTag(DateTimeS dateTimeS, TI ti) {
        this.dateTimeS = dateTimeS;
        this.ti = ti;
        this.timeTagStr = dateTimeS.toHexString()+ti.toHexString();
    }
    //发送时标 		   	date_time_s，
    //允许传输延时时间 	TI


    @Override
    public String toHexString() {
        return TextUtils.isEmpty(timeTagStr)?"00":"01"+timeTagStr;
    }
}
