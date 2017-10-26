package com.yg.yourexhibit.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yg.yourexhibit.R;

/**
 * Created by 김민경 on 2017-10-26.
 */

public class LogoutDialog extends Dialog {
//
//    @BindView(R.id.txtDialogLogout)
//    TextView txtDialogLogout;
//
//    @BindView(R.id.btnDialogLogoutNo)
//    Button btnDialogLogoutNo;
//
//    @BindView(R.id.btnDialogLogoutYes)
//    Button btnDialogLogoutYes;

    TextView txtDialogLogout;
    Button btnDialogLogoutNo;
    Button btnDialogLogoutYes;
//
    View.OnClickListener mLeftClickListener;
    View.OnClickListener mRightClickListener;

    public LogoutDialog(Context context, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;

//        btnDialogLogoutYes.setOnClickListener(mRightClickListener);
//        btnDialogLogoutNo.setOnClickListener(mLeftClickListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout);

        txtDialogLogout = (TextView)findViewById(R.id.txtDialogLogout);
        btnDialogLogoutNo = (Button)findViewById(R.id.btnDialogLogoutNo);
        btnDialogLogoutYes = (Button)findViewById(R.id.btnDialogLogoutYes);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;

        getWindow().setAttributes(lpWindow);

        txtDialogLogout.setText("'당신'의 전시에서 \n 로그아웃 하시겠습니까?");
        btnDialogLogoutYes.setOnClickListener(mRightClickListener);
        btnDialogLogoutNo.setOnClickListener(mLeftClickListener);


    }
}
