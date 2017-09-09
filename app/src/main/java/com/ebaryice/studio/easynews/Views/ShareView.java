package com.ebaryice.studio.easynews.Views;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.R;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class ShareView extends BaseActivity {
    private TextView title;
    private ImageButton back;
    @Override
    protected int getContentViewId() {
        return R.layout.share_view;
    }

    @Override
    protected void initView() {
        title = $(R.id.text_toolbar);
        back = $(R.id.btn_back);
        init();
    }
    private void init(){
        title.setText("推荐一下");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private void back(){
        finish();
    }
}
