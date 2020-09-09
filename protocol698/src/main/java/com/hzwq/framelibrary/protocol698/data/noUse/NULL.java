package com.hzwq.framelibrary.protocol698.data.noUse;

import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2018/5/12 17:32
 * Description:
 */
public class NULL extends Data {
    public static NULL getInstance() {
        return SingleTon.INSTANCE;
    }

    private NULL() {
    }

    private static class SingleTon {
        private static final NULL INSTANCE = new NULL();
    }

    @Override
    public int dataType() {
        return 0;
    }

    // todo 应该是 空字符串
    @Override
    public String toHexString() {
        return "";
    }
}
