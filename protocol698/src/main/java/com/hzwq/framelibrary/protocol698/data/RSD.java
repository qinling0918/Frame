package com.hzwq.framelibrary.protocol698.data;


import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.noUse.NULL;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;

import static com.hzwq.framelibrary.protocol698.data.number.Unsigned.MAX_VALUE;
import static com.hzwq.framelibrary.protocol698.data.number.Unsigned.MIN_VALUE;

/**
 * Created by qinling on 2018/5/11 11:39
 * Description:
 */
public abstract class RSD extends Data {

    private static final String SELECTOR_NULL = "00";
    private static final String SELECTOR1 = "01";
    private static final String SELECTOR2 = "02";
    private static final String SELECTOR3 = "03";
    private static final String SELECTOR4 = "04";
    private static final String SELECTOR5 = "05";
    private static final String SELECTOR6 = "06";
    private static final String SELECTOR7 = "07";
    private static final String SELECTOR8 = "08";
    private static final String SELECTOR9 = "09";
    private static final String SELECTOR10 = "0A"; // 10进制 10

    @Override
    public int dataType() {
        return 90;
    }

    public static RSD selectNull(){
        return new SelectorNull();
    }
    public static RSD select1(OAD oad,Data data){
        return new Selector1(oad,data);
    }
    public static RSD select1(String oad,Data data){
        return new Selector1(new OAD(oad),data);
    }
    public static Selector2 select2(OAD oad, Data start, Data end, Data interval){
        return new Selector2(oad,start,end,interval);
    }
    public static Selector2 select2(OAD oad, Data start, Data end){
        return select2(oad,start,end,Data.NULL);
    }
    public static Selector2 select2(String oad, Data start, Data end){
        return new Selector2(new OAD(oad),start,end,Data.NULL);
    }
    public static RSD select3(Selector2...selector2s){
        return new Selector3(selector2s);
    }
    public static RSD select4(DateTimeS dateTimeS, MS ms){
        return new Selector4(dateTimeS,ms);
    }
    public static RSD select5(DateTimeS dateTimeS, MS ms){
        return new Selector5(dateTimeS,ms);
    }
    public static RSD select6(DateTimeS start, DateTimeS end, TI ti, MS ms){
        return new Selector6(start,end,ti,ms);
    }
    public static RSD select7(DateTimeS start, DateTimeS end, TI ti, MS ms){
        return new Selector7(start,end,ti,ms);
    }
    public static RSD select8(DateTimeS start, DateTimeS end, TI ti, MS ms){
        return new Selector8(start,end,ti,ms);
    }
    public static RSD select9(@IntRange(from = MIN_VALUE,to = MAX_VALUE) int value){
        return new Selector9(value);
    }
    public static RSD select10(@IntRange(from = MIN_VALUE,to = MAX_VALUE) int value,MS ms){
        return new Selector10(value,ms);
    }

    // todo 判断是否可以将这些类的修饰词从 public 改为private
    public abstract String selectorType();

    public static class SelectorNull extends RSD {
        public SelectorNull() {
        }

        @Override
        public String toHexString() {
            return selectorType() ;
        }

        @Override
        public String selectorType() {
            return SELECTOR_NULL;
        }
    }

    public static class Selector1 extends RSD {
        private OAD oad;
        private Data data;

        public Selector1(OAD oad, Data data) {
            this.oad = oad;
            this.data = data;
        }

        @Override
        public String toHexString() {
            return selectorType() + oad.toHexString() + data.dataTypeStr() + data.toHexString();
        }

        @Override
        public String selectorType() {
            return SELECTOR1;
        }
    }

    /**
     * Selector2为指定对象区间内连续间隔值
     */
    public static class Selector2 extends RSD {
        /**
         * 对象属性描述符
         * 当对象属性描述符的数值类型为DateTime或DateTime_S类型时，
         * 其间隔的数据类型为TI；其他场景下通常间隔数据类型与数值类型相同。
         */
        private OAD oad;
        // 起始值
        private Data start;
        // 结束值
        private Data end;
        /**
         * 数据间隔
         * 是和OAD相关的类型，=NULL表示无间隔（即：指定区间内全部）。
         */
        private Data interval;

        @Override
        public String toHexString() {
            return selectorType() + oad.toHexString()
                    + start.dataTypeStr() + start.toHexString()
                    + end.dataTypeStr() + end.toHexString()
                    + interval.dataTypeStr() + interval.toHexString();

        }

        public Selector2(OAD oad, Data start, Data end) {
            this(oad, start, end, Data.NULL);
            this.start = start;
            this.end = end;
        }

        public Selector2(OAD oad, Data start, Data end, Data interval) {
            this.oad = oad;
            this.start = start;
            this.end = end;
            this.interval = interval;
        }

        @Override
        public String selectorType() {
            return SELECTOR2;
        }
    }

    public static class Selector3 extends RSD {

