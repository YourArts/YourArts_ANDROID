package com.yg.yourexhibit.Tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionFirstAdapter;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionSecondAdapter;
import com.yg.yourexhibit.Adapter.Collection.TabCollectionThirdAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.NetworkService;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionDetailResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResponse;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResult;
import com.yg.yourexhibit.Util.CustomLinearLayout;
import com.yg.yourexhibit.Util.NetworkController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 2yg on 2017. 10. 8..
 */

public class Tab_Collection extends Fragment{

    @BindView(R.id.tab_collection_fisrt)
    RecyclerView first;

    @BindView(R.id.tab_collection_second)
    RecyclerView second;

    @BindView(R.id.tab_collection_third)
    RecyclerView third;

    @BindView(R.id.tab_collection_message)
    TextView text;

    @BindView(R.id.tab_collection_edit)
    ImageView edit;

    private static final String TAG = "LOG::Collection";

    private NetworkController networkController;
    private TabCollectionFirstAdapter tabCollectionFirstAdapter;
    private TabCollectionSecondAdapter tabCollectionSecondAdapter;
    private TabCollectionThirdAdapter tabCollectionThirdAdapter;
    private RequestManager requestManagerFirst;
    private RequestManager requestManagerSecond;
    private RequestManager requestManagerThird;
    private NetworkService networkService;

    private CustomLinearLayout linearLayoutManagerFirst;
    private CustomLinearLayout linearLayoutManagerSecond;
    private CustomLinearLayout linearLayoutManagerThird;

    private ArrayList<ExhibitCollectionResult> firstResult;
    private ArrayList<ExhibitCollectionResult> secondResult;
    private ArrayList<ExhibitCollectionResult> thirdResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collection, container, false);
        ButterKnife.bind(this, v);
        Log.v(TAG, "createView");
//        if(!ApplicationController.getInstance().isCollectionEventSwtich()){
//            Log.v(TAG, "register");
//            EventBus.getInstance().register(this);
//            ApplicationController.getInstance().setCollectionEventSwtich(true);
//        }
        beforInit();
        networkService = ApplicationController.getInstance().getNetworkService();
        ApplicationController.getInstance().setInDetail(false);
        ApplicationController.getInstance().setFromWork(false);
        networkController = new NetworkController();
        requestManagerFirst = Glide.with(this);
        requestManagerSecond = Glide.with(this);
        requestManagerThird = Glide.with(this);
        first.clearOnScrollListeners();
        second.clearOnScrollListeners();
        third.clearOnScrollListeners();
        //TODO : 여기에 원래 Shared에 저장한 아이디 들어가야 함
        //networkController.getCollectionData(ApplicationController.getInstance().token);
        getCollectionDatas(ApplicationController.getInstance().token);


        return v;
    }
    public void beforInit(){
        if (ApplicationController.getInstance().isFromEdit()) {
            android.support.v4.app.FragmentManager fm = getFragmentManager();

            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.detach(this).attach(this).commit();

//            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
//                Log.v(TAG, "count");
//                fm.popBackStack();
//            }
            ApplicationController.getInstance().setFromEdit(false);
        }

    }


    public void getCollectionDatas(String token){
        //NetworkService networkService = ApplicationController.getInstance().getNetworkService();
        firstResult = new ArrayList<ExhibitCollectionResult>();
        secondResult = new ArrayList<ExhibitCollectionResult>();
        thirdResult = new ArrayList<ExhibitCollectionResult>();

        Call<ExhibitCollectionResponse> exhibitCollectionResponse = networkService.getCollectionResponse(token);
        //final ArrayList<ExhibitCollectionResult> finalFirstResult = firstResult;
        exhibitCollectionResponse.enqueue(new Callback<ExhibitCollectionResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionResponse> call, Response<ExhibitCollectionResponse> response) {

                if(response.body().isStatus()){
                    ApplicationController.getInstance().setCollectionSize(response.body().getResult().size());
                    Log.v(TAG, "getCollectionSuccess");
                    for(int i = 0; i<response.body().getResult().size(); i++){
                        switch(i%3){
                            case 0:
                                firstResult.add(response.body().getResult().get(i));
                                break;
                            case 1:
                                secondResult.add(response.body().getResult().get(i));
                                break;
                            case 2:
                                thirdResult.add(response.body().getResult().get(i));
                                break;
                            default:
                                break;
                        }
                    }
                    ApplicationController.getInstance().setExhibitCollectionResultFirst(firstResult);
                    ApplicationController.getInstance().setExhibitCollectionResultSecond(secondResult);
                    ApplicationController.getInstance().setExhibitCollectionResultThird(thirdResult);
                    initFragment();
                    //EventBus.getInstance().post(EventCode.EVENT_CODE_COLLECTION_GET);
                }else{
                    Log.v(TAG,"getCollectionFail");
                }
            }

            @Override
            public void onFailure(Call<ExhibitCollectionResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "create");

    }

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "attach");
        super.onAttach(context);
    }

