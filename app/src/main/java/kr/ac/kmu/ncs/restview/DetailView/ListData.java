package kr.ac.kmu.ncs.restview.DetailView;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Eddie Sangwon Kim on 2016-11-06.
 */

public class ListData implements Parcelable {
    public String mIR;
    public String mPIR;
    public String mPIREnterTime;
    public Drawable mPIRState;
    public Drawable mPaperState;
    public String mPaper;
    public Drawable mRepairState;
    public String mRepair;
    public String mTimestamp;

    public ListData(){}

    protected ListData(Parcel in) {
        mIR = in.readString();
        mPIR = in.readString();
        mPIREnterTime = in.readString();
        mPaper = in.readString();
        mRepair = in.readString();
        mTimestamp = in.readString();
    }

    public static final Creator<ListData> CREATOR = new Creator<ListData>() {
        @Override
        public ListData createFromParcel(Parcel in) {
            return new ListData(in);
        }

        @Override
        public ListData[] newArray(int size) {
            return new ListData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mIR);
        parcel.writeString(mPIR);
        parcel.writeString(mPIREnterTime);
        parcel.writeString(mPaper);
        parcel.writeString(mRepair);
        parcel.writeString(mTimestamp);
    }
}
