package com.hzwq.framelibrary.protocol698.data;


import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.hzwq.framelibrary.common.util.NumberConvert;


/**
 * Created by qinling on 2018/5/11 18:22
 * Description:
 */
public class ScalerUnit extends Data {

    public static void main(String[] args) {
        new ScalerUnit(255, 23);
      //  System.out.println(new Frame698.Builder().setLinkData(APDUFactory.link().linkRequest(LinkRequestTypeEnum.HEART_BEAT)).build().toHexString());
    }

    private int pow;
    private Unit unit;

    private String hexStr;

    public int getPow() {
        return pow;
    }

    public void setPow(int pow) {
        this.pow = pow;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getUnitStr() {
        return unit.getUnit();
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ScalerUnit(String hexStr) {
        if (null == hexStr || hexStr.length() < 4) {
            throw new IndexOutOfBoundsException("16进制字符串格式有误");
        }
        this.hexStr = hexStr;
        this.pow = hexStringToBytes(hexStr.substring(0,2))[0]             ;
      //  System.out.println(Integer.parseInt(hexStr.substring(2,4),16));
        this.unit = Unit.newInstance(Integer.parseInt(hexStr.substring(2,4),16)    );
    }

    public ScalerUnit(int pow, Unit unit) {
        this.pow = pow;
        this.unit = unit;
        this.hexStr = NumberConvert.toHexStr(pow,2)+ NumberConvert.toHexStr(unit.getCode(),2);
        System.out.println(hexStr);
    }

    public ScalerUnit(int pow, @IntRange(from = 0, to = 255) int unitCode) {
        this(pow, Unit.newInstance(unitCode));
    }

    @Override
    public int dataType() {
        return 89;
    }

    @Override
    public String toHexString() {
        return hexStr;
    }

   /* @Override
    public String format() {
        return "";
    }*/


    public static class Unit {

        private static final String UNIT_INFO = "1\ta\t时间\t年\t \n" +
                "2\tmo\t时间\t月\t \n" +
                "3\twk\t时间\t周\t7*24*60*60s\n" +
                "4\td\t时间\t日\t24*60*60s\n" +
                "5\th\t时间\t小时\t60*60s\n" +
                "6\tmin\t时间\t分\t60s\n" +
                "7\ts\t时间（t）\t秒\ts\n" +
                "8\t°\t（相）角\t度\trad*180/π\n" +
                "9\t℃\t温度（T）\t摄氏度\tK-273.15\n" +
                "10\t货币\t（当地）货币\t \t \n" +
                "11\tm\t长度（l）\t米\tm\n" +
                "12\tm/s\t速度（v）\t米/秒\tm/s\n" +
                "13\tm³\t体积（V）rV，仪表常数或脉冲值（容积）\t立方米\tm³\n" +
                "14\tm³\t修正的体积\t立方米\tm³\n" +
                "15\tm³/h\t流量\t立方米每小时\tm³/(60*60s)\n" +
                "16\tm³/h\t修正的流量\t立方米每小时\tm³/(60*60s)\n" +
                "17\tm³/d\t流量\t立方米每24小时\tm³/(24*60*60s)\n" +
                "18\tm³/d\t修正的流量\t立方米每24小时\tm³/(24*60*60s)\n" +
                "19\tl\t容积\t升\t10-3m³\n" +
                "20\tkg\t质量（m）\t千克\tkg\n" +
                "21\tN\t力（F）\t牛顿\tN\n" +
                "22\tNm\t能量\t牛顿米\tJ=Nm=Ws\n" +
                "23\tP\t压力（p）\t帕斯卡\tN/㎡\n" +
                "24\tbar\t压力（p）\t巴\t10-5N/㎡\n" +
                "25\tJ\t能量\t焦耳\tJ=Nm=Ws\n" +
                "26\tJ/h\t热功\t焦每小时\tJ/(60*60s)\n" +
                "27\tW\t有功功率（P）\t瓦\tW=J/s\n" +
                "28\tkW\t有功功率（P）\t千瓦\tkW=J/(s*1000)\n" +
                "29\tVA\t视在功率（S）\t伏安\t \n" +
                "30\tkVA\t视在功率（S）\t千伏安\t \n" +
                "31\tvar\t无功功率（Q）\t乏\t \n" +
                "32\tkvar\t无功功率（Q）\t千乏\t \n" +
                "33\tkWh\t有功能量 rw，有功电能表常数或脉冲值\t千瓦-时\tkW*(60*60s)\n" +
                "34\tkVAh\t视在能量 rS，视在电能表常数或脉冲值\t千伏-安-小时\tkVA*(60*60s)\n" +
                "35\tkvarh\t无功能量 rB，无功电能表常数或脉冲\t千乏-时\tkvar*(60*60s)\n" +
                "36\tA\t电流（I）\t安培\tA\n" +
                "37\tC\t电量（Q）\t库仑\tC=As\n" +
                "38\tV\t电压（U）\t伏特\tV\n" +
                "39\tV/m\t电场强度（E）\t伏每米\tV/m\n" +
                "40\tF\t电容（C）\t法拉\tC/V=As/V\n" +
                "41\tΩ\t电阻（R）\t欧姆\tΩ=V/A\n" +
                "42\tΩ㎡/m\t电阻系数（ρ）\t \tΩm\n" +
                "43\tWb\t磁通量（Φ）\t韦伯\tWb=Vs\n" +
                "44\tT\t磁通密度（T）\t泰斯拉\tWb/㎡\n" +
                "45\tA/m\t磁场强度（H）\t安培每米\tA/m\n" +
                "46\tH\t电感（L）\t亨利\tH=Wb/A\n" +
                "47\tHz\t频率\t赫兹\t1/s\n" +
                "48\t1/(Wh)\t有功能量表常数或脉冲\t \timp/kWh\n" +
                "49\t1/(varh)\t无功能量表常数或脉冲\t \t \n" +
                "50\t1/(VAh)\t视在能量表常数或脉冲\t \t \n" +
                "51\t%\t百分比\t百分之\t \n" +
                "52\tbyte\t字节\t字节\t \n" +
                "53\tdBm\t分贝毫瓦\t \t \n" +
                "54\t元/kWh\t电价\t \t \n" +
                "55\tAh\t安时\t安时\t \n" +
                "56\tms\t毫秒\t毫秒\t \n" +
                "57\tdBμV\t \t \t \n";
        /**
         * 代码
         */
        private int code;
        /**
         * 单位
         */
        private String unit;
        /**
         * 量
         */
        private String physicalQuantity;
        /**
         * 单位名称
         */
        private String unitName;
        /**
         * SI 定义
         */
        private String SI;

        private SparseArray<Unit> unitSparseArray = new SparseArray<>();

        public static Unit newInstance(int code) {
            System.out.println("Unit "+ code);
            return SingleTon.INSTANCE.getUnit(code);
        }

        public static Unit newInstance(String code) {
            return SingleTon.INSTANCE.getUnit(code);
        }

        private Unit() {
            initUnits();
        }

        private Unit(int code, String error) {
            this(code, error, error, error, error);
        }

        public int getCode() {
            return code;
        }

        public String getUnit() {
            return unit;
        }

        public String getPhysicalQuantity() {
            return physicalQuantity;
        }

        public String getUnitName() {
            return unitName;
        }

        public String getSI() {
            return SI;
        }

        private void initUnits() {
            String[] units = UNIT_INFO.split("\n");
            System.out.println(units.length);
            // System.out.println(unit);
            for (String unitStr : units) {
                String[] unitArr = unitStr.split("\t");
                int code = 0;
                Unit unit = null;
                try {
                    code = Integer.parseInt(unitArr[0]);
                    unit = new Unit(code, unitArr[1], unitArr[2], unitArr[3], unitArr[4]);
                } catch (Exception e) {
                    // android.util.Log.e(TAG, "initUnits: ", );
                }
                unitSparseArray.put(code, unit);
            }
        }

        public Unit getUnitWithDefault(int code) {
            if (code == 254) return new Unit(254, "其他单位");
            if (code == 255) return new Unit(254, "无单位、缺单位、计数");
            return unitSparseArray.get(code, new Unit(code, "保留"));
        }

        public Unit getUnitWithDefault(String codeStr) {
            return getUnitWithDefault(parseInt(codeStr, 0));
        }

        @Nullable
        private Unit getUnit(int code) {
            return unitSparseArray.get(code, null);
        }

        @Nullable
        private Unit getUnit(String codeStr) {
            return getUnit(parseInt(codeStr, 0));
        }

        private int parseInt(String codeHexStr, int defaultValue) {
            try {
                return Integer.parseInt(codeHexStr, 16);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }


        private static class SingleTon {
            private static final Unit INSTANCE = new Unit();
        }


        Unit(int code, String unit, String physicalQuantity, String unitName, String SI) {
            this.code = code;
            this.unit = unit;
            this.physicalQuantity = physicalQuantity;
            this.unitName = unitName;
            this.SI = SI;
        }

        @Override
        public String toString() {
            return "Unit{" +
                    "code=" + code +
                    ", unit='" + unit + '\'' +
                    ", physicalQuantity='" + physicalQuantity + '\'' +
                    ", unitName='" + unitName + '\'' +
                    ", SI='" + SI + '\'' +
                    '}';
        }
    }
}
