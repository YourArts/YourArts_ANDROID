package com.yg.yourexhibit.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 2yg on 2017. 10. 30..
 */

public class FindIdOkFragment extends Fragment{


    @BindView(R.id.id_search_text1)
    TextView id;//찾은 아이디

    @BindView(R.id.find_search_id_no)
    TextView no;

    @BindView(R.id.find_search_id_info)
    TextView info;//아이디 있었을 때 정보 띄우기



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.id_search_ok, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        //TODO : 여기에 원래 Shared에 저장한 아이디 들어가야 함
        

        return v;
    }

//    @Subscribe
//    public void onEventLoad(String ids){
//        if(!ids.equals("")){
//            //아이디 존재
//            info.setVisibility(View.VISIBLE);
//            id.setVisibility(View.VISIBLE);
//            no.setVisibility(View.GONE);
//            id.setText(ids);
//        }else{
//            //아이디 존재X
//            info.setVisibility(View.GONE);
//            id.setVisibility(View.GONE);
//            no.setVisibility(View.VISIBLE);
//        }
//    }

    public void initFragment(){

    }

    public void initFragment2(){

    }

}
