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
import com.yg.yourexhibit.Adapter.Home.TabComingDetailAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResult;
import com.yg.yourexhibit.Util.EventBus;
import com.yg.yourexhibit.Util.NetworkController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 2yg on 2017. 10. 11..
 */

public class Tab_Coming_Details extends Fragment{

    @BindView(R.id.coming_details_preview)
    RecyclerView preViewList;

    @BindView(R.id.coming_details_date)
    TextView date;

    @BindView(R.id.coming_details_location)
    TextView location;

    @BindView(R.id.coming_details_desciption)
    TextView description;

    @BindView(R.id.coming_details_time)
    TextView time;

    @BindView(R.id.coming_details_img)
    ImageView represent;

    @BindView(R.id.coming_details_name)
    TextView name;

    @BindView(R.id.coming_details_heart)
    ImageView heart;

    @BindView(R.id.coming_details_pretext)
    TextView preText;

    private ExhibitDetailResult exhibitDetailResult;
    private RequestManager requestManager;
    private LinearLayoutManager linearLayoutManager;
    private TabComingDetailAdapter tabComingDetailAdapter;
    private NetworkController networkController;
    private boolean heartCheck = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_coming_details, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        networkController = new NetworkController();
        exhibitDetailResult = ApplicationController.getInstance().getExhibitDetailResult();

        initFragment();
        return v;
    }

    public void initFragment(){
        requestManager = Glide.with(this);

        name.setText(exhibitDetailResult.getExhibition_name());
        time.setText("시간 : " + exhibitDetailResult.getExhibition_start_time() + "~" + exhibitDetailResult.getExhibition_end_time());
        location.setText("장소 : " + exhibitDetailResult.getExhibition_location());
        date.setText("일시 : " + exhibitDetailResult.getExhibition_stard_date() + "~" + exhibitDetailResult.getExhibition_end_date());
        String tempString = exhibitDetailResult.getExhibition_description();
        String tempString2 = "소개 : " + tempString.replace(" ", "\u00a0");
        //Log.v("문자", tempString);
        //Log.v("문자2", tempString.replace(" ", "\u00a0"));

        description.setText(tempString2);
        requestManager.load(exhibitDetailResult.getExhibition_picture()).centerCrop().into(represent);

        if(exhibitDetailResult.getImages().isEmpty()){
            preText.setVisibility(View.VISIBLE);
        }else {
            preViewList.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
            preViewList.setLayoutManager(linearLayoutManager);
            tabComingDetailAdapter = new TabComingDetailAdapter(exhibitDetailResult.getImages(), requestManager);
            //tabEndAdapter.setOnItemClickListener(this.getView().OnClickListener);
            preViewList.setAdapter(tabComingDetailAdapter);
            //Intent intent = new Intent(getActivity().getApplicationContext(), Tab_End.class);
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

    @OnClick(R.id.coming_details_heart)
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
