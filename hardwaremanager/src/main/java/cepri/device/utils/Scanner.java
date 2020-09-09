package cepri.device.utils;



/**
 * Created by xuzl on 2016/10/31.
 */
public class Scanner {


    /**
     * 扫描头初始化，包括打开电源和通信端口
     *
     * @return 0:成功  其它：错误号
     */
    public native static int Init();

    /**
     * 扫描头通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public native static int DeInit();

    /**
     * 读取条码或者二维码
     *
     * @param timeout 超时时间
     * @param code    读取到的数据
     * @param offset  偏移量
     * @return 读取到的条码或者二维码的字节数据长度
     */
    public native static int Decode(int timeout, byte[] code, int offset);

}
