package com.ebaryice.studio.easynews.Views;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.R;

/**
 * Created by Administrator on 2017/9/9/009.
 */

public class ShareView extends BaseActivity {
    private TextView title;
    private ImageButton back;
    private ImageView img;
    @Override
    protected int getContentViewId() {
        return R.layout.share_view;
    }

    @Override
    protected void initView() {
        title = $(R.id.text_toolbar);
        back = $(R.id.btn_back);
        img = $(R.id.share_img);
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
        Glide.with(this).load("http://s11.sinaimg.cn/bmiddle/62614be6t72bd0b6bb8ca&690").asGif().into(img);
    }
    private void back(){
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
