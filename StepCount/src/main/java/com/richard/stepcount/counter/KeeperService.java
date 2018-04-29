package com.richard.stepcount.counter;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.richard.stepcount.StrongServicelInterface;
import com.richard.stepcount.utils.ProcessUtil;


/**
 * Created by Administrator on 2017/4/7.
 */

public class KeeperService extends Service {
    private int NOTIFICATION_ID = 0x01;
    private String Process_Name = "com.richard.stepcount:StepService";
    private StrongServicelInterface stepServiceImp = new StrongServicelInterface.Stub(){
        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), StepService.class);
            getBaseContext().startService(i);
        }

        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), StepService.class);
            getBaseContext().stopService(i);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        openCounterService();
    }
    @Override
    public void onTrimMemory(int level){
//        Toast.makeText(getBaseContext(), "KeeperService onTrimMemory..."+level, Toast.LENGTH_SHORT).show();
//        startForeground(NOTIFICATION_ID, new Notification());
        openCounterService();
    }

    private void openCounterService() {
        boolean isRun = ProcessUtil.isProessRunning(this, Process_Name);
        if (isRun == false) {
            try {

                stepServiceImp.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
          }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(getBaseContext(), "启动 KeeperService", Toast.LENGTH_SHORT).show();
        openCounterService();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder)stepServiceImp;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        openCounterService();
    }
}
