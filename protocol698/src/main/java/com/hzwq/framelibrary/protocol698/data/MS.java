package com.hzwq.framelibrary.protocol698.data;


import android.support.annotation.IntRange;

import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;
import com.hzwq.framelibrary.protocol698.data.number.Unsigned;

import static com.hzwq.framelibrary.protocol698.data.number.Unsigned.MAX_VALUE;
import static com.hzwq.framelibrary.protocol698.data.number.Unsigned.MIN_VALUE;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description:  电能表集合MS（Meter Set）
 */
public abstract class MS extends Data {
    private static final String MS_NULL = "00";
    private static final String MS_ALL_ADDRESS = "01";
    private static final String MS_USER_TYPE = "02";
    private static final String MS_USER_ADDRESS = "03";
    private static final String MS_CONFIG_NUMBER = "04";
    private static final String MS_USER_TYPE_RANGE = "05";
    private static final String MS_USER_ADDRESS_RANGE = "06";
    private static final String MS_CONFIG_NUMBER_RANGE = "07";
    public static final MS NULL = new MS() {
        @Override
        String choice() {
            return MS_NULL;
        }

        @Override
        public String toHexString() {
            return "";
        }
    };
    public static final MS ALL_USER_ADDRESS = new MS() {
        @Override
        String choice() {
            return MS_ALL_ADDRESS;
        }

        @Override
        public String toHexString() {
            return "";
        }
    };

    @Override
    public int dataType() {
        return 92;
    }
    abstract String choice();

    public static UserTypeMS userType(@IntRange(from = MIN_VALUE, to = MAX_VALUE) int... value) {
        Unsigned[] unsigneds = new Unsigned[value.length];
        for (int i = 0; i < value.length; i++) {
            unsigneds[i] = new Unsigned(value[i]);
        }
        return new UserTypeMS(unsigneds);
    }

    public static UserAddressMS userAddress(String... addressHex) {
        TSA[] tsas = new TSA[addressHex.length];
        for (int i = 0; i < addressHex.length; i++) {
            tsas[i] = new TSA(addressHex[i]);
        }
        return new UserAddressMS(tsas);
    }
    public static ConfigNumberMS configNumber(@IntRange(from = LongUnsigned.MIN_VALUE, to = LongUnsigned.MAX_VALUE) int... value) {
        LongUnsigned[] longUnsigneds = new LongUnsigned[value.length];
        for (int i = 0; i < value.length; i++) {
            longUnsigneds[i] = new LongUnsigned(value[i]);
        }
        return new ConfigNumberMS(longUnsigneds);
    }
    public static UserTypeRegionMS userTypeRegion(Region... regions) {
        return new UserTypeRegionMS(regions);
    }
    public static UserAddressRegionMS userAddressRegion(Region... regions) {
        return new UserAddressRegionMS(regions);
    }
    public static ConfigNumberRegionMS configNumberRegion(Region... regions) {
        return new ConfigNumberRegionMS(regions);
    }

    public static class UserTypeMS extends MS  {


        private Unsigned[] userType;

        public UserTypeMS(Unsigned... userType) {
            this.userType = userType;
        }

        @Override
        public String toHexString() {
            return choice() + userType.toString();
        }

        @Override
        public String choice() {
            return MS_USER_TYPE;
        }
    }

    public static class UserAddressMS extends MS  {
        private TSA[] tsas;

        public UserAddressMS(TSA... tsas) {
            this.tsas = tsas;
        }

        @Override
        public String toHexString() {
            return choice() + toString4Array(tsas);
        }

        @Override
        public String choice() {
            return MS_USER_ADDRESS;
        }
    }

    public static class ConfigNumberMS extends MS  {


        private LongUnsigned[] configNumbers;

        public ConfigNumberMS(LongUnsigned... configNumbers) {
            this.configNumbers = configNumbers;
        }

        @Override
        public String toHexString() {
            return choice() + toString4Array(configNumbers);
        }

        @Override
        public String choice() {
            return MS_CONFIG_NUMBER;
        }
    }

    private abstract static class BaseRegionMS extends MS  {
        private Region[] regions;

        public BaseRegionMS(Region... regions) {
            this.regions = regions;
        }

        @Override
        public String toHexString() {
            return choice() + toString4Array(regions);
        }

    }

    public static class UserTypeRegionMS extends BaseRegionMS {
        public UserTypeRegionMS(Region... regions) {
            super(regions);
        }

        @Override
        public String choice() {
            return MS_USER_TYPE_RANGE;
        }
    }

    public static class UserAddressRegionMS extends BaseRegionMS {
        public UserAddressRegionMS(Region... regions) {
            super(regions);
        }

        @Override
        public String choice() {
            return MS_USER_ADDRESS_RANGE;
        }
    }

    public static class ConfigNumberRegionMS extends BaseRegionMS {
        public ConfigNumberRegionMS(Region... regions) {
            super(regions);
        }

        @Override
        public String choice() {
            return MS_CONFIG_NUMBER_RANGE;
        }
    }
}
