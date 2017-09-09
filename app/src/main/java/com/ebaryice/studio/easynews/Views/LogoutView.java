package com.ebaryice.studio.easynews.Views;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class LogoutView extends BaseActivity {
    private ImageButton back;
    private Button logout;
    private TextView toolbar_text,username,nickname;
    private RoundedImageView icon;
    public static FinishLogout listener;
    public interface FinishLogout{
        void onFinish(String text);
    }
    @Override
    protected int getContentViewId() {
        return R.layout.logout_view;
    }

    @Override
    protected void initView() {
        icon = $(R.id.icon_logout);
        toolbar_text = $(R.id.text_toolbar);
        back = $(R.id.btn_back);
        logout = $(R.id.logout);
        username = $(R.id.username_logout);
        nickname = $(R.id.nickname_logout);
        init();
    }

    private void init() {
        toolbar_text.setText("退出登录");
        if (AVUser.getCurrentUser().getAVFile("userIcon")!=null){

            Glide.with(getActivity()).load(AVUser.getCurrentUser().getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(icon);

        }
        username.setText(AVUser.getCurrentUser().getUsername().toString());
        nickname.setText(AVUser.getCurrentUser().getString("nickname"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        AVUser.getCurrentUser().logOut();
        if(listener!=null){
            listener.onFinish("ok");
        }
        SharedPreferences.Editor editor = getSharedPreferences("currentUser",MODE_PRIVATE).edit();
        editor.putString("username","");
        editor.putString("password","");
        editor.commit();
        Toast.makeText(getActivity(),"退出登录",Toast.LENGTH_SHORT).show();
        back();
    }

    private void back() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
