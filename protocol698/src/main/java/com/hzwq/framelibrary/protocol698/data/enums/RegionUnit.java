package com.hzwq.framelibrary.protocol698.data.enums;

/**
 * Created by qinling on 2019/4/26 17:02
 * Description:
 */
public class RegionUnit extends Enum {
    @Override
    public int enumType() {
        return REGION_UNIT;
    }
    public static RegionUnit CLOSE_OPEN = new RegionUnit(0);
    public static RegionUnit OPNE_CLOSE = new RegionUnit(1);
    public static RegionUnit CLOSE_CLOSE = new RegionUnit(2);
    public static RegionUnit OPNE_OPNE = new RegionUnit(3);

    public RegionUnit(int value) {
        super(value);
    }



}
