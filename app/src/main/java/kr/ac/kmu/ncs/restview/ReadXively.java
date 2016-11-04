package kr.ac.kmu.ncs.restview;

/**
 * Created by NCS-KSW on 2016-11-04.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.temboo.Library.Xively.ReadWriteData.*;
import com.temboo.core.TembooSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;
import java.util.Vector;

public class ReadXively implements Runnable {



    Handler handler;

    public ReadXively() {
        handler = new Handler();
        handler.post(this);
    }

    @Override
    public void run() {

        while (true) {

        }
    }
}
