package kr.ac.kmu.ncs.restview;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NCS on 2016-11-16.
 */

public class DataParser {
    public void parseQuery(String index, Map parameters){
        if(index != null){
            //String pairs[] = query.split("[&]");
            String pairs[] = new JsonController().getDataById(index).split("[&]");
            HashMap<String, String> childParameters = new HashMap<>();
            if(pairs == null) return;

            for(String pair : pairs){
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
        }
    }

    public String getIR(String index, HashMap parameters){
        //index값으로 해당 칸 정보 가져오기
        if(!parameters.isEmpty()){
            HashMap<String, String> childParameters = (HashMap<String, String>) parameters.get(index);
            int state = Integer.parseInt(childParameters.get("IR").toString());

            switch (state){
                case 0:
                    return "Sufficient";
                case 1:
                    return "Unsufficient";
            }
        }
        return "";
    }

    public void getOverviewData(Map parameters){
        if(!parameters.isEmpty()){
            //순환하며 데이터 수집

        }
    }
}
