package com.yg.yourexhibit.Tabs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Dialog.LogoutDialog;
import com.yg.yourexhibit.Dialog.SignoutDialog;
import com.yg.yourexhibit.Login.SplashActivity;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitDelete.TabSettingDeleteUserResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWPost;
import com.yg.yourexhibit.Retrofit.RetrofitPost.TabSettingPWResponse;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNamePut;
import com.yg.yourexhibit.Retrofit.RetrofitPut.TabSettingNameResponse;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.settingLogout)
    Button goLogout;

    @BindView(R.id.txtSettinOut)
    TextView goSignout;

    @BindView(R.id.txtSettingGallery)
    TextView gallerySetting;

    NetworkService networkService;
    String nickname;
    String userID;
    String userEmail;
    String userPW1;
    String userPW2;
    LogoutDialog logoutDialog;
    SignoutDialog signoutDialog;
    private boolean gallery = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_setting, container, false);

        ButterKnife.bind(this,v);

        networkService = ApplicationController.getInstance().getNetworkService();
        nickname = newName.getText().toString();

        userID = SharedPrefrernceController.getLoginId(this.getContext());
        userEmail = SharedPrefrernceController.getUserEmail(this.getContext());
        userPW1 = newPW.getText().toString();
        userPW2 = checkPW.getText().toString();

        gallery = SharedPrefrernceController.getGallery(this.getContext());

        newName.setHint(SharedPrefrernceController.getUserNickname(getContext()));

        if(gallery){
            gallerySetting.setTextColor(Color.parseColor("#00ffc4"));
        }else{
            gallerySetting.setTextColor(Color.parseColor("#666666"));
        }
        settingName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newName.getText().length()==0) Toast.makeText(getContext(),"닉네임을 입력해주세요.",Toast.LENGTH_LONG).show();
                else{

                    Call<TabSettingNameResponse> tabSettingNameResponseCall = networkService.putName(ApplicationController.getToken(),new TabSettingNamePut(newName.getText().toString()));

                    tabSettingNameResponseCall.enqueue(new Callback<TabSettingNameResponse>() {
                        @Override
                        public void onResponse(Call<TabSettingNameResponse> call, Response<TabSettingNameResponse> response) {
                            settingName.setTextColor(Color.parseColor("#00FFC4"));
                            settingName.setText("완료");

                            SharedPrefrernceController.setUserNickname(getContext(),newName.getText().toString());
                            Log.d("nickChangeCheck",newName.getText().toString());

                            Log.d("nickCheck",response.body().message);
                        }

                        @Override
                        public void onFailure(Call<TabSettingNameResponse> call, Throwable t) {
                        }
                    });
                }
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
                    Call<TabSettingPWResponse> settingPWResponseCall = networkService.postPW(new TabSettingPWPost(userID,userEmail,userPW1,userPW2));
                    settingPWResponseCall.enqueue(new Callback<TabSettingPWResponse>() {
                        @Override
                        public void onResponse(Call<TabSettingPWResponse> call, Response<TabSettingPWResponse> response) {
                            Log.d("changePW",response.body().message);
                            if(response.isSuccessful()){
                                settingPW.setTextColor(Color.parseColor("#00FFC4"));
                                settingPW.setText("완료");
                                SharedPrefrernceController.setPasswd(getContext(),userPW1);
                            }
                        }

                        @Override
                        public void onFailure(Call<TabSettingPWResponse> call, Throwable t) {

                        }
                    });
                }else{
                    if(newPW.getText().length()==0) Toast.makeText(getContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show();
                    else Toast.makeText(getContext(),"비밀번호 확인이 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                }
            }
        });

         final View.OnClickListener leftListener = new View.OnClickListener() {
            public void onClick(View v) {
                logoutDialog.dismiss();

            }
        };
         final View.OnClickListener rightListener = new View.OnClickListener() {
            public void onClick(View v) {
                SharedPrefrernceController.setLoginId(getActivity(), "");
                SharedPrefrernceController.setSelected(getActivity(), false);
                SharedPrefrernceController.setPasswd(getActivity(), "");
                logoutDialog.dismiss();

//                getActivity().finish();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(Tab_Setting.this).commit();

                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        };

        goLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog = new LogoutDialog(getContext(),
                        leftListener, // 왼쪽 버튼 이벤트
                        rightListener); // 오른쪽 버튼 이벤트
                logoutDialog.show();
            }
        });

        final View.OnClickListener leftListener2 = new View.OnClickListener() {
            public void onClick(View v) {
                signoutDialog.dismiss();

            }
        };
        final View.OnClickListener rightListener2 = new View.OnClickListener() {
            public void onClick(View v) {
                Call<TabSettingDeleteUserResponse> tabSettingDeleteUserResponseCall = networkService.deleteUser(ApplicationController.getToken());
                tabSettingDeleteUserResponseCall.enqueue(new Callback<TabSettingDeleteUserResponse>() {
                    @Override
                    public void onResponse(Call<TabSettingDeleteUserResponse> call, Response<TabSettingDeleteUserResponse> response) {
                        if(response.isSuccessful()){
                            Log.d("Signout","succuess");
                            SharedPrefrernceController.setLoginId(getActivity(), "");
                            SharedPrefrernceController.setSelected(getActivity(), false);
                            SharedPrefrernceController.setPasswd(getActivity(), "");
                            signoutDialog.dismiss();

//                            getActivity().finish();
//                            startActivity(new Intent(getContext(), SplashActivity.class));
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().remove(Tab_Setting.this).commit();

                            Intent intent = new Intent(getContext(), SplashActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Log.d("Signout","fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<TabSettingDeleteUserResponse> call, Throwable t) {

                    }
                });

            }
        };

        goSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signoutDialog = new SignoutDialog(getContext(),
                        leftListener2, // 왼쪽 버튼 이벤트
                        rightListener2); // 오른쪽 버튼 이벤트
                signoutDialog.show();
            }
        });

        return v;

    }

    @OnClick(R.id.txtSettingGallery)
    public void onClickGallery(){
        if(gallery){
            //갤러리 트루일 때
            SharedPrefrernceController.setGallery(getContext(), false);
            gallerySetting.setTextColor(Color.parseColor("#666666"));
            gallerySetting.setText("OFF");
            gallery = false;
        }else{
            //갤러리 퍼스일 때
            SharedPrefrernceController.setGallery(getContext(), true);
            gallerySetting.setTextColor(Color.parseColor("#00ffc4"));
            gallerySetting.setText("ON");
            gallery = true;
        }
    }
}
