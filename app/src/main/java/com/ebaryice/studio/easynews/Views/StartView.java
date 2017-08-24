package com.ebaryice.studio.easynews.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
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
        AVOSCloud.initialize(this,"ma8UB1XfoKqUhwtaK4tFUER3-gzGzoHsz","cNPB5u0j2M2Ufd1EIaFdGFLD");
        imageView = $(R.id.start_img);
        cardView = $(R.id.card_skip);
        model = new NewsModel();
        setFirstPic(model);
        intent();
        SharedPreferences pref = getSharedPreferences("currentUser",MODE_PRIVATE);
        String username = pref.getString("username","");
        String password = pref.getString("password","");
        if (username.length()!=0&&password.length()!=0){
            preLogin(username,password);
        }
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

    @Override
    public void preLogin(String username,String password) {
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e==null){
                        Toast.makeText(getActivity(),"你好,"+avUser.getString("nickname"),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("0001",e.toString());
                    }
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
