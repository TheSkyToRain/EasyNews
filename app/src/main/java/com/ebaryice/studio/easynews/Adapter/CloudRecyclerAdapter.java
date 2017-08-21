package com.ebaryice.studio.easynews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Bean.CloudBean;
import com.ebaryice.studio.easynews.R;
import com.ebaryice.studio.easynews.Views.ContentView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class CloudRecyclerAdapter extends RecyclerView.Adapter<CloudRecyclerAdapter.MyViewHolder>{

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
        return cloudBeen.size();
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
