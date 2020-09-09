package com.hzwq.framelibrary.protocol698.apdu.link;


import com.hzwq.framelibrary.protocol698.apdu.IAPDU;
import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.protocol698.data.DateTime;
import com.hzwq.framelibrary.protocol698.data.enums.LinkRequestTypeEnum;

import static com.hzwq.framelibrary.common.util.NumberConvert.toHexStr;


/**
 * Created by qinling on 2018/5/11 14:40
 * Description: 预连接协议数据单元（LINK-APDU）
 */
public class LinkAPDU implements IAPDU /*ISecurityableAPDU*/ {

    @Override
    public String toHexString() {
        return service.classifyStr() + service.toHexString();
    }

    private IService service;

    public interface IService extends IHex {
        /*** 预连接请求*/
        int LINK_REQUEST = 0x01;
        /*** 预连接响应*/
        int LINK_RESPONSE = 0x81;

        int classify();

        default String classifyStr() {
            return toHexStr(classify(), 2);
        }
    }

    public LinkAPDU(IService service) {
        this.service = service;
    }

    public static LinkAPDU linkRequest() {
        return linkRequest(new LinkRequest.Builder().build());
    }

    public static LinkAPDU linkRequest(int piid, LinkRequestTypeEnum linkRequestTypeEnum, int heartCycle) {
        return linkRequest((new LinkRequest.Builder()
                .setPiid(piid)
                .setHeartCycle(heartCycle)
                .setLinkRequestType(linkRequestTypeEnum).build()));
    }

    public static LinkAPDU linkRequest(LinkRequest linkRequest) {
        return new LinkAPDU(linkRequest);
    }


    public static LinkAPDU linkResponse() {
        return new LinkAPDU(new LinkResponse.Builder().build());
    }

    public static LinkAPDU linkResponse(LinkResponse linkResponse) {
        return new LinkAPDU(linkResponse);
    }

    public static LinkAPDU linkResponse(DateTime requestTime, DateTime receiveTime) {
        return new LinkAPDU(
                new LinkResponse.Builder().setRequestTime(requestTime)
                        .setReceivedTime(receiveTime)
                        .build()
        );
    }


}
