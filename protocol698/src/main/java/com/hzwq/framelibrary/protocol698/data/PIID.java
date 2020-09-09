package com.hzwq.framelibrary.protocol698.data;

import android.support.annotation.IntRange;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.common.util.NumberConvert;


/**
 * Created by qinling on 2018/5/10 17:00
 * Description:
 */
public class PIID implements IHex {

    // PIID & PIID-ACD
    //服务优先 int serverPriority = isPIID_HighPriority ? 1 : 0;
    private  boolean isHighPriority_PIID; //0: 一般的，1：高级的，在.response APDU中，其值与对应.request APDU中的相等
    //请求访问ACD int acd = isACD_Request ? 1:0;
    private  boolean isRequest_ACD;//0：不请求，1：请求
    //服务序号 0-63
    private  int serviceSerialNumber_PIID;
    //private  String piidStr;
    private  int piid = -1;

    public String getPiid() {
        return piid== -1? toString() : NumberConvert.toHexStr(piid,2);
    }

    public void setPiid(@IntRange(from = 0,to = 255) int piid) {
        this.piid = piid;
        String piidStr = NumberConvert.toBinaryStr(piid,8);
        isHighPriority_PIID = piidStr.charAt(0) == '1';
        isRequest_ACD = piidStr.charAt(1) == '1';
        serviceSerialNumber_PIID = Integer.parseInt(piidStr.substring(2,8),2);
    }

    /**
     *
     * @param piidHexStr 1字节 16进制字符串
     */
    public PIID(String piidHexStr) {
        if (piidHexStr == null || piidHexStr.length() == 0){
     //   if (TextUtils.isEmpty(piidHexStr)) {
            setPiid(0);
            return;
        }
        if (piidHexStr.length()!=2 || !NumberConvert.isHexStr(piidHexStr)){
                throw new IllegalArgumentException("参数异常,需要1字节 16进制字符串");
         }
        setPiid(Integer.parseInt(piidHexStr,16));
    }

    public PIID() {
        this(false,false,00);
    }

    public PIID(@IntRange(from = 0,to = 63) int serviceSerialNumber_PIID) {
        this(false,false,serviceSerialNumber_PIID);
    }

    public PIID(boolean isHighPriority_PIID) {
        this(isHighPriority_PIID,false,00);
    }

    public PIID(boolean isHighPriority_PIID, @IntRange(from = 0,to = 63) int serviceSerialNumber_PIID) {
       this(isHighPriority_PIID,false,serviceSerialNumber_PIID);
    }

    public PIID(boolean isHighPriority_PIID, boolean isRequest_ACD,@IntRange(from = 0,to = 63) int serviceSerialNumber_PIID) {
        this.isHighPriority_PIID = isHighPriority_PIID;
        this.isRequest_ACD = isRequest_ACD;
        this.serviceSerialNumber_PIID = serviceSerialNumber_PIID;
    }

    public boolean isHighPriority_PIID() {
        return isHighPriority_PIID;
    }

    public void setHighPriority_PIID(boolean highPriority_PIID) {
        isHighPriority_PIID = highPriority_PIID;
    }

    public boolean isRequest_ACD() {
        return isRequest_ACD;
    }

    public void setRequest_ACD(boolean request_ACD) {
        isRequest_ACD = request_ACD;
    }

    public int getServiceSerialNumber_PIID() {
        return serviceSerialNumber_PIID;
    }
    public int getServiceSerialNumber_PIID(String piidStr) {
        return serviceSerialNumber_PIID;
    }

    public void setServiceSerialNumber_PIID(@IntRange(from = 0,to = 63) int serviceSerialNumber_PIID) {
        this.serviceSerialNumber_PIID = serviceSerialNumber_PIID;
    }



    /**
     * 获取PIID/PIID-ACD
     *
     * @return 一字节16进制字符串
     */
    @Override
    public String toHexString() {
        int serverPriority = isHighPriority_PIID ? 1 : 0;
        int acd = isRequest_ACD ? 1 : 0;
        StringBuffer stringBuffer = new StringBuffer();
        String binaryStr = stringBuffer
                .append(serverPriority)
                .append(acd)
                .append(NumberConvert.toBinaryStr(serviceSerialNumber_PIID, 6))
                .toString();
        return NumberConvert.addZeroToStringLeft(NumberConvert.binaryStrToHexStr(binaryStr), 2);
    }
}
