package kr.ac.kmu.ncs.restview.OverView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.kmu.ncs.restview.R;

/**
 * Created by NCS-KSW on 2016-11-06.
 */
public class OverViewFragment extends Fragment {

    OverViewController overViewController = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_over, container, false);

        if(overViewController == null){
            overViewController = new OverViewController(view);
        }
        return  view;
    }
}
