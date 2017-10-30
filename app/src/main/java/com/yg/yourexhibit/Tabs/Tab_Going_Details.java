package com.yg.yourexhibit.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.Adapter.Home.TabGoingDetailAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.EventCode;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 11..
 */

public class Tab_Going_Details extends Fragment{

    @BindView(R.id.going_details_preview)
    RecyclerView preViewList;

    @BindView(R.id.going_details_date)
    TextView date;

    @BindView(R.id.going_details_location)
    TextView location;

    @BindView(R.id.going_details_desciption)
    TextView description;

    @BindView(R.id.going_details_time)
    TextView time;

    @BindView(R.id.going_details_1)
    ImageView like1;

    @BindView(R.id.going_details_2)
    ImageView like2;

    @BindView(R.id.going_details_3)
    ImageView like3;

    @BindView(R.id.going_details_4)
    ImageView like4;

    @BindView(R.id.going_details_5)
    ImageView like5;

    @BindView(R.id.going_details_img)
    ImageView represent;

    @BindView(R.id.going_details_name)
    TextView name;

    @BindView(R.id.going_details_preBtn)
    View preBtn;

    @BindView(R.id.going_details_heart)
    ImageView heart;



    private ExhibitDetailResult exhibitDetailResult;
    private RequestManager requestManager;
    private LinearLayoutManager linearLayoutManager;
    private TabGoingDetailAdapter tabGoingDetailAdapter;
    private NetworkController networkController;
    private int idx;
    private boolean heartCheck = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_going_details, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        exhibitDetailResult = ApplicationController.getInstance().getExhibitDetailResult();

