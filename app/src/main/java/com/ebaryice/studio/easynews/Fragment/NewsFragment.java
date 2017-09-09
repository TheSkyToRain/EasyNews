package com.ebaryice.studio.easynews.Fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.OvershootInterpolator;

import com.ebaryice.studio.easynews.Adapter.NewsRecyclerAdapter;
import com.ebaryice.studio.easynews.Base.BaseFragment;
import com.ebaryice.studio.easynews.Bean.NewsBean;
import com.ebaryice.studio.easynews.Model.NewsModel;
import com.ebaryice.studio.easynews.R;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class NewsFragment extends BaseFragment {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private NewsRecyclerAdapter adapter;
    private String type;
    private NewsModel model;
    public NewsFragment(String type){
        this.type = type;
    }
    @Override
    protected void initView() {
        refreshLayout = $(R.id.refresh);
        recyclerView = $(R.id.recyclerView);
        refreshLayout.setColorSchemeColors(Color.parseColor("#29b6f6"));
        refreshLayout.setProgressViewEndTarget(true,150);
        model = new NewsModel();
        loading();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    protected int getResourceId() {
        return R.layout.news_fragment;
    }
    private void loading(){
        model.getNews(type, new NewsModel.OnGetNews() {
            @Override
            public void onFinish(Object bean) {
                NewsBean newsBean = (NewsBean) bean;
                adapter = new NewsRecyclerAdapter(newsBean,getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                recyclerView.setItemAnimator(new SlideInLeftAnimator());
            }
        });
    }
    private void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading();
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
