package kr.ac.kmu.ncs.restview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Eddie Sangwon Kim on 2016-11-09.
 */

public class JsonController {
    JSONObject jsonData;

    public void setJson() {
        try {
            jsonData = new JSONObject(MyConstatns.contents);
        } catch (Exception e) {

        }
    }

    public String getDataById(String id) {
        setJson();
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonData.getJSONArray("datastreams");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (((JSONObject) (jsonArray.get(i))).getString("id").equals(id)) {
                        return ((JSONObject)(jsonArray.get(i))).getString("current_value");
                }
            }
        } catch (JSONException e) {
        }
        return "";
    }
}
