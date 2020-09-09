package cepri.device.utils;



/**
 * Created by xuzl on 2016/10/31.
 */
public class RESAM {


    /**
     * R-ESAM初始化，包括打开电源和通信端口
     *
     * @return 0:成功  其它：错误号
     */
    public native static int Init();

    /**
     * R-ESAM通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public native static int DeInit();

    /**
     * 清空R-ESAM的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int ClearSendCache();

    /**
     * 清空R-ESAM的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int ClearRecvCache();

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率  支持 300、600、1200、2400、4800、9600、19200、38400、57600、115200 等
     * @param databits 数据位  5:5个数据位，6:6个数据位，7:7个数据位，8:8个数据位
     * @param parity 校验位，0:无校验，1:奇校验，2:偶校验
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     *                  * @param blockmode 阻塞模式。0为无阻塞，1为阻塞
     * @return 0:成功 其它：错误号
     */
    public native static int Config(int baudrate, int databits, int parity, int stopbits,int blockmode);

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public native static int SendData(byte[] data, int offset, int length);

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public native static int RecvData(byte[] data, int offset,int count);

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public native static int SetTimeOut(int direction, int timeout);

    /***************  1.0 之后多出来的     **********************/

    /**
     * R-ESAM复位模块
     *
     * @return 0:成功  其它：错误号
     */
    @SuppressWarnings({"unchecked","version"})
    public native static int Reset();

    /**
     *
     * @param mode SPI模式。0，1，2，3分别对应模式0，1，2，3
     * @param speed SPI时钟。单位为Hz，范围为0－5M。
     * @param halfword 传输模式。 0为8位，1为16位
     * @return 0:成功  其它：错误号
     */
    @SuppressWarnings({"unchecked","version"})
    public native static int SpiConfig(int mode, int speed, int halfword);
}
