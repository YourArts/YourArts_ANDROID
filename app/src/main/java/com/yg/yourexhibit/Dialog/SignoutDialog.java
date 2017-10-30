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

public class SignoutDialog extends Dialog {

    TextView txtDialogSignout;
    Button btnDialogSignoutNo;
    Button btnDialogSignoutYes;

    View.OnClickListener mLeftClickListener;
    View.OnClickListener mRightClickListener;

    public SignoutDialog(Context context, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_signout);

        txtDialogSignout = (TextView)findViewById(R.id.txtDialogSignout);
        btnDialogSignoutNo = (Button)findViewById(R.id.btnDialogSignoutNo);
        btnDialogSignoutYes = (Button)findViewById(R.id.btnDialogSignoutYes);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;

        getWindow().setAttributes(lpWindow);

        txtDialogSignout.setText("더이상 전시를 모을 수 없습니다. \n 정말로 탈퇴하시겠습니까?");
        btnDialogSignoutYes.setOnClickListener(mRightClickListener);
        btnDialogSignoutNo.setOnClickListener(mLeftClickListener);


    }
}
