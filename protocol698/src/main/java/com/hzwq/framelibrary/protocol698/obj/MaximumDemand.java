package com.hzwq.framelibrary.protocol698.obj;


import android.util.SparseArray;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.DataManager;
import com.hzwq.framelibrary.protocol698.data.ScalerUnit;



/**
 * Created by Tsinling on 2019/1/31 17:35
 * Description:7.3.2　最大需量接口类（class_id=2）
 * 本接口类对象提供存储最大需量类信息，定义见表127　。
 * <p>
 * 组合无功最大需量的正负数是用来标志潮流的方向，
 * 组合无功最大需量从参与组合无功电能运算的象限中抽取最大值，
 * 如果来自象限3、4，以负数上传
 */
public class MaximumDemand extends Obj {
    private static final String[] INFOS = new String[]{
            "1010\t2\t正向有功最大需量\n" + // kw  double-long-unsigned
                    "1011\t2\tA相正向有功最大需量\n" +
                    "1012\t2\tB相正向有功最大需量\n" +
                    "1013\t2\tC相正向有功最大需量\n" +
                    "1020\t2\t反向有功最大需量\n" +
                    "1021\t2\tA相反向有功最大需量\n" +
                    "1022\t2\tB相反向有功最大需量\n" +
                    "1023\t2\tC相反向有功最大需量\n",
            "1030\t2\t组合无功1最大需量\n" + // kvar double-long
                    "1031\t2\tA相组合无功1最大需量\n" +
                    "1032\t2\tB相组合无功1最大需量\n" +
                    "1033\t2\tC相组合无功1最大需量\n" +
                    "1040\t2\t组合无功2最大需量\n" +
                    "1041\t2\tA相组合无功2最大需量\n" +
                    "1042\t2\tB相组合无功2最大需量\n" +
                    "1043\t2\tC相组合无功2最大需量\n",
            "1050\t2\t第一象限最大需量\n" + // kvar double-long-unsigned
                    "1051\t2\tA相第一象限最大需量\n" +
                    "1052\t2\tB相第一象限最大需量\n" +
                    "1053\t2\tC相第一象限最大需量\n" +
                    "1060\t2\t第二象限最大需量\n" +
                    "1061\t2\tA相第二象限最大需量\n" +
                    "1062\t2\tB相第二象限最大需量\n" +
                    "1063\t2\tC相第二象限最大需量\n" +
                    "1070\t2\t第三象限最大需量\n" +
                    "1071\t2\tA相第三象限最大需量\n" +
                    "1072\t2\tB相第三象限最大需量\n" +
                    "1073\t2\tC相第三象限最大需量\n" +
                    "1080\t2\t第四象限最大需量\n" +
                    "1081\t2\tA相第四象限最大需量\n" +
                    "1082\t2\tB相第四象限最大需量\n" +
                    "1083\t2\tC相第四象限最大需量\n",
            "1090\t2\t正向视在最大需量\n" + //  kVA double-long-unsigned
                    "1091\t2\tA相正向视在最大需量\n" +
                    "1092\t2\tB相正向视在最大需量\n" +
                    "1093\t2\tC相正向视在最大需量\n" +
                    "10A0\t2\t反向视在最大需量\n" +
                    "10A1\t2\tA 相反向视在最大需量\n" +
                    "10A2\t2\tB相反向视在最大需量\n" +
                    "10A3\t2\tC相反向视在最大需量\n",
            "1110\t2\t冻结周期内正向有功最大需量\n" + //  kW  double-long-unsigned
                    "1111\t2\t冻结周期内A相正向有功最大需量\n" +
                    "1112\t2\t冻结周期内B相正向有功最大需量\n" +
                    "1113\t2\t冻结周期内C相正向有功最大需量\n" +
                    "1120\t2\t冻结周期内反向有功最大需量\n" +
                    "1121\t2\t冻结周期内A相反向有功最大需量\n" +
                    "1122\t2\t冻结周期内B相反向有功最大需量\n" +
                    "1123\t2\t冻结周期内C相反向有功最大需量\n",
            "1130\t2\t冻结周期内组合无功1最大需量\n" + // kvar double-long
                    "1131\t2\t冻结周期内A相组合无功1最大需量\n" +
                    "1132\t2\t冻结周期内B相组合无功1最大需量\n" +
                    "1133\t2\t冻结周期内C相组合无功1最大需量\n" +
                    "1140\t2\t冻结周期内组合无功2最大需量\n" +
                    "1141\t2\t冻结周期内A相组合无功2最大需量\n" +
                    "1142\t2\t冻结周期内B相组合无功2最大需量\n" +
                    "1143\t2\t冻结周期内C相组合无功2最大需量\n",
            "1150\t2\t冻结周期内第一象限最大需量\n" + // kvar double-long-unsigned
                    "1151\t2\t冻结周期内A相第一象限最大需量\n" +
                    "1152\t2\t冻结周期内B相第一象限最大需量\n" +
                    "1153\t2\t冻结周期内C相第一象限最大需量\n" +
                    "1160\t2\t冻结周期内第二象限最大需量\n" +
                    "1161\t2\t冻结周期内A相第二象限最大需量\n" +
                    "1162\t2\t冻结周期内B相第二象限最大需量\n" +
                    "1163\t2\t冻结周期内C相第二象限最大需量\n" +
                    "1170\t2\t冻结周期内第三象限最大需量\n" +
                    "1171\t2\t冻结周期内A相第三象限最大需量\n" +
                    "1172\t2\t冻结周期内B相第三象限最大需量\n" +
                    "1173\t2\t冻结周期内C相第三象限最大需量\n" +
                    "1180\t2\t冻结周期内第四象限最大需量\n" +
                    "1181\t2\t冻结周期内A相第四象限最大需量\n" +
                    "1182\t2\t冻结周期内B相第四象限最大需量\n" +
                    "1183\t2\t冻结周期内C相第四象限最大需量\n",
            "1190\t2\t冻结周期内正向视在最大需量\n" + //kVA double-long-unsigned
                    "1191\t2\t冻结周期内A相正向视在最大需量\n" +
                    "1192\t2\t冻结周期内B相正向视在最大需量\n" +
                    "1193\t2\t冻结周期内C相正向视在最大需量\n" +
                    "11A0\t2\t冻结周期内反向视在最大需量\n" +
                    "11A1\t2\t冻结周期内A 相反向视在最大需量\n" +
                    "11A2\t2\t冻结周期内B相反向视在最大需量\n" +
                    "11A3\t2\t冻结周期内C相反向视在最大需量"
    };

