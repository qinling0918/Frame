package com.hzwq.framelibrary.protocol698.data;


import com.hzwq.framelibrary.protocol698.data.enums.RegionUnit;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description: Region用于描述数据的区间范围，
 * 包括以下四种：
 * （起始值，结束值）、
 * [起始值，结束值）、
 * （起始值，结束值]、
 * [起始值，结束值]。
 */
public class Region extends Data {
    RegionUnit regionUnit;
    Data start;
    Data end;

    public Region(RegionUnit regionUnit, Data start, Data end) {
        this.regionUnit = regionUnit;
        this.start = start;
        this.end = end;
    }


    @Override
    public int dataType() {
        return 88;
    }

    @Override
    public String toHexString() {
        return regionUnit.dataTypeStr() + regionUnit.toHexString()
                + start.dataTypeStr() + start.toHexString()
                + end.dataTypeStr() + end.toHexString();
    }
}
