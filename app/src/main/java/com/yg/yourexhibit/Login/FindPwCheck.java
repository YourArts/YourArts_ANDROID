package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWResponse;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPwCheck extends AppCompatActivity {


    @BindView(R.id.editText_find_pw)
    EditText pw;

    @BindView(R.id.editText_find_correct)
    EditText correct;

    @BindView(R.id.pcorrect)
    TextView checkCorrect;

    @BindView(R.id.btn_pw_ok)
    ImageButton ok;



    private NetworkService networkService;
    private String id;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw_check);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        email = intent.getExtras().getString("email");

        networkService = ApplicationController.getInstance().getNetworkService();


        correct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(pw.getText().toString().equals(correct.getText().toString())){
                    checkCorrect.setTextColor(Color.parseColor("#00FFC4"));
                }else{
                    checkCorrect.setTextColor(Color.parseColor("#666666"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @OnClick(R.id.btn_pw_ok)
    public void clickOk(){
        if(checkCorrect.getCurrentTextColor() == Color.parseColor("#00FFC4")){
            Call<TabSettingPWResponse> settingPWResponseCall = networkService.postPW(new TabSettingPWPost(id,email,pw.getText().toString(),correct.getText().toString()));
            settingPWResponseCall.enqueue(new Callback<TabSettingPWResponse>() {
                @Override
                public void onResponse(Call<TabSettingPWResponse> call, Response<TabSettingPWResponse> response) {
                    Log.d("changePW",response.body().message);
                    if(response.isSuccessful()){
                        SharedPrefrernceController.setPasswd(FindPwCheck.this,pw.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<TabSettingPWResponse> call, Throwable t) {

                }
            });
        }else{
            if(pw.getText().length()==0)
                ApplicationController.getInstance().makeToast("비밀번호를 입력해주세요");
            else
                ApplicationController.getInstance().makeToast("비밀번호 확인이 일치하지 않습니다");
        }
    }

    @OnClick(R.id.btn_pw_ok)
    public void toLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}