        public Selector3(Selector2[] selector2s) {
            this.selector2s = selector2s;
        }

        private Selector2[] selector2s;

        @Override
        public String toHexString() {
            return selectorType() + Data.toString4Array(selector2s);
        }

        @Override
        public String selectorType() {
            return SELECTOR3;
        }
    }

    private abstract static class Selector4_5 extends RSD {
        // 采集启动时间 / 采集启动时间
        private DateTimeS dateTimeS;
        // 电能表集合
        private MS ms;

        @Override
        public String toHexString() {
            return selectorType()
                    + dateTimeS.toHexString()
                    + ms.toHexString();
        }

        public Selector4_5(DateTimeS dateTimeS, MS ms) {
            this.ms = ms;
            this.dateTimeS = dateTimeS;
        }

    }

    /**
     * Selector4为指定电能表集合、指定采集启动时间。
     */
    public static class Selector4 extends Selector4_5 {

        public Selector4(DateTimeS dateTimeS, MS ms) {
            super(dateTimeS, ms);
        }

        @Override
        public String selectorType() {
            return SELECTOR4;
        }
    }

    /**
     * Selector5为指定电能表集合、指定采集存储时间。
     */
    public static class Selector5 extends Selector4_5 {
        public Selector5(DateTimeS dateTimeS, MS ms) {
            super(dateTimeS, ms);
        }

        @Override
        public String selectorType() {
            return SELECTOR5;
        }
    }

    /**
     * Selector6为指定电能表集合、指定采集启动时间区间内连续间隔值。
     */
    private abstract static class Selector678 extends RSD {
        /**
         * 采集启动(6)/存储(7)/成功(8)时间起始值
         */
        private DateTimeS dateTimeS_start;
        /**
         * 采集启动(6)/存储(7)/成功(8)时间结束值
         */
        private DateTimeS dateTimeS_end;
        /**
         * 时间间隔
         */
        private TI ti;
        /**
         * 电能表集合
         */
        private MS ms;

        @Override
        public String toHexString() {
            return selectorType()
                    + dateTimeS_start.dataTypeStr() + dateTimeS_start.toHexString()
                    + dateTimeS_end.dataTypeStr() + dateTimeS_end.toHexString()
                    + ti.dataTypeStr() + ti.toHexString()
                    + ms.dataTypeStr() + ms.toHexString();
        }

        public Selector678(DateTimeS start, DateTimeS end, TI ti, MS ms) {
            this.dateTimeS_start = start;
            this.dateTimeS_end = end;
            this.ti = ti;
            this.ms = ms;
        }

    }

    /**
     * Selector6为指定电能表集合、指定采集启动时间区间内连续间隔值。
     */

    public static class Selector6 extends Selector678 {
        public Selector6(DateTimeS start, DateTimeS end, TI ti, MS ms) {
            super(start, end, ti, ms);
        }

        @Override
        public String selectorType() {
            return SELECTOR6;
        }
    }

    /**
     * Selector7为指定电能表集合、指定采集存储时间区间内连续间隔值。
     */
    public static class Selector7 extends Selector678 {
        public Selector7(DateTimeS start, DateTimeS end, TI ti, MS ms) {
            super(start, end, ti, ms);
        }

        @Override
        public String selectorType() {
            return SELECTOR7;
        }
    }
    /**
     * Selector8为指定电能表集合、指定采集到时间区间内连续间隔值。
     */
    public static class Selector8 extends Selector678 {
        public Selector8(DateTimeS start, DateTimeS end, TI ti, MS ms) {
            super(start, end, ti, ms);
        }

        @Override
        public String selectorType() {
            return SELECTOR8;
        }
    }

    /**
     * Selector9为指定选取上第n次记录
     */
    public  static class Selector9 extends RSD {
        /**
         * 上第n次记录  unsigned
         */
        private Unsigned reecordNum;
        @Override
        public String toHexString() {
            return selectorType()
                    + reecordNum.dataTypeStr() + reecordNum.toHexString();
        }

        public Selector9(@IntRange(from = MIN_VALUE,to = MAX_VALUE) int value) {
            this.reecordNum = new Unsigned(value);
        }

        @Override
        public String selectorType() {
            return SELECTOR9;
        }
    }

    /**
     * Select10为指定选取最新的n条记录。
     */
    public  static class Selector10 extends RSD {
        /**
         * 上第n次记录  unsigned
         */
        private Unsigned reecordNum;
        /**
         *  电能表集合  MS
         */
        private MS ms;
        @Override
        public String toHexString() {
            return selectorType()
                    + reecordNum.dataTypeStr() + reecordNum.toHexString()
                    + ms.dataTypeStr() + ms.toHexString();

        }

        public Selector10(@IntRange(from = MIN_VALUE,to = MAX_VALUE) int value,MS ms) {
            this.reecordNum = new Unsigned(value);
            this.ms = ms;
        }

        @Override
        public String selectorType() {
            return SELECTOR10;
        }
    }



}