    private static final ScalerUnit[] scalerUnits = new ScalerUnit[]{
            new ScalerUnit(-4, 28), //  kw  double-long-unsigned
            new ScalerUnit(-4, 32), // kvar double-long
            new ScalerUnit(-4, 32), // kvar double-long-unsigned
            new ScalerUnit(-4, 30), //  kVA double-long-unsigned
            new ScalerUnit(-4, 28), //  kW  double-long-unsigned
            new ScalerUnit(-4, 32), // kvar double-long
            new ScalerUnit(-4, 32), // kvar double-long-unsigned
            new ScalerUnit(-4, 30), //kVA double-long-unsigned

    };
    private static final Choice[] choices = new Choice[]{
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,

    };

    public static void main(String[] args) {
       // String apdu = "85010510100200010105020206000000001c00000000000000020206000000001c00000000000000020206000000001c00000000000000020206000000001c00000000000000020206000000001c000000000000000000";
        String apdu = "0105020206000000001c00000000000000020206000000001c00000000000000020206000000001c00000000000000020206000000001c00000000000000020206000000001c000000000000000000";
        MaximumDemand energy = MaximumDemand.newInstance("10100200");
        energy.setObjValueHexStr(apdu);
       // System.out.println(energy.getRatePower()[0]);
    }
    public RateDemandTime[] getRateDemandArr() {
        // if (this.objValueHexStr )
        if (rateDemandArr != null && rateDemandArr.length != 0) return rateDemandArr;
        String hexStr = this.objValueHexStr;
        // 获取第一个字节：Array的元素个数
        try {
            Integer len = Integer.parseInt(hexStr.substring(0, 2), 16);
            rateDemandArr = new RateDemandTime[len];
            hexStr = hexStr.substring(2);
            for (int i = 0; i < len; i++) {
                // 数据类型标志位 ，此处应该是structure
                Integer dataType = Integer.parseInt(hexStr.substring(0, 2), 16);
                if (dataType != 0x02) {
                    parseException(new Exception("数据类型应该为structure(0x02)"));
                    return rateDemandArr;
                }

                // 下面是structure的元素个数，此处应该是double-long /double-long-unsigned 和date_time_s
                Integer dataCount = Integer.parseInt(hexStr.substring(2, 4), 16);

                RateDemandTime rateDemandTime = new RateDemandTime();

                Integer childDataType1 = Integer.parseInt(hexStr.substring(4, 6), 16);

                // 判断是不是double-long 或者double-long-unsigned
                boolean isEnergyChoiceType = false;
                for (Energy.Choice choice : Energy.Choice.values()) {
                    isEnergyChoiceType = isEnergyChoiceType || childDataType1 == choice.value;
                }
                if (!isEnergyChoiceType) {
                    throw new Exception("获取到的数据类型不正确，应为: double-long(0x05)或者double-long-unsigned(0x06)");
                }

                int dataLen = DataManager.getInstance().getDataByteSize(childDataType1) * 2;
                String ratePowerHex = hexStr.substring(6, dataLen);
                rateDemandTime.setRateDemand(getRatePower(childDataType1, ratePowerHex));

                // 此处应该是和 date_time_s

                Integer childDataType2 = Integer.parseInt(hexStr.substring(6 + dataLen, 8 + dataLen), 16);
                if (childDataType2 != 0x1c) {
                    throw new Exception("获取到的数据类型不正确，应为: date_time_s(0x1c)");
                }
                int data2Len = DataManager.getInstance().getDataByteSize(childDataType2) * 2;

                rateDemandArr[i] = rateDemandTime;
                // 将数据移到下一项
                hexStr = hexStr.substring(2 + dataLen);
            }
        } catch (Exception e) {
            parseException(e);
            rateDemandArr = null;
        }
        return rateDemandArr;
    }

