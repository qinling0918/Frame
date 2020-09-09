package com.hzwq.framelibrary.protocol698;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.hzwq.framelibrary.common.throwable.ParseFrameException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by qinling on 2018/7/6 17:28
 * Description: 698 帧报文 分割以及拼接
 */
public class Frame698Separator implements Iterable<Frame698> {
    private static final char FRAME_SEPARATOR = ';';
    private final ArrayList<Frame698> frame698s;
    private final Frame698.Builder frame698Builder;
    private final int upperLimit;
    private final String linkDataStr;

    public Frame698Separator() {
        this(new Builder());

    }
  /*  public Frame698Separator(Frame698 frame698) {
        this(new Builder().setFrame698Builder(frame698.newBuilder()));

    }*/

    private Frame698Separator(Builder builder) {
        frame698s = builder.frame698s;
        frame698Builder = builder.frame698Builder;
        upperLimit = builder.upperLimit;
        linkDataStr = builder.linkDataStr;
        //separateCount = builder.separateCount;

    }

    /**
     * 创建确认帧 作为响应方需要对请求方 提供确认信息.
     *
     * @param frame698Str 上一次请求帧(请求方)
     * @return 确认帧  若是输入的请求帧 不是分帧则返回""
     */
    public String confirm(String frame698Str) {
        Frame698.Parser parser = new Frame698.Parser(frame698Str);
        if (parser.parseCode!=0) throw new ParseFrameException(parser.getErrorStr());
        if (!parser.isFrameSeparate()) return "";
        Frame698 frame698 = parser.reBuild();

        return frame698.newBuilder()
                .setFrameSeparateStatus(0b10)
                .setFrameSeparateNumber(parser.getFrameSeparateNumber())
                .build().toHexString();
    }


    public Builder newBuilder() {
        return new Builder(this);
    }

    public Builder newBuilder(Frame698.Builder frame698Builder) {
        return new Builder(this, frame698Builder);
    }

    @Override
    public Iterator<Frame698> iterator() {
        return (Iterator<Frame698>) newBuilder().build();
    }

    public static final class Builder /*extends Frame698.Builder*/ {
        private ArrayList<Frame698> frame698s;
        private Frame698.Builder frame698Builder;
        private int upperLimit; // 超过多少字节数会进行分帧
        private String linkDataStr;
        //int separateCount ;

        public Builder() {
            frame698s = new ArrayList<>();
            frame698Builder = new Frame698().newBuilder();
            upperLimit = Frame698.FRAME_SEPARATE;
            linkDataStr = "";
            //separateCount = 1;
        }

        public Builder(Frame698Separator frame698Separator) {
            frame698s = frame698Separator.frame698s;
            frame698Builder = frame698Separator.frame698Builder;
            upperLimit = frame698Separator.upperLimit;
            linkDataStr = frame698Separator.linkDataStr;
            //separateCount = frame698Separator.separateCount;
        }

        public Builder(Frame698Separator frame698Separator, Frame698.Builder frame698Builder) {
            this(frame698Separator);
            setFrame698Builder(frame698Builder);
        }

        public ArrayList<Frame698> build() {
            ArrayList<String> apduLinkStrs = getApduLinkStrs(linkDataStr, upperLimit * 2);

            if (apduLinkStrs.size() == 1) {
                getNormalFrame698s(apduLinkStrs);
            } else {
                getSeparateFrame698s(apduLinkStrs);
            }
            return frame698s;
        }

        private void getNormalFrame698s(ArrayList<String> apduLinkStrs) {
            frame698s.add(getNormalFrame(apduLinkStrs.get(0)));
        }

        private void getSeparateFrame698s(ArrayList<String> apduLinkStrs) {
            for (int i = 0; i < apduLinkStrs.size(); i++) {
                int separateStatus = getSeparateStatus(i, apduLinkStrs.size());
                frame698s.add(createFrame698(i, separateStatus, apduLinkStrs.get(i)));
            }
            // fixme 确认帧 应该是应答方 生成的，此处应该不需要添加。
            // 且分帧块号是实时生成的。
            // frame698s.add(1, createFrame698(0b00, 0b00, ""));
        }


