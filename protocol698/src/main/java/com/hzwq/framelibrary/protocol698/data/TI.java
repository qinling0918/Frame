package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.protocol698.data.enums.TimeUnit;
import com.hzwq.framelibrary.protocol698.data.number.LongUnsigned;


/**
 * Created by qinling on 2018/5/11 18:22
 * Description:
 */
public class TI extends Data {
    public static void main(String[] args) {
    }

    private TimeUnit unit;
    private LongUnsigned time;

    @Override
    public int dataType() {
        return 84;
    }

    public TI(TimeUnit unit, LongUnsigned time) {
        this.unit = unit;
        this.time = time;
    }

    @Override
    public String toHexString() {
        return unit.toHexString() + time.toHexString();
    }


}
