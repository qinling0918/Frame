package com.hzwq.framelibrary.protocol698.data.enums;


import android.util.SparseArray;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;

import java.util.ArrayList;

/**
 * Created by qinling on 2018/5/12 17:50
 * Description: 22	枚举的元素在对象属性或方法的描述中定义	0…255
 */
public abstract class Enum extends Data {
    protected ArrayList<Enum> enums;
    protected static final int LINK_REQUEST_TYPE = 0;
    protected static final int TIME_UNIT = 1;
    protected static final int DAR = 2;
    protected static final int REGION_UNIT = 3;

    public Enum(int value) {
        enums = new ArrayList<>();
        enums.add(this);
        this.value = value;
    }

    protected int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    @Override
    public int dataType() {
        return 22;
    }

    @Override
    public String toHexString() {
        return NumberConvert.toHexStr(value,2);
    }

    public abstract int enumType();

    //public static

    // todo 貌似没有必要管理这些枚举类。未雨绸缪
    public static class EnumMagager {

        private SparseArray<Class> enumSparseArray = new SparseArray<>();
        private static class SingleTon {
            private static EnumMagager instance = new EnumMagager();
        }

        public static EnumMagager getInstance() {
            return EnumMagager.SingleTon.instance;
        }

        private EnumMagager() {
            initData();
        }

        private void initData() {
            enumSparseArray.put(LINK_REQUEST_TYPE,LinkRequestTypeEnum.class);
            enumSparseArray.put(TIME_UNIT,TimeUnit.class);
            enumSparseArray.put(DAR,DAREnum.class);
            enumSparseArray.put(REGION_UNIT,RegionUnit.class);
        }
    }

}
