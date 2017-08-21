package com.ebaryice.studio.easynews.Views;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVOSCloud;
import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.Bean.NewsBean;
import com.ebaryice.studio.easynews.Model.NewsModel;
import com.ebaryice.studio.easynews.R;

import java.util.Timer;
import java.util.TimerTask;


public class StartView extends BaseActivity implements IStartView{
    private ImageView imageView;
    private CardView cardView;
    private NewsModel model;
    @Override
    protected int getContentViewId() {
        return R.layout.start_view;
    }

    @Override
    protected void initView() {
        imageView = $(R.id.start_img);
        cardView = $(R.id.card_skip);
        model = new NewsModel();
        setFirstPic(model);
        intent();
    }
    public void init(String url){
        Glide.with(getActivity()).load(url).into(imageView);
    }

    @Override
    public void intent() {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                quickIntent();
            }
        };
        timer.schedule(task,1000*3);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quickIntent();
                timer.cancel();
            }
        });
    }
    private void quickIntent(){
        Intent intent = new Intent(getActivity(),MainView.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
    private void  setFirstPic(NewsModel model){
        //取得头条第一张图片
        model.getNews("top", new NewsModel.OnGetNews() {
            @Override
            public void onFinish(Object bean) {
                NewsBean newsBean = (NewsBean) bean;
                String url = newsBean.getResult().getData().get(0).getThumbnail_pic_s();
                init(url);
            }
        });
    }
}
