package com.yg.yourexhibit.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNamePut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNameResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.txtSettingName)
    TextView settingName;

    @BindView(R.id.editSettingName)
    EditText newName;

    @BindView(R.id.editSettingPW)
    EditText newPW;

    @BindView(R.id.editSettingCheckPW)
    EditText checkPW;

    @BindView(R.id.txtCheckPW)
    TextView settingCheckPW;

    @BindView(R.id.txtSettingPW)
    EditText settingPW;

    NetworkService networkService;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        networkService = ApplicationController.getInstance().getNetworkService();
        nickname = newName.getText().toString();

        settingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<TabSettingNameResponse> tabSettingNameResponseCall = networkService.putName(ApplicationController.getToken(),new TabSettingNamePut(nickname));
                tabSettingNameResponseCall.enqueue(new Callback<TabSettingNameResponse>() {
                    @Override
                    public void onResponse(Call<TabSettingNameResponse> call, Response<TabSettingNameResponse> response) {
                        settingName.setTextColor(Color.parseColor("#00FFC4"));
                        settingName.setText("완료");
                        Log.d("nickCheck",response.body().message);
                    }

                    @Override
                    public void onFailure(Call<TabSettingNameResponse> call, Throwable t) {
                    }
                });
            }
        });

        checkPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(newPW.getText().toString().equals(checkPW.getText().toString())){
                    settingCheckPW.setTextColor(Color.parseColor("#00FFC4"));
                }else{
                    settingCheckPW.setTextColor(Color.parseColor("#666666"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        settingPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

    }
}
