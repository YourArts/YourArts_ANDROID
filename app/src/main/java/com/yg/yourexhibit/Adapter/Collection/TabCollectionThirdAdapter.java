package com.yg.yourexhibit.Adapter.Collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.yg.yourexhibit.R;
import com.yg.yourexhibit.Retrofit.RetrofitGet.ExhibitCollectionResult;

import java.util.ArrayList;

/**
 * Created by 2yg on 2017. 10. 16..
 */

public class TabCollectionThirdAdapter extends RecyclerView.Adapter<TabCollectionThirdViewHolder>{

    private ArrayList<ExhibitCollectionResult> third;
    private RequestManager requestManager;
    private View.OnClickListener onItemClick;

    public TabCollectionThirdAdapter(ArrayList<ExhibitCollectionResult> third, RequestManager requestManager, View.OnClickListener onItemClick) {
        this.third = third;
        this.requestManager = requestManager;
        this.onItemClick = onItemClick;
    }


    @Override
    public TabCollectionThirdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_collection_items, parent,false);
        TabCollectionThirdViewHolder viewHolder = new TabCollectionThirdViewHolder(itemView);

        itemView.setOnClickListener(onItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabCollectionThirdViewHolder holder, int position) {
        requestManager.load(third.get(position).getCollection_image()).into(holder.collectionImage);

    }

    @Override
    public int getItemCount() {
        return (third!=null)? third.size() : 0;
    }
}
