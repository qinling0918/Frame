package com.hzwq.framelibrary.protocol698.data.enums;


/**
 * Created by qinling on 2018/5/21 16:18
 * Description:
 */
public class TimeUnit extends Enum {
    @Override
    public int enumType() {
        return TIME_UNIT;
    }

    public static TimeUnit SECOND = new TimeUnit(0);
    public static TimeUnit MINUTE = new TimeUnit(1);
    public static TimeUnit HOUR = new TimeUnit(2);
    public static TimeUnit DAY = new TimeUnit(3);
    public static TimeUnit MONTH = new TimeUnit(4);
    public static TimeUnit YEAR = new TimeUnit(5);

    private TimeUnit(int value) {
        super(value);
    }

}