//    @Subscribe
//    public void onEventLoad(Integer code){
//        switch(code){
//            case EventCode.EVENT_CODE_COLLECTION_GET:
//                initFragment();
//                break;
//            case EventCode.EVENT_CODE_COLLECTION_DETAIL:
//                //Fragment fragment = Tab_Collection_Detail.newInstance();
//                Log.v(TAG, "toDetail");
//                getFragmentManager()
//                        .beginTransaction()
//                        .addToBackStack(null)
//                        .add(R.id.tab_collection_container, new Tab_Collection(), "base")
//                        .replace(R.id.tab_collection_container, new Tab_Collection_Detail(), "detail")
//                        .commit();
//                break;
//        }
//    }

    public void initFragment(){
        firstResult = ApplicationController.getInstance().getExhibitCollectionResultFirst();
        secondResult = ApplicationController.getInstance().getExhibitCollectionResultSecond();
        thirdResult = ApplicationController.getInstance().getExhibitCollectionResultThird();



        linearLayoutManagerFirst = new CustomLinearLayout(getActivity());
        linearLayoutManagerFirst.setOrientation(LinearLayout.VERTICAL);

        linearLayoutManagerSecond = new CustomLinearLayout(getActivity());
        linearLayoutManagerSecond.setOrientation(LinearLayout.VERTICAL);

        linearLayoutManagerThird = new CustomLinearLayout(getActivity());
        linearLayoutManagerThird.setOrientation(LinearLayout.VERTICAL);
        if(!firstResult.isEmpty()){
            first.setNestedScrollingEnabled(false);
            first.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
            first.setLayoutManager(linearLayoutManagerFirst);
            tabCollectionFirstAdapter = new TabCollectionFirstAdapter(firstResult, requestManagerFirst, clickEventFirst);
            first.setAdapter(tabCollectionFirstAdapter);
        }else{
            first.setVisibility(View.GONE);
            second.setVisibility(View.GONE);
            third.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
        }

        if(!secondResult.isEmpty()){
            second.setNestedScrollingEnabled(false);
            second.setVisibility(View.VISIBLE);
            second.setLayoutManager(linearLayoutManagerSecond);
            tabCollectionSecondAdapter = new TabCollectionSecondAdapter(secondResult, requestManagerSecond, clickEventSecond);
            second.setAdapter(tabCollectionSecondAdapter);

        }

        if(!thirdResult.isEmpty()){
            third.setNestedScrollingEnabled(false);
            third.setVisibility(View.VISIBLE);
            third.setLayoutManager(linearLayoutManagerThird);
            tabCollectionThirdAdapter = new TabCollectionThirdAdapter(thirdResult, requestManagerThird, clickEventThird);
            third.setAdapter(tabCollectionThirdAdapter);

        }
    }

    public View.OnClickListener clickEventFirst = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v(TAG, "first");
            int itemPosition = first.getChildPosition(v);
            ApplicationController.getInstance().setFromEdit(false);
            //ApplicationController.getInstance().makeToast(String.valueOf(itemPosition));
            int idx = firstResult.get(itemPosition).getCollection_idx();
            ApplicationController.getInstance().setCollectionIdx(idx);
            getCollectionDetailData(ApplicationController.getInstance().token, idx);

        }
    };

    public View.OnClickListener clickEventSecond = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v(TAG, "second");
            int itemPosition = second.getChildPosition(v);
            ApplicationController.getInstance().setFromEdit(false);
            //ApplicationController.getInstance().makeToast(String.valueOf(itemPosition));
            int idx = secondResult.get(itemPosition).getCollection_idx();
            ApplicationController.getInstance().setCollectionIdx(idx);
            getCollectionDetailData(ApplicationController.getInstance().token, idx);

        }
    };

    public View.OnClickListener clickEventThird = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v(TAG, "third");
            int itemPosition = third.getChildPosition(v);
            ApplicationController.getInstance().setFromEdit(false);
            //ApplicationController.getInstance().makeToast(String.valueOf(itemPosition));
            int idx = thirdResult.get(itemPosition).getCollection_idx();
            ApplicationController.getInstance().setCollectionIdx(idx);
            getCollectionDetailData(ApplicationController.getInstance().token, idx);
        }
    };

    public void getCollectionDetailData(String token, int idx){
        Call<ExhibitCollectionDetailResponse> detailResponse = networkService.getCollectionDetailResponse(token, idx);
        detailResponse.enqueue(new Callback<ExhibitCollectionDetailResponse>() {
            @Override
            public void onResponse(Call<ExhibitCollectionDetailResponse> call, Response<ExhibitCollectionDetailResponse> response) {
                if(response.body().isStatus()){
                    ApplicationController.getInstance().setExhibitCollectionDetailResult(response.body().getResult());
                    Log.v(TAG, "toDetail");
                 getFragmentManager()
                        .beginTransaction()
                        .disallowAddToBackStack()
                        .replace(R.id.tab_collection_container, new Tab_Collection_Detail())
                        .commit();


                    Log.v(TAG, "getCollectionDetailSuccess");
                }else{
                    Log.v(TAG, "getCollectionDetailFail");
                }
            }
            @Override
            public void onFailure(Call<ExhibitCollectionDetailResponse> call, Throwable t) {
                Log.v(TAG,"checkNetwork");
            }
        });
    }

    @OnClick(R.id.tab_collection_edit)
    public void toEdit(){
        ApplicationController.getInstance().setFromDetail(false);
        getFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.tab_collection_container, new Tab_Collection(), "base")
                .replace(R.id.tab_collection_container, new Tab_Collection_Edit(), "edit")
                .commit();
    }

    @Override
    public void onDetach() {
        Log.v(TAG,"detach");
        super.onDetach();
       // EventBus.getInstance().unregister(this);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG,"destroy");
        super.onDestroy();
        //EventBus.getInstance().unregister(this);
    }

    @Override
    public void onPause() {
        Log.v(TAG,"pause");
        super.onPause();
       // ApplicationController.getInstance().setCollectionEventSwtich(true);
        //ApplicationController.getInstance().setCollectionEventSwtich(false);

//        if(ApplicationController.getInstance().isCollectionEventSwtich()){
//            EventBus.getInstance().unregister(this);
//            Log.v(TAG, "unregister");
//            ApplicationController.getInstance().setCollectionEventSwtich(false);
//        }
    }

    @Override
    public void onResume() {
        Log.v(TAG, "resume");
        super.onResume();
//        if(!ApplicationController.getInstance().isCollectionEventSwtich()){
//            EventBus.getInstance().register(this);
//            Log.v(TAG, "register");
//            ApplicationController.getInstance().setCollectionEventSwtich(true);
//        }
    }
}
