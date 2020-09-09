package com.hzwq.framelibrary.protocol698.data.conformance.function;


import com.hzwq.framelibrary.protocol698.data.string.BitString;

/**
 * Created by qinling on 2018/5/16 12:05
 * Description:  功能一致性协商内容  bit-string（SIZE（128））  默认值   16字节 FF
 */
public class FunctionConformance extends BitString {
    private static final String DEFAULT = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";

    public FunctionConformance() {
        super(DEFAULT);
    }

    public FunctionConformance(String functionConformanceHexStr) {
        super(functionConformanceHexStr);
    }

    public FunctionConformance(int value) {
        super(value);
    }
    /**
     * 电能量计量							（0），
     双向有功计量							（1），
     无功电能计量							（2），
     视在电能计量							（3），
     有功需量						    		（4），
     无功需量						    		（5），
     视在需量						    		（6），
     复费率					     		（7），
     阀控									（8），
     本地费控								（9），
     远程费控								（10），
     基波电能							（11），W
     谐波电能								（12），
     谐波含量								（13），
     波形失真度							（14），
     多功能端子输出						（15），
     事件记录						    		（16），
     事件上报								（17），
     温度测量								（18），
     状态量监测（如：开表盖/开端钮盖）		（19），
     以太网通信							（20），
     红外通信								（21），
     蓝牙通信								（22），
     多媒体采集							（23），
     级联									（24），
     直流模拟量							（25），
     弱电端子12V输出						（26），
     搜表									（27），
     三相负载平衡							（28），
     升级									（29），
     比对									（30）
     */


}
