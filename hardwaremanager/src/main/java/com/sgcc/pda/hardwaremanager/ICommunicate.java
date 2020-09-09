package com.sgcc.pda.hardwaremanager;

/**
 * 创建者 田汉鑫
 * 创建时间 2016/3/21
 * 定义与物理设备以及SOCKET设备通讯通用接口
 */
public interface ICommunicate {
    /**
     * 打开通讯端口
     *
     * @return 0-成功 其它-错误码
     */
    int open();

    /**
     * 关闭通讯端口
     *
     * @return 0-成功 其它-错误码
     */
    int close();

    /**
     * 获取通讯端口状态
     *
     * @return 0-成功 其它-错误码
     */
    int getStatus();

    /**
     * 从通讯端口中读取数据
     *
     * @param data    返回-数据内容
     * @param timeout 读取超时 毫秒
     * @return 0-成功 其它-错误码
     */
    int read(StringBuffer data, long timeout);

    /**
     * 向通讯端口中写入数据
     *
     * @param message 待写入的数据
     * @return 0-写入成功 其它-错误码
     */
    int write(String message, long timeout);
}
