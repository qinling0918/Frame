package com.hzwq.framelibrary.protocol698.data.datahelpers;

import com.hzwq.framelibrary.common.IHex;
import com.hzwq.framelibrary.common.util.NumberConvert;
import com.hzwq.framelibrary.protocol698.data.Data;
import com.hzwq.framelibrary.protocol698.data.IData;
import com.hzwq.framelibrary.protocol698.data.datahelpers.parser.AResultNormal;
import com.hzwq.framelibrary.protocol698.data.datahelpers.parser.AResultRecord;


/**
 * Created by qinling on 2019/4/24 19:13
 * Description: 跟随上报信息域（FollowReport）的数据类型定义见表120　。
 * 表120　FollowReport数据类型定义
 * <p>
 * FollowReport∷=CHOICE
 * {
 * 若干个对象属性及其数据        [1]  SEQUENCE OF A-ResultNormal，
 * 若干个记录型对象属性及其数据  [2]  SEQUENCE OF A-ResultRecord
 * }
 */
public class FollowReport implements IHex {

    private AResultNormal[] resultNormals;


    private AResultRecord[] resultRecords;
    private static final int A_RESULT_NORMAL = 0x01;
    private static final int A_RESULT_RECORD = 0x02;
    private final int choice;

    public FollowReport() {
        this.resultRecords = null;
        this.choice = 0x00;
    }

    public FollowReport(AResultRecord... resultRecords) {
        this.resultRecords = resultRecords;
        this.choice = resultRecords==null? 0x00:A_RESULT_RECORD;
    }

    public FollowReport(AResultNormal... resultNormals) {
        this.resultNormals = resultNormals;
        this.choice = resultNormals==null? 0x00:A_RESULT_RECORD;
    }

    public FollowReport(String followReportStr) {
        choice = Integer.parseInt(followReportStr.substring(0, 2), 16);
        if (A_RESULT_NORMAL == choice) {
            resultNormals = parseNormal(followReportStr.substring(2));
        } else if (A_RESULT_RECORD == choice) {
            resultRecords = parseRecord(followReportStr.substring(2));
        } else {
            // TODO 需要 自定义一个解析失败异常。
            throw new StringIndexOutOfBoundsException("解析失败/无跟随上报信息域");
        }
    }

    private AResultRecord[] parseRecord(String substring) {
        return null;
    }

    private AResultNormal[] parseNormal(String substring) {
        return null;
    }


    @Override
    public String toHexString() {
        if (choice == A_RESULT_NORMAL) {
            return NumberConvert.toHexStr(choice, 2) + Data.toString4Array(resultNormals);
        } else if (choice == A_RESULT_RECORD) {
            return NumberConvert.toHexStr(choice, 2) + Data.toString4Array(resultRecords);
        }
        return "00";
    }

}
