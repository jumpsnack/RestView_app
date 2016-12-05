package kr.ac.kmu.ncs.restview.DetailView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.kmu.ncs.restview.DataParser;
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
    private ListViewAdapter listViewAdapter;
//    private ArrayAdapter<String> listItems;

    public DetailViewController(final View view) {
        this.view = view;
        initUI();
        listThread();

    }

    private void initUI() {
        listView = (ListView) view.findViewById(R.id.lv_detail);
        listViewAdapter = new ListViewAdapter(view.getContext());
        listView.setAdapter(listViewAdapter);

        for (int i = 0; i < 6; i++) {
            listViewAdapter.addItem("0", "Insufficient");
        }
    }

    private void listThread() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // final JsonController jsonController = new JsonController();
                final DataParser parser = new DataParser();
                while (true) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (MyConstatns.contents.length() > 0) {
                                // 무조건 한 번 수행


                                /**
                                 * @dsec : 화장실 칸에 따라 정보 인출
                                 * @arg1 : 화장실 칸
                                 * @arg2 : PIR 값
                                 * @arg3 : 휴지케이스 IR 값
                                 * */
                                for (int i = 1; i <= listViewAdapter.getCount(); i++) {
                                    if (i == 1) {
                                        parser.parseQuery(i+"", MyConstatns.parentParameter);
                                        listViewAdapter.changeItemValue((i-1), parser.getPIR(i+"", MyConstatns.parentParameter), parser.getIR(i+"", MyConstatns.parentParameter));
                                    } else if (i == 2) {
                                        parser.parseQuery(i+"", MyConstatns.parentParameter);
                                        listViewAdapter.changeItemValue((i-1), parser.getPIR(i+"", MyConstatns.parentParameter), parser.getIR(i+"", MyConstatns.parentParameter));
                                    } else {
                                        listViewAdapter.changeItemValue((i-1),  parser.getPIR((int)(Math.random()*2)+1+"", MyConstatns.parentParameter), parser.getIR((int)(Math.random()*2)+1+"", MyConstatns.parentParameter));
                                    }
                                }
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


    class ViewHolder {
        public TextView tvLabel;
        public ImageView ivState;
        public TextView tvState;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context ctx;
        private ArrayList<ListData> listDatas = new ArrayList<>();
        private Drawable imgOccupoed = view.getResources().getDrawable(R.drawable.img_occupied);
        private Drawable imgVacant = view.getResources().getDrawable(R.drawable.img_vacant);

        public ListViewAdapter(Context ctx) {
            this.ctx = ctx;
        }

        @Override
        public int getCount() {
            return listDatas.size();
        }

        @Override
        public Object getItem(int i) {
            return listDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_item, null);

                holder.tvLabel = (TextView) view.findViewById(R.id.tv_label);
                holder.ivState = (ImageView) view.findViewById(R.id.iv_state);
                holder.tvState = (TextView) view.findViewById(R.id.tv_state);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            ListData mData = listDatas.get(i);

            holder.ivState.setImageDrawable(mData.mPIRState);
            if(i<3){
            holder.tvLabel.setText("남자 " + (i+1) + "번째 칸");

            }else{
                int index = (i+1)%3;
                if(index == 0) index = 3;
                holder.tvLabel.setText("여자 " + index + "번째 칸");
            }
            holder.tvState.setText(mData.mIR);

            return view;
        }

        public void addItem(String pir, String ir) {
            ListData addInfo = new ListData();


            addInfo.mPIR = pir;
            addInfo.mIR = ir;

           /* switch (state) {
                case 0:
                    addInfo.mPIRState = imgVacant;
                    break;
                default:
                    addInfo.mPIRState = imgOccupoed;
                    break;
            }*/

            switch (Integer.parseInt(pir)) {
                case 0:
                    addInfo.mPIRState = imgVacant;
                    break;
                default:
                    addInfo.mPIRState = imgOccupoed;
                    break;
            }

            listDatas.add(addInfo);
            dataChange();
        }

        public void changeItemValue(int index, String pir, String ir) {
            ListData listData = listDatas.get(index);

            listData.mPIR = pir;
            listData.mIR = ir;
           /* switch (state) {
                case 0:
                    addInfo.mPIRState = imgVacant;
                    break;
                default:
                    addInfo.mPIRState = imgOccupoed;
                    break;
            }*/

            switch (Integer.parseInt(pir)) {
                case 0:
                    listData.mPIRState = imgVacant;
                    break;
                default:
                    listData.mPIRState = imgOccupoed;
                    break;
            }
            dataChange();
        }

        public void remove(int position) {
            listDatas.remove(position);
            dataChange();
        }

        public void dataChange() {
            listViewAdapter.notifyDataSetChanged();
        }
    }
}
