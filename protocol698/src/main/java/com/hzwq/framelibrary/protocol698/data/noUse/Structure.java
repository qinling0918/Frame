package com.hzwq.framelibrary.protocol698.data.noUse;

import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2018/5/12 17:35
 * Description:
 *
 * SEQUENCE OF Data（见6.3.3.1　）
    结构的元素在对象属性或方法的描述中定义
 */

public class Structure  extends Data {
    @Override
    public int dataType() {
        return 2;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
