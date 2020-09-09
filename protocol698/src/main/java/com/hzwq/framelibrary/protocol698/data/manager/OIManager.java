package com.hzwq.framelibrary.protocol698.data.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by qinling on 2019/5/22 10:36
 * Description:
 */
public class OIManager {

    private final static String ENERGY_INFO =
            "0000\t1\t组合有功电能\t\t\n" +
                    "0010\t1\t正向有功电能\t\t\n" +
                    "0011\t1\tA相正向有功电能\t\t\n" +
                    "0012\t1\tB相正向有功电能\t\t\n" +
                    "0013\t1\tC相正向有功电能\t\t\n" +
                    "0020\t1\t反向有功电能\t\t\n" +
                    "0021\t1\tA相反向有功电能\t\t\n" +
                    "0022\t1\tB相反向有功电能\t\t\n" +
                    "0023\t1\tC相反向有功电能\t\t\n" +
                    "0030\t1\t组合无功1电能\t\t\n" +
                    "0031\t1\tA相组合无功1电能\t\t\n" +
                    "0032\t1\tB相组合无功1电能\t\t\n" +
                    "0033\t1\tC相组合无功1电能\t\t\n" +
                    "0040\t1\t组合无功2电能\t\t\n" +
                    "0041\t1\tA相组合无功2电能\t\t\n" +
                    "0042\t1\tB相组合无功2电能\t\t\n" +
                    "0043\t1\tC相组合无功2电能\t\t\n" +
                    "0050\t1\t第一象限无功电能\t\t\n" +
                    "0051\t1\tA相第一象限无功电能\t\t\n" +
                    "0052\t1\tB相第一象限无功电能\t\t\n" +
                    "0053\t1\tC相第一象限无功电能\t\t\n" +
                    "0060\t1\t第二象限无功电能\t\t\n" +
                    "0061\t1\tA相第二象限无功电能\t\t\n" +
                    "0062\t1\tB相第二象限无功电能\t\t\n" +
                    "0063\t1\tC相第二象限无功电能\t\t\n" +
                    "0070\t1\t第三象限无功电能\t\t\n" +
                    "0071\t1\tA相第三象限无功电能\t\t\n" +
                    "0072\t1\tB相第三象限无功电能\t\t\n" +
                    "0073\t1\tC相第三象限无功电能\t\t\n" +
                    "0080\t1\t第四象限无功电能\t\t\n" +
                    "0081\t1\tA相第四象限无功电能\t\t\n" +
                    "0082\t1\tB相第四象限无功电能\t\t\n" +
                    "0083\t1\tC相第四象限无功电能\t\t\n" +
                    "0090\t1\t正向视在电能\t\t\n" +
                    "0091\t1\tA相正向视在电能\t\t\n" +
                    "0092\t1\tB相正向视在电能\t\t\n" +
                    "0093\t1\tC相正向视在电能\t\t\n" +
                    "00A0\t1\t反向视在电能\t\t\n" +
                    "00A1\t1\tA 相反向视在电能\t\t\n" +
                    "00A2\t1\tB相反向视在电能\t\t\n" +
                    "00A3\t1\tC相反向视在电能\t\t\n" +
                    "0110\t1\t正向有功基波总电能\t\t\n" +
                    "0111\t1\tA 相正向有功基波电能\t\t\n" +
                    "0112\t1\tB 相正向有功基波电能\t\t\n" +
                    "0113\t1\tC 相正向有功基波电能\t\t\n" +
                    "0120\t1\t反向有功基波总电能\t\t\n" +
                    "0121\t1\tA 相反向有功基波电能\t\t\n" +
                    "0122\t1\tB 相反向有功基波电能\t\t\n" +
                    "0123\t1\tC 相反向有功基波电能\t\t\n" +
                    "0210\t1\t正向有功谐波总电能\t\t\n" +
                    "0211\t1\tA 相正向有功谐波电能\t\t\n" +
                    "0212\t1\tB 相正向有功谐波电能\t\t\n" +
                    "0213\t1\tC相正向有功谐波电能\t\t\n" +
                    "0220\t1\t反向有功谐波总电能\t\t\n" +
                    "0221\t1\tA 相反向有功谐波电能\t\t\n" +
                    "0222\t1\tB 相反向有功谐波电能\t\t\n" +
                    "0223\t1\tC 相反向有功谐波电能\t\t\n" +
                    "0300\t1\t铜损有功总电能补偿量\t\t\n" +
                    "0301\t1\tA 相铜损有功电能补偿量\t\t\n" +
                    "0302\t1\tB 相铜损有功电能补偿量\t\t\n" +
                    "0303\t1\tC 相铜损有功电能补偿量\t\t\n" +
                    "0400\t1\t铁损有功总电能补偿量\t\t\n" +
                    "0401\t1\tA 相铁损有功电能补偿量\t\t\n" +
                    "0402\t1\tB 相铁损有功电能补偿量\t\t\n" +
                    "0403\t1\tC 相铁损有功电能补偿量\t\t\n" +
                    "0500\t1\t关联总电能\t\t\n" +
                    "0501\t1\tA相关联电能\t\t\n" +
                    "0502\t1\tB相关联电能\t\t\n" +
                    "0503\t1\tC相关联电能\t\t\n";
    // 最大需量类
    private final static String MAXIMUM_DEMAND_INFO =
            "1010\t2\t正向有功最大需量\t\t\n" +
                    "1011\t2\tA相正向有功最大需量\t\t\n" +
                    "1012\t2\tB相正向有功最大需量\t\t\n" +
                    "1013\t2\tC相正向有功最大需量\t\t\n" +
                    "1020\t2\t反向有功最大需量\t\t\n" +
                    "1021\t2\tA相反向有功最大需量\t\t\n" +
                    "1022\t2\tB相反向有功最大需量\t\t\n" +
                    "1023\t2\tC相反向有功最大需量\t\t\n" +
                    "1030\t2\t组合无功1最大需量\t\t\n" +
                    "1031\t2\tA相组合无功1最大需量\t\t\n" +
                    "1032\t2\tB相组合无功1最大需量\t\t\n" +
                    "1033\t2\tC相组合无功1最大需量\t\t\n" +
                    "1040\t2\t组合无功2最大需量\t\t\n" +
                    "1041\t2\tA相组合无功2最大需量\t\t\n" +
                    "1042\t2\tB相组合无功2最大需量\t\t\n" +
                    "1043\t2\tC相组合无功2最大需量\t\t\n" +
                    "1050\t2\t第一象限最大需量\t\t\n" +
                    "1051\t2\tA相第一象限最大需量\t\t\n" +
                    "1052\t2\tB相第一象限最大需量\t\t\n" +
                    "1053\t2\tC相第一象限最大需量\t\t\n" +
                    "1060\t2\t第二象限最大需量\t\t\n" +
                    "1061\t2\tA相第二象限最大需量\t\t\n" +
                    "1062\t2\tB相第二象限最大需量\t\t\n" +
                    "1063\t2\tC相第二象限最大需量\t\t\n" +
                    "1070\t2\t第三象限最大需量\t\t\n" +
                    "1071\t2\tA相第三象限最大需量\t\t\n" +
                    "1072\t2\tB相第三象限最大需量\t\t\n" +
                    "1073\t2\tC相第三象限最大需量\t\t\n" +
                    "1080\t2\t第四象限最大需量\t\t\n" +
                    "1081\t2\tA相第四象限最大需量\t\t\n" +
                    "1082\t2\tB相第四象限最大需量\t\t\n" +
                    "1083\t2\tC相第四象限最大需量\t\t\n" +
                    "1090\t2\t正向视在最大需量\t\t\n" +
                    "1091\t2\tA相正向视在最大需量\t\t\n" +
                    "1092\t2\tB相正向视在最大需量\t\t\n" +
                    "1093\t2\tC相正向视在最大需量\t\t\n" +
                    "10A0\t2\t反向视在最大需量\t\t\n" +
                    "10A1\t2\tA 相反向视在最大需量\t\t\n" +
                    "10A2\t2\tB相反向视在最大需量\t\t\n" +
                    "10A3\t2\tC相反向视在最大需量\t\t\n" +
                    "1110\t2\t冻结周期内正向有功最大需量\t\t\n" +
                    "1111\t2\t冻结周期内A相正向有功最大需量\t\t\n" +
                    "1112\t2\t冻结周期内B相正向有功最大需量\t\t\n" +
                    "1113\t2\t冻结周期内C相正向有功最大需量\t\t\n" +
                    "1120\t2\t冻结周期内反向有功最大需量\t\t\n" +
                    "1121\t2\t冻结周期内A相反向有功最大需量\t\t\n" +
                    "1122\t2\t冻结周期内B相反向有功最大需量\t\t\n" +
                    "1123\t2\t冻结周期内C相反向有功最大需量\t\t\n" +
                    "1130\t2\t冻结周期内组合无功1最大需量\t\t\n" +
                    "1131\t2\t冻结周期内A相组合无功1最大需量\t\t\n" +
                    "1132\t2\t冻结周期内B相组合无功1最大需量\t\t\n" +
                    "1133\t2\t冻结周期内C相组合无功1最大需量\t\t\n" +
                    "1140\t2\t冻结周期内组合无功2最大需量\t\t\n" +
                    "1141\t2\t冻结周期内A相组合无功2最大需量\t\t\n" +
                    "1142\t2\t冻结周期内B相组合无功2最大需量\t\t\n" +
                    "1143\t2\t冻结周期内C相组合无功2最大需量\t\t\n" +
                    "1150\t2\t冻结周期内第一象限最大需量\t\t\n" +
                    "1151\t2\t冻结周期内A相第一象限最大需量\t\t\n" +
                    "1152\t2\t冻结周期内B相第一象限最大需量\t\t\n" +
                    "1153\t2\t冻结周期内C相第一象限最大需量\t\t\n" +
                    "1160\t2\t冻结周期内第二象限最大需量\t\t\n" +
                    "1161\t2\t冻结周期内A相第二象限最大需量\t\t\n" +
                    "1162\t2\t冻结周期内B相第二象限最大需量\t\t\n" +
                    "1163\t2\t冻结周期内C相第二象限最大需量\t\t\n" +
                    "1170\t2\t冻结周期内第三象限最大需量\t\t\n" +
                    "1171\t2\t冻结周期内A相第三象限最大需量\t\t\n" +
                    "1172\t2\t冻结周期内B相第三象限最大需量\t\t\n" +
                    "1173\t2\t冻结周期内C相第三象限最大需量\t\t\n" +
                    "1180\t2\t冻结周期内第四象限最大需量\t\t\n" +
                    "1181\t2\t冻结周期内A相第四象限最大需量\t\t\n" +
                    "1182\t2\t冻结周期内B相第四象限最大需量\t\t\n" +
                    "1183\t2\t冻结周期内C相第四象限最大需量\t\t\n" +
                    "1190\t2\t冻结周期内正向视在最大需量\t\t\n" +
                    "1191\t2\t冻结周期内A相正向视在最大需量\t\t\n" +
                    "1192\t2\t冻结周期内B相正向视在最大需量\t\t\n" +
                    "1193\t2\t冻结周期内C相正向视在最大需量\t\t\n" +
                    "11A0\t2\t冻结周期内反向视在最大需量\t\t\n" +
                    "11A1\t2\t冻结周期内A 相反向视在最大需量\t\t\n" +
                    "11A2\t2\t冻结周期内B相反向视在最大需量\t\t\n" +
                    "11A3\t2\t冻结周期内C相反向视在最大需量\t\t\n";
    // 变量类
    private final static String VARIABLE_INFO =
            "2000\t3\t电压\t\t\n" +
                    "2001\t3\t电流\t\t\n" +
                    "2002\t3\t电压相角\t\t\n" +
                    "2003\t3\t电压电流相角\t\t\n" +
                    "2004\t4\t有功功率\t\t\n" +
                    "2005\t4\t无功功率\t\t\n" +
                    "2006\t4\t视在功率\t\t\n" +
                    "2007\t4\t一分钟平均有功功率\t\t\n" +
                    "2008\t4\t一分钟平均无功功率\t\t\n" +
                    "2009\t4\t一分钟平均视在功率\t\t\n" +
                    "200A\t4\t功率因数\t\t\n" +
                    "200B\t3\t电压波形失真度\t\t\n" +
                    "200C\t3\t电流波形失真度\t\t\n" +
                    "200D\t5\t电压谐波含有量（总及2…n次）\t\t\n" +
                    "200E\t5\t电流谐波含有量（总及2…n次）\t\t\n" +
                    "200F\t6\t电网频率\t\t\n" +
                    "2010\t6\t表内温度\t\t\n" +
                    "2011\t6\t时钟电池电压\t\t\n" +
                    "2012\t6\t停电抄表电池电压\t\t\n" +
                    "2013\t6\t时钟电池工作时间\t\t\n" +
                    "2014\t6\t电能表运行状态字\t\t\n" +
                    "2015\t6\t电能表跟随上报状态字\t\t\n" +
                    "2017\t6\t当前有功需量\t\t\n" +
                    "2018\t6\t当前无功需量\t\t\n" +
                    "2019\t6\t当前视在需量\t\t\n" +
                    "201A\t6\t当前电价\t\t\n" +
                    "201B\t6\t当前费率电价\t\t\n" +
                    "201C\t6\t当前阶梯电价\t\t\n" +
                    "201E\t8\t事件发生时间\t\t\n" +
                    "2020\t8\t事件结束时间\t\t\n" +
                    "2021\t8\t数据冻结时间\t\t\n" +
                    "2022\t8\t事件记录序号\t\t\n" +
                    "2023\t8\t冻结记录序号\t\t\n" +
                    "2024\t8\t事件发生源\t\t\n" +
                    "2025\t8\t事件当前值\t\t\n" +
                    "2026\t6\t电压不平衡率\t\t\n" +
                    "2027\t6\t电流不平衡率\t\t\n" +
                    "2028\t6\t负载率\t\t\n" +
                    "2029\t6\t安时值\t\t\n" +
                    "202A\t8\t目标服务器地址\t\t\n" +
                    "202C\t8\t（当前）钱包文件\t\t\n" +
                    "202D\t6\t（当前）透支金额\t\t\n" +
                    "202E\t6\t累计购电金额\t\t\n" +
                    "2031\t6\t月度用电量\t\t\n" +
                    "2032\t6\t阶梯结算用电量\t\t\n" +
                    "2033\t9\t电器设备\t\t\n" +
                    "2040\t6\t控制命令执行状态字\t\t\n" +
                    "2041\t6\t控制命令错误状态字\t\t\n" +
                    "2050\t6\t电流回路状态\t\t\n" +
                    "2100\t14\t分钟区间统计\t\t\n" +
                    "2101\t14\t小时区间统计\t\t\n" +
                    "2102\t14\t日区间统计\t\t\n" +
                    "2103\t14\t月区间统计\t\t\n" +
                    "2104\t14\t年区间统计 \t\t\n" +
                    "2110\t15\t分钟平均\t\t\n" +
                    "2111\t15\t小时平均\t\t\n" +
                    "2112\t15\t日平均\t\t\n" +
                    "2113\t15\t月平均\t\t\n" +
                    "2114\t15\t年平均\t\t\n" +
                    "2120\t16\t分钟极值\t\t\n" +
                    "2121\t16\t小时极值\t\t\n" +
                    "2122\t16\t日极值\t\t\n" +
                    "2123\t16\t月极值\t\t\n" +
                    "2124\t16\t年极值\t\t\n" +
                    "2131\t6\t当月A相电压合格率\t\t\n" +
                    "2132\t6\t当月B相电压合格率\t\t\n" +
                    "2133\t6\t当月C相电压合格率\t\t\n" +
                    "2140\t2\t日最大有功功率及发生时间\t\t\n" +
                    "2141\t2\t月最大有功功率及发生时间\t\t\n" +
                    "2200\t6\t通信流量\t\t\n" +
                    "2203\t6\t供电时间\t\t\n" +
                    "2204\t6\t复位次数\t\t\n" +
                    "2301\t23\t总加组1\t\t\n" +
                    "2302\t23\t总加组2\t\t\n" +
                    "2303\t23\t总加组3\t\t\n" +
                    "2304\t23\t总加组4\t\t\n" +
                    "2305\t23\t总加组5\t\t\n" +
                    "2306\t23\t总加组6\t\t\n" +
                    "2307\t23\t总加组7\t\t\n" +
                    "2308\t23\t总加组8\t\t\n" +
                    "2401\t12\t脉冲计量1\t\t\n" +
                    "2402\t12\t脉冲计量2\t\t\n" +
                    "2403\t12\t脉冲计量3\t\t\n" +
                    "2404\t12\t脉冲计量4\t\t\n" +
                    "2405\t12\t脉冲计量5\t\t\n" +
                    "2406\t12\t脉冲计量6\t\t\n" +
                    "2407\t12\t脉冲计量7\t\t\n" +
                    "2408\t12\t脉冲计量8\t\t\n" +
                    "2500\t6\t累计水（热）流量\t\t\n" +
                    "2501\t6\t累计气流量\t\t\n" +
                    "2502\t6\t累计热量\t\t\n" +
                    "2503\t6\t热功率\t\t\n" +
                    "2504\t6\t累计工作时间\t\t\n" +
                    "2505\t6\t水温\t\t\n" +
                    "2506\t6\t（仪表）状态ST\t\t\n";
    // 事件类
    private final static String EVENT_INFO = "3000\t24\t电能表失压事件\t\t\n" +
            "3001\t24\t电能表欠压事件\t\t\n" +
            "3002\t24\t电能表过压事件\t\t\n" +
            "3003\t24\t电能表断相事件\t\t\n" +
            "3004\t24\t电能表失流事件\t\t\n" +
            "3005\t24\t电能表过流事件\t\t\n" +
            "3006\t24\t电能表断流事件\t\t\n" +
            "3007\t24\t电能表功率反向事件\t\t\n" +
            "3008\t24\t电能表过载事件\t\t\n" +
            "3009\t7\t电能表正向有功需量超限事件\t\t\n" +
            "300A\t7\t电能表反向有功需量超限事件\t\t\n" +
            "300B\t24\t电能表无功需量超限事件\t\t\n" +
            "300C\t7\t电能表功率因数超下限事件\t\t\n" +
            "300D\t7\t电能表全失压事件\t\t\n" +
            "300E\t7\t电能表辅助电源掉电事件\t\t\n" +
            "300F\t7\t电能表电压逆相序事件\t\t\n" +
            "3010\t7\t电能表电流逆相序事件\t\t\n" +
            "3011\t7\t电能表掉电事件\t\t\n" +
            "3012\t7\t电能表编程事件\t\t\n" +
            "3013\t7\t电能表清零事件\t\t\n" +
            "3014\t7\t电能表需量清零事件\t\t\n" +
            "3015\t7\t电能表事件清零事件\t\t\n" +
            "3016\t7\t电能表校时事件\t\t\n" +
            "3017\t7\t电能表时段表编程事件\t\t\n" +
            "3018\t7\t电能表时区表编程事件\t\t\n" +
            "3019\t7\t电能表周休日编程事件\t\t\n" +
            "301A\t7\t电能表结算日编程事件\t\t\n" +
            "301B\t7\t电能表开盖事件\t\t\n" +
            "301C\t7\t电能表开端钮盒事件\t\t\n" +
            "301D\t7\t电能表电压不平衡事件\t\t\n" +
            "301E\t7\t电能表电流不平衡事件\t\t\n" +
            "301F\t7\t电能表跳闸事件\t\t\n" +
            "3020\t7\t电能表合闸事件\t\t\n" +
            "3021\t7\t电能表节假日编程事件\t\t\n" +
            "3022\t7\t电能表有功组合方式编程事件\t\t\n" +
            "3023\t7\t电能表无功组合方式编程事件\t\t\n" +
            "3024\t7\t电能表费率参数表编程事件\t\t\n" +
            "3025\t7\t电能表阶梯表编程事件\t\t\n" +
            "3026\t7\t电能表密钥更新事件\t\t\n" +
            "3027\t7\t电能表异常插卡事件\t\t\n" +
            "3028\t7\t电能表购电记录\t\t\n" +
            "3029\t7\t电能表退费记录\t\t\n" +
            "302A\t7\t电能表恒定磁场干扰事件\t\t\n" +
            "302B\t7\t电能表负荷开关误动作事件\t\t\n" +
            "302C\t7\t电能表电源异常事件\t\t\n" +
            "302D\t7\t电能表电流严重不平衡事件\t\t\t\n" +
            "302E\t7\t电能表时钟故障事件\t\t\n" +
            "302F\t7\t电能表计量芯片故障事件\t\t\n" +
            "3030\t7\t通信模块变更事件\t\t\n" +
            "3100\t7\t终端初始化事件\t\t\n" +
            "3101\t7\t终端版本变更事件\t\t\n" +
            "3104\t7\t终端状态量变位事件\t\t\n" +
            "3105\t7\t电能表时钟超差事件\t\t\n" +
            "3106\t7\t终端停/上电事件\t\t\n" +
            "3107\t7\t终端直流模拟量越上限事件\t\t\n" +
            "3108\t7\t终端直流模拟量越下限事件\t\t\n" +
            "3109\t7\t终端消息认证错误事件\t\t\n" +
            "310A\t7\t设备故障记录\t\t\n" +
            "310B\t7\t电能表示度下降事件\t\t\n" +
            "310C\t7\t电能量超差事件\t\t\n" +
            "310D\t7\t电能表飞走事件\t\t\n" +
            "310E\t7\t电能表停走事件\t\t\n" +
            "310F\t7\t终端抄表失败事件\t\t\n" +
            "3110\t7\t月通信流量超限事件\t\t\n" +
            "3111\t7\t发现未知电能表事件\t\t\n" +
            "3112\t7\t跨台区电能表事件\t\t\n" +
            "3114\t7\t终端对时事件\t\t\n" +
            "3115\t7\t遥控跳闸记录\t\t\n" +
            "3116\t7\t有功总电能量差动越限事件记录\t\t\n" +
            "3117\t7\t输出回路接入状态变位事件记录\t\t\n" +
            "3118\t7\t终端编程记录\t\t\n" +
            "3119\t7\t终端电流回路异常事件\t\t\n" +
            "311A\t7\t电能表在网状态切换事件\t\t\n" +
            "311B\t7\t终端对电表校时记录\t\t\n" +
            "311C\t7\t电能表数据变更监控记录\t\t\n" +
            "311d\t7\t电能表在线状态变化事件\t\t\n" +
            "3120\t7\t电流互感器异常事件\t\t\n" +
            "3121\t7\tTA专用模块工况事件\t\t\n" +
            "3140\t7\t安全事件变更记录\t\t\n" +
            "3200\t7\t功控跳闸记录\t\t\n" +
            "3201\t7\t电控跳闸记录\t\t\n" +
            "3202\t7\t购电参数设置记录\t\t\n" +
            "3203\t7\t电控告警事件记录\t\t\n" +
            "3300\t8\t事件上报状态\t\t\n" +
            "3301\t8\t标准事件记录单元\t\t\n" +
            "3302\t8\t编程记录事件单元\t\t\n" +
            "3303\t8\t发现未知电能表事件单元\t\t\n" +
            "3304\t8\t跨台区电能表事件单元\t\t\n" +
            "3305\t8\t功控跳闸记录单元\t\t\n" +
            "3306\t8\t电控跳闸记录单元\t\t\n" +
            "3307\t8\t电控告警事件单元\t\t\n" +
            "3308\t8\t电能表需量超限事件单元\t\t\n" +
            "3309\t8\t停/上电事件记录单元\t\t\n" +
            "330A\t8\t遥控事件记录单元\t\t\n" +
            "330B\t8\t有功总电能量差动越限事件记录单元\t\t\n" +
            "330C\t8\t事件清零事件记录单元\t\t\n" +
            "330D\t8\t终端对电表校时记录单元\t\t\n" +
            "330E\t8\t电能表在网状态切换事件单元\t\t\n" +
            "330F\t8\t电能表数据变更监控记录单元\t\t\n" +
            "3310\t8\t异常插卡事件记录单元\t\t\n" +
            "3311\t8\t退费事件记录单元\t\t\n" +
            "3312\t8\t通信模块变更事件单元\t\t\n" +
            "3313\t8\t电能表时钟超差记录单元\t\t\n" +
            "3314\t8\t电能表时段表编程事件记录单元\t\t\n" +
            "3315\t8\t电能表节假日编程事件记录单元\t\t\n" +
            "3316\t8\t安全事件变更记录单元\t\t\n" +
            "3317\t8\t电能表在线状态变化事件单元\t\t\n" +
            "3318\t8\t电流互感器异常事件单元\t\t\n" +
            "3320\t8\t新增上报事件列表\t\t\n";
    // 参变量类
    private final static String PARAMETER_INFO = "4000\t8\t日期时间\t\t\n" +
            "4001\t8\t通信地址\t\t\n" +
            "4002\t8\t表号\t\t\n" +
            "4003\t8\t客户编号\t\t\n" +
            "4004\t8\t设备地理位置\t\t\n" +
            "4005\t8\t组地址\t\t\n" +
            "4006\t8\t时钟源\t\t\n" +
            "4007\t8\tLCD参数\t\t\n" +
            "4008\t8\t备用套时区表切换时间\t\t\n" +
            "4009\t8\t备用套日时段切换时间\t\t\n" +
            "400A\t8\t备用套分时费率切换时间\t\t\n" +
            "400B\t8\t备用套阶梯电价切换时间\t\t\n" +
            "400C\t8\t时区时段数\t\t\n" +
            "400D\t8\t阶梯数\t\t\n" +
            "400E\t8\t谐波分析次数\t\t\n" +
            "400F\t8\t密钥总条数\t\t\n" +
            "4010\t8\t计量元件数\t\t\n" +
            "4011\t8\t公共假日表\t\t\n" +
            "4012\t8\t周休日特征字\t\t\n" +
            "4013\t8\t周休日釆用的日时段表号\t\t\n" +
            "4014\t8\t当前套时区表\t\t\n" +
            "4015\t8\t备用套时区表\t\t\n" +
            "4016\t8\t当前套日时段表\t\t\n" +
            "4017\t8\t备用套日时段表\t\t\n" +
            "4018\t8\t当前套费率电价\t\t\n" +
            "4019\t8\t备用套费率电价\t\t\n" +
            "401A\t8\t当前套阶梯电价\t\t\n" +
            "401B\t8\t备用套阶梯电价\t\t\n" +
            "401C\t8\t电流互感器变比\t\t\n" +
            "401D\t8\t电压互感器变比\t\t\n" +
            "401E\t8\t报警金额限值\t\t\n" +
            "401F\t8\t其它金额限值\t\t\n" +
            "4020\t8\t报警电量限值\t\t\n" +
            "4021\t8\t其它电量限值\t\t\n" +
            "4022\t6\t插卡状态字\t\t\n" +
            "4024\t8\t剔除\t\t\n" +
            "4025\t8\t采集器远程升级结果表\t\t\n" +
            "4026\t8\t采集器升级结果\t\t\n" +
            "4030\t8\t电压合格率参数\t\t\n" +
            "4040\t8\tTA 专用模块\t\t\n" +
            "4041\t8\t电流回路监测使能\t\t\n" +
            "4100\t8\t最大需量周期\t\t\n" +
            "4101\t8\t滑差时间\t\t\n" +
            "4102\t8\t校表脉冲宽度\t\t\n" +
            "4103\t8\t资产管理编码\t\t\n" +
            "4104\t8\t额定电压\t\t\n" +
            "4105\t8\t额定电流/基本电流\t\t\n" +
            "4106\t8\t最大电流\t\t\n" +
            "4107\t8\t有功准确度等级\t\t\n" +
            "4108\t8\t无功准确度等级\t\t\n" +
            "4109\t8\t电能表有功常数\t\t\n" +
            "410A\t8\t电能表无功常数\t\t\n" +
            "410B\t8\t电能表型号\t\t\n" +
            "410C\t8\tABC各相电导系数\t\t\n" +
            "410D\t8\tABC各相电抗系数\t\t\n" +
            "410E\t8\tABC各相电阻系数\t\t\n" +
            "410F\t8\tABC各相电纳系数\t\t\n" +
            "4111\t8\t软件备案号\t\t\n" +
            "4112\t8\t有功组合方式特征字\t\t\n" +
            "4113\t8\t无功组合方式1特征字\t\t\n" +
            "4114\t8\t无功组合方式2特征字\t\t\n" +
            "4116\t8\t结算日\t\t\n" +
            "4117\t8\t期间需量冻结周期\t\t\n" +
            "4202\t8\t级联通信参数\t\t\n" +
            "4204\t8\t终端广播校时\t\t\n" +
            "4300\t19\t电气设备\t\t\n" +
            "4307\t19\t水表\t\t\n" +
            "4308\t19\t气表\t\t\n" +
            "4309\t19\t热表\t\t\n" +
            "4310\t19\t锁具设备\t\t\n" +
            "4400\t20\t应用连接\t\t\n" +
            "4401\t8\t认证密码\t\t\n" +
            "4500\t25\t公网通信模块1\t\t\n" +
            "4501\t25\t公网通信模块2\t\t\n" +
            "4510\t26\t以太网通信模块1\t\t\n" +
            "4511\t26\t以太网通信模块2\t\t\n" +
            "4512\t26\t以太网通信模块3\t\t\n" +
            "4513\t26\t以太网通信模块4\t\t\n" +
            "4514\t26\t以太网通信模块5\t\t\n" +
            "4515\t26\t以太网通信模块6\t\t\n" +
            "4516\t26\t以太网通信模块7\t\t\n" +
            "4517\t26\t以太网通信模块8\t\t\n" +
            "4520\t8\t公网远程通信多接入点备用通道\t\t\n" +
            "4800\t11\t电器设备集合\t\t\n";
    // 冻结类
    private final static String FREEZE_INFO =
            "5000\t9\t瞬时冻结\t\t\n" +
                    "5001\t9\t秒冻结\t\t\n" +
                    "5002\t9\t分钟冻结\t\t\n" +
                    "5003\t9\t小时冻结\t\t\n" +
                    "5004\t9\t日冻结\t\t\n" +
                    "5005\t9\t结算日冻结\t\t\n" +
                    "5006\t9\t月冻结\t\t\n" +
                    "5007\t9\t年冻结\t\t\n" +
                    "5008\t9\t时区表切换冻结\t\t\n" +
                    "5009\t9\t日时段表切换冻结\t\t\n" +
                    "500A\t9\t费率电价切换冻结\t\t\n" +
                    "500B\t9\t阶梯切换冻结\t\t\n" +
                    "5011\t9\t阶梯结算冻结\t\t\n";
    // 采集监控类
    private final static String ACQUISITION_MONITORING_INFO =
            "6000\t11\t采集档案配置表\t\t\n" +
                    "6001\t8\t采集档案配置单元\t\t\n" +
                    "6002\t11\t搜表\t\t\n" +
                    "6003\t8\t一个搜表结果\t\t\n" +
                    "6004\t8\t一个跨台区结果\t\t\n" +
                    "6005\t8\t户表新增标识\t\t\n" +
                    "6012\t10\t任务配置表\t\t\n" +
                    "6014\t11\t普通采集方案集\t\t\n" +
                    "6015\t8\t普通采集方案\t\t\n" +
                    "6016\t11\t事件采集方案集\t\t\n" +
                    "6017\t8\t事件采集方案\t\t\n" +
                    "6018\t11\t透明方案集\t\t\n" +
                    "6019\t8\t透明方案\t\t\n" +
                    "601A\t11\t透明方案结果集\t\t\n" +
                    "601B\t8\t一个透明方案结果\t\t\n" +
                    "601C\t11\t上报方案集\t\t\n" +
                    "601D\t8\t上报方案\t\t\n" +
                    "601E\t11\t采集规则库\t\t\n" +
                    "601F\t8\t采集规则\t\t\n" +
                    "6032\t11\t采集状态集\t\t\n" +
                    "6033\t8\t一个采集状态\t\t\n" +
                    "6034\t11\t采集任务监控集\t\t\n" +
                    "6035\t8\t采集任务监控单元\t\t\n" +
                    "6040\t8\t采集启动时标\t\t\n" +
                    "6041\t8\t采集成功时标\t\t\n" +
                    "6042\t8\t采集存储时标\t\t\n";
    //集合类
    private final static String COLLECTION_INFO =
            "7000\t11\t文件集合\t\t\n" +
                    "7001\t8\t文件\t\t\n" +
                    "7010\t11\t脚本集合\t\t\n" +
                    "7011\t8\t脚本\t\t\n" +
                    "7012\t11\t脚本执行结果集\t\t\n" +
                    "7013\t8\t一个脚本执行结果\t\t\n" +
                    "7100\t11\t扩展变量对象集合\t\t\n" +
                    "7101\t11\t扩展参变量对象集合\t\t\n";
    // 控制类
    private final static String CONTROL_INFO =
            "8000\t8\t远程控制\t\t\n" +
                    "8001\t8\t保电\t\t\n" +
                    "8002\t8\t催费告警\t\t\n" +
                    "8003\t11\t一般中文信息\t\t\n" +
                    "8004\t11\t重要中文信息\t\t\n" +
                    "8100\t8\t终端保安定值\t\t\n" +
                    "8101\t8\t终端功控时段\t\t\n" +
                    "8102\t8\t功控告警时间\t\t\n" +
                    "8103\t13\t时段功控\t\t\n" +
                    "8104\t13\t厂休控\t\t\n" +
                    "8105\t13\t营业报停控\t\t\n" +
                    "8106\t13\t当前功率下浮控\t\t\n" +
                    "8107\t13\t购电控\t\t\n" +
                    "8108\t13\t月电控\t\t\n" +
                    "8109\t8\t时段功控配置单元\t\t\n" +
                    "810A\t8\t厂休控配置单元\t\t\n" +
                    "810B\t8\t营业报停控配置单元\t\t\n" +
                    "810C\t8\t购电控配置单元\t\t\n" +
                    "810D\t8\t月电控配置单元\t\t\n" +
                    "810E\t8\t控制对象\t\t\n" +
                    "810F\t8\t跳闸轮次\t\t\n" +
                    "8110\t8\t电控定值\t\t\n";
    // 文件传输类
    private final static String FILE_TRANSFER_INFO = "" +
            "F000\t18\t文件分帧传输管理\t\t\n" +
            "F001\t18\t文件分块传输管理\t\t\n" +
            "F002\t18\t文件扩展传输管理\t\t\n";
    // ESAM 接口类
    private final static String ESAM_INTERFACE_INFO =
            "F100\t21\tESAM\t\t\n" +
                    "F101\t8\t安全模式参数\t\t\n";
    //  输入输出设备类
    private final static String IN_0UT_DEVICE_INFO =
            "F200\t22\tRS232\t\t\n" +
                    "F201\t22\tRS485\t\t\n" +
                    "F202\t22\t红外\t\t\n" +
                    "F203\t22\t开关量输入\t\t\n" +
                    "F204\t22\t直流模拟量\t\t\n" +
                    "F205\t22\t继电器输出\t\t\n" +
                    "F206\t22\t告警输出\t\t\n" +
                    "F207\t22\t多功能端子\t\t\n" +
                    "F208\t22\t交采接口\t\t\n" +
                    "F209\t22\t载波/微功率无线接口\t\t\n" +
                    "F20A\t22\t脉冲输入设备\t\t\n" +
                    "F20B\t22\t蓝牙\t\t\n" +
                    "F20C\t22\t230M无线专网接口\t\t\n" +
                    "F20E\t22\tUSB\t\t\n" +
                    "F20F\t22\tNFC\t\t\n" +
                    "F210\t8\t从节点单元\t\t\n" +
                    "F212\t8\t节点相位信息单元\t\t\n";
    // 显示类
    private final static String DISPLAY_INFO =
            "F300\t17\t自动轮显\t\t\n" +
                    "F301\t17\t按键轮显\t\t\n";