        /**
         * 获取分帧格式域数据
         * bit15=0，bit14=0：表示分帧传输数据起始帧；
         * bit15=1，bit14=0：表示分帧传输确认帧（确认帧不包含APDU片段域）；
         * bit15=0，bit14=1：表示分帧传输最后帧；
         * bit15=1，bit14=1：表示分帧传输中间帧。
         *
         * @param i    当前帧在整条帧报文（分帧集合）中的位置
         * @param size 分帧总条数
         * @return int 分帧格式域数据
         */
        // 若是只分成了两帧，则不存在分帧传输帧，直接结束帧。
        private int getSeparateStatus(int i, int size) {
            return i == 0 ? 0b00 //
                    : i == size - 1 ? 0b01
                    : 0b11;
        }

        @NonNull
        private Frame698 createFrame698(int separateNumber, int separateStatus, String linkDataStr) {
            return frame698Builder
                    .setFrameSeparate(true)
                    .setFrameSeparateNumber(separateNumber)
                    .setFrameSeparateStatus(separateStatus)
                    .setLinkDataStr(linkDataStr)
                    .build();
        }

        /**
         * 获取不分帧报文（）
         *
         * @param s 链路层数据
         * @return Frame698 对象
         */
        private Frame698 getNormalFrame(String s) {
            return frame698Builder
                    .setFrameSeparate(false)
                    .setLinkDataStr(s)
                    .build();
        }

        /**
         * @param data   总链路数据
         * @param length 每段分帧 数据域字符串长度
         * @return ArrayList<String>   链路层数据集合
         */
        private ArrayList<String> getApduLinkStrs(String data, int length) {
            ArrayList<String> strList = new ArrayList<>();
            while (data.length() >= length) {
                strList.add(data.substring(0, length));
                data = data.substring(length, data.length());
            }
            if (data.length() > 0) {
                strList.add(data);
            }
            return strList;
        }

        // 多个 frame之间会有空格隔开
        // todo 可以用换行符隔开,但注意 toStrings 对应分隔符
        //   stringBuffer.append("\n");
        @Override
        public String toString() {
            if (isEmptyList(frame698s)) return "";
            StringBuilder stringBuffer = new StringBuilder();
            for (Frame698 frame698 : frame698s) {
                stringBuffer.append(frame698.toString());
                stringBuffer.append(FRAME_SEPARATOR);
            }
            return stringBuffer.toString().trim();
        }

        public String[] toStrings() {
            return toString().split(" ");
        }

        private boolean isEmptyList(List list) {
            return list == null || list.size() == 0;
        }


        /**
         * @param upperLimit 设置分帧上限
         * @return 此处将分帧上限最大值设置为 0x1800 字节
         */
        public Builder setFrameUpperLimit(@IntRange(from = 0, to = Frame698.FRAME_SEPARATE) int upperLimit) {
            this.upperLimit = upperLimit;
            return this;
        }

        public Builder setFrame698Builder(Frame698.Builder frame698Builder) {
            this.frame698Builder = frame698Builder;
            return this;
        }

        /**
         * 设置总链路数据层
         *
         * @param linkDataStr 链路层数据
         * @return Builder
         */
        public Builder setLinkDataStr(String linkDataStr) {
            this.linkDataStr = linkDataStr;
            return this;
        }


    }

    public Parser parse(String... frame698Strs) {
        return new Parser(frame698Strs);
    }


    public static final class Parser {
        private SparseArray<Frame698.Parser> frame698Parsers;
        private final String[] frame698Strs;

        public final int[] frame698ParseCode;
        public final boolean parserSuccess;
        private int frameSize;

        //a)采用串行通信方式实现本地数据传输时，在发送数据时，在有效数据帧前加4个FEH作为前导码。
        public Parser(String... frame698Strs) {
            this.frame698Strs = frame698Strs;
            frame698ParseCode = isNotEmpty(frame698Strs) ?
                    getParserCodeArr(frame698Strs)
                    : null;
            parserSuccess = frame698ParseCode != null // 不为null
                    && isParserSuccess(frame698ParseCode)  // 每一个帧的解析码都正常
                    && checkFrame698s(); // 所有帧地址一致，并且没有丢失帧


        }

        /**
         * 检查帧地址以及 这些帧是否能够组成一个完整的帧。
         *
         * @return boolean 是否能组合成完整帧
         */
        private boolean checkFrame698s() {
            // 检查地址域是否一致 以及分帧序号，分帧状态是否完全
            return checkAddress() && framesIsAll();

        }

        /**
         * 帧是否齐全，是否能完全组成完整报文
         *
         * @return true 所有报文能组成完整的帧， false 则是帧报文缺失。
         */
        private boolean framesIsAll() {
            frame698Parsers = getParsersSparseArray();
            frameSize = getFrameSize();
            // 若是获取帧个数为0，则说明帧不全
            return frameSize != 0 && isParserAllNotNull(frameSize);

        }


