package kr.ac.kmu.ncs.restview;

import android.os.AsyncTask;
import android.util.Log;

import com.temboo.Library.Xively.ReadWriteData.ReadFeed;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Eddie Sangwon Kim on 2016-11-05.
 */
public class HttpConnexion extends AsyncTask<Void, String, String> {
    final static private String TAG = "HTTPCONNEXION";
    private String contents = "";
    @Override
    protected String doInBackground(Void... voids) {
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
        } catch (Exception e){
            e.printStackTrace();
        }

        return contents;
    }

}
