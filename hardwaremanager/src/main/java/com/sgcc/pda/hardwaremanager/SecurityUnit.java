package com.sgcc.pda.hardwaremanager;

/**
 * Created by qinling on 2019/5/27 16:12
 * Description:
 */
public class SecurityUnit implements  ICommunicate {

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


}
