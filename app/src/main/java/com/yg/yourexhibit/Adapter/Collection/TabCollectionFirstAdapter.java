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

public class TabCollectionFirstAdapter extends RecyclerView.Adapter<TabCollectionFirstViewHolder>{

    private ArrayList<ExhibitCollectionResult> first;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick;

    public TabCollectionFirstAdapter(ArrayList<ExhibitCollectionResult> first, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.first = first;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }


    @Override
    public TabCollectionFirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_collection_items, parent,false);
        TabCollectionFirstViewHolder viewHolder = new TabCollectionFirstViewHolder(itemView);

        itemView.setOnClickListener(onItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabCollectionFirstViewHolder holder, int position) {
//        requestManager
//                .load(first.get(position).getCollection_image())
//                .centerCrop()
//                .into(holder.collectionImage);
        Picasso.with(ApplicationController.getInstance().getApplicationContext())
                .load(first.get(position).getCollection_image())
                .resize(120,150)
                .into(holder.collectionImage);
    }

    @Override
    public int getItemCount() {
        return (first !=null)? first.size() : 0;
    }
}
