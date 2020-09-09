package com.hzwq.framelibrary.protocol698.data.enums;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.noUse.NULL;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import static com.hzwq.framelibrary.protocol698.data.enums.Enum.LINK_REQUEST_TYPE;


/**
 * Created by qinling on 2018/5/16 17:25
 * Description:
 */
public class LinkRequestTypeEnum extends Enum  {

    public static LinkRequestTypeEnum LOGIN = new LinkRequestTypeEnum(0);
    public static LinkRequestTypeEnum HEART_BEAT = new LinkRequestTypeEnum(1);
    public static LinkRequestTypeEnum SIGN_OUT = new LinkRequestTypeEnum(2);

    public static LinkRequestTypeEnum getLinkRequestTypeEnum(int value){
        if (value == 0)return LOGIN;
        if (value == 1)return HEART_BEAT;
        if (value == 2)return SIGN_OUT;
        return LOGIN;
    }

    private LinkRequestTypeEnum(int value) {
        super(value);
    }

    @Override
    public int enumType() {
        return LINK_REQUEST_TYPE;
    }


}
