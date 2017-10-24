package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.Activity.HomeActivity;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editText_id)
    EditText id;

    @BindView(R.id.editText_pw)
    EditText pw;

    @BindView(R.id.login_auto)
    ImageView auto;

    ImageButton signupbutton;

    TextView forgetIDPW;
    private CallbackManager callbackManager;
    private NetworkController networkController;
    private boolean autoLogin = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        EventBus.getInstance().register(this);
        ButterKnife.bind(this);
        networkController = new NetworkController();
        //startActivity(new Intent(this,SplashActivity.class));


        signupbutton = (ImageButton) findViewById(R.id.btn_signup);
        forgetIDPW = (TextView) findViewById(R.id.forgetidpw);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

        forgetIDPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),FindIdPwActivity.class);
                startActivity(intent);

            }
        });
    }



    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_LOGIN:
                SharedPrefrernceController.setLoginId(this, id.getText().toString());
                SharedPrefrernceController.setPasswd(this, pw.getText().toString());
                Intent intent = new Intent(
                        getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case EventCode.EVENET_CODE_LOGIN_FAIL:
                ApplicationController.getInstance().makeToast("존재하지 않는 정보입니다.");
                break;
        }
    }

    @OnClick(R.id.login_auto)
    public void checkAuto(){
        if(!autoLogin){
            auto.setImageResource(R.drawable.login_auto_check_on);
            autoLogin = true;
            SharedPrefrernceController.setSelected(this, autoLogin);
        }else{
            auto.setImageResource(R.drawable.login_auto_check_off);
            autoLogin = false;
            SharedPrefrernceController.setSelected(this, autoLogin);
        }
    }

    @OnClick(R.id.login_custom)
    public void clickLogin(){
        networkController.login(id.getText().toString(), pw.getText().toString());
    }

    @OnClick(R.id.login_facebook)
    public void clickFacebook(){
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            result.getAccessToken();
                        }
                    }
                });
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
