package com.ebaryice.studio.easynews.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17 0017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    String[] mTitles;
    List<Fragment> mFragments;

    public void setTitles(String[] titles){
        this.mTitles = titles;
    }
    public void setFragments(List<Fragment> fragments){
        this.mFragments = fragments;
    }
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
