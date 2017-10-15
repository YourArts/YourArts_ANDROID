package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yg.yourexhibit.Dialog.JoinDialog;
import com.yg.yourexhibit.R;

public class SignupActivity extends AppCompatActivity {

    private JoinDialog joinDialog;


    ImageButton joinbutton, Signupbackbutton;
    TextView txtjoinCorrect;

    private EditText editTextID, editTextPW, editTextPWcorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        joinbutton = (ImageButton) findViewById(R.id.btn_join);
        Signupbackbutton = (ImageButton) findViewById(R.id.backbtn_signup);

        editTextPW = (EditText) findViewById(R.id.editText_join_pw);
        editTextPWcorrect = (EditText) findViewById(R.id.editText_join_pwcorrect);
        txtjoinCorrect = (TextView) findViewById(R.id.joinCorrect);

        joinbutton = (ImageButton) findViewById(R.id.btn_join);
        editTextID = (EditText) findViewById(R.id.editText_join_id);


        //비밀번호랑 비밀번호 확인 같으면 correct 색깔 바뀌기
        editTextPWcorrect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (editTextPW.getText().toString().equals(editTextPWcorrect.getText().toString())) {

                    String strColor = "#00FFC4";
                    txtjoinCorrect.setTextColor(Color.parseColor(strColor));

                    // txtjoinCorrect.setText(R.color.textGray);
                } else {

                    String strColor = "#95989A";
                    txtjoinCorrect.setTextColor(Color.parseColor(strColor));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Signupbackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(
                        SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }



    public void onClickView(View v) {

        joinbutton = (ImageButton) findViewById(R.id.btn_join);
        editTextID = (EditText) findViewById(R.id.editText_join_id);

        Log.i("MyTag","editID" + String.valueOf(editTextID.getText().toString()));
        Log.i("MyTag","editID1" + String.valueOf("a"));

//        if(String.valueOf(editTextID.getText().toString()) == String.valueOf("a")){
//
//
//            Log.i("MyTag","editID11" + String.valueOf(editTextID.getText().toString()));
//            joinDialog = new JoinDialog(this,
//                            "입력한 정보로 존재하는 사용자가 있습니다. ID/PW를 찾으시겠습니까?",
//                            leftListener, // 왼쪽 버튼 이벤트
//                            rightListener); // 오른쪽 버튼 이벤트
//                    joinDialog.show();
//        }else {
//            joinDialog = new JoinDialog(this,
//
//                            "작품을 모을 준비가 완료되었습니다! ‘당신’의 전시를 시작하시겠습니까?",
//                            leftListener, // 왼쪽 버튼 이벤트
//                            rightListener); // 오른쪽 버튼 이벤트
//                    joinDialog.show();
//        }

        switch (v.getId()) {
            case R.id.btn_join:

                    joinDialog = new JoinDialog(this,
                            "입력한 정보로 존재하는 사용자가 있습니다.ID/PW를 찾으시겠습니까?",
                            leftListener, // 왼쪽 버튼 이벤트
                            rightListener); // 오른쪽 버튼 이벤트
                    joinDialog.show();
                    break;

        }
    }
//        switch (v.getId()) {
//            case R.id.btn_join:
//                editTextID = (EditText) findViewById(R.id.editText_join_id);
//                if(String.valueOf(editTextID.getText().toString()) == String.valueOf("a"))
//                {
//                    editTextID = (EditText) findViewById(R.id.editText_join_id);
//                    Log.i("MyTag","editID1" + String.valueOf(editTextID.getText().toString()));
//                    joinDialog = new JoinDialog(this,
//                            "입력한 정보로 존재하는 사용자가 있습니다. ID/PW를 찾으시겠습니까?",
//                            leftListener, // 왼쪽 버튼 이벤트
//                            rightListener); // 오른쪽 버튼 이벤트
//                    joinDialog.show();
//                    break;
//                }
//                else {
//                    joinDialog = new JoinDialog(this,
//
//                            "작품을 모을 준비가 완료되었습니다! ‘당신’의 전시를 시작하시겠습니까?",
//                            leftListener, // 왼쪽 버튼 이벤트
//                            rightListener); // 오른쪽 버튼 이벤트
//                    joinDialog.show();
//                    break;
//                }
//        }


    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소 클릭",
                    Toast.LENGTH_SHORT).show();
            joinDialog.dismiss();

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "ID/PW 찾기",
                    Toast.LENGTH_SHORT).show();
            joinDialog.dismiss();
            Intent intent = new Intent(
                    getApplicationContext(),FindIdPwActivity.class);
            startActivity(intent);
        }
    };


}

