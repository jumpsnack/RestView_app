package kr.ac.kmu.ncs.restview;

/**
 * Created by NCS-KSW on 2016-11-04.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

public class ReadXivelyService extends Service {
    final static private String TAG = "ReadXivelyService";

    private ArrayList<Messenger> clients = new ArrayList<>();
    private Messenger messenger = new Messenger(new IncomingHander());
    private Handler thread = new Handler();
    private Runnable runnable = null;
    private boolean isActivityAlive = false;
    private String contents;

    class IncomingHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstatns.REGIST_CLIENT:
                    clients.add(msg.replyTo);
                    isActivityAlive = true;
                    getXivelyDatastream();
                    break;
                case MyConstatns.UNREGIST_CLIENT:
                    clients.remove(msg.replyTo);
                    isActivityAlive = false;
                    break;
            }
        }
    }

    private void getXivelyDatastream() {
        runnable = new Runnable() {
            @Override
            public void run() {
                HttpConnexion httpConnexion = new HttpConnexion();
                contents = "";
                try {
//                    contents = httpConnexion.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
                    contents = httpConnexion.execute().get();
                } catch (Exception e) {
                    contents = "System down...:(";
                    Log.d(TAG, e.getMessage());
                }
                for (int i = 0; i < clients.size(); i++) {
                    try {
                        clients.get(i).send(Message.obtain(null, MyConstatns.SUCESSFUL, contents));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if (isActivityAlive) {
                    thread.postDelayed(runnable, MyConstatns.REQUEST_TIME_INTERVAL);
                }
                httpConnexion.cancel(true);
            }
        };
        thread.post(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
