package com.hzwq.framelibrary.protocol698.data;


/**
 * Created by qinling on 2018/5/12 17:34
 * Description:
 * date_time	26	octet-string（SIZE（5））
 */
public class Date extends Data {
    @Override
    public int dataType() {
        return 26;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
