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

public class MainActivity extends FragmentActivity {

    TextView tvHello;

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
                case MyConstatns.SUCESSFUL:
//                    String contents = (String) msg.obj;
                    MyConstatns.contents = (String) msg.obj;
                    tvHello.setText(MyConstatns.contents);
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

        tvHello = (TextView) findViewById(R.id.text_view);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(new DetailViewFragment(), "DETAIL");
        fragmentTransaction.add(new OverViewFragment(), "OVERVIEW");
        fragmentTransaction.commit();

//        readXivelyService = new ReadHttpService();
//        readXivelyService.setDaemon(true);
//        readXivelyService.start();
    }






/*
    class ReadHttpService extends Thread {
        final static private String TAG = "ReadHttpService";

        private String contents;

        @Override
        public void run() {
            while (true) {
                contents = "";
                try {

                    // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
                    TembooSession session = new TembooSession("jpsn", "myFirstApp", "CNezc9O6o5FfrCZsq3FwniJ3LIdgQlTP");

                    ReadFeed readFeedChoreo = new ReadFeed(session);

                    // Get an InputSet object for the choreo
                    ReadFeed.ReadFeedInputSet readFeedInputs = readFeedChoreo.newInputSet();

                    // Set inputs
                    readFeedInputs.set_APIKey("pzRV0MjPrEFfOT4zHAdSEkJwWvt9xjiOJvCJZkvYSoruMvgk");
                    readFeedInputs.set_FeedID("46267484");

                    // Execute Choreo
                    ReadFeed.ReadFeedResultSet readFeedResults = readFeedChoreo.execute(readFeedInputs);
                    Log.d(TAG, readFeedResults.get_Response());

                    JSONObject response = new JSONObject(readFeedResults.get_Response());


                    TimeZone tz = TimeZone.getTimeZone("KST");
                    Calendar cal = Calendar.getInstance(tz);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    sdf.setCalendar(cal);
                    cal.setTime(sdf.parse(response.getString("updated")));
                    Date date = cal.getTime();
                    contents += "[ " + date + " ]\n";

                    JSONArray datastream = response.getJSONArray("datastreams");
                    Log.d(TAG, datastream.length() + "");

                    for (int i = 0; i < datastream.length(); i++) {
                        contents += datastream.getJSONObject(i).get("id") + "\n>>";
                        contents += datastream.getJSONObject(i).get("current_value") + "\n\n";
                    }

                    Thread.sleep(6000);


                } catch (Exception e) {
                    contents = "System down...:(";
                    Log.d(TAG + " Exception", e.getLocalizedMessage());
                }
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = contents;
                handler.sendMessage(msg);
            }
        }
    }
    */
}
