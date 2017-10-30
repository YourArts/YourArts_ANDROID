package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
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
import com.yg.yourexhibit.Util.BaseActivity;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;
import com.yg.yourexhibit.Util.SharedPrefrernceController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.editText_id)
    EditText id;

    @BindView(R.id.editText_pw)
    EditText pw;

    @BindView(R.id.login_auto)
    ImageView auto;

//    @BindView(R.id.login_facebook)
//    ImageButton facebookSign;

    ImageButton signupbutton;

    TextView forgetIDPW;
    private CallbackManager callbackManager;
    private NetworkController networkController;
    private boolean autoLogin = false;
    private String fbToken = "";
    private String fbNickName = "";

    private static final String TAG = "LOG::LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        EventBus.getInstance().register(this);
        ButterKnife.bind(this);
        Log.v(TAG, "create");
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
                Log.v(TAG, "event1");
                SharedPrefrernceController.setLoginId(this, id.getText().toString());
                SharedPrefrernceController.setPasswd(this, pw.getText().toString());
                Intent intent = new Intent(
                        getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
                //Log.v(TAG, "event1");
                break;
            case EventCode.EVENET_CODE_LOGIN_FAIL:
                ApplicationController.getInstance().makeToast("존재하지 않는 정보입니다.");
                Log.v(TAG, "event2");
                break;
            case EventCode.EVENT_CODE_FB_LOGIN:
                Log.v(TAG, "event3");
                SharedPrefrernceController.setFacebookToken(this, fbToken);
                SharedPrefrernceController.setUserNickname(this, fbNickName);
                Intent intent2 = new Intent(
                        getApplicationContext(),HomeActivity.class);
                startActivity(intent2);
                finish();
                break;
            case EventCode.EVENT_CODE_FB_LOGN_FAIL:
                Log.v(TAG, "event4");
                networkController.facebookSign(fbToken);
                break;
            case EventCode.EVENT_CODE_SIGN:

                break;
            case EventCode.EVENT_CDOE_FB_SIGN_FAIL:
                Log.v(TAG, "event5");
                ApplicationController.getInstance().makeToast("잘못된 토큰입니다.");
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
        Log.v(TAG, "facebook1");
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {
                Log.v(TAG, "facebook2");

                Log.v(TAG, "token : " + result.getAccessToken().getToken());

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        try {
                            fbToken = result.getAccessToken().getToken();
                            networkController.facebookLogin(fbToken);
                            Log.v(TAG, user.get("name").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                //Log.v(TAG, request.getParameters().get("filed"));
            }

            @Override
            public void onError(FacebookException error) {
                Log.v(TAG, "facebook5");
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                Log.v(TAG, "facebook6");
//finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "facebook7");
    }
}
