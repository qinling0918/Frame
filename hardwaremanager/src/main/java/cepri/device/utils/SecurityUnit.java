package cepri.device.utils;

import com.sgcc.pda.hardwaremanager.ICommunicate;


/**
 */
public class SecurityUnit implements ICommunicate {
    public static class Config{
        private static final int BUFFER_LENGTH = 512;

        private static final int READ_TIME_OUT = 15000;
        private static final int WRITE_TIME_OUT = 15000;
        private static final int BAUDRATE = 115200;

        private static final int DATA_BITS = 8;
        private static final int PARITY = 2;
        private static final int STOPBITS = 1;
        private static final int BLOCKMODE = 0;
    }
    @Override
    public int open() {
        return 0;
    }

    @Override
    public int close() {
        return 0;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public int read(StringBuffer data, long timeout) {
        return 0;
    }

    @Override
    public int write(String message, long timeout) {
        return 0;
    }

    /**
     * 打开安全单元
     *
     * @return 打开成功返回 0；打开失败返回 其它值
     */
    public native static int Init();
    /**
     * 关闭安全单元
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int DeInit();
    /**
     * 清空安全单元的发送缓存
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int ClearSendCache();
    /**
     * 清空安全单元的接收缓存
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int ClearRecvCache();
    /**
     * 设置与安全单元模块的通讯参数
     *
     * @param baudrate  波特率，支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param databits  数据位，5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param parity    校验位，0:无校验，1:奇校验，2:偶校验
     * @param stopbits  停止位，1:一个停止位，2:两个停止位
     * @param blockmode 阻塞模式。0为无阻塞，1为阻塞
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int Config(int baudrate, int databits, int parity, int stopbits, int blockmode);

    /**
     * 向安全单元模块发送命令
     *
     * @param data   发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 成功返回0，端口未打开返回1，发送出错返回2
     */
    public native static int SendData(byte[] data, int offset, int length);

    /**
     * 接收安全单元模块返回的数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @param length 接收数据的可用长度
     * @return 成功接收的字节数
     */
    public native static int RecvData(byte[] data, int offset, int length);

    /**
     * 设置发送和接收数据的超时时间<br>
     * 该接口必须在 sendData 和 recvData 之前调用才可生效
     *
     * @param direction 传输方向，0: 发送，1: 接收
     * @param timeout   收发的超时时间，单位毫秒
     * @return 设置成功返回 0，失败返回 其它值
     */
    public native static int SetTimeOut(int direction, int timeout);


    /*******************   1.0 之后多的      ********************/
    /**
     *
     * @param mode SPI模式。0，1，2，3分别对应模式0，1，2，3
     * @param speed SPI时钟。单位为Hz，范围为0－18M。
     * @param halfword 传输模式。 0为8位，1为16位。
     * @return  0 成功 其他失败
     */
    @SuppressWarnings({"unused","uncheck"})
    public native static int SpiConfig(/*@IntRange(from = 0,to=3)*/int mode, /*@IntRange(from = 0,to = 18)*/ int speed, /*@IntRange(from = 0,to=1)*/ int halfword);

    /**
     * 获取安全单元硬件版本号
     * @return  1  执行成功(安全单元 1.0)
     *           2  执行成功(安全单元 2.0)
     *
     *  SO库版本1.0 出现
     */
    @SuppressWarnings({"unused","uncheck"})
    public native static float GetVersion();


}
