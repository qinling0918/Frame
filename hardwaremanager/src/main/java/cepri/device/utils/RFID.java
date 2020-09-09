package cepri.device.utils;

/**
 * Created by ywj on 2018/6/21.
 * 高频RFID操作接口
 */
@SuppressWarnings("unused")
public class RFID {


    /**
     * 发送缓冲区
     */
    private static byte[] sendBuffer;

    /**
     * 打开高频RFID
     *
     * @return 打开成功返回 0；打开失败返回 其它值
     */
    public native static int Init();

    /**
     * 关闭高频RFID
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int DeInit();

    /**
     * 复位高频RFID
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int Reset();

    /**
     * 清空高频RFID的发送缓存
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int ClearSendCache();

    /**
     * 清空高频RFID的接收缓存
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int ClearRecvCache();

    /**
     * 设置与高频RFID模块的通讯参数
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
     * 使用RFID 模块读取标签的UID
     *
     * @return 读取到的 UID，失败返回 0 字节数组
     */
    public native static byte[] ReadUid();

    /**
     * 向高频RFID模块发送命令
     *
     * @param data   发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 成功返回0，端口未打开返回1，发送出错返回2
     */
    public static int SendData(byte[] data, int offset, int length) {
        sendBuffer = new byte[length];
        System.arraycopy(data, offset, sendBuffer, 0, length);
        return 0;
    }

    /**
     * 接收高频RFID模块返回的数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @param length 接收数据的可用长度
     * @return 成功接收的字节数
     */
    public static int RecvData(byte[] data, int offset, int length) {
        if (sendBuffer == null)
            return 0;

        byte[] recv = jniPenetrate(sendBuffer, 0, sendBuffer.length, length, 6000, 100);
        if (recv.length > 0) {
            System.arraycopy(recv, 0, data, offset, recv.length);
        }
        return recv.length;
    }

    /**
     * 与 RFID 13.56M 标签透传通讯字节数组（空中命令）
     *
     * @param sendFrame    发送的数据字节数组
     * @param offset       发送数据在字节数组中的起始位置
     * @param length       发送数据的长度
     * @param maxLength    预计返回数据的最大长度
     * @param timeout      接收超时时间，单位毫秒
     * @param timeoutBlock 数据块接收超时，该值不应大于timeout，单位毫秒，设置为0表示使用默认值，默认为1000
     * @return 接收到的数据内容
     */
    private native static byte[] jniPenetrate(byte[] sendFrame, int offset, int length,
                                              int maxLength, int timeout, int timeoutBlock);

    /**
     * 设置发送和接收数据的超时时间<br>
     * 该接口必须在 sendData 和 recvData 之前调用才可生效
     *
     * @param direction 传输方向，0: 发送，1: 接收
     * @param timeout   收发的超时时间，单位毫秒
     * @return 设置成功返回 0，失败返回 其它值
     */
    public native static int SetTimeOut(int direction, int timeout);

}
