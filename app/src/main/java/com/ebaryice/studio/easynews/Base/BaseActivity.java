package com.ebaryice.studio.easynews.Base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


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

    private void initState(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    public Activity getActivity() {
        return this;
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
