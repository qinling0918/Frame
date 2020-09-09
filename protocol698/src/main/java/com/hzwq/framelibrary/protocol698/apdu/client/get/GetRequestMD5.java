package com.hzwq.framelibrary.protocol698.apdu.client.get;

import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.base.Base1;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description: 逻辑同 GetRequestMD5
 */
public class GetRequestMD5 extends Base1 implements ClientAPDU.GetRequest, FormatAble {

    @Override
    public int type() {
        return GET_REQUEST_MD5;
    }

    public GetRequestMD5() {
        this(new Builder());
    }

    public GetRequestMD5(Builder builder) {
        super(builder);
    }

    public Builder newBuilder() {
        return new Builder(this);
    }


    @Override
    public String format(String apduStr) {
        return new Parser(apduStr).toFormatString();
    }

    public static final class Builder extends Base1.Builder<GetRequestMD5> {
        public Builder(GetRequestMD5 getRequestMD5) {
            super(getRequestMD5);
        }

        public Builder() {
            super();
        }

        @Override
        public GetRequestMD5 build() {
            return new GetRequestMD5(this);
        }
    }


    public static final class Parser extends Base1.Parser<GetRequestMD5> {

        public Parser(String apduStr) {
            super(apduStr);
        }

        // todo 未完待续
        @Override
        protected GetRequestMD5 reBuild() {
            return new GetRequestMD5.Builder()
                    .setPiid(getPiid())
                    .setOad(getOad())
                    .build();
        }


    }
}