package kr.ac.kmu.ncs.restview;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Eddie Sangwon Kim on 2016-11-05.
 */
public class HttpConnexion extends AsyncTask<String, String, String> {
    final static private String TAG = "HTTPCONNEXION";
    private String contents = "";
    URL url;
    HttpURLConnection conn;
    String rcvData;

    @Override
    protected String doInBackground(String... strings) {
        try {
            // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
            /// TembooSession session = new TembooSession("jpsn", "myFirstApp", "CNezc9O6o5FfrCZsq3FwniJ3LIdgQlTP");

            ///  ReadFeed readFeedChoreo = new ReadFeed(session);

            // Get an InputSet object for the choreo
            ///    ReadFeed.ReadFeedInputSet readFeedInputs = readFeedChoreo.newInputSet();

            // Set inputs
            ///   readFeedInputs.set_APIKey("pzRV0MjPrEFfOT4zHAdSEkJwWvt9xjiOJvCJZkvYSoruMvgk");
            ///  readFeedInputs.set_FeedID("46267484");

            // Execute Choreo
            ///   ReadFeed.ReadFeedResultSet readFeedResults = readFeedChoreo.execute(readFeedInputs);
            ///   Log.d(TAG, readFeedResults.get_Response());

            /// JSONObject response = new JSONObject(readFeedResults.get_Response());

            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            rcvData = rcvFromServer();
            if (rcvData == null) return "none";


        } catch (Exception e) {
            e.printStackTrace();
        }

        return rcvData;
    }

    @Override
    protected void onPostExecute(String s) {

    }

    private String rcvFromServer() {
        try {
            conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder result = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    result.append(line);
                }

                Log.w(TAG, result.toString());

                return result.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        } finally {
            conn.disconnect();
        }
        return "none";
    }
}
