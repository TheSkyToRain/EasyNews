package com.ebaryice.studio.easynews.Views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.ebaryice.studio.easynews.Adapter.CloudRecyclerAdapter;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.Bean.CloudBean;
import com.ebaryice.studio.easynews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class CollectView extends BaseActivity implements ICollectView{
    private CloudRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ImageButton back;
    private TextView text_toolbar;

    @Override
    public void init() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        text_toolbar.setText("我的收藏");
        find();
    }

    @Override
    public void find() {
        AVQuery<AVObject> query = new AVQuery<>("myCollection");
        query.whereEqualTo("userId",AVUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                List<CloudBean> been = new ArrayList<>();
                for (int i=0;i<list.size();i++){
                    CloudBean cloudBean = new CloudBean();
                    cloudBean.setTitle(list.get(i).getString("title"));
                    cloudBean.setCover(list.get(i).getString("cover"));
                    cloudBean.setUrl(list.get(i).getString("url"));
                    cloudBean.setDate(list.get(i).getString("date"));
                    been.add(cloudBean);
                }
                adapter = new CloudRecyclerAdapter(getActivity(),been);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.collect_view;
    }

    @Override
    protected void initView() {
        back = $(R.id.btn_back);
        text_toolbar = $(R.id.text_toolbar);
        recyclerView = $(R.id.collectionRecyclerView);
        init();
    }
    @Override
    public void back() {
        finish();
    }
}
