package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 17..
 */

public class Tab_Collection_Detail extends Fragment{

    @BindView(R.id.collection_detail_img)
    ImageView detailImg;

    @BindView(R.id.collection_detail_name)
    TextView name;

    @BindView(R.id.collection_detail_content)
    TextView content;

    @BindView(R.id.collection_detail_edit)
    TextView edit;

    private ExhibitCollectionDetailResult detailResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_detail, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        //networkController = new NetworkController();

        initFragment();
        return v;
    }

    public void initFragment(){
        Glide.with(this).load(detailResult.getCollection_image()).into(detailImg);
        name.setText("전시 이름");
        name.setText(detailResult.getExhibition_name());
        content.setText(detailResult.getCollection_content());
    }

    @OnClick(R.id.collection_detail_edit)
    public void toEdit(){
        ApplicationController.getInstance().setFromDetail(true);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.collection_detail_container, new Tab_Collection_Edit())
                .commit();

        EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_EDIT2);
    }
}
