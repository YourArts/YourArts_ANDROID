package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.otto.Subscribe;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 17..
 */

public class Tab_Collection_Detail extends Fragment {

    @BindView(R.id.collection_detail_img)
    ImageView detailImg;

    @BindView(R.id.collection_detail_name)
    TextView name;

    @BindView(R.id.collection_detail_content)
    TextView content;

    @BindView(R.id.collection_detail_edit)
    TextView edit;

    private ExhibitCollectionDetailResult detailResult;
    private NetworkController networkController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_detail, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        ApplicationController.getInstance().setInDetail(true);
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        networkController = new NetworkController();
        Log.v("탭콜디1", "탭콜디1");
       // ((BaseActivity) getActivity()).setOnKeyBackPressedListener(this);
        //ApplicationController.getInstance().setFromEdit(false);
        initFragment();
        return v;
    }

    public void initFragment(){
        Glide.with(this).load(detailResult.getCollection_image()).into(detailImg);
        name.setText("전시 이름");
        name.setText(detailResult.getExhibition_name());
        if(ApplicationController.getInstance().isFromEdit())
            content.setText(ApplicationController.getInstance().getEditContent());
        else{
            content.setText(detailResult.getCollection_content());
        }
    }

    @OnClick(R.id.collection_detail_edit)
    public void toEdit(){
        ApplicationController.getInstance().setFromDetail(true);

            getFragmentManager()
                    .beginTransaction()
                    .addToBackStack("toEdit")
                    .replace(R.id.collection_detail_container, new Tab_Collection_Edit())
                    .commit();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        menu.add("삭제");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                networkController.deleteCollection(ApplicationController.getInstance().token,
                        detailResult.getCollection_idx());
                //ApplicationController.getInstance().makeToast("삭제 완료.");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEventLoad(Integer code){
        switch (code){
            case EventCode.EVENT_CODE_COLLECTION_DELETE:
                returnToCollection();
                break;
            case EventCode.EVENT_CODE_BACK:
                ApplicationController.getInstance().setInDetail(false);

                    getFragmentManager()
                            .beginTransaction()
                            .disallowAddToBackStack()
                            .add(R.id.collection_detail_container, new Tab_Collection_Detail())
                            .replace(R.id.collection_detail_container, new Tab_Collection())
                            .commit();

                break;

        }
    }

    public void returnToCollection(){

        getFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.collection_detail_container, new Tab_Collection_Detail())
                .replace(R.id.collection_detail_container, new Tab_Collection())
                .commit();
    }

//    @Override
//    public void onBack() {
//        BaseActivity activity = (BaseActivity) getActivity();
//        activity.setOnKeyBackPressedListener(null);
//        activity.onBackPressed();
//
//        returnToCollection();
//    }

    public static Fragment newInstance()
    {
        Tab_Collection_Detail myFragment = new Tab_Collection_Detail();
        return myFragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }
}
