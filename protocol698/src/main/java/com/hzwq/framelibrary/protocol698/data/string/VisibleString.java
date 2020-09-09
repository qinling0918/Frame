package com.hzwq.framelibrary.protocol698.data.string;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;

/**
 * Created by qinling on 2018/5/12 17:44
 * Description: ASCII字符串（VisibleString）
 */
public class VisibleString  extends Data implements CharSequence {
    private String valueStr;
    public VisibleString(String valueStr) {
        this.valueStr = valueStr;
    }

    public VisibleString() {
    }

    @Override
    public int dataType() {
        return 10;
    }

    @Override
    public int length() {
        return valueStr.length();
    }

    @Override
    public char charAt(int index) {
        return valueStr.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return valueStr.subSequence(start,end);
    }

    @Override
    public String toHexString() {
        // todo 该类型数据是不是需要 添加字节数。
        return NumberConvert.asciiStringToHex(valueStr);
    }



    /**
     *  16进制转Ascii码
     * @param hex
     * @return
     */
    public String hexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){
            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }
}
