package com.yg.yourexhibit.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Dialog.ErrornumDialog;
import com.yg.yourexhibit.Dialog.FindPWdialog;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhyemin on 2017. 10. 12..
 */

public class PWfindFragment extends Fragment  {


    ImageButton pwfindnextbutton;

    @BindView(R.id.findpw_send_email)
    TextView sendEmail;

    EditText editTextfindpwnum;

    @BindView(R.id.editText_findpwemail)
    EditText findPWEmail;

    @BindView(R.id.editText_findpwname)
    EditText findPwName;

    private FindPWdialog findPWdialog;
    private ErrornumDialog errornumDialog;

    private NetworkController networkController;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pw, container, false);
        ButterKnife.bind(this, view);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        pwfindnextbutton = (ImageButton) view.findViewById(R.id.btn_pw_next);
        editTextfindpwnum = (EditText) view.findViewById(R.id.editText_findpwnum);



        pwfindnextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPWdialog = new FindPWdialog(getContext());
                findPWdialog.setContentView(R.layout.dialog_find_pw);
                findPWdialog.getWindow().setLayout(900, 900);//레이아웃에서 다이얼로그 뜰때 화면에서 어느정도 비율을 차지할 것인지
                findPWdialog.show();
            }
        });
//
//        if(editTextfindpwnum.getText().toString()=="a"){
//            pwfindnextbutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
//        }


        return view;
    }

    @Subscribe
    public void onEventLoad(boolean sendStatus){
        if(sendStatus){
            findPWdialog = new FindPWdialog(getContext());
            findPWdialog.setContentView(R.layout.dialog_find_pw);
            //findPWdialog.getWindow().setLayout(900, 900);//레이아웃에서 다이얼로그 뜰때 화면에서 어느정도 비율을 차지할 것인지
            findPWdialog.show();
        }else{
            errornumDialog = new ErrornumDialog(getContext());

        }
    }

    @OnClick(R.id.findpw_send_email)
    public void sendAuth(){
        networkController.postAuth(editTextfindpwnum.getText().toString());
    }
}



