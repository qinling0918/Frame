package com.hzwq.framelibrary.protocol698.data.conformance.protocol;


import com.hzwq.framelibrary.protocol698.data.string.BitString;

/**
 * Created by qinling on 2018/5/16 12:05
 * Description:   ProtocolConformance 协议一致性协商内容  bit-string（SIZE（64））  默认  8字节FFFFFFFF
 */
public class ProtocolConformance extends BitString {
    /**
     * 应用连接协商Application Association			（0），
     * 请求对象属性Get Normal						（1），
     * 批量请求基本对象属性Get With List			（2），
     * 请求记录型对象属性Get Record				（3），
     * 代理请求对象属性Get Proxy					（4），
     * 代理请求记录型对象属性Get Proxy Record		（5），
     * 请求分帧后续帧Get Subsequent Frame			（6），
     * 设置基本对象属性Set Normal					（7），
     * 批量设置基本对象属性Set With List			（8），
     * 设置后读取Set With Get						（9），
     * 代理设置对象属性Set Proxy					（10），
     * 代理设置后读取对象属性Set Proxy With Get		（11），
     * 执行对象方法Action Normal					（12），
     * 批量执行对象方法Action With List				（13），
     * 执行方法后读取Action With List				（14），
     * 代理执行对象方法Action Proxy				（15），
     * 代理执行后读取Action Proxy With Get			（16），
     * 事件主动上报Active Event Report				（17），
     * 事件尾随上报                              	（18)，
     * 事件请求访问位ACD上报                    	（19)，
     * 分帧数据传输 Slicing Service              	（20），
     * Get-request分帧                           	（21），
     * Get-response分帧                          	（22），
     * Set-request分帧                         		（23），
     * Set-response分帧                        		（24），
     * Action-request分帧                      		（25），
     * Action-response分帧                     		（26），
     * Proxy-request 分帧                       		（27），
     * Proxy-response分帧                      		（28），
     * 事件上报分帧                            		（29），
     * DL/T645-2007                            		（30），
     * 安全方式传输                            		（31），
     * 对象属性ID为0的读取访问                     （32），
     * 对象属性ID为0的设置访问                     （33）
     */

    private static final String DEFAULT = "FFFFFFFFFFFFFFFF";

    public ProtocolConformance() {
        super(DEFAULT);
    }

    public ProtocolConformance(int value) {
        super(value);
    }

    public ProtocolConformance(String valueStr) {
        super(valueStr);
    }
}
