package com.yg.yourexhibit.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Dialog.FindIDdialog;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinhyemin on 2017. 10. 12..
 */

public class IDfindFragment extends Fragment {


    @BindView(R.id.editText_findemail)
    EditText findEmail;

    @BindView(R.id.editText_findname)
    EditText findName;

    ImageButton nextbutton;

    FindIDdialog findIdfialog;

    private NetworkController networkController;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_id, container, false);
        ButterKnife.bind(this, view);

        EventBus.getInstance().register(this);

        nextbutton = (ImageButton) view.findViewById(R.id.btn_next);
        networkController = new NetworkController();

        nextbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //아이디 찾기 확인 다이얼로그 뜨는 곳
                networkController.postFindId(findName.getText().toString(), findEmail.getText().toString());

            }
        });

        return view;

    }

    @Subscribe
    public void onEventLoad(String id){
        if(!id.equals("")){
            findIdfialog = new FindIDdialog(getContext(), true, id);
            findIdfialog.setContentView(R.layout.dialog_find_id);
            //findIdfialog.getWindow().setLayout(900, 900);//레이아웃에서 다이얼로그 뜰때 화면에서 어느정도 비율을 차지할 것인지
            findIdfialog.show();
        }else{
            findIdfialog = new FindIDdialog(getContext(), false, "");
            findIdfialog.setContentView(R.layout.dialog_find_id);
            //findIdfialog.getWindow().setLayout(900, 900);//레이아웃에서 다이얼로그 뜰때 화면에서 어느정도 비율을 차지할 것인지
            findIdfialog.show();
        }
    }



}



