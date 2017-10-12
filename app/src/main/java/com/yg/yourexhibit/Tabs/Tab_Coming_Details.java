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
import com.yg.yourexhibit.Adapter.TabComingDetailAdapter;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitDetailResult;
import com.yg.yourexhibit.Util.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private ExhibitDetailResult exhibitDetailResult;
    private RequestManager requestManager;
    private LinearLayoutManager linearLayoutManager;
    private TabComingDetailAdapter tabComingDetailAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_home_coming_details, container, false);
        ButterKnife.bind(this, v);
        EventBus.getInstance().register(this);
        exhibitDetailResult = ApplicationController.getInstance().getExhibitDetailResult();

        initFragment();
        return v;
    }

    public void initFragment(){
        requestManager = Glide.with(this);

        name.setText(exhibitDetailResult.getExhibition_name());
        time.setText(exhibitDetailResult.getExhibition_start_time() + "~" + exhibitDetailResult.getExhibition_end_time());
        location.setText(exhibitDetailResult.getExhibition_location());
        date.setText(exhibitDetailResult.getExhibition_stard_date() + "~" + exhibitDetailResult.getExhibition_end_date());
        description.setText(exhibitDetailResult.getExhibition_description());
        requestManager.load(exhibitDetailResult.getExhibition_picture()).into(represent);

        preViewList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setOrientation(LinearLayout.HORIZONTAL);
        preViewList.setLayoutManager(linearLayoutManager);
        tabComingDetailAdapter = new TabComingDetailAdapter(exhibitDetailResult.getImages(), requestManager);
        //tabEndAdapter.setOnItemClickListener(this.getView().OnClickListener);
        preViewList.setAdapter(tabComingDetailAdapter);
        //Intent intent = new Intent(getActivity().getApplicationContext(), Tab_End.class);
    }
}
