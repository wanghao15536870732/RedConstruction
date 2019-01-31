package com.example.zhongahiyi.redconstruction;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;


public class PageAdapter extends FragmentPagerAdapter {

    private final int COUNT = 5;  //总共有5张fragment
    public Context mContext;

    public PageAdapter(FragmentManager fm,Context context) {
        super( fm );
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SimpleFragment1.newInstance();
            case 1:
                return SimpleFragment2.newInstance();
            case 2:
                return SimpleFragment3.newInstance();
            case 3:
                return SimpleFragment4.newInstance();
            case 4:
                return SimpleFragment5.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
