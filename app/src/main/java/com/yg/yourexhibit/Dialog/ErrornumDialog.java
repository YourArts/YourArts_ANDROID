package com.yg.yourexhibit.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yg.yourexhibit.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by jinhyemin on 2017. 10. 14..
 */

public class ErrornumDialog extends Dialog {

    //인증번호 입력하고 틀렸을때 나오는 다이얼로그임

    //private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @BindView(R.id.dialog_err_btn_no)
    Button button;

    @BindView(R.id.dialog_err_txt_content)
    TextView mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_errornum);

        ButterKnife.bind(this);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        onClicked();
        // 제목과 내용을 생성자에서 셋팅한다.
        mContentView.setText(mContent);

        // 클릭 이벤트 셋팅
//        if (mLeftClickListener != null && mRightClickListener != null) {
//            mLeftButton.setOnClickListener(mLeftClickListener);
//            mRightButton.setOnClickListener(mRightClickListener);
//        } else if (mLeftClickListener != null
//                && mRightClickListener == null) {
//            mLeftButton.setOnClickListener(mLeftClickListener);
//        } else {
//
//        }
    }

    private void onClicked() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrornumDialog.this.dismiss();
            }
        });
    }

    public ErrornumDialog(Context context, String mContent) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = mContent;

    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public ErrornumDialog(Context context,
                      View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mLeftClickListener = singleListener;
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
}




