package com.ebaryice.studio.easynews.Views;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.R;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class LoginView extends BaseActivity implements ILoginView{
    private EditText account,password;
    private Button signIn,register;
    private ImageButton back;
    private String username,psw;
    public static FinishLogin listener;

    public interface FinishLogin{
        void onFinish(String text);
    }

    @Override
    public void login(final String username, String psw) {
        AVUser.logInInBackground(username, psw, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null){
                    if(listener != null){
                        listener.onFinish("ok");
                    }
                    Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getActivity(),"密码错误或者用户名不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void register(final String username, String psw) {
        final AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(psw);
        user.put("nickname",username);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Toast.makeText(getActivity(),"注册成功,快去登录吧",Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("123456",e.toString());
                    Toast.makeText(getActivity(),"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void back() {
        getActivity().finish();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.login_view;
    }

    @Override
    protected void initView() {
        account = $(R.id.input_account);
        password = $(R.id.input_password);
        signIn = $(R.id.signIn);
        register = $(R.id.register);
        back = $(R.id.btn_back);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = account.getText().toString();
                psw = password.getText().toString();
                if (username.length()!=11||psw.length()>16||psw.length()<9){
                    Toast.makeText(getActivity(),"请规范输入哦",Toast.LENGTH_SHORT).show();
                }
                else {
                    register(username,psw);
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = account.getText().toString();
                psw = password.getText().toString();
                if(username.length()!=0&&psw.length()!=0){
                    login(username,psw);
                }
                else{
                    Toast.makeText(getActivity(),"请规范输入哦",Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
}
