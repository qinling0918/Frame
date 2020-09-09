package com.sgcc.pda.frame;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cepri.device.utils.HardWareManager;
import cepri.device.utils.ISecurityUnit;
import cepri.device.utils.SecurityUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         HardWareManager.getInstance().bind(this);
       // final ISecurityUnit securityUnit = HardWareManager.getInstance().getSecurityUnit();
       // Log.e("ServiceConnection ", "onCreate: " + (securityUnit == null));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Log.e("ServiceConnection  ", "init result :" + SecurityUnit.Init());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } /*catch (RemoteException e) {
                    e.printStackTrace();
                }*/
            }
        }).start();
    }
       /* try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Log.e("ServiceConnection  ", "init result :"+securityUnit.Init());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // securityUnit.Init();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
      //  Log.e("ServiceConnection  ", "init result :"+SecurityUnit.Init());
    }
}
