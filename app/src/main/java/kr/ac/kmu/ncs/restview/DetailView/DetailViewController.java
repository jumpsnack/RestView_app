package kr.ac.kmu.ncs.restview.DetailView;

import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import kr.ac.kmu.ncs.restview.MyConstatns;
import kr.ac.kmu.ncs.restview.R;

/**
 * Created by Eddie Sangwon Kim on 2016-11-05.
 */
public class DetailViewController {
//    static private DetailViewController instance = new DetailViewController();

//    private DetailViewController() {
//    }
//
//    ;
//
//    static public DetailViewController getInstance() {
//        return instance;
//    }

    private View view = null;

    private ListView listView;
    private ArrayAdapter<String> listItems;

    public DetailViewController(View view) {
        this.view = view;
        initUI();
        final Handler handler = new Handler();
        new Thread(new Runnable(){
            @Override
            public void run() {
                while (true) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listItems.clear();
                            listItems.add(MyConstatns.contents);
                        }
                    });
                    try {
                        Thread.sleep(MyConstatns.REQUEST_TIME_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initUI() {
        listView = (ListView) view.findViewById(R.id.lv_detail);
        listItems = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(listItems);
    }
}
