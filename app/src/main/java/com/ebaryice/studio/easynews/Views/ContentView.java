package com.ebaryice.studio.easynews.Views;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
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

public class ContentView extends BaseActivity{
    private WebView webView;
    private Intent intent;
    private ImageButton back,collect;
    public static FinishCollect listener;
    private float mPosX,mPosY,mCurPosX,mCurPosY;
    public interface FinishCollect{
        void onFinish(String text);
    }

    private void init() {
        intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(url);
        setFling(webView,getActivity());
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

    private void back() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void collect() {
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
    private void setFling(View view, Context context){
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mPosX = motionEvent.getX();
                        mPosY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = motionEvent.getX();
                        mCurPosY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(mCurPosY-mPosY<30&&mCurPosX-mPosX>=65){
                            back();
                        }
                }
                return true;
            }
        });
    }
}
