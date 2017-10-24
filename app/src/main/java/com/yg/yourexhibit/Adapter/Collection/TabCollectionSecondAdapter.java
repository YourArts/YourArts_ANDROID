package com.yg.yourexhibit.Adapter.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.squareup.picasso.Picasso;
import com.yg.yourexhibit.App.ApplicationController;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResult;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 16..
 */

public class TabCollectionSecondAdapter extends RecyclerView.Adapter<TabCollectionSecondViewHolder>{
    private ArrayList<ExhibitCollectionResult> second;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick;

    public TabCollectionSecondAdapter(ArrayList<ExhibitCollectionResult> second, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.second = second;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }


    @Override
    public TabCollectionSecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_collection_items, parent,false);
        TabCollectionSecondViewHolder viewHolder = new TabCollectionSecondViewHolder(itemView);

        itemView.setOnClickListener(onItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabCollectionSecondViewHolder holder, int position) {
        //requestManager.load(second.get(position).getCollection_image()).into(holder.collectionImage);
        Picasso.with(ApplicationController.getInstance().getApplicationContext())
                .load(second.get(position).getCollection_image())
                .resize(120,150)
                .into(holder.collectionImage);
    }

    @Override
    public int getItemCount() {
        return (second != null)? second.size() : 0;
    }
}
