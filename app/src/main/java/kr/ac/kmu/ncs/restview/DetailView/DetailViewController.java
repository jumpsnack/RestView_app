package kr.ac.kmu.ncs.restview.DetailView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
//private static DetailViewController instance;
//    public static DetailViewController getInstance(){
//        if(instance == null){
//            instance = new DetailViewController();
//        }
//        return instance;
//    }

    private View view = null;

    private ListView listView;
    public ListViewAdapter listViewAdapter;

    public static Drawable imgOccupoed;
    public static Drawable imgVacant;
    public static Drawable imgPaper;

    public static Drawable imgRepair;
    public static Drawable imgEmpty;

    public static ListData extraListData;

    private DetailViewController() {
    }

    public void setView(View view) {
        this.view = view;
        initUI();
        listThread();
    }

    public DetailViewController(final View view) {
        this.view = view;
        imgOccupoed = view.getResources().getDrawable(R.drawable.img_occupied);
        imgVacant = view.getResources().getDrawable(R.drawable.img_vacant);
        imgPaper = view.getResources().getDrawable(R.drawable.img_paper);
        imgRepair = view.getResources().getDrawable(R.drawable.img_repair);
        imgEmpty = view.getResources().getDrawable(R.drawable.img_empty);
        initUI();
        listThread();

    }

    private void initUI() {
        listView = (ListView) view.findViewById(R.id.lv_detail);
        listViewAdapter = new ListViewAdapter(view.getContext());
        listView.setAdapter(listViewAdapter);

        for (int i = 0; i < 6; i++) {
            listViewAdapter.addItem("0", "Insufficient", "0", "0", "00:00:00");
        }
        listView.setOnItemClickListener(new onListItemClick());
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
                                        parser.parseQuery(i + "", MyConstatns.parentParameter);
                                        listViewAdapter.changeItemValue(
                                                (i - 1)
                                                , parser.getPIR(i + "", MyConstatns.parentParameter)
                                                , parser.getIR(i + "", MyConstatns.parentParameter)
                                                , parser.getPaperRequest(i + "", MyConstatns.parentParameter)
                                                , parser.getRepaireRequest(i + "", MyConstatns.parentParameter)
                                                , parser.getTimestamp(i + "", MyConstatns.parentParameter)
                                        );
                                    } else if (i == 2) {
                                        parser.parseQuery(i + "", MyConstatns.parentParameter);
                                        listViewAdapter.changeItemValue(
                                                (i - 1)
                                                , parser.getPIR(i + "", MyConstatns.parentParameter)
                                                , parser.getIR(i + "", MyConstatns.parentParameter)
                                                , parser.getPaperRequest(i + "", MyConstatns.parentParameter)
                                                , parser.getRepaireRequest(i + "", MyConstatns.parentParameter)
                                                , parser.getTimestamp(i + "", MyConstatns.parentParameter)
                                        );
                                    } else {
                                        //listViewAdapter.changeItemValue((i-1),  parser.getPIR((int)(Math.random()*2)+1+"", MyConstatns.parentParameter), parser.getIR((int)(Math.random()*2)+1+"", MyConstatns.parentParameter));
                                       /* listViewAdapter.changeItemValue(
                                                (i - 1)
                                                , parser.getPIR(1 + "", MyConstatns.parentParameter)
                                                , parser.getIR(1 + "", MyConstatns.parentParameter)
                                                , parser.getPaperRequest(1 + "", MyConstatns.parentParameter)
                                                , parser.getRepaireRequest(1 + "", MyConstatns.parentParameter)
                                                , parser.getTimestamp(1 + "", MyConstatns.parentParameter)
                                        );*/
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
        public ImageView ivPaper;
        public ImageView ivRepair;
    }

    public class ListViewAdapter extends BaseAdapter {
        private Context ctx;
        private ArrayList<ListData> listDatas = new ArrayList<>();


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
                holder.ivPaper = (ImageView) view.findViewById(R.id.iv_paper);
                holder.ivRepair = (ImageView) view.findViewById(R.id.iv_repair);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            ListData mData = listDatas.get(i);

            holder.ivState.setImageDrawable(mData.mPIRState);
            holder.ivPaper.setImageDrawable(mData.mPaperState);
            holder.ivRepair.setImageDrawable(mData.mRepairState);

            if (i < 3) {
                holder.tvLabel.setText("남자 " + (i + 1) + "번째 칸");

            } else {
                int index = (i + 1) % 3;
                if (index == 0) index = 3;
                holder.tvLabel.setText("여자 " + index + "번째 칸");
            }
            holder.tvState.setText(mData.mIR);

            return view;
        }

        public void addItem(String pir, String ir, String paper, String repair, String timestamp) {
            ListData addInfo = new ListData();


            addInfo.mPIR = pir;
            addInfo.mIR = ir;
            addInfo.mTimestamp = timestamp;
            addInfo.mPIREnterTime = "00:00:00";

            switch (Integer.parseInt(pir)) {
                case 0:
                    addInfo.mPIRState = imgVacant;
                    break;
                default:
                    addInfo.mPIRState = imgOccupoed;
                    break;
            }

            //As Paper Request
            switch (Integer.parseInt(paper)) {
                case 0:
                    addInfo.mPaper = paper;
                    //addInfo.mPaperState = imgEmpty;
                    break;
                case 1:
                    addInfo.mPaper = paper;
                    addInfo.mPaperState = imgPaper;
                    break;
            }

            //As Repair Request
            switch (Integer.parseInt(repair)) {
                case 0:
                    addInfo.mRepair = repair;
                    //addInfo.mRepairState = imgEmpty;
                    break;
                case 1:
                    addInfo.mRepair = repair;
                    addInfo.mRepairState = imgRepair;
                    break;
            }

            listDatas.add(addInfo);
            dataChange();
        }

        public void changeItemValue(int index, String pir, String ir, String paper, String repair, String timestamp) {
            ListData listData = listDatas.get(index);

            listData.mPIR = pir;
            listData.mIR = ir;
            listData.mTimestamp = timestamp;
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
                    listData.mPIREnterTime = "00:00:00";
                    listData.mPIRState = imgVacant;
                    break;
                default:
                    if (listData.mPIREnterTime.equals("00:00:00")) {
                        listData.mPIREnterTime = timestamp;
                    }
                    listData.mPIRState = imgOccupoed;
                    break;
            }

            //Check PAPER state
            switch (Integer.parseInt(paper)) {
                case 0:
                    // listData.mPaperState = imgEmpty;
                    break;
                case 1:
                    listData.mPaper = paper;
                    listData.mPaperState = imgPaper;
                    break;
            }

            //Check REPAIR state
            switch (Integer.parseInt(repair)) {
                case 0:
                    // listData.mRepairState = imgEmpty;
                    break;
                case 1:
                    listData.mRepair = repair;
                    listData.mRepairState = imgRepair;
                    break;
            }

            dataChange();
        }

        public void remove(int position) {
            listDatas.remove(position);
            dataChange();
        }

        public String getIRbyIndex(int index) {
            if (!listDatas.isEmpty()) {
                return listDatas.get(index).mIR;
            }
            return "0";
        }

        public String getPIRbyIndex(int index) {
            if (!listDatas.isEmpty()) {
                return listDatas.get(index).mPIR;
            }
            return "0";
        }

        public void resetPaperState(int index) {
            listDatas.get(index).mPaperState = imgEmpty;
        }

        public ListData getListDataByIndex(int index) {
            return listDatas.get(index);
        }

        public void dataChange() {
            listViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @desc : ListView event listener
     */
    class onListItemClick implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View listView, int index, long id) {
            if (!listViewAdapter.isEmpty()) {
                ListData listData = listViewAdapter.getListDataByIndex(index);
                Intent intent = new Intent(view.getContext(), DetailInfoActivity.class);

                intent.putExtra("IR", listData.mIR);
                intent.putExtra("PIR", listData.mPIR);
                intent.putExtra("PAPER", listData.mPaper);
                intent.putExtra("REPAIR", listData.mRepair);
                intent.putExtra("TIMESTAMP", listData.mTimestamp);
                intent.putExtra("PIRENTERTIME", listData.mPIREnterTime);
                extraListData = listData;
                intent.putExtra("index", index);
                view.getContext().startActivity(intent);
            }
        }
    }
}

