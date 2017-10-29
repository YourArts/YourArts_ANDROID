package com.yg.yourexhibit.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;

/**
 * Created by jinhyemin on 2017. 10. 14..
 */

public class FindPWdialog extends Dialog {

    ImageButton btnfindIdok;
    TextView pwfindcorrect;
    EditText editText_find_correct,editText_find_pwcorrect;

    public FindPWdialog(@NonNull Context context) {
        super(context);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_find_pw);

    }

    public FindPWdialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public FindPWdialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getInstance().register(this);
        initViews();
        onClicked();

        editText_find_correct= (EditText) findViewById(R.id.editText_find_correct);
        editText_find_pwcorrect= (EditText) findViewById(R.id.editText_find_pwcorrect);
        pwfindcorrect = (TextView) findViewById(R.id.pwfindcorrect);

        //비밀번호랑 비밀번호 확인 같으면 correct 색깔 바뀌기
        editText_find_pwcorrect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (editText_find_correct.getText().toString().equals(editText_find_pwcorrect.getText().toString())) {

                    String strColor = "#00FFC4";
                    pwfindcorrect.setTextColor(Color.parseColor(strColor));

                    // txtjoinCorrect.setTe
                    // t(R.color.textGray);
                } else {

                    String strColor = "#95989A";
                    pwfindcorrect.setTextColor(Color.parseColor(strColor));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                FindPWdialog.this.dismiss();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
