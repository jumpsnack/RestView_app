package kr.ac.kmu.ncs.restview;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by NCS on 2016-11-16.
 */

public class DataParser {
    public void parseQuery(String query, Map parameters){
        if(query != null){
            String pairs[] = query.split("[&]");

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
                    parameters.put(key, value);
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public String getIR(int index, Map parameters){
        //index값으로 해당 칸 정보 가져오기
        if(!parameters.isEmpty()){
            int state = Integer.parseInt(parameters.get("IR").toString());

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
