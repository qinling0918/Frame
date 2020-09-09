package com.hzwq.framelibrary.protocol698.obj;


import android.util.SparseArray;

import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.DataManager;
import com.hzwq.framelibrary.protocol698.data.ScalerUnit;


import java.text.NumberFormat;


/**
 * Created by qinling on 2018/5/22 15:07
 * Description:7.3.1　电能量接口类（class_id=1）
 * 本接口类对象提供存储电能量类信息，定义见表124
 * 费率， 总，尖，峰，平，谷
 */
public class Energy extends Obj.IC1 {

    private static final String[] ENERGY_INFO = new String[]{
            "0000\t1\t组合有功电能",
            "0010\t1\t正向有功电能\n" +
                    "0011\t1\tA相正向有功电能\n" +
                    "0012\t1\tB相正向有功电能\n" +
                    "0013\t1\tC相正向有功电能\n" +
                    "0020\t1\t反向有功电能\n" +
                    "0021\t1\tA相反向有功电能\n" +
                    "0022\t1\tB相反向有功电能\n" +
                    "0023\t1\tC相反向有功电能",
            "0030\t1\t组合无功1电能\n" +
                    "0031\t1\tA相组合无功1电能\n" +
                    "0032\t1\tB相组合无功1电能\n" +
                    "0033\t1\tC相组合无功1电能\n" +
                    "0040\t1\t组合无功2电能\n" +
                    "0041\t1\tA相组合无功2电能\n" +
                    "0042\t1\tB相组合无功2电能\n" +
                    "0043\t1\tC相组合无功2电能",
            "0050\t1\t第一象限无功电能\n" +
                    "0051\t1\tA相第一象限无功电能\n" +
                    "0052\t1\tB相第一象限无功电能\n" +
                    "0053\t1\tC相第一象限无功电能\n" +
                    "0060\t1\t第二象限无功电能\n" +
                    "0061\t1\tA相第二象限无功电能\n" +
                    "0062\t1\tB相第二象限无功电能\n" +
                    "0063\t1\tC相第二象限无功电能\n" +
                    "0070\t1\t第三象限无功电能\n" +
                    "0071\t1\tA相第三象限无功电能\n" +
                    "0072\t1\tB相第三象限无功电能\n" +
                    "0073\t1\tC相第三象限无功电能\n" +
                    "0080\t1\t第四象限无功电能\n" +
                    "0081\t1\tA相第四象限无功电能\n" +
                    "0082\t1\tB相第四象限无功电能\n" +
                    "0083\t1\tC相第四象限无功电能\n",
            "0090\t1\t正向视在电能\n" +
                    "0091\t1\tA相正向视在电能\n" +
                    "0092\t1\tB相正向视在电能\n" +
                    "0093\t1\tC相正向视在电能\n" +
                    "00A0\t1\t反向视在电能\n" +
                    "00A1\t1\tA 相反向视在电能\n" +
                    "00A2\t1\tB相反向视在电能\n" +
                    "00A3\t1\tC相反向视在电能\n",
            "0110\t1\t正向有功基波总电能\n" +
                    "0111\t1\tA 相正向有功基波电能\n" +
                    "0112\t1\tB 相正向有功基波电能\n" +
                    "0113\t1\tC 相正向有功基波电能\n" +
                    "0120\t1\t反向有功基波总电能\n" +
                    "0121\t1\tA 相反向有功基波电能\n" +
                    "0122\t1\tB 相反向有功基波电能\n" +
                    "0123\t1\tC 相反向有功基波电能\n" +
                    "0210\t1\t正向有功谐波总电能\n" +
                    "0211\t1\tA 相正向有功谐波电能\n" +
                    "0212\t1\tB 相正向有功谐波电能\n" +
                    "0213\t1\tC相正向有功谐波电能\n" +
                    "0220\t1\t反向有功谐波总电能\n" +
                    "0221\t1\tA 相反向有功谐波电能\n" +
                    "0222\t1\tB 相反向有功谐波电能\n" +
                    "0223\t1\tC 相反向有功谐波电能\n" +
                    "0300\t1\t铜损有功总电能补偿量\n" +
                    "0301\t1\tA 相铜损有功电能补偿量\n" +
                    "0302\t1\tB 相铜损有功电能补偿量\n" +
                    "0303\t1\tC 相铜损有功电能补偿量\n" +
                    "0400\t1\t铁损有功总电能补偿量\n" +
                    "0401\t1\tA 相铁损有功电能补偿量\n" +
                    "0402\t1\tB 相铁损有功电能补偿量\n" +
                    "0403\t1\tC 相铁损有功电能补偿量\n" +
                    "0500\t1\t关联总电能\n" +
                    "0501\t1\tA相关联电能\n" +
                    "0502\t1\tB相关联电能\n" +
                    "0503\t1\tC相关联电能"
    };
    // kWh 33 ,kvarh
    private static final ScalerUnit[] scalerUnits = new ScalerUnit[]{
            new ScalerUnit(-2, 33), // kWh
            new ScalerUnit(-2, 33),
            new ScalerUnit(-2, 35),
            new ScalerUnit(-2, 35),
            new ScalerUnit(-2, 34),
            new ScalerUnit(-2, 33),

    };
    private static final Choice[] choices = new Choice[]{
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,
            Choice.DOUBLE_LONG_UNSIGNED,


    };

