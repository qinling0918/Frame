package com.hzwq.framelibrary.protocol698.data;


import android.support.annotation.IntRange;

import java.util.Locale;

import static com.hzwq.framelibrary.protocol698.data.COMDCB.BaudRate._115200bps;

/**
 * Created by qinling on 2018/5/11 18:22
 * Description:
 */
public class COMDCB extends Data {

    public static final COMDCB DEFAULT = new COMDCB(_115200bps, 0, 5, 1, 0);

    public BaudRate getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(BaudRate baudRate) {
        this.baudRate = baudRate;
    }

    public int getCheckBit() {
        return checkBit;
    }

    public COMDCB setCheckBit(@IntRange(from = 0, to = 2) int checkBit) {
        this.checkBit = checkBit;
        return this;
    }

    public int getDataBit() {
        return dataBit;
    }

    public COMDCB setDataBit(@IntRange(from = 5, to = 8) int dataBit) {
        this.dataBit = dataBit;
        return this;
    }

    public int getStopBit() {
        return stopBit;
    }

    public COMDCB setStopBit(@IntRange(from = 1, to = 2) int stopBit) {
        this.stopBit = stopBit;
        return this;
    }

    public int getStreamControl() {
        return streamControl;
    }

    public COMDCB setStreamControl(@IntRange(from = 0, to = 2) int streamControl) {
        this.streamControl = streamControl;
        return this;
    }

    /**
     * 波特率
     */
    public enum BaudRate {
        _300bps(0), _600bps(1), _1200bps(2),
        _2400bps(3), _4800bps(4), _7200bps(5),
        _9600bps(6), _19200bps(7), _38400bps(8),
        _57600bps(9), _115200bps(10), adaptive(255);

        int value;

        BaudRate(int value) {
            this.value = value;
        }
    }

    private BaudRate baudRate;
    /**
     * 校验位  ENUMERATED {无校验（0），奇校验（1），偶校验（2）}，
     */
    private int checkBit;
    /**
     * 数据位  ENUMERATED {5（5），6（6），7（7），8（8）}，
     */
    private int dataBit;
    /**
     * 停止位  ENUMERATED {1（1），2（2）}，
     */
    private int stopBit;
    /**
     * 流控    ENUMERATED {无(0)，硬件(1)，软件(2)}
     */
    private int streamControl;

    public COMDCB(BaudRate baudRate,
                  @IntRange(from = 0, to = 2) int checkBit,
                  @IntRange(from = 5, to = 8) int dataBit,
                  @IntRange(from = 1, to = 2) int stopBit,
                  @IntRange(from = 0, to = 2) int streamControl) {
        this.baudRate = baudRate;
        this.checkBit = checkBit;
        this.dataBit = dataBit;
        this.stopBit = stopBit;
        this.streamControl = streamControl;
    }


    @Override
    public int dataType() {
        return 95;
    }

    @Override
    public String toHexString() {
        return String.format(Locale.CHINA, "%02x%02x%02x%02x%02x"
                , baudRate.value, checkBit, dataBit, stopBit, streamControl);
    }
}
