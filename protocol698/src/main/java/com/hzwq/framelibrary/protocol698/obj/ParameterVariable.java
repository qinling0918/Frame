package com.hzwq.framelibrary.protocol698.obj;

import com.hzwq.framelibrary.protocol698.SerializedName;
import com.hzwq.framelibrary.protocol698.data.DateTimeS;
import com.hzwq.framelibrary.protocol698.data.OAD;
import com.hzwq.framelibrary.protocol698.ic.IC8;

/**
 * Created by qinling on 2019/4/25 10:12
 * Description: 参变量类
 */
public class ParameterVariable extends Obj {
    public String build(OAD oad) {

        return super.toString();
    }
    public static Obj_4000 newInstance4000() {
        return new Obj_4000();
    }

    public static class Obj_4000 extends IC8 {

        public Obj_4000() {
        }

        public DateTimeS getDateTimeS() {
            return dateTimeS;
        }

        public TimingMode getTimingMode() {
            return timingMode;
        }

        public PreciseTimingParames getPreciseTimingParames() {
            return preciseTimingParames;
        }
        @SerializedName(2)
        private DateTimeS dateTimeS;
        private TimingMode timingMode;
        private PreciseTimingParames preciseTimingParames;

        @Override
        protected String logicName() {
            return "4000";
        }

        public void broadCastCheckTime(DateTimeS dateTimeS){

        }
        enum TimingMode {
            /**
             *  校时模式
             */
            MASTER_STATION_TIMING (0,"主站授时"),
            TERMINAL_ACCURATE_TIMING (1,"终端精确校时"),
            BEIDOU_GPS(2,"北斗/GPS"),
            OTHER (255,"其它");
            int value;
            String mode;
            TimingMode(int value, String mode) {
                this. value = value;
                this. mode = mode;
            }
        }
        public class PreciseTimingParames {
            // 最近心跳时间总个数
            public int totalRecentHertbeats;
            // 最大值剔除个数
            public int maxCullingCount;
            // 最小值剔除个数
            public int minCullingCount;
            // 通讯延时阈值 （单位：秒），
            public int CommunicationDelayThreshold ;
            // 最少有效个数
            public int minValidCount;
        }
    }
}
