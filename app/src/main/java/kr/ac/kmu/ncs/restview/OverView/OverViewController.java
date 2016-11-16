package kr.ac.kmu.ncs.restview.OverView;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import kr.ac.kmu.ncs.restview.DataParser;
import kr.ac.kmu.ncs.restview.MyConstatns;
import kr.ac.kmu.ncs.restview.R;

/**
 * Created by NCS on 2016-11-16.
 */

public class OverViewController {
    View view;

    ImageView imgMan;
    ImageView imgWomen;

    public OverViewController(View view) {
        this.view = view;

        initUI();
        imageThread();
    }

    void initUI() {
        this.imgMan = (ImageView) view.findViewById(R.id.img_men);
        this.imgWomen = (ImageView) view.findViewById(R.id.img_women);

        this.imgMan.setColorFilter(Color.parseColor("#ffffff"));
        this.imgWomen.setColorFilter(Color.parseColor("#ffffff"));
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
                            if (MyConstatns.contents.length() > 0 && !MyConstatns.parameters.isEmpty()) {
                                //parser.getOverviewData 여기서 수행 후 이미지 변경
                            }
                        }
                    });
                    try {
                        Thread.sleep(MyConstatns.REQUEST_TIME_INTERVAL / 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
