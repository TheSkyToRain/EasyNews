package com.ebaryice.studio.easynews.Adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Bean.NewsBean;
import com.ebaryice.studio.easynews.R;
import com.ebaryice.studio.easynews.Views.ContentView;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder> {

    private NewsBean bean;
    private Context context;
    public NewsRecyclerAdapter(NewsBean bean, Context context){
        this.bean = bean;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewsBean.ResultBean.DataBean dataBean = bean.getResult().getData().get(position);
        Glide.with(context).load(dataBean.getThumbnail_pic_s()).into(holder.imageView);
        holder.title.setText(dataBean.getTitle());
        holder.date.setText(dataBean.getDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentView.listener = new ContentView.FinishCollect() {
                    @Override
                    public void onFinish(String text) {
                        AVObject avObject = new AVObject("myCollection");
                        avObject.put("userId",AVUser.getCurrentUser().getObjectId());
                        avObject.put("title",dataBean.getTitle());
                        avObject.put("date",dataBean.getDate());
                        avObject.put("cover",dataBean.getThumbnail_pic_s());
                        avObject.put("url",dataBean.getUrl());
                        avObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if(e == null){
                                    Log.d("123123","成功");
                                }
                            }
                        });
                    }
                };
                Intent intent = new Intent(context, ContentView.class);
                intent.putExtra("url",dataBean.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getResult().getData().size();
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
