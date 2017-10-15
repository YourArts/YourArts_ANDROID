package com.yg.yourexhibit.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.yg.yourexhibit.R;


/**
 * Created by jinhyemin on 2017. 10. 12..
 */


public class FindIDdialog extends Dialog {

    ImageButton btnfindIdok;

    public FindIDdialog(@NonNull Context context) {
        super(context);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_find_id);

    }

    public FindIDdialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public FindIDdialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        onClicked();
    }

    private void initViews() {
//        memo_date = (TextView) findViewById(R.id.memo_date);
//        memo_time = (TextView) findViewById(R.id.memo_time);
//        contentsTextView = (TextView) findViewById(R.id.contentsTextView);
        btnfindIdok = (ImageButton) findViewById(R.id.btn_id_ok);
    }

    private void onClicked() {
        btnfindIdok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindIDdialog.this.dismiss();
            }
        });
    }


}

