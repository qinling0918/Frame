package cepri.device.utils;

/**
 * Created by xuzl on 2016/10/31.
 */
public class LibInfo {


    /**
     * 获取so库的版本序号
     *
     * @return So库的版本序号
     */
    public native static int getVersion();

    /**
     * 获取so库的厂家编码
     *
     * @return So库的厂家编码
     */
    public native static int getCompany();
}
