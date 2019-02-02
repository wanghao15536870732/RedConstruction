package com.example.zhongahiyi.redconstruction.view.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.bean.newses;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "news_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_detail );
        Intent intent = getIntent();
        final String newsTitle = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.news_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(newsTitle);
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load( newses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menus,menu);
        return super.onCreateOptionsMenu( menu );
    }
}