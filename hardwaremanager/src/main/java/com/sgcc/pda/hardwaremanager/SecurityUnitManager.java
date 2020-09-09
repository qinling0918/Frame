package com.sgcc.pda.hardwaremanager;

/**
 * Created by qinling on 2019/5/30 10:02
 * Description:
 */
public class SecurityUnitManager {
    public static SecurityUnitManager getInstance() {
        return SingleTon.INSTANCE;
    }
    private SecurityUnitManager() {
    }
    private static class SingleTon {
        private static final SecurityUnitManager INSTANCE = new SecurityUnitManager();
    }


}
