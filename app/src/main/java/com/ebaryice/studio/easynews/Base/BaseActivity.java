package com.ebaryice.studio.easynews.Base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;


/**
 * Created by Administrator on 2017/8/16 0016.
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        initState();
        initView();
    }

    protected abstract int getContentViewId();

    protected abstract void initView();

    //适配于Android 4.4以上系统

    private void initState(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
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
