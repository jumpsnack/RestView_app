package kr.ac.kmu.ncs.restview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.temboo.Library.Xively.ReadWriteData.ReadFeed;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    ReadXively readXively;

    TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHello = (TextView) findViewById(R.id.text_view);

        readXively = new ReadXively();
        readXively.setDaemon(true);
        readXively.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String contents = (String) msg.obj;
                    tvHello.setText(contents);
            }
        }
    };

    class ReadXively extends Thread {
        final static private String TAG = "ReadXively";

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

                    Thread.sleep(4000);


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


}
