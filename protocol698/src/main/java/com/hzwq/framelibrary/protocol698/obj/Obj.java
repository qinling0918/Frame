package com.hzwq.framelibrary.protocol698.obj;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.OI;
import com.hzwq.framelibrary.protocol698.data.ScalerUnit;

import java.text.DecimalFormat;

/**
 * Created by qinling on 2019/1/31 12:52
 * Description:
 */
public class Obj {
    protected String objName; // 对象名称
    protected int interfClass; // 接口类id
    protected int obj_method; // 属性或方法
    protected OI logicName; // 对象标识

    protected String oadHexStr; // OAD 4B
    protected String objValueHexStr; // 对象对应字符串
    // 例如 正向有功电能（0010）
    // 则上述属性分别对应：
    //  objName = “正向有功电能”
    //  interfClass = 1  参见698协议  接口类 class_id
    //  obj = 1（属性：逻辑名，方法：复位） 、2（属性：总及费率电能量数组，方法：执行）、
    //          3 （属性：换算及单位）、4（属性：高精度总及费率电能量数组）、5（属性：高精度换算及单位）
    //  logicName = new OI("0010)
    //  objName = “正向有功电能”
    //  oadHexStr ="00100200"
    //  objValueHexStr ="0506000000000600000000060000000006000000000600000000" // 报文中关于正向有功电能的部分。

    protected void setObjValue(String objName, int interfClass, String logicName) {
        this.objName = objName;
        this.interfClass = interfClass;
        this.logicName = new OI(logicName);
    }

    /**
     * 获取格式化后的数据
     *
     * @param value      数值
     * @param scalerUnit 单位
     * @return
     */

    protected String getFormatValueWithUnit(long value, ScalerUnit scalerUnit) {
        return getFormatValueWithUnit(value, scalerUnit.getUnitStr(), scalerUnit.getPow());
    }

    protected String getFormatValueWithUnit(long value, String unit, int pow) {
        double num =  (value*(Math.pow(10,pow)));
        StringBuilder foramt = new StringBuilder("#0.0");
        for (int i = 1; i < Math.abs(pow); i++) {
            foramt.append(0);
        }
        foramt.append(unit);
        DecimalFormat decimalFormat = new DecimalFormat(foramt.toString());
        return decimalFormat.format(num);
    }

    FrameParserException exception = new FrameParserException();

    protected void parseException(Exception e) {
        exception = new FrameParserException(e);
    }

    /**
     * 获取异常对象
     *
     * @return 若是解析异常，则返回帧解析异常
     * 若为null，则解析成功
     */
    public Exception exception() {
        return exception;
    }

    public void setObjValueHexStr(String objValueHexStr) {
        this.objValueHexStr = objValueHexStr;
    }

    /**
     * 复位
     */
    public String reset() {
        String omd = logicName.toHexString() + "0100";//OMD
        return omd + "0f00";// 参数 integer(0)
    }

    /**
     * 执行
     */
    public String execute(Data data) {
        String omd = logicName.toHexString() + "0200";//OMD
        return omd + data.dataTypeStr() + data.toHexString();// 参数Data，
    }



    public interface JsonAble {
        String toJson();
    }

    public static abstract class IC1 extends Obj {
        public Builder build(){
            return new Builder();
        }

        abstract String[] getRatePower();

        abstract String getRatePower(@IntRange(from = 1) int index);


        public static  class Builder  {
            public String getRatePower(){
                return "0200";
             }

            public String getRatePower(@IntRange(from = 1) int index){
                return "02"+ NumberConvert.toHexStr(index,2);
            }

            public String getHighPrecisionRatePower(){
                return "0400";
            }

            public String getHighPrecisionRatePower(@IntRange(from = 1) int index){
                 return "04"+ NumberConvert.toHexStr(index,2);
             }
        }
    }

    public static abstract class IC2 extends Obj {

    }

    public static abstract class IC3 extends Obj {

    }

    public static abstract class IC4 extends Obj {

    }

    public static abstract class IC5 extends Obj {

    }

    public static abstract class IC6 extends Obj {

    }

    public static abstract class IC7 extends Obj {

    }
}