        initFragment();
        return v;
    }

    public void initFragment(){
        requestManager = Glide.with(this);

        setLike();
        name.setText(exhibitDetailResult.getExhibition_name());
        time.setText("시간 : " + exhibitDetailResult.getExhibition_start_time() + "~" + exhibitDetailResult.getExhibition_end_time());
        location.setText("장소 : " + exhibitDetailResult.getExhibition_location());
        date.setText("일시 : " + exhibitDetailResult.getExhibition_stard_date() + "~" + exhibitDetailResult.getExhibition_end_date());
        String tempString = exhibitDetailResult.getExhibition_description();
        String tempString2 = "소개 : " + tempString.replace(" ", "\u00a0");
        //Log.v("문자", tempString);
        //Log.v("문자2", tempString.replace(" ", "\u00a0"));

        description.setText(tempString2);
//        Picasso.with(ApplicationController.getInstance().getApplicationContext())
//                .load(exhibitDetailResult.getExhibition_picture())
//                .resize(380,220)
//                .into(represent);

        requestManager.load(exhibitDetailResult.getExhibition_picture()).centerCrop().into(represent);

        preViewList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        preViewList.setLayoutManager(linearLayoutManager);
        tabGoingDetailAdapter = new TabGoingDetailAdapter(exhibitDetailResult.getImages(), requestManager, clickEvent);
        //tabEndAdapter.setOnItemClickListener(this.getView().OnClickListener);
        preViewList.setAdapter(tabGoingDetailAdapter);
        //Intent intent = new Intent(getActivity().getApplicationContext(), Tab_End.class);
    }

    public void setLike(){
        switch (exhibitDetailResult.getLike_count()){
            case 0:
                break;
            case 1:
                like1.setImageResource(R.drawable.ic_good_on);
                break;
            case 2:
                like1.setImageResource(R.drawable.ic_good_on);
                like2.setImageResource(R.drawable.ic_good_on);
                break;
            case 3:
                like1.setImageResource(R.drawable.ic_good_on);
                like2.setImageResource(R.drawable.ic_good_on);
                like3.setImageResource(R.drawable.ic_good_on);
                break;
            case 4:
                like1.setImageResource(R.drawable.ic_good_on);
                like2.setImageResource(R.drawable.ic_good_on);
                like3.setImageResource(R.drawable.ic_good_on);
                like4.setImageResource(R.drawable.ic_good_on);
                break;
            case 5:
                like1.setImageResource(R.drawable.ic_good_on);
                like2.setImageResource(R.drawable.ic_good_on);
                like3.setImageResource(R.drawable.ic_good_on);
                like4.setImageResource(R.drawable.ic_good_on);
                like5.setImageResource(R.drawable.ic_good_on);
                break;
        }

        if(exhibitDetailResult.getHeart_used() == 1){
            heartCheck = true;
            heart.setImageResource(R.drawable.ic_exhibit_wish_on);
        }
        else {
            heartCheck = false;
            heart.setImageResource(R.drawable.ic_exhibit_wish_off);
        }
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = preViewList.getChildPosition(v);
            idx = ApplicationController.getInstance().getExhibitEndResult().get(itemPosition).getExhibition_idx();
            //networkController.getDetailData(0, idx);
        }
    };

    @OnClick(R.id.going_details_preBtn)
    public void toPreview(){
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.going_details_container, new Tab_Going_Preview())
                .commit();
    }

    @OnClick(R.id.going_details_1)
    public void onClick1(){
        like1.setImageResource(R.drawable.ic_good_on);
        like2.setImageResource(R.drawable.ic_good_off);
        like3.setImageResource(R.drawable.ic_good_off);
        like4.setImageResource(R.drawable.ic_good_off);
        like5.setImageResource(R.drawable.ic_good_off);
        if(exhibitDetailResult.getLike_count() == 0) {
            networkController.postLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx()
                    ,1);
        }else {
            networkController.putLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx(),
                    exhibitDetailResult.getLike_count(),1);
        }
    }

    @OnClick(R.id.going_details_2)
    public void onClick2(){
        like1.setImageResource(R.drawable.ic_good_on);
        like2.setImageResource(R.drawable.ic_good_on);
        like3.setImageResource(R.drawable.ic_good_off);
        like4.setImageResource(R.drawable.ic_good_off);
        like5.setImageResource(R.drawable.ic_good_off);

        if(exhibitDetailResult.getLike_count() == 0) {
            networkController.postLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx()
                    ,2);
        }else {
            networkController.putLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx(),
                    exhibitDetailResult.getLike_count(),2);
        }
    }

    @OnClick(R.id.going_details_3)
    public void onClick3(){
        like1.setImageResource(R.drawable.ic_good_on);
        like2.setImageResource(R.drawable.ic_good_on);
        like3.setImageResource(R.drawable.ic_good_on);
        like4.setImageResource(R.drawable.ic_good_off);
        like5.setImageResource(R.drawable.ic_good_off);

        if(exhibitDetailResult.getLike_count() == 0) {
            networkController.postLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx()
                    ,3);
        }else {
            networkController.putLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx(),
                    exhibitDetailResult.getLike_count(),3);
        }
    }

    @OnClick(R.id.going_details_4)
    public void onClick4(){
        like1.setImageResource(R.drawable.ic_good_on);
        like2.setImageResource(R.drawable.ic_good_on);
        like3.setImageResource(R.drawable.ic_good_on);
        like4.setImageResource(R.drawable.ic_good_on);
        like5.setImageResource(R.drawable.ic_good_off);

        if(exhibitDetailResult.getLike_count() == 0) {
            networkController.postLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx()
                    ,4);
        }else {
            networkController.putLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx(),
                    exhibitDetailResult.getLike_count(),4);
        }
    }

    @OnClick(R.id.going_details_5)
    public void onClick5(){
        like1.setImageResource(R.drawable.ic_good_on);
        like2.setImageResource(R.drawable.ic_good_on);
        like3.setImageResource(R.drawable.ic_good_on);
        like4.setImageResource(R.drawable.ic_good_on);
        like5.setImageResource(R.drawable.ic_good_on);

        if(exhibitDetailResult.getLike_count() == 0) {
            networkController.postLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx()
                    ,5);
        }else {
            networkController.putLike(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx(),
                    exhibitDetailResult.getLike_count(),5);
        }
    }

    @OnClick(R.id.going_details_heart)
    public void clickHeart(){
        if(!heartCheck){
            heart.setImageResource(R.drawable.ic_exhibit_wish_on);
            if(exhibitDetailResult.getHeart_used() == 0) {
                networkController.postHeart(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx());
            }else{
                networkController.putHeart(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx());
            }
            heartCheck = true;
        }else{
            heart.setImageResource(R.drawable.ic_exhibit_wish_off);
            networkController.putHeart(ApplicationController.getInstance().token, exhibitDetailResult.getExhibition_idx());
            heartCheck = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getInstance().unregister(this);
    }
}
