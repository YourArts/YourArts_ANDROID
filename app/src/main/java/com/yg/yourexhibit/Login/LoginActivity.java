package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yg.yourexhibit.R;

public class LoginActivity extends AppCompatActivity {

    ImageButton signupbutton;

    TextView forgetIDPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
}
