package com.cepri.dev;

import android.util.Log;

/**
 * Created by xuzl on 2016/10/31.
 */
public class SecurityUnit {
    private static final String TAG = "SecurityUnit";

    public static int throwable(Throwable throwable){
        Log.e(TAG,SecurityUnit.class.getName()+throwable);
       return -1;
    }
    
    /**
     * 安全单元初始化，包括打开电源和通信
     *
     * @return 0:成功 其它：错误号  throwable(throwable) SO加载失败
     */
    public static int init_(){
        try {
            return init();
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 安全单元通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public  static int deInit_(){
        try {
            return deInit();
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 清空安全单元的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public  static int clearSendCache_(){
        try {
            return clearSendCache();
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 清空安全单元的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public static int clearRevCache_(){
        try {
            return clearSendCache();
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public static int config_(int baudrate, int databits, int parity, int stopbits){
        try {
            return config(baudrate,databits,parity,stopbits);
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public  static int sendData_(byte[] data, int offset, int length){
        try {
            return sendData(data,offset,length);
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public  static int recvData_(byte[] data, int offset){
        try {
            return recvData(data,offset);
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public  static int setTimeOut_(int direction, int timeout){
        try {
            return setTimeOut(direction,timeout);
        }catch (Throwable throwable){
            return throwable(throwable);
        }
    }
    

    /**
     * 安全单元初始化，包括打开电源和通信
     *
     * @return 0:成功 其它：错误号
     */
    public native static int init();

    /**
     * 安全单元通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     */
    public native static int deInit();

    /**
     * 清空安全单元的发送缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int clearSendCache();

    /**
     * 清空安全单元的接收缓存
     *
     * @return 0：成功 其它：错误号
     */
    public native static int clearRevCache();

    /**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     */
    public native static int config(int baudrate, int databits, int parity, int stopbits);

    /**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功发送数据的字节长度
     */
    public native static int sendData(byte[] data, int offset, int length);

    /**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     */
    public native static int recvData(byte[] data, int offset);

    /**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     */
    public native static int setTimeOut(int direction, int timeout);

}