    public enum OI {
        /**
         * OI类型
         */
        ENERGY(ENERGY_INFO),
        MAXIMUM_DEMAND(MAXIMUM_DEMAND_INFO),
        VARIABLE(VARIABLE_INFO),
        EVENT(EVENT_INFO),
        PARAMETER(PARAMETER_INFO),
        FREEZE(FREEZE_INFO),
        ACQUISITION_MONITORING(ACQUISITION_MONITORING_INFO),
        COLLECTION(COLLECTION_INFO),
        CONTROL(CONTROL_INFO),
        FILE_TRANSFER(FILE_TRANSFER_INFO),
        ESAM_INTERFACE(ESAM_INTERFACE_INFO),
        IN_0UT_DEVICE(IN_0UT_DEVICE_INFO),
        DISPLAY(DISPLAY_INFO);

        private String value;


        private HashMap<String, Inner> ois;

        OI(String value) {
            this.value = value;
            init();
        }

        private void init() {
            ois = new HashMap<>();
            String[] vars = getValue().split("\n");
            for (String var : vars) {
                String[] varArr = var.split("\t");
                Inner inner = new Inner(varArr[0], varArr[1], varArr[2]);
                ois.put(varArr[0], inner);
            }
          //  System.out.println(ois.size());
        }

        public String getValue() {
            return value;
        }

