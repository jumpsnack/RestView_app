package kr.ac.kmu.ncs.restview.DetailView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.kmu.ncs.restview.R;

/**
 * Created by Eddie Sangwon Kim on 2016-11-05.
 */
public class DetailViewFragment extends Fragment {

    private DetailViewController detailViewController = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_detail, container, false);

//        detailViewController = DetailViewController.getInstance();
//        detailViewController.setView(view);
        if(detailViewController == null){
            detailViewController = new DetailViewController(view);
        }

        return view;
    }
}