    private static SparseArray<Energy> energySparseArray = new SparseArray<>();

    static {

        for (int i = 0; i < ENERGY_INFO.length; i++) {
            String energyStr = ENERGY_INFO[i];
            ScalerUnit scalerUnit = scalerUnits[i];
            Choice choice = choices[i];
            String[] energyInfoArr = energyStr.split("\n");
            for (String energyInfo : energyInfoArr) {
                String[] energyArr = energyInfo.split("\t");
                int interfClass = Integer.parseInt(energyArr[1], 16);
                int objNo = Integer.parseInt(energyArr[0], 16);
                Energy energy = new Energy(energyArr[2], interfClass, energyArr[0], choice, scalerUnit);
                energySparseArray.put(objNo, energy);
            }
        }
    }

    public static void main(String[] args) {
        NumberFormat nf = NumberFormat.getInstance();
        //  System.out.println("格式化后显示数字："+nf.format(10000000));
        //  System.out.println("格式化后显示数字："+nf.format(10000.345));

        Energy energy = Energy.newInstance("00100200");
        String hex = "05 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00 06 00 00 00 00".replace(" ", "");
        energy.setObjValueHexStr(hex);
        System.out.println(energy.getRatePower()[0]);
        System.out.println(energy.getRatePower()[1]);
        System.out.println(energy.getRatePower()[2]);
        System.out.println(energy.getRatePower()[3]);
        System.out.println(energy.getRatePower()[4]);

    }

    public enum Choice {
        DOUBLE_LONG(5),
        DOUBLE_LONG_UNSIGNED(6),
        LONG64(20),
        LONG64_UNSIGNED(21);
        int value;

        Choice(int value) {
            this.value = value;
        }
    }


    private String[] ratePower; // 费率
    private ScalerUnit scalerUnit;
    private Choice choice;


/*    public String getJson() {
        try {
            JSONArray jsonArray = new JSONArray(getRatePower());
            return jsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            parseException(e);
            return null;
        }
    }*/

    public String[] getRatePower() {
        if (ratePower != null && ratePower.length != 0) return ratePower;
        String hexStr = this.objValueHexStr;
        // 获取第一个字节：Array的元素个数
        try {
            Integer len = Integer.parseInt(hexStr.substring(0, 2), 16);
            ratePower = new String[len];
            hexStr = hexStr.substring(2);
            for (int i = 0; i < len; i++) {
                Integer dataType = Integer.parseInt(hexStr.substring(0, 2), 16);
                // 判断是不是电能量对应的四个 数据类型
                boolean isEnergyChoiceType = false;
                for (Choice choice : Choice.values()) {
                    isEnergyChoiceType = isEnergyChoiceType || dataType == choice.value;
                }
                if (!isEnergyChoiceType) {
                    String dataTypeStr = obj_method == 4
                            ? "long64(0x14)，long64-unsigned(0x15)"
                            : "double-long(0x05)，double-long-unsigned(0x06)";
                    throw new Exception("获取到的数据类型不正确，应为: " + dataTypeStr);
                }

                int dataLen = DataManager.getInstance().getDataByteSize(dataType) * 2;
                String ratePowerHex = hexStr.substring(2, dataLen);
                ratePower[i] = getRatePower(dataType, ratePowerHex);
                // 将数据移到下一项
                hexStr = hexStr.substring(2 + dataLen);
            }
        } catch (Exception e) {
            parseException(e);
            ratePower = null;
        }
        return ratePower;
    }