        public HashMap<String, Inner> getOIs() {
            return ois;
        }
    }


    public static OIManager getInstance() {
        return SingleTon.INSTANCE;
    }


    private OIManager() {
        init();
    }

    private HashMap<String, Inner> ois;
    private HashMap<Integer, HashMap<String, Inner>> ois_interfs;

    private void init() {

        ois = new HashMap<>();
        for (OI oi : OI.values()) {
            ois.putAll(oi.getOIs());
        }

        ois_interfs = new HashMap<>();

        for (Map.Entry<String, Inner> entry : ois.entrySet()) {
            HashMap<String, Inner> ois_interf = ois_interfs.get(entry.getValue().interfClass);
            if (null == ois_interf) {
                ois_interf = new HashMap<>();
            }
            ois_interf.put(entry.getValue().oi, entry.getValue());
            ois_interfs.put(entry.getValue().interfClass, ois_interf);
        }


    }

    private static class SingleTon {
        private static final OIManager INSTANCE = new OIManager();
    }

    public int getInterfClass(String oi) {
        return ois.get(oi).interfClass;
    }

    public HashMap<String, Inner> getOIsByInfterface(int interfNo) {
        HashMap<String, Inner> innerHashMap = ois_interfs.get(interfNo);
        return null == innerHashMap ? new HashMap<>() : innerHashMap;
    }
    public HashMap<String, Inner> getOIs() {
        return ois;
    }
    public String getObjName(String oi) {
        return ois.get(oi).objName;
    }

    public static class Inner {
        private String objName;
        private int interfClass;
        private String oi;

        public Inner(String oi, String interfClass, String objName) {
            this.objName = objName;
            this.interfClass = Integer.parseInt(interfClass);
            this.oi = oi;
        }
    }

}
