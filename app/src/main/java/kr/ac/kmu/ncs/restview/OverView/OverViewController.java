package kr.ac.kmu.ncs.restview.OverView;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import kr.ac.kmu.ncs.restview.DataParser;
import kr.ac.kmu.ncs.restview.MyConstatns;
import kr.ac.kmu.ncs.restview.R;

/**
 * Created by NCS on 2016-11-16.
 */

public class OverViewController {
    View view;

    ImageView imgSuffMen;
    ImageView imgSuffWomen;

    ImageView imgMan;
    ImageView imgWomen;

    TextView tvTemp;
    TextView tvHum;

    public OverViewController(View view) {
        this.view = view;

        initUI();
        imageThread();
    }

    void initUI() {
        this.imgSuffMen = (ImageView) view.findViewById(R.id.img_suff_men);
        this.imgSuffWomen = (ImageView) view.findViewById(R.id.img_suff_women);

        this.imgSuffMen.setColorFilter(Color.parseColor("#ffffff"));
        this.imgSuffWomen.setColorFilter(Color.parseColor("#ffffff"));

        this.imgMan = (ImageView) view.findViewById(R.id.img_men);
        this.imgWomen = (ImageView) view.findViewById(R.id.img_women);

        this.imgMan.setColorFilter(Color.parseColor("#ffffff"));
        this.imgWomen.setColorFilter(Color.parseColor("#ffffff"));

        this.tvHum = (TextView) view.findViewById(R.id.tv_hum);
        this.tvTemp = (TextView) view.findViewById(R.id.tv_temp);
    }

    void imageThread() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final DataParser parser = new DataParser();
                while (true) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (MyConstatns.contents.length() > 0 && !MyConstatns.parentParameter.isEmpty()) {
                                //parser.getOverviewData 여기서 수행 후 이미지 변경
                                parser.setOverviewData(MyConstatns.parentParameter);

                                setTemperature();
                                setHumidity();

                                collectPIRData(MyConstatns.parentParameter);
                                collectIRData(MyConstatns.parentParameter);
                            }
                        }
                    });
                    try {
                        Thread.sleep(MyConstatns.REQUEST_TIME_INTERVAL / 3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    void setTemperature() {
        tvTemp.setText(MyConstatns.temp + " °c");
    }

    void setHumidity() {
        tvHum.setText(MyConstatns.hum + " %");
    }

    void collectPIRData(Map parameters) {
        if (!parameters.isEmpty()) {
            Set<String> keys = parameters.keySet();

            int pirValue = 0;
            int dataCount = parameters.size();
            for (String key : keys) {
                HashMap<String, String> childParameter = (HashMap<String, String>) parameters.get(key);
                pirValue += Integer.parseInt(childParameter.get("PIR"));
            }

            if (pirValue / dataCount == 1) {
                setFullOccupiedState();
            } else {
                setVacantState();
            }
        }
    }

    void collectIRData(Map parameters) {
        if (!parameters.isEmpty()) {
            Set<String> keys = parameters.keySet();

            int irValue = 0;
            int dataCount = parameters.size();
            for (String key : keys) {
                HashMap<String, String> childParameters = (HashMap<String, String>) parameters.get(key);
                irValue += Integer.parseInt(childParameters.get("IR"));
            }

            if (irValue / dataCount == 1) {
                setInsufficientState();
            } else {
                setSufficientState();
            }
        }
    }

    void setFullOccupiedState() {
        imgMan.setColorFilter(Color.parseColor("#ff0000"));
    }

    void setVacantState() {
        imgMan.setColorFilter(Color.parseColor("#00ff00"));
    }

    void setInsufficientState() {
        imgSuffMen.setColorFilter(Color.parseColor("#ff0000"));
    }

    void setSufficientState() {
        imgSuffMen.setColorFilter(Color.parseColor("#00ff00"));
    }
}
