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
 * Created by 2yg on 2017. 10. 26..
 */

public class GalleryDialog extends Dialog{



    private TextView mContentView;
    private Button mLeftButton;
    private Button mRightButton;
    private String mContent;

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

        setContentView(R.layout.dialog_gallery);

        mContentView = (TextView) findViewById(R.id.dialog_gallery_txt_content);
        mLeftButton = (Button) findViewById(R.id.dialog_gallery_btn_no);
        mRightButton = (Button) findViewById(R.id.dialog_gallery_btn_yes);

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

    public GalleryDialog(Context context,
                      String content, View.OnClickListener leftListener,
                      View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = content;
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;

    }



}