        /**
         * 根据现有的结束帧中 获取到的大小，从0到frameSize-1，若是接收完毕，则不会出现为空的情况
         * 出现null，便是说明，这些帧有所丢失
         *
         * @param frameSize 报文数量
         * @return true 全部的报文都能通过解析
         */
        private boolean isParserAllNotNull(int frameSize) {
            for (int i = 0; i < frameSize; i++) {
                Frame698.Parser parser = getParser(i);
                if (parser == null) return false;
            }
            return true;
        }

        /**
         * 根据结束帧 获取帧序号，帧序号表示 帧个数
         *
         * @return 结束帧报文上 记录的帧序号（其可以表示该组分帧总条数）
         */
        private int getFrameSize() {
            int frameSize = 0;
            for (int i = 0; i < frame698Parsers.size(); i++) {
                int key = frame698Parsers.keyAt(i);
                Frame698.Parser parser = getParser(key);
                // 结束帧 的序号
                if (parser != null && parser.getFrameSeparateStatus() == 0b01) {
                    frameSize = parser.getFrameSeparateNumber() + 1;
                    break;
                }
            }
            return frameSize;
        }

        /**
         * 从集合中取 数据，若为null ，则说明 并未存入，即表明帧接收不完全，
         *
         * @param pos 在多个698报文分帧集合中的文职
         * @return Frame698.Parser 698报文解析器
         */
        private Frame698.Parser getParser(int pos) {
            frame698Parsers = null == frame698Parsers
                    ? getParsersSparseArray()
                    : frame698Parsers;
            return frame698Parsers.get(pos, null);
        }

        /**
         * 将现有的帧，依照 序号为key，存入集合。后面会根据序号去获取，
         *
         * @return SparseArray<Frame698.Parser> 解析器集合
         */
        private SparseArray<Frame698.Parser> getParsersSparseArray() {

            SparseArray<Frame698.Parser> frame698Parsers = new SparseArray<>();
            for (String frame698Str : frame698Strs) {
                Frame698.Parser parser = new Frame698.Parser(frame698Str);
                if (parser.getFrameSeparateStatus() != 0b10) { // 确认帧
                    frame698Parsers.put(parser.getFrameSeparateNumber(), parser);
                }
            }
            return frame698Parsers;
        }

        /**
         * 校验这些帧服务地址是否相同
         *
         * @return true，相同 false 不同
         */
        private boolean checkAddress() {
            HashSet<String> set = new HashSet<>();
            for (String frame698Str : frame698Strs) {
                set.add(new Frame698.Parser(frame698Str).getServerAddress());
            }
            return set.size() == 1;
        }

        /**
         * 根据所有分帧的解析码 来验证 所有分帧是否 能够成功解析。
         * 由于698 帧解析码的错误码均小于  0
         * 故 若所有分帧的解析码相加 不为0 则视为失败。
         *
         * @param frame698ParseCode 698报文解析码。0为解析成功
         * @return true 正常 false 不正常。
         */
        private boolean isParserSuccess(int[] frame698ParseCode) {
            int sum = 0;
            for (int parserCode : frame698ParseCode) {
                sum += parserCode;
            }
            return sum == 0;
        }

        private boolean isNotEmpty(String[] frame698Strs) {
            return frame698Strs != null && frame698Strs.length > 0;
        }

        /**
         * 对每个分帧进行解析 并获取其对应的解析码。  0为正常，其他（小于0）均为失败
         *
         * @param frame698Strs 698报文分帧数组，多条数据
         * @return int[] 获取每一个698报文的解析码。全部为0 才算ok
         */
        private int[] getParserCodeArr(String[] frame698Strs) {
            int length = frame698Strs.length;
            int[] frame698ParseCode = new int[length];
            for (int i = 0; i < length; i++) {
                int parseCode = new Frame698.Parser(frame698Strs[i]).parseCode;
                frame698ParseCode[i] = parseCode;
            }
            return frame698ParseCode;
        }


        /**
         * 链路数据层
         *
         * @return String 根据所有的分帧，组装完后
         * 所有的分帧的链路数据合并后的字符串
         */
        public String getLinkDataStr() {
            if (!parserSuccess) return "";
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < frameSize; i++) {
                stringBuilder.append(getParser(i).getLinkDataStr());
            }
            return stringBuilder.toString();
        }

        /*public IAPDUParser parseAPDU() {
            if (!parserSuccess) return null;
            return APDU.parse(getLinkDataStr(), true);
        }*/

    }
}
