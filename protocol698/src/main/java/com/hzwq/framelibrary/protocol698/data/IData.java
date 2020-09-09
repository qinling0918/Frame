package com.hzwq.framelibrary.protocol698.data;

import com.hzwq.framelibrary.common.IHex;

/**
 * Created by qinling on 2018/5/14 9:55
 * Description:
 */
public interface IData extends IHex {
    String toHexString();

    int dataType();

}
