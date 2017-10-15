package com.yg.yourexhibit.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yg.yourexhibit.Dialog.FindIDdialog;
import com.yg.yourexhibit.R;

/**
 * Created by jinhyemin on 2017. 10. 12..
 */

public class IDfindFragment extends Fragment {


    ImageButton nextbutton;

    FindIDdialog findIdfialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_id, container, false);

        nextbutton = (ImageButton) view.findViewById(R.id.btn_next);


        nextbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //아이디 찾기 확인 다이얼로그 뜨는 곳
                findIdfialog = new FindIDdialog(getContext());
                findIdfialog.setContentView(R.layout.dialog_find_id);
                findIdfialog.getWindow().setLayout(900, 900);//레이아웃에서 다이얼로그 뜰때 화면에서 어느정도 비율을 차지할 것인지
                findIdfialog.show();

            }
        });

        return view;

    }

}



