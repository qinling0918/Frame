package com.hzwq.framelibrary.protocol698.ic;


import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.util.NumberConvert;

/**
 * Created by qinling on 2019/4/25 10:33
 * Description: 7.3.1　电能量接口类（class_id=1）
 */
public abstract class IC1 {
    public IC1.Builder build() {
        return new IC1.Builder();
    }

    abstract String[] getRatePower();

    abstract String getRatePower(@IntRange(from = 1) int index);


    public static class Builder {
        public String getRatePower() {
            return "0200";
        }

        public String getRatePower(@IntRange(from = 1) int index) {
            return "02" + NumberConvert.toHexStr(index, 2);
        }

        public String getHighPrecisionRatePower() {
            return "0400";
        }

        public String getHighPrecisionRatePower(@IntRange(from = 1) int index) {
            return "04" + NumberConvert.toHexStr(index, 2);
        }
    }


}
