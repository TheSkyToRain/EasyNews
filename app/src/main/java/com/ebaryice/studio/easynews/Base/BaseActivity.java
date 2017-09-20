package com.ebaryice.studio.easynews.Base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ebaryice.studio.easynews.R;

import java.lang.reflect.Field;


/**
 * Created by Administrator on 2017/8/16 0016.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition explode = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        }
        //进入时使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(explode);
        }
        initState();
        setContentView(getContentViewId());
        initView();
    }

    protected abstract int getContentViewId();

    protected abstract void initView();

    //适配于Android 4.4以上系统
    private void initState(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //手机下部虚拟键透明
//            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
    }
    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 通过反射得到状态栏的高度（noUse）
     * @return
     */
    private int getStatusBarHeight(){
        try{
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 快速找id,拒绝knife
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T $(int id){
        return (T) findViewById(id);
    }
}
