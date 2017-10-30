package com.yg.yourexhibit.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.Dialog.FindIDdialog;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by jinhyemin on 2017. 10. 12..
 */

public class IDfindFragment extends Fragment {


    @BindView(R.id.editText_findemail)
    EditText findEmail;

    @BindView(R.id.editText_findname)
    EditText findName;

    ImageButton nextbutton;

    FindIDdialog findIdfialog;

    private NetworkController networkController;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_id, container, false);
        ButterKnife.bind(this, view);

        EventBus.getInstance().register(this);

        nextbutton = (ImageButton) view.findViewById(R.id.btn_next);
        networkController = new NetworkController();

        nextbutton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //아이디 찾기 확인 다이얼로그 뜨는 곳
                if(findEmail.getText().toString().equals("") || findName.getText().toString().equals("")) {
                    ApplicationController.getInstance().makeToast("모든 입력란을 채워주세요");
                }else
                    networkController.findPwPost(findName.getText().toString(), findEmail.getText().toString());

            }
        });

        return view;

    }

    @Subscribe
    public void onEventLoad(String id){

        Intent intent = new Intent(getApplicationContext(), FIndIdCheck.class);
        intent.putExtra("id", id);
        startActivity(intent);

//        if(!id.equals("")){
//            Fragment fragment = new IDfindFragment(); // Fragment 생성
//            Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
//            bundle.putString("id", id); // key , value
//            fragment.setArguments(bundle);
//
//
//            getFragmentManager()
//                    .beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.find_id_container, new FindIdOkFragment())
//                    .commit();
//
//        }else{
//            Fragment fragment = new IDfindFragment(); // Fragment 생성
//            Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
//            bundle.putString("id", id); // key , value
//            fragment.setArguments(bundle);
//            getFragmentManager()
//                    .beginTransaction()
//                    .addToBackStack(null)
//                    .replace(R.id.find_id_container, new FindIdOkFragment())
//                    .commit();
//        }
    }
}



