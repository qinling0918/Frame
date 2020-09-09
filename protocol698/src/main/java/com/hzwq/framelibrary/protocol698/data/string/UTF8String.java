package com.hzwq.framelibrary.protocol698.data.string;

import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2018/5/12 17:44
 * Description: UTF-8编码的字符串
 */
public class UTF8String extends Data {
    @Override
    public int dataType() {
        return 12;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
