package cepri.device.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by qinling on 2019/5/30 14:31
 * Description:
 */
public class HardWareManager {
    public static HardWareManager getInstance() {
        return SingleTon.INSTANCE;
    }

    private HardWareManager() {
    }

    private static class SingleTon {
        private static final HardWareManager INSTANCE = new HardWareManager();
    }


    public boolean bind(Context context) {
        //如果没有绑定 则先进行aidl服务绑定
         if(!bindflag){

       // Intent intent = new Intent();
      //  intent.setComponent(new ComponentName("com.sgcc.safeunitupgradetool", "SecurityUnitService"));


        //CountDownLatch countDownLatch = new CountDownLatch(1);
        Intent intent = new Intent("com.sgcc.pda.hardware.AIDL_SECURITY_UNIT_SERVICE");
        intent.setPackage("com.sgcc.safeunitupgradetool");
             bindflag = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.e("ServiceConnection ", "bind reslut: "+ bindflag+"");
        /*          try {
  Intent intent = new Intent("com.sgcc.pda.hardware.AIDL_SECURITY_UNIT_SERVICE");
  intent.setPackage("com.sgcc.pda.hardware");
                bindflag = context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
            } catch (SecurityException e) {
                e.printStackTrace();
            }*/
        }
        return bindflag;
    }

    public boolean isRemoteServiceAvailable() {
        return null != connection && null != securityUnit && securityUnit.asBinder().isBinderAlive();
    }


    public HashMap<String, IBinder> getBinders() {
        return binders;
    }

    public ISecurityUnit getSecurityUnit() {
       /* IBinder binder = getBinders().get(
                new ComponentName("com.sgcc.pda.hardware", "SecurityUnitService")
                        .flattenToString());

        return  ISecurityUnit.Stub.asInterface(binder);*/
      // if (isRemoteServiceAvailable())
       return securityUnit;
    }

    HashMap<String, IBinder> binders = new HashMap<>();
    //  HashMap<String, IInterface> interfaces = new HashMap<>();
   static ISecurityUnit securityUnit ;
    private boolean bindflag;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("ServiceConnection ", name.flattenToString()
                    + "service== null: " +(service== null ));
            binders.put(name.flattenToString(), service);
            securityUnit = ISecurityUnit.Stub.asInterface(service);
            bindflag = true;
            // interfaces.put(name.flattenToString(), service);
            Log.e("ServiceConnection ", name.flattenToString()
                    + "service.isBinderAlive(): " + service.isBinderAlive());


            try {
                Log.e("ServiceConnection  ", "init result :"+securityUnit.Init());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("ServiceConnection  ", "init result :"+SecurityUnit.Init());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            securityUnit = null;
            bindflag = false;
        }
    };


}
