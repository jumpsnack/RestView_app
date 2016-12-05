package kr.ac.kmu.ncs.restview;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by NCS on 2016-11-16.
 */

public class DataParser {
    public void parseQuery(String index, Map parameters) {
        if (index != null) {
            String pairs[] = MyConstatns.contents.split("[&]");
            if (pairs != null) {
                for (String pair : pairs) {
                    String param[] = pair.split("[=]");//[0] 1 [1]H/10,T/20
                    HashMap<String, String> childHash = new HashMap<String, String>();

                    if (param[0].equalsIgnoreCase("cmd")) {
                        if (param[1].equalsIgnoreCase("clear")) {
                            parameters.clear();
                        } else if (param[1].equalsIgnoreCase("exit")) {
                            System.exit(1);
                        }
                        return;
                    }

                    for (String body : param) {
                        String mass[] = body.split("[,]");//[0] H/10 [1] T/20

                        if (mass.length > 1) {
                            for (String keys : mass) {
                                String values[] = keys.split("[/]"); // [0] H [1] 20

                                String key = null;
                                String value = null;

                                try {
                                    if (values.length > 0) {
                                        key = URLDecoder.decode(values[0], System.getProperty("file.encoding"));
                                    }

                                    if (values.length > 1) {
                                        value = URLDecoder.decode(values[1], System.getProperty("file.encoding"));
                                    }

                                    childHash.put(key, value);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    parameters.put(param[0], childHash);
                }
            }
            ///////
          /*  for(String pair : pairs){
                String param[] = pair.split("[=]");

                String key = null;
                String value = null;

                try {
                    if(param.length > 0){
                        key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
                    }

                    if(param.length > 1){
                        value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
                    }

                    if(key.equalsIgnoreCase("cmd")){
                        if(value.equalsIgnoreCase("clear")){
                            parameters.clear();
                        } else if (value.equalsIgnoreCase("exit")){
                            System.exit(1);
                        }
                        return ;
                    }
                    childParameters.put(key, value);
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            parameters.put(index, childParameters);
        }*/
        }
    }

    public String getIR(String index, HashMap parameters) {
        //index값으로 해당 칸 정보 가져오기
        if (!parameters.isEmpty()) {
            HashMap<String, String> childParameters = (HashMap<String, String>) parameters.get(index);
            int state = Integer.parseInt(childParameters.get("IR").toString());

            switch (state) {
                case 0:
                    return "Sufficient";
                case 1:
                    return "Unsufficient";
            }
        }
        return "";
    }

    public String getPIR(String index, HashMap parameters){
        if(!parameters.isEmpty()){
            HashMap<String, String> childParameters = (HashMap<String, String>) parameters.get(index);
            int state = Integer.parseInt(childParameters.get("PIR").toString());

            switch (state){
                case 0:
                    return "0";

                case 1:
                    return "1";
            }
        }
        return "0";
    }

    public void setOverviewData(Map parameters) {
        if (!parameters.isEmpty()) {
            //순환하며 데이터 수집
            Set<String> keys = parameters.keySet();

            for(String key : keys){
                HashMap<String, String> childParameters = (HashMap<String, String>) parameters.get(key);
                MyConstatns.temp = (int)Double.parseDouble(childParameters.get("Temperature"));
                MyConstatns.hum = (int)Double.parseDouble(childParameters.get("Humidity"));

            }
        }
    }
}
