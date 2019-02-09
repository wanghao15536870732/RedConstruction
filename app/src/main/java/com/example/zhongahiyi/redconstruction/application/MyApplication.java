package com.example.zhongahiyi.redconstruction.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        Bmob.initialize(this, "d8d9254b5fa68f0087ce3b1ace6fabba");
    }

    private void initImageLoader(){
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault( this );
        ImageLoader.getInstance().init( configuration );
    }
}
