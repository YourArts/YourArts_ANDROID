package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yg.yourexhibit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FIndIdCheck extends AppCompatActivity {

    @BindView(R.id.find_id_check_back)
    ImageButton back;

    @BindView(R.id.find_id_info)
    TextView info;

    @BindView(R.id.find_id_no)
    TextView no;

    @BindView(R.id.find_id_text)
    TextView ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_check);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        if(id.equals("")){
            no.setVisibility(View.VISIBLE);
            ids.setVisibility(View.GONE);
            info.setVisibility(View.GONE);
        }else{
            no.setVisibility(View.GONE);
            ids.setVisibility(View.VISIBLE);
            info.setVisibility(View.VISIBLE);
            ids.setText(id);
        }
    }

    @OnClick(R.id.find_id_check_back)
    public void checkBack(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
