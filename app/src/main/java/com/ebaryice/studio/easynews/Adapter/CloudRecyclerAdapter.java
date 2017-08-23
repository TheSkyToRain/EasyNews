package com.ebaryice.studio.easynews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Bean.CloudBean;
import com.ebaryice.studio.easynews.ItemTouchHelperAdapter;
import com.ebaryice.studio.easynews.R;
import com.ebaryice.studio.easynews.Views.ContentView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class CloudRecyclerAdapter extends RecyclerView.Adapter<CloudRecyclerAdapter.MyViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private List<CloudBean> cloudBeen;

    public CloudRecyclerAdapter(Context context,List<CloudBean> cloudBeen){
        this.cloudBeen = cloudBeen;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(cloudBeen.get(position).getTitle());
        holder.date.setText(cloudBeen.get(position).getDate());
        Glide.with(context).load(cloudBeen.get(position).getCover()).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContentView.class);
                intent.putExtra("url",cloudBeen.get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cloudBeen.size()==0){
            return 0;
        }
        return cloudBeen.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(cloudBeen,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemDissmiss(int position) {
        AVQuery<AVObject> query = new AVQuery<>("myCollection");
        query.whereEqualTo("title",cloudBeen.get(position).getTitle());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for(int i=0;i<list.size();i++){
                    if(list.get(i).get("userId").equals(AVUser.getCurrentUser().getObjectId())){
                        list.get(i).deleteInBackground();
                        Log.d("456456",list.get(i).toString());
                    }
                }
            }
        });
        cloudBeen.remove(position);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title,date;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_cardView);
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            imageView = itemView.findViewById(R.id.item_img);
        }
    }
}
