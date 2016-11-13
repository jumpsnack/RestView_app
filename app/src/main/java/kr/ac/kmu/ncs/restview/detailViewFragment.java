package kr.ac.kmu.ncs.restview;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.kmu.ncs.restview.R;

/**
 * Created by Eddie Sangwon Kim on 2016-11-05.
 */
public class detailViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragmnet_detail, container, false);

      //  newAccountUi.edtPassword = (EditText) viewGroup.findViewById(R.id.edt_new_account_password);
      //  newAccountUi.edtConfirmPassword = (EditText) viewGroup.findViewById(R.id.edt_new_account_confirm_password);
      //  newAccountUi.btnPasswordFinish = (Button) viewGroup.findViewById(R.id.btn_fragment_password_finish);

        return viewGroup;
    }
}
