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
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitDelete.ExhibitCollectionDeleteResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResult;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private NetworkService networkService;
    private Fragment temp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection_detail, container, false);
        ButterKnife.bind(this, v);
        Log.v(TAG, "create");
       // ApplicationController.getInstance().temp2 = this;
        //ApplicationController.getInstance().setCollectionEventSwtich(false);
        //ApplicationController.getInstance().setColEditSwitch(false);

//        if(ApplicationController.getInstance().isColDetailSwitch()){
//            EventBus.getInstance().unregister(this);
//            ApplicationController.getInstance().setColDetailSwitch(true);
//        }
        temp = this;
        //ApplicationController.getInstance().setFromDetail(true);
        networkService = ApplicationController.getInstance().getNetworkService();
        //ApplicationController.getInstance().setInDetail(true);
        detailResult = ApplicationController.getInstance().getExhibitCollectionDetailResult();
        networkController = new NetworkController();
        initFragment();
        return v;
    }

    public void initFragment(){
        Glide.with(this).load(detailResult.getCollection_image()).centerCrop().into(detailImg);
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
        ApplicationController.getInstance().setDetailFromEdit(false);

//        getFragmentManager()
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .replace(R.id.collection_detail_container, new Tab_Collection_Edit())
//                .commit();
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
                deleteCollection(ApplicationController.getInstance().token,
                        detailResult.getCollection_idx());
                //ApplicationController.getInstance().makeToast("삭제 완료.");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteCollection(String token, int idx){
        Call<ExhibitCollectionDeleteResponse> deleteCollectionResponse = networkService.deleteCollectionResponse(token, idx);
        deleteCollectionResponse.enqueue(new Callback<ExhibitCollectionDeleteResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionDeleteResponse> call, Response<ExhibitCollectionDeleteResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast("삭제 완료.");
                    //EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_DELETE);
                    returnToCollection();

                    Log.v(TAG,"deletetCollectionSuccess");
                }else{
                    Log.v(TAG,"deletetCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionDeleteResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
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

        android.support.v4.app.FragmentManager fm = getFragmentManager();

        Fragment fromFrag = null, toFrag = null;


//        fromFrag = getActivity().getSupportFragmentManager().findFragmentByTag("base");
////            toFrag = getActivity().getSupportFragmentManager().findFragmentByTag("detail");
//        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//        ft.detach(fromFrag);
//            ft.attach(toFrag);
       // ft.commit();

        getActivity().finish();
        startActivity(getActivity().getIntent());
        ApplicationController.getInstance().setReFresh(true);
        ApplicationController.getInstance().setTabNum(2);



    }

//    @Override
//    public void onBack() {
//        BaseActivity activity = (BaseActivity) getActivity();
//        activity.setOnKeyBackPressedListener(null);
//        activity.onBackPressed();
//
//        returnToCollection();
//    }


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
//        ApplicationController.getInstance().setColDetailSwitch(true);
//        if(ApplicationController.getInstance().isColDetailSwitch()){
//            EventBus.getInstance().unregister(this);
//            ApplicationController.getInstance().setColDetailSwitch(false);
//        }
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.v(TAG, "resume");
        //EventBus.getInstance().register(this);
//        if(!ApplicationController.getInstance().isColDetailSwitch()){
//            EventBus.getInstance().register(this);
//            ApplicationController.getInstance().setColDetailSwitch(true);
//        }
        super.onResume();

    }
}
