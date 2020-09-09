package cepri.device.utils;

/**
 * Created by ywj on 2016/11/1.
 * 串口操作接口
 */

public class Serialport {


    /**
     * 打开串口
     *
     * @return 打开成功返回 0；打开失败返回 其它值
     */

    public native static int Init();

    /**
     * 关闭串口
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int DeInit();

    /**
     * 清空串口的发送缓存
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int ClearSendCache();

    /**
     * 清空串口的接收缓存
     *
     * @return 关闭成功返回 0，失败返回 其它值
     */
    public native static int ClearRecvCache();

    /**
     * 设置与串口模块的通讯参数
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
     * 通过串口模块发送命令
     *
     * @param data   发送的数据字节数组
     * @param offset 发送数据在字节数组中的起始位置
     * @param length 发送数据的长度
     * @return 成功返回0，端口未打开返回1，发送出错返回2
     */
    public native static int SendData(byte[] data, int offset, int length);

    /**
     * 接收串口模块返回的数据
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
}
