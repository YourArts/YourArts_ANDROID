package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.BaseActivity;

public class MainActivity extends BaseActivity {

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
