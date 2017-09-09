package com.ebaryice.studio.easynews.Views;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/8/19 0019.
 */

public class DataView extends BaseActivity {
    private static final int CODE_PHOTO_REQUEST = 1;
    private RoundedImageView icon_data;
    private TextView toolbar_text;
    private Button save;
    private ImageButton back;
    private MaterialEditText nickname_data, email_data;
    public static FinishSave listener;

    public interface FinishSave {
        void onFinish(String text);
    }

    public void change() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(intent, CODE_PHOTO_REQUEST);
    }

    public void init() {
        toolbar_text.setText("我的资料");
        AVUser user = AVUser.getCurrentUser();
        if (user != null){
            nickname_data.setText(AVUser.getCurrentUser().getString("nickname"));
            if (user.getEmail()==null){
                email_data.setText("");
            }
            email_data.setText(AVUser.getCurrentUser().getEmail());
            if(user.getAVFile("userIcon")==null){
                Log.d("userIcon","error没有头像");
                Glide.with(getActivity()).load(R.drawable.icon).into(icon_data);
            }
            else{
                Glide.with(getActivity()).load((user.getAVFile("userIcon")).getUrl()).asBitmap().override(100,100).into(icon_data);
            }
        }
        icon_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_data.getText().toString();
                String nickname = nickname_data.getText().toString();
                if (AVUser.getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                else if (nickname.length() == 0 || nickname.equals("点击登录")){
                    Toast.makeText(getActivity(), "请规范输入哦", Toast.LENGTH_SHORT).show();
                }
                else{
                    save(email, nickname);
                }
            }
        });
    }

    public void save(String email, String nickname) {
        AVUser.getCurrentUser().setEmail(email);
        AVUser.getCurrentUser().put("nickname", nickname);
        AVUser.getCurrentUser().saveInBackground();
        if (listener != null) {
            listener.onFinish("ok");
        }
        Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
        back();
    }

    public void back() {
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.data_view;
    }

    @Override
    protected void initView() {
        icon_data = $(R.id.icon_data);
        toolbar_text = $(R.id.text_toolbar);
        back = $(R.id.btn_back);
        save = $(R.id.save);
        nickname_data = $(R.id.nickname_data);
        email_data = $(R.id.email_data);
        init();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_CANCELED){
            //取消
            Toast.makeText(getActivity(),"取消",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode) {
            case CODE_PHOTO_REQUEST:
                final AVUser user = AVUser.getCurrentUser();
                final Uri uri = data.getData();
                Glide.with(getActivity()).load(uri).asBitmap().override(100,100).into(icon_data);
                String bitmapPath = getRealFilePath(this,uri);
                AVFile avFile = null;
                try {
                    if (bitmapPath != null) {
                        avFile = AVFile.withAbsoluteLocalPath("userIcon", bitmapPath);
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                if (avFile != null) {
                    user.put("userIcon",avFile);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                AVFile a = user.getAVFile("userIcon");
                                Log.d("a",a.getUrl());
                            }
                        }
                    });
                } else {
                    Log.d("avFile","error");
                }
                break;
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
