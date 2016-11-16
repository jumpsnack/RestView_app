package kr.ac.kmu.ncs.restview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;

import kr.ac.kmu.ncs.restview.DetailView.DetailViewFragment;
import kr.ac.kmu.ncs.restview.OverView.OverViewFragment;

public class MainActivity extends FragmentActivity {

    private Messenger msgReceiver = new Messenger(new IncomingHandler());
    private Messenger msgSender = null;
    private boolean isServiceBounded = false;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            msgSender = new Messenger(iBinder);

            try {
                // Send a message to service for registered to this activity as new client
                Message msg = Message.obtain(null, MyConstatns.REGIST_CLIENT);
                msg.replyTo = msgReceiver;
                msgSender.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isServiceBounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                Message msg = Message.obtain(null, MyConstatns.UNREGIST_CLIENT);
                msg.replyTo = msgReceiver;
                msgSender.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isServiceBounded = false;
        }
    };

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstatns.SUCCESSFUL:
//                    String contents = (String) msg.obj;
                    MyConstatns.contents = (String) msg.obj;
                    Toast.makeText(getApplicationContext(), MyConstatns.contents, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

       if (isServiceBounded) {
            try {
                Message msg = Message.obtain(null, MyConstatns.UNREGIST_CLIENT);
                msg.replyTo = msgReceiver;
                msgSender.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            msgSender = null;
            isServiceBounded = false;
        }
        unbindService(serviceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isServiceBounded) {
            isServiceBounded = true;
            bindService(new Intent(this, ReadHttpService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(new DetailViewFragment(), "DETAIL");
        fragmentTransaction.add(new OverViewFragment(), "OVERVIEW");
        fragmentTransaction.commit();
    }


}
