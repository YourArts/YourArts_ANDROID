package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitWorkResult;
import com.yg.yourexhibit.Util.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 17..
 */

public class Tab_Work extends Fragment{

    @BindView(R.id.tab_work_img)
    ImageView workImg;

    @BindView(R.id.tab_work_name)
    TextView workName;

    @BindView(R.id.tab_work_when)
    TextView workWhen;

    @BindView(R.id.tab_work_where)
    TextView workWhere;

    @BindView(R.id.tab_work_how)
    TextView workHow;

    @BindView(R.id.tab_work_size)
    TextView workSize;

    @BindView(R.id.tab_work_underline)
    View underLine;

    @BindView(R.id.tab_work_move)
    ImageButton moveToCollection;

    private ArrayList<ExhibitWorkResult> workResult;
    private RequestManager requestManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_work, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        moveToCollection.setImageResource(R.drawable.move_to_collection_off);
        //EventBus.getInstance().post(EventCode.EVENT_CODE_GOING_PREVIEW);
        workResult = ApplicationController.getInstance().getExhibitWorkResult();

        initFragment();
        return v;
    }

    public void initFragment(){
        Glide.with(this).load(workResult.get(0).getWork_image()).into(workImg);
        workName.setText(workResult.get(0).getWork_name());
        workWhen.setText("사조 : " + workResult.get(0).getWork_idea());
        workHow.setText("기법 : " + workResult.get(0).getWork_style());
        workWhere.setText("소장 : " + workResult.get(0).getWork_owner());
        workSize.setText("크기 : " + workResult.get(0).getWork_size());
        //underLine.getLayoutParams().width = workName.getMinWidth();
        underLine.setMinimumWidth(underLine.getLayoutParams().width);
    }

    @OnClick(R.id.tab_work_move)
    public void toCollection(){
        moveToCollection.setImageResource(R.drawable.move_to_collection_on);
        ApplicationController.getInstance().setFromWork(true);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.tab_work_container, new Tab_Work())
                .replace(R.id.tab_work_container, new Tab_Collection_Edit())
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
        moveToCollection.setImageResource(R.drawable.move_to_collection_off);
    }
}
