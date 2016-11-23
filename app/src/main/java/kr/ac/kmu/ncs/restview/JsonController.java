package kr.ac.kmu.ncs.restview;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eddie Sangwon Kim on 2016-11-09.
 */

public class JsonController {
    private static String ARG_1 = "PIR";
    private static String ARG_2 = "IR";
    private static String ARG_3 = "BTN";

    public String getDataById(String id) {
        try {
            JSONObject jsonInput = new JSONObject(MyConstatns.contents);
            if(jsonInput != null){
                JSONObject parsedData = jsonInput.getJSONObject(id);
                if(parsedData.length() > 0 && parsedData != null){
                    String PIRdata = parsedData.getString(ARG_1);
                    String IRdata = parsedData.getString(ARG_2);
                    String BTNdata = parsedData.getString(ARG_3);

                    String result = ARG_1 + "=" + PIRdata + "&" + ARG_2 + "=" + IRdata + "&" + ARG_3 + "=" + BTNdata;

                    return result;
                }
            }
        } catch (JSONException e) {
        }
        return "";
    }
}
