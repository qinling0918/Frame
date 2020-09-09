package cepri.device.utils;

import android.os.RemoteException;
import android.util.Log;

import static cepri.device.utils.HardWareManager.securityUnit;

/**
 * Created by xuzl on 2016/10/31.
 */
public class SecurityUnit {

    /**
     * 安全单元初始化，包括打开电源和通信
     *
     * @return 0:成功 其它：错误号
     */

    public static ISecurityUnit getHardwareManager() {
        if (securityUnit == null) {
            securityUnit = HardWareManager.getInstance().getSecurityUnit();
        }
        return securityUnit;
    }
    public  static int Init(){
        try {


            return getHardwareManager().Init();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

 /*   *//**
     * 安全单元通讯关闭和电源关闭
     *
     * @return 0：成功 其它：错误号
     *//*
    public  static int DeInit();

    *//**
     * 清空安全单元的发送缓存
     *
     * @return 0：成功 其它：错误号
     *//*
    public  static int ClearSendCache();

    *//**
     * 清空安全单元的接收缓存
     *
     * @return 0：成功 其它：错误号
     *//*
    public  static int ClearRecvCache();

    *//**
     * 设置通讯参数
     *
     * @param baudrate 通讯波特率
     * @param databits 数据位
     * @param parity   校验位 0为无校验，1为奇校验，2为偶校验，3为Mark校验，4为Space校验
     * @param stopbits 停止位 0为无停止位，1为1位停止位，2为2位停止位，3为1.5位停止位
     * @return 0:成功 其它：错误号
     *//*
    public  static int Config(int baudrate, int databits, int parity, int stopbits, int blockmode);

    *//**
     * 发送数据
     *
     * @param data   发送数据缓冲区
     * @param offset 偏移量
     * @param length 发送数据的目标长度
     * @return 成功返回0，端口未打开返回1，发送出错返回2
     *//*
    public  static int SendData(byte[] data, int offset, int length);

    *//**
     * 接收数据
     *
     * @param data   接收数据缓冲区
     * @param offset 偏移量
     * @return 成功接收的字节数
     *//*
    public  static int RecvData(byte[] data, int offset, int count);

    *//**
     * 设置发送和接收数据的超时时间
     *
     * @param direction 方向  发送；1,接收
     * @param timeout   超时时间 单位 ms(毫秒)
     * @return 0：成功 其它：错误号
     *//*
    public  static int SetTimeOut(int direction, int timeout);
*/
}
