package com.yg.yourexhibit.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yg.yourexhibit.R;


public class JoinDialog extends Dialog {

    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mContent;
    private boolean join;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.dialog_join);

        mContentView = (TextView) findViewById(R.id.dialog_join_txt_content);
        mLeftButton = (Button) findViewById(R.id.dialog_join_btn_no);
        mRightButton = (Button) findViewById(R.id.dialog_join_btn_start);
        if(join){
            mLeftButton.setText("NO");
            mRightButton.setText("START!");
        }

        // 제목과 내용을 생성자에서 셋팅한다.
        mContentView.setText(mContent);

        // 클릭 이벤트 셋팅
        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        } else {

        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public JoinDialog(Context context,
                        View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mLeftClickListener = singleListener;
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public JoinDialog(Context context,
                        String content, View.OnClickListener leftListener,
                        View.OnClickListener rightListener, boolean join) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
        this.join = join;

    }
}




