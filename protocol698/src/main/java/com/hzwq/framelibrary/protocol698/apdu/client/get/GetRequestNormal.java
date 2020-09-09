package com.hzwq.framelibrary.protocol698.apdu.client.get;

import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.base.Base1;

import static com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU.GetRequest.GET_REQUEST_NORMAL;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description: 逻辑同 GetRequestMD5
 */
public class GetRequestNormal extends Base1 implements ClientAPDU.GetRequest, FormatAble {

    @Override
    public int type() {
        return GET_REQUEST_NORMAL;
    }

    public GetRequestNormal() {
        this(new Builder());
    }

    public GetRequestNormal(Builder builder) {
        super(builder);
    }

    public Builder newBuilder() {
        return new Builder(this);
    }


   /* @Override
    public String toHexString() {
        return newBuilder().toHexString();
    }*/

    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends Base1.Builder<GetRequestNormal> {
        public Builder(GetRequestNormal getRequestMD5) {
            super(getRequestMD5);
        }

        public Builder() {
            super();
        }

        @Override
        public GetRequestNormal build() {
            return new GetRequestNormal(this);
        }
    }


    public static final class Parser extends Base1.Parser<GetRequestNormal> {

        public Parser(String apduStr) {
            super(apduStr);
        }

        // todo 未完待续
        @Override
        protected GetRequestNormal reBuild() {
            return new GetRequestNormal()
                    .newBuilder()
                    .setPiid(getPiid())
                    .setOad(getOad())
                    .build();
        }


    }
}