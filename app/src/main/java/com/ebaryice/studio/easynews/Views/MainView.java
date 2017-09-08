package com.ebaryice.studio.easynews.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.ebaryice.studio.easynews.Adapter.MyPagerAdapter;
import com.ebaryice.studio.easynews.Base.BaseActivity;
import com.ebaryice.studio.easynews.Fragment.NewsFragment;
import com.ebaryice.studio.easynews.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class MainView extends BaseActivity implements IMainView {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textView,username;
    private RoundedImageView roundedImageView,icon;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyPagerAdapter adapter;
    private String[] title = {"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    private String[] type = {"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};
    private List<Fragment> list;
    AVUser user ;
    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public void init() {
        list = new ArrayList<>();
        for (int i = 0;i<title.length;i++){
            list.add(new NewsFragment(type[i]));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                textView.setText(title[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (user==null||user.getAVFile("userIcon")==null){
            Glide.with(getActivity()).load(R.drawable.icon).into(roundedImageView);
            Glide.with(getActivity()).load(R.drawable.icon).into(icon);

        }else{
            Glide.with(getActivity()).load(user.getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(roundedImageView);
            Glide.with(getActivity()).load(user.getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(icon);
        }
        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setCheckedItem(R.id.read);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("点击登录")){
                    toLogin();
                }
                else{
                    toLogout();
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.read:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.myData:
                        toMyData();
                        break;
                    case R.id.myCollection:
                        toMyCol();
                        break;
                    case R.id.share:
                        break;
                    case R.id.aboutDeveloper:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void toLogin() {
        Intent intent = new Intent(getActivity(),LoginView.class);
        getActivity().startActivity(intent);
        LoginView.listener = new LoginView.FinishLogin() {
            @Override
            public void onFinish(String text) {
                if (AVUser.getCurrentUser().getAVFile("userIcon") != null){
                    Glide.with(getActivity()).load(AVUser.getCurrentUser().getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(roundedImageView);
                    Glide.with(getActivity()).load(AVUser.getCurrentUser().getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(icon);
                }
                username.setText(AVUser.getCurrentUser().getString("nickname"));
            }
        };
    }

    @Override
    public void toMyData() {
        Intent intent = new Intent(getActivity(),DataView.class);
        getActivity().startActivity(intent);
        DataView.listener = new DataView.FinishSave() {
            @Override
            public void onFinish(String text) {
                if (AVUser.getCurrentUser().getAVFile("userIcon") == null){
                    Glide.with(getActivity()).load(R.drawable.icon).into(roundedImageView);
                    Glide.with(getActivity()).load(R.drawable.icon).into(icon);
                }else {
                    Glide.with(getActivity()).load(AVUser.getCurrentUser().getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(roundedImageView);
                    Glide.with(getActivity()).load(AVUser.getCurrentUser().getAVFile("userIcon").getUrl()).asBitmap().override(100,100).into(icon);
                }
                username.setText(AVUser.getCurrentUser().getString("nickname"));
            }
        };
    }

    @Override
    public void toMyCol() {
        if(AVUser.getCurrentUser()==null){
            Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(getActivity(),CollectView.class);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void toLogout() {
        Intent intent = new Intent(getActivity(),LogoutView.class);
        getActivity().startActivity(intent);
        LogoutView.listener = new LogoutView.FinishLogout() {
            @Override
            public void onFinish(String text) {
                Glide.with(getActivity()).load(R.drawable.icon).asBitmap().override(100,100).into(roundedImageView);
                Glide.with(getActivity()).load(R.drawable.icon).asBitmap().override(100,100).into(icon);
                username.setText("点击登录");
            }
        };
    }

    @Override
    protected int getContentViewId() {
        return R.layout.main_view;
    }
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            this.finish();
        }
    }
    @Override
    protected void initView() {
        user = AVUser.getCurrentUser();
        drawerLayout = $(R.id.drawer_layout);
        navigationView = $(R.id.nav_view);
        textView = $(R.id.toolbar_text);
        roundedImageView = $(R.id.icon);
        viewPager = $(R.id.viewpager);
        tabLayout = $(R.id.tablayout);
        View headView = navigationView.getHeaderView(0);
        username = headView.findViewById(R.id.name_user);
        if(AVUser.getCurrentUser() != null){
            username.setText(AVUser.getCurrentUser().getString("nickname"));
        }
        icon = headView.findViewById(R.id.icon_user);
        init();
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.setTitles(title);
        adapter.setFragments(list);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
