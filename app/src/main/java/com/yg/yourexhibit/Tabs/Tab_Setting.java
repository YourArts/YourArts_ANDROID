package com.yg.yourexhibit.Tabs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNamePut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNameResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 김민경 on 2017-10-24.
 */

public class Tab_Setting extends Fragment {

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
    TextView settingPW;

    NetworkService networkService;
    String nickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_setting, container, false);

        ButterKnife.bind(this,v);

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
                if(settingCheckPW.getCurrentTextColor() == Color.parseColor("#00FFC4")){
                    Call<TabSettingPWResponse> settingPWResponseCall = networkService.postPW(new TabSettingPWPost())
                }
            }
        });
//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        return v;


    }
}
