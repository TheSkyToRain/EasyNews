package com.ebaryice.studio.easynews.Views;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.R;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public class ContentView extends BaseActivity implements IContentView{
    private WebView webView;
    private Intent intent;
    private ImageButton back,collect;
    public static FinishCollect listener;
    public interface FinishCollect{
        void onFinish(String text);
    }
    @Override
    public void init() {
        intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(url);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AVUser.getCurrentUser()==null){
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                }
                else {
                    collect();
                    Toast.makeText(getActivity(),"已收藏",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void collect() {
        if (listener!=null){
            listener.onFinish("ok");
        }
        collect.setImageResource(R.drawable.collect2);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.content_view;
    }

    @Override
    protected void initView() {
        back = $(R.id.btn_back);
        webView = $(R.id.webview);
        collect = $(R.id.btn_col);
        init();
    }
}
