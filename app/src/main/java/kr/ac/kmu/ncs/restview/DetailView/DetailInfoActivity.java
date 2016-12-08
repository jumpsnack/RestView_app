package kr.ac.kmu.ncs.restview.DetailView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.ac.kmu.ncs.restview.MainActivity;
import kr.ac.kmu.ncs.restview.R;

/**
 * Created by eddie on 2016. 12. 7..
 */

public class DetailInfoActivity extends Activity {
    ListData extraListData;

    TextView tvTitle;
    TextView tvPir;
    TextView tvIr;

    TextView tvPirTime;

    Button btnPaper;
    Button btnRepair;
    Button btnBack;
    SimpleListData listData;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

//        detailViewController = DetailViewController.getInstance();

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvPir = (TextView) findViewById(R.id.tv_pir);
        tvIr = (TextView) findViewById(R.id.tv_ir);

        tvPirTime = (TextView) findViewById(R.id.tv_pir_time);

        btnPaper = (Button) findViewById(R.id.btn_paper);
        btnPaper.setOnClickListener(new onPaperClick());
        btnRepair = (Button) findViewById(R.id.btn_repair);
        btnRepair.setOnClickListener(new onRepairClick());
        btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        this.listData = new SimpleListData(intent.getStringExtra("IR"), intent.getStringExtra("PIR"), intent.getStringExtra("PAPER"), intent.getStringExtra("REPAIR"), intent.getStringExtra("TIMESTAMP"), intent.getStringExtra("PIRENTERTIME"));
        this.index = intent.getIntExtra("index", 0) + 1;

        this.extraListData = DetailViewController.extraListData;

        setTitle(index);
        setVacant(listData.mPIR);
        setSufficiency(listData.mIR);
        setPaperButton(listData.mPaper);
        setRepairButton(listData.mRepair);
    }

    public void setTitle(int index) {
        String title = "";
        if (index < 4) {
            title += "남자 - ";
            title += index;
        } else {
            title += "여자 - ";
            title += index - 3;
        }
        this.tvTitle.setText(title);
    }

    public void setVacant(String pir) {
        switch (Integer.parseInt(pir)) {
            case 0:
                this.tvPirTime.setText("");
                this.tvPir.setText("Vacant");
                break;
            case 1:
                Time enterTime = calcTime(listData.mPIREnterTime);
                Time nowTime = calcTime(listData.mTimestamp);
                int hourTerm = nowTime.hour - enterTime.hour;
                int minuteTerm = nowTime.minute - enterTime.minute;
                this.tvPirTime.setText((hourTerm * 60) + minuteTerm + "분 경과");
                this.tvPir.setText("Occupied");
                break;
        }
    }

    public void setSufficiency(String ir) {
        tvIr.setText(ir);
        if (ir.equals("Sufficient")) {
        } else if (ir.equals("Insufficient")) {
        }
    }

    public void setPaperButton(String paper) {
        this.btnPaper.setEnabled(false);

        switch (Integer.parseInt(paper)) {
            case 0:

                break;
            case 1:
                this.btnPaper.setEnabled(true);
                break;
        }
    }

    public void setRepairButton(String repair) {
        this.btnRepair.setEnabled(false);

        switch (Integer.parseInt(repair)) {
            case 0:

                break;
            case 1:
                this.btnRepair.setEnabled(true);
                break;
        }
    }

    public Time calcTime(String mTime) {
        Time time = new Time();
        String[] mTimes = mTime.split(":");
        time.hour = Integer.parseInt(mTimes[0]);
        time.minute = Integer.parseInt(mTimes[1]);
        time.second = Integer.parseInt(mTimes[2]);

        return time;
    }

    class onPaperClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new MessageBox("paper","부족한 휴지를 채웠습니까?").show();
        }
    }

    class onRepairClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            new MessageBox("repair","다 고쳐졌습니까?").show();
        }
    }

    class MessageBox {
        String input;
        String msg;

        public MessageBox(String input, String msg) {
            this.input = input;
            this.msg = msg;
        }

        public void show() {
            AlertDialog.Builder confirm_alert = new AlertDialog.Builder(DetailInfoActivity.this);
            confirm_alert.setMessage(msg).setCancelable(false).setPositiveButton("처리완료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(input.equals("paper")){
                        extraListData.mPaper = "0";
                        extraListData.mPaperState = DetailViewController.imgEmpty;
                        btnPaper.setEnabled(false);
                    } else if(input.equals("repair")){
                        extraListData.mRepair = "0";
                        extraListData.mRepairState = DetailViewController.imgEmpty;
                        btnRepair.setEnabled(false);
                    }
                }
            }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            AlertDialog alert = confirm_alert.create();
            alert.show();
        }
    }
}

class SimpleListData {
    public String mIR;
    public String mPIR;
    public String mPaper;
    public String mRepair;
    public String mTimestamp;
    public String mPIREnterTime;

    public SimpleListData(String... strings) {
        mIR = strings[0];
        mPIR = strings[1];
        mPaper = strings[2];
        mRepair = strings[3];
        mTimestamp = strings[4];
        mPIREnterTime = strings[5];
    }
}