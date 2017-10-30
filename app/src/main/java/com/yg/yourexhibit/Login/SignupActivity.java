package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Activity.HomeActivity;
import com.yg.yourexhibit.Dialog.JoinDialog;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    private JoinDialog joinDialog;


    ImageButton joinbutton, Signupbackbutton;
    TextView txtjoinCorrect;

    @BindView(R.id.btn_join)
    ImageButton join;

    private EditText editTextID, editTextPW, editTextPWcorrect, editTextNickName, editTextEmail;
    private NetworkController networkController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EventBus.getInstance().register(this);
        ButterKnife.bind(this);
        networkController = new NetworkController();
       // joinbutton = (ImageButton) findViewById(R.id.btn_join);
        Signupbackbutton = (ImageButton) findViewById(R.id.backbtn_signup);

        editTextPW = (EditText) findViewById(R.id.editText_join_pw);
        editTextPWcorrect = (EditText) findViewById(R.id.editText_join_pwcorrect);
        txtjoinCorrect = (TextView) findViewById(R.id.joinCorrect);

        joinbutton = (ImageButton) findViewById(R.id.btn_join);
        editTextID = (EditText) findViewById(R.id.editText_join_id);
        editTextNickName = (EditText) findViewById(R.id.editText_name);

        editTextEmail = (EditText) findViewById(R.id.editText_name);

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

                finish();

            }
        });
    }

    @OnClick(R.id.btn_join)
    public void clickJoin(){

        if(editTextEmail.getText().toString().isEmpty() || editTextNickName.getText().toString().isEmpty() ||
                editTextID.getText().toString().isEmpty() || editTextPW.getText().toString().isEmpty() ||
                editTextPWcorrect.getText().toString().isEmpty()){
            Toast.makeText(this, "모든 입력란을 채워주세요!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!editTextPW.getText().toString().equals(editTextPWcorrect.getText().toString())){
            Toast.makeText(this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
            return;
        }



        networkController.sign(editTextID.getText().toString(), editTextPW.getText().toString(),
                editTextPW.getText().toString(), editTextEmail.getText().toString(), editTextNickName.getText().toString());
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_LOGIN:
                Intent intent = new Intent(
                        getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case EventCode.EVENT_CODE_SIGN:
                joinDialog = new JoinDialog(this,

                        ("작품 모을 준비가 완료되었습니다!\n" + "'"+SharedPrefrernceController.getUserNickname(this)+"'" + "의\n 전시를 시작하시겠습니까?").replace(" ", "\u00a0"),
                            leftListener, // 왼쪽 버튼 이벤트
                            rightListener2, true); // 오른쪽 버튼 이벤트
                    joinDialog.show();
                break;
            case EventCode.EVENET_CODE_LOGIN_FAIL:
                break;
            case EventCode.EVENT_CODE_SIGN_FAIL:
                    joinDialog = new JoinDialog(this,
                            "입력한 정보로 존재하는\n사용자가 있습니다. ID/PW를 찾으시겠습니까?",
                            leftListener, // 왼쪽 버튼 이벤트
                            rightListener, false); // 오른쪽 버튼 이벤트
                    joinDialog.show();
                break;
        }
    }


//    public void onClickView(View v) {
//
//        //joinbutton = (ImageButton) findViewById(R.id.btn_join);
//        editTextID = (EditText) findViewById(R.id.editText_join_id);
//
//        Log.i("MyTag","editID" + String.valueOf(editTextID.getText().toString()));
//        Log.i("MyTag","editID1" + String.valueOf("a"));
//
////        if(String.valueOf(editTextID.getText().toString()) == String.valueOf("a")){
////
////
////            Log.i("MyTag","editID11" + String.valueOf(editTextID.getText().toString()));
////            joinDialog = new JoinDialog(this,
////                            "입력한 정보로 존재하는 사용자가 있습니다. ID/PW를 찾으시겠습니까?",
////                            leftListener, // 왼쪽 버튼 이벤트
////                            rightListener); // 오른쪽 버튼 이벤트
////                    joinDialog.show();
////        }else {
////            joinDialog = new JoinDialog(this,
////
////                            "작품을 모을 준비가 완료되었습니다! ‘당신’의 전시를 시작하시겠습니까?",
////                            leftListener, // 왼쪽 버튼 이벤트
////                            rightListener); // 오른쪽 버튼 이벤트
////                    joinDialog.show();
////        }
////
////        switch (v.getId()) {
////            case R.id.btn_join:
////
////                    joinDialog = new JoinDialog(this,
////                            "입력한 정보로 존재하는 사용자가 있습니다.ID/PW를 찾으시겠습니까?",
////                            leftListener, // 왼쪽 버튼 이벤트
////                            rightListener); // 오른쪽 버튼 이벤트
////                    joinDialog.show();
////                    break;
////
////        }
//    }
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
            //Toast.makeText(getApplicationContext(), "취소 클릭",
                    //Toast.LENGTH_SHORT).show();
            joinDialog.dismiss();

        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            //Toast.makeText(getApplicationContext(), "ID/PW 찾기",
                    //Toast.LENGTH_SHORT).show();
            joinDialog.dismiss();
            Intent intent = new Intent(
                    getApplicationContext(),FindIdPwActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener rightListener2 = new View.OnClickListener() {
        public void onClick(View v) {
//            Toast.makeText(getApplicationContext(), "ID/PW 찾기",
//                    Toast.LENGTH_SHORT).show();
            networkController.login(editTextID.getText().toString(), editTextPW.getText().toString());
            joinDialog.dismiss();

        }
    };


}

