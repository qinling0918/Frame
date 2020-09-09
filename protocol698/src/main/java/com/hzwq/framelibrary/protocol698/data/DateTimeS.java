package com.hzwq.framelibrary.protocol698.data;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by qinling on 2018/5/12 17:34
 * Description:
 * date_time	28	octet-string（SIZE（7）） // 7字节
 */
public class DateTimeS extends Data {
  //  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static int[] FIELD_DATE_TIME_S = new int[]{
            Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH,
            Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND
    };
    @Override
    public int dataType() {
        return 28;
    }

    /**
     * year、milliseconds=FFFFH表示无效。
     * month、day_of_month、day_of_week、hour、minute、second=FFH表示无效。
     * 若 输入 -1，则表示无效
     * day_of_week：0表示周日，1…6分别表示周一到周六。
     */
    private final DateTime dateTime;
    public DateTimeS() {
        this(System.currentTimeMillis());
    }

    public DateTimeS(long timeMillis) {
        this(new Date(timeMillis));
    }

    public DateTimeS(Date date) {
        dateTime = new DateTime(date);
      //  dateTime.setFields(FIELD_DATE_TIME_S);
    }

    public DateTimeS(String hexTimeStr) {
        checkNull(hexTimeStr);
        if (hexTimeStr.length()<8)   throw new StringIndexOutOfBoundsException("字符串长度不符合");
        String hexTimeYMD = hexTimeStr.substring(0,8) ;
        String hexTimeHMS = hexTimeStr.substring(8) ;
        hexTimeStr = hexTimeYMD+"FF"+hexTimeHMS+"0000";
        dateTime = new DateTime(hexTimeStr);
        // dateTime.setFields(FIELD_DATE_TIME_S);
    }
    public static DateTimeS newInstance(){
        return new DateTimeS();
    }


    public DateTimeS(@IntRange(from = 1970, to = 3000) Integer year,
                     @IntRange(from = 1, to = 12) Integer month,
                     @IntRange(from = 1, to = 31) Integer day_of_month) {
        this(year, month, day_of_month, 0, 0, 0);
    }

    public DateTimeS(@IntRange(from = 1970, to = 3000) Integer year,
                     @IntRange(from = 1, to = 12) Integer month,
                     @IntRange(from = 1, to = 31) Integer day,
                     @IntRange(from = 0, to = 23) int hour,
                     @IntRange(from = 0, to = 59) int minute,
                     @IntRange(from = 0, to = 59) int second) {
        dateTime = new DateTime(year,month,day,hour,minute,second);
      //  dateTime.setFields(FIELD_DATE_TIME_S);
    }

    @Override
    public String toHexString() {
        dateTime.setFields(FIELD_DATE_TIME_S);
        return dateTime.toHexString();
    }

    // 返回 yyyy-MM-dd HH:mm:ss
    public String format() {
        dateTime.setFields(FIELD_DATE_TIME_S);
        dateTime.setFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        return dateTime.format();
    }

    public int getYear() {
        return dateTime.getYear();
    }

    public int getMonth() {
        return dateTime.getMonth();
    }

    public int getDayOfMonth() {
        return dateTime.getDayOfMonth();
    }



    public int getHour() {
        return dateTime.getHour();
    }

    public int getMinute() {
        return dateTime.getMinute();
    }

    public int getSecond() {
        return dateTime.getMinute();
    }



    /**
     * 返回时间戳，方便使用者格式化
     *
     * @return 时间戳  毫秒级
     * 若是小于 0 则表示格式有误。
     */
    public long getTimeTamps() {
     return dateTime.getTimeTamps();
    }

    public DateTimeS setYear(@IntRange(from = 1970, to = 3000)int year) {
        dateTime.setYear(year);
        return this;
    }
    public DateTimeS setMonth(@IntRange(from = 1, to = 12)int month) {
        dateTime.setMonth(month);
        return this;
    }

    public DateTimeS setDayOfMonth(@IntRange(from = 1, to = 31)int dayOfMonth) {
        dateTime.setDayOfMonth(dayOfMonth);
        return this;
    }

    public DateTimeS setHour(@IntRange(from = 0, to = 24)int hour) {
        dateTime.setHour(hour);
        return this;
    }

    public DateTimeS setMinute(@IntRange(from = 0, to = 59)int minute) {
        dateTime.setMinute(minute);
        return this;
    }

    public DateTimeS setSecond(@IntRange(from = 0, to = 59)int second) {
        dateTime.setSecond(second);
        return this;
    }
}
