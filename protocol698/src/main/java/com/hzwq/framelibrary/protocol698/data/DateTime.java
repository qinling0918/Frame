package com.hzwq.framelibrary.protocol698.data;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.util.NumberConvert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.hzwq.framelibrary.common.util.NumberConvert.toHexStr;

/**
 * Created by qinling on 2018/5/12 17:34
 * Description:
 * date_time	25	octet-string（SIZE（10））
 */
public class DateTime extends Data {

    public static int[] FIELD_DATE_TIME = new int[]{
            Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.DAY_OF_WEEK_IN_MONTH,
            Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
    };
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA);

    @Override
    public int dataType() {
        return 25;
    }

    private final Calendar calendar;
    private int[] times = new int[8];


    private int[] fields = FIELD_DATE_TIME;
    //private String[] timesStr = new String[8];

    /***
     * 提供给其他使用到时间格式的
     * @param fields 修改时间域，根据不同时间域 格式化不同的值
     */
    public void setFields(int[] fields) {
        this.fields = fields;
    }

    /**
     * 设置 时间格式
     *
     * @param pattern
     * @param locale
     */
    public void setFormat(String pattern, Locale locale) {
        this.format = new SimpleDateFormat(pattern, locale);
    }

    /**
     * year、milliseconds=FFFFH表示无效。
     * month、day_of_month、day_of_week、hour、minute、second=FFH表示无效。
     * 若 输入 -1，则表示无效
     * day_of_week：0表示周日，1…6分别表示周一到周六。
     */

    public DateTime() {
        this(System.currentTimeMillis());
    }

    public DateTime(long timeMillis) {
        this(new Date(timeMillis));
    }

    public DateTime(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
    }

    public DateTime(String hexTimeStr) {
        checkNull(hexTimeStr);
        if (hexTimeStr.length() != 20) {
            throw new StringIndexOutOfBoundsException("参数异常,需要10字节 16进制字符串");
        }
        if (!NumberConvert.isHexUnsignedStr(hexTimeStr)) {
            throw new IllegalArgumentException("参数异常,需要为16进制字符串");
        }

        String[] timsStrArr = getStringArr(hexTimeStr);
        int len = timsStrArr.length;
        for (int i = 0; i < len; i++) {
            times[i] = getTime(timsStrArr[i]);
        }

        calendar = Calendar.getInstance();
        calendar.set(times[0], times[1] - 1, times[2], times[4], times[5]);
        calendar.set(Calendar.SECOND, times[6]);
        calendar.set(Calendar.MILLISECOND, times[7]);
        System.out.println("12345:    " + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        // System.out.println("12345:    "+times[3]);
    }


    public DateTime(@IntRange(from = 1970, to = 3000) Integer year,
                    @IntRange(from = 1, to = 12) Integer month,
                    @IntRange(from = 1, to = 31) Integer day_of_month,
                    @IntRange(from = 0, to = 23) int hour,
                    @IntRange(from = 0, to = 59) Integer minute,
                    @IntRange(from = 0, to = 59) Integer second) {
        this(year, month, day_of_month, hour, minute, second, 0);
    }

    public DateTime(@IntRange(from = 1970, to = 3000) Integer year,
                    @IntRange(from = 1, to = 12) Integer month,
                    @IntRange(from = 1, to = 31) Integer day_of_month) {
        this(year, month, day_of_month, 0, 0, 0, 0);
    }

    public DateTime(@IntRange(from = 1970, to = 3000) int year,
                    @IntRange(from = 1, to = 12) int month,
                    @IntRange(from = 1, to = 31) int day_of_month,
                    @IntRange(from = 0, to = 23) int hour,
                    @IntRange(from = 0, to = 59) int minute,
                    @IntRange(from = 0, to = 59) int second,
                    @IntRange(from = 0, to = 999) int milliseconds) {

        month = month - 1;
        calendar = Calendar.getInstance();
        calendar.set(year, month, day_of_month, hour, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliseconds);
        this.times = new int[]{year, month, day_of_month, calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH),
                hour, minute, second, milliseconds};
    }

    @Override
    public String toHexString() {
        String[] formatTimeStr = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            int field = fields[i];
            int strLength = field == Calendar.MILLISECOND || field == Calendar.YEAR ? 4 : 2;
            int time = getTimeByField(field);
            formatTimeStr[i] = toHexStr(time, strLength);
            //   System.out.println("time:  " + time + " hex: " + formatTimeStr[i]);
        }
        StringBuilder sb = new StringBuilder();
        for (String str : formatTimeStr) {
            sb.append(str);
        }
        return sb.toString().toUpperCase(Locale.CHINA);

    }


    // 返回 yyyy-MM-dd HH:mm:ss:SSS
    public String format() {

     /*   String[] formatTimeStr = new String[fields.length];
        StringBuilder sb = new StringBuilder();
        for (int field : fields) {
            calendar.isSet(field)? :
          //  int time = getTimeByField(field);
        }*/

        return format.format(calendar.getTime());

    }

    private int getTimeByField(int field) {
        // 是否设置过相对应的值
        return calendar.isSet(field)
                // 若是月，则需要+1
                ? (field == Calendar.MONTH ? calendar.get(field) + 1 : calendar.get(field))
                // 该值是否被设置过值，若设置过，则取其值，若时未赋值，则为FF,
                : (field == Calendar.MILLISECOND || field == Calendar.YEAR ? 0xffff : 0xff);
    }


    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getDayOfMonth() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayOfWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    public int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    public int getMilliseconds() {
        return calendar.get(Calendar.MILLISECOND);
    }

    ///
    public DateTime setYear(@IntRange(from = 1970, to = 3000)int year) {
        calendar.set(Calendar.YEAR, year);
        return this;
    }

    public DateTime setMonth(@IntRange(from = 1, to = 12)int month) {
        calendar.set(Calendar.MONTH, month - 1);
        return this;
    }

    public DateTime setDayOfMonth(@IntRange(from = 1, to = 31)int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return this;
    }

    public DateTime setHour(@IntRange(from = 0, to = 24)int hour) {
         calendar.set(Calendar.HOUR_OF_DAY,hour);
        return this;
    }

    public DateTime setMinute(@IntRange(from = 0, to = 59)int minute) {
         calendar.set(Calendar.MINUTE,minute);
        return this;
    }

    public DateTime setSecond(@IntRange(from = 0, to = 59)int second) {
        calendar.set(Calendar.SECOND,second);
        return this;
    }

    public DateTime setMilliseconds(@IntRange(from = 0, to = 999)int milliseconds) {
         calendar.set(Calendar.MILLISECOND,milliseconds);
        return this;
    }

    /**
     * 返回时间戳，方便使用者格式化
     *
     * @return
     */
    public long getTimeTamps() {
        return calendar.getTimeInMillis();
    }

    private String[] getStringArr(String hexTimeStr) {
        return new String[]{
                hexTimeStr.substring(0, 4),
                hexTimeStr.substring(4, 6),
                hexTimeStr.substring(6, 8),
                hexTimeStr.substring(8, 10),
                hexTimeStr.substring(10, 12),
                hexTimeStr.substring(12, 14),
                hexTimeStr.substring(14, 16),
                hexTimeStr.substring(16, 20)

        };
    }

    /**
     * @param timeStr 16进制 时间字符串
     * @return 若是FF, 则返回0XFF, 其他按照正常值
     */
    private int getTime(String timeStr) {
       /* StringBuilder stringBuffer = new StringBuilder();
        int len = hourStr.length();
        for (int i = 0; i < len; i++) {
            stringBuffer.append("F");
        }
        return hourStr.equalsIgnoreCase(stringBuffer.toString().trim()) ? -1 : Integer.parseInt(hourStr, 16);*/
        return Integer.parseInt(timeStr, 16);
    }

}
