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

    private static final String TAG = "LOG::CollectionDetail";
    private ExhibitCollectionDetailResult detailResult;
    private NetworkController networkController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_detail, container, false);
        ButterKnife.bind(this, v);
        Log.v(TAG, "create");
        ApplicationController.getInstance().setCollectionEventSwtich(false);
        ApplicationController.getInstance().setColEditSwitch(false);

        if(ApplicationController.getInstance().isColDetailSwitch()){
            EventBus.getInstance().unregister(this);
            ApplicationController.getInstance().setColDetailSwitch(false);
        }
        ApplicationController.getInstance().setInDetail(true);
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        networkController = new NetworkController();
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

//                    getFragmentManager()
//                            .beginTransaction()
//                            .disallowAddToBackStack()
//                            .add(R.id.collection_detail_container, new Tab_Collection_Detail())
//                            .replace(R.id.collection_detail_container, new Tab_Collection())
//                            .commit();

                break;

        }
    }

    public void returnToCollection(){

        getFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.collection_detail_container, new Tab_Collection())
                .commit();


//            Fragment fromFrag = null, toFrag = null;
//            fromFrag = getActivity().getSupportFragmentManager().findFragmentByTag("base");
//            toFrag = getActivity().getSupportFragmentManager().findFragmentByTag("detail");
//            final android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.detach(fromFrag);
//            ft.attach(toFrag);
//            ft.commit();

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
    public void onDestroy() {
        Log.v(TAG,"destroy");
        super.onDestroy();
//        EventBus.getInstance().unregister(this);
    }

    @Override
    public void onDetach() {
        Log.v(TAG,"detach");
        super.onDetach();
       // EventBus.getInstance().unregister(this);
    }

    @Override
    public void onPause() {
        Log.v(TAG,"pause");
        if(ApplicationController.getInstance().isColDetailSwitch()){
            EventBus.getInstance().unregister(this);
            ApplicationController.getInstance().setColDetailSwitch(false);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.v(TAG, "resume");
        //EventBus.getInstance().register(this);
        super.onResume();
        if(!ApplicationController.getInstance().isColDetailSwitch()){
            EventBus.getInstance().register(this);
            ApplicationController.getInstance().setColDetailSwitch(true);
        }
    }
}
