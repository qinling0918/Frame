package com.hzwq.framelibrary.protocol698.data.datahelpers.parser;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.*;
import com.hzwq.framelibrary.protocol698.data.IData;

/**
 * Created by qinling on 2018/5/22 11:23
 * Description:
 */
public class GetResult implements IHex {
    private static final int CHOICE_DAR = 0;
    private static final int CHOICE_DATA = 1;
    private IData data;

    public GetResult(IData data) {
    }
    public GetResult(DAR dar) {
    }

    public GetResult(String hexStr) {
        String hex = hexStr;
        int choice = Integer.parseInt(hex.substring(0,2),16);
        hex = hex.substring(2);
        int value = Integer.parseInt(hex.substring(0,2),16);
        if (choice==CHOICE_DAR){
            darParse(value);
        }else if (choice==CHOICE_DATA){
            dataParse(value);
        }

    }

    private void dataParse(int value) {

     //   DataManager.getInstance().getDataClass(value).cast()
    }

    private void darParse(int value) {
        for (DAR dar:DAR.values()) {
            if (dar.getValue() == value){
                data = dar;
            }
        }
        if (data == null ){
            throw new NullPointerException("解析错误，未找到对应的DAR类型");
        }
    }

    public IData getData() {
        return data;
    }

    @Override
    public String toHexString() {
        return null;
    }
}
