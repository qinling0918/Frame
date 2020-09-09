package com.hzwq.framelibrary.protocol698.apdu.client.get;

import com.hzwq.framelibrary.protocol698.apdu.FormatAble;
import com.hzwq.framelibrary.protocol698.apdu.client.ClientAPDU;
import com.hzwq.framelibrary.protocol698.apdu.client.base.Base2;

/**
 * Created by qinling on 2019/4/29 9:00
 * Description:
 */
public class GetRequestNormalList extends Base2 implements ClientAPDU.GetRequest, FormatAble {

    @Override
    public int type() {
        return GET_REQUEST_NORMAL_LIST;
    }

    @Override
    public String format(String formatAbleStr) {
        return new Parser(formatAbleStr).toFormatString();
    }

    private GetRequestNormalList(Builder builder) {
        super(builder);
    }

    public GetRequestNormalList() {
        super(new Builder());
    }

    public Builder newBulider() {
        return new Builder(this);
    }

    public static final class Builder extends Base2.Builder<GetRequestNormalList> {

        public Builder(GetRequestNormalList reportResponseRecordList) {
            super(reportResponseRecordList);
        }

        public Builder() {
            super();
        }

        @Override
        public GetRequestNormalList build() {
            return new GetRequestNormalList(this);
        }
    }


    public static final class Parser extends Base2.Parser<GetRequestNormalList> {
        public Parser(String apduStr) {
            super(apduStr);
        }

        @Override
        protected GetRequestNormalList reBuild() {
            return new GetRequestNormalList().newBulider()
                    .setOads(getOads())
                    .setPiid(getPiid())
                    .build();
        }
    }
}