    @Override
    String getRatePower(int index) {
        return getRatePower(index);
    }

   // public static final class Builder extends Obj.IC1.Builder{}



  /*  public void setRatePowerHexStr(String ratePowerHexStr) {
        this.objValueHexStr = ratePowerHexStr;
        String hexStr = ratePowerHexStr;
        // 获取第一个字节：Array的元素个数
        try {
            Integer len = Integer.parseInt(hexStr.substring(0, 2), 16);
            ratePower = new String[len];
            hexStr = hexStr.substring(2);
            for (int i = 0; i < len; i++) {
                Integer dataType = Integer.parseInt(hexStr.substring(0, 2), 16);
                // 判断是不是电能量对应的四个 数据类型
                boolean isEnergyChoiceType = false;
                for (Choice choice : Choice.values()) {
                    isEnergyChoiceType = isEnergyChoiceType || dataType == choice.value;
                }
                if (!isEnergyChoiceType) {
                    String dataTypeStr = obj == 4
                            ? "long64(0x14)，long64-unsigned(0x15)"
                            : "double-long(0x05)，double-long-unsigned(0x06)";
                    throw new Exception("获取到的数据类型不正确，应为: " + dataTypeStr);
                }

                int dataLen = DataManager.getInstance().getDataByteSize(dataType) * 2;
                String ratePowerHex = hexStr.substring(2, dataLen);
                ratePower[i] = getRatePower(dataType, ratePowerHex);
                // 将数据移到下一项
                hexStr = hexStr.substring(2 + dataLen);
            }
        } catch (Exception e) {
            parseException(e);
        }


    }*/

    private String getRatePower(Integer dataType, String ratePowerHex) throws NumberFormatException {
        if (dataType == Choice.DOUBLE_LONG.value) {
            return getFormatValueWithUnit(Integer.parseInt(ratePowerHex, 16), scalerUnit);
        } else if (dataType == Choice.DOUBLE_LONG_UNSIGNED.value) {
            return getFormatValueWithUnit(NumberConvert.parseUnsignedInt(ratePowerHex, 16), scalerUnit);
        } else if (dataType == Choice.LONG64_UNSIGNED.value) {
            return getFormatValueWithUnit(NumberConvert.parseUnsignedLong(ratePowerHex, 16), scalerUnit);
        } else if (dataType == Choice.LONG64.value) {
            return getFormatValueWithUnit(Long.parseLong(ratePowerHex, 16), scalerUnit);
        } else {
            return "";
        }

    }

    private Energy() {
    }

    private Energy(String objName, int interfClass, String logicName, Choice choice, ScalerUnit scalerUnit) {
        setEnergy(objName, interfClass, logicName, choice, scalerUnit);
    }

    private void setEnergy(String objName, int interfClass, String logicName, Choice choice, ScalerUnit scalerUnit) {
        setObjValue(objName, interfClass, logicName);
        this.choice = choice;
        this.scalerUnit = scalerUnit;
    }

    //
    public static Energy newInstance(String oadHexStr) {
        Energy energy = new Energy();
        try {
            String oiStr = oadHexStr.substring(0, 4);
            int logicNo = Integer.parseInt(oiStr, 16);
            energy = energySparseArray.get(logicNo);
            energy.oadHexStr = oadHexStr;
            // 0010 0200 正向有功电能（低精度)   0010 0400 正向有功电能（高精度)
            energy.obj_method = Integer.parseInt(oadHexStr.substring(4, 6), 16);
            if (energy.obj_method == 4) { // 默认低精度，当为4时，需要调整为高精度
                // 若是位数转换 -2，则调整为 -4
                energy.scalerUnit.setPow(-4);
            }
        } catch (Exception e) {
            // 截取字符串，或者数字格式化失败
            energy.parseException(e);
        }
        return energy;
    }

    /**
     * 根据数据标识以及对应的费率16进制字符串  生成电能量实例
     *
     * @param oadHexStr       数据标识
     * @param ratePowerHexStr
     * @return
     */
    public Energy newInstance(String oadHexStr, String ratePowerHexStr) {
        Energy energy = newInstance(oadHexStr);
        energy.setObjValueHexStr(ratePowerHexStr);
        return energy;
    }


}