    private String getRatePower(Integer dataType, String ratePowerHex) throws NumberFormatException {
        if (dataType == Choice.DOUBLE_LONG.value) {
            return getFormatValueWithUnit(Integer.parseInt(ratePowerHex, 16), scalerUnit);
        } else if (dataType == Choice.DOUBLE_LONG_UNSIGNED.value) {
            return getFormatValueWithUnit(NumberConvert.parseUnsignedInt(ratePowerHex, 16), scalerUnit);
        } else {
            return "";
        }

    }

    private RateDemandTime[] rateDemandArr; // 费率最大需量数组
    private ScalerUnit scalerUnit;
    private Choice choice;

    private MaximumDemand(String objName, int interfClass, String logicName, Choice choice, ScalerUnit scalerUnit) {
        setObjValue(objName, interfClass, logicName);
        this.choice = choice;
        this.scalerUnit = scalerUnit;
    }

    private MaximumDemand() {
    }

    public enum Choice {
        DOUBLE_LONG(5),
        DOUBLE_LONG_UNSIGNED(6);

        int value;

        Choice(int value) {
            this.value = value;
        }
    }

    private static SparseArray<MaximumDemand> objSsparseArray = new SparseArray<>();

    static {
        for (int i = 0; i < INFOS.length; i++) {
            String energyStr = INFOS[i];
            ScalerUnit scalerUnit = scalerUnits[i];
            Choice choice = choices[i];
            String[] energyInfoArr = energyStr.split("\n");
            for (String energyInfo : energyInfoArr) {
                String[] energyArr = energyInfo.split("\t");
                int interfClass = Integer.parseInt(energyArr[1]);
                int objNo = Integer.parseInt(energyArr[0]);
                MaximumDemand obj = new MaximumDemand(energyArr[2], interfClass, energyArr[0], choice, scalerUnit);
                objSsparseArray.put(objNo, obj);
            }
        }
    }

    public static MaximumDemand newInstance(String oadHexStr) {
        MaximumDemand obj = (MaximumDemand) new Obj();
        try {
            String oiStr = oadHexStr.substring(0, 4);
            int logicNo = Integer.parseInt(oiStr, 16);
            obj = objSsparseArray.get(logicNo);
            obj.oadHexStr = oadHexStr;
            obj.obj_method = Integer.parseInt(oadHexStr.substring(4, 6), 16);
        } catch (Exception e) {
            // 截取字符串，或者数字格式化失败
            obj.parseException(e);
        }
        return obj;
    }


    /**
     * 最大需量及发生时间
     */
    private static final class RateDemandTime {
        private String rateDemand; // 最大需量值
        private String time; // 发生时间

        public String getRateDemand() {
            return rateDemand;
        }

        public void setRateDemand(String rateDemand) {
            this.rateDemand = rateDemand;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}
