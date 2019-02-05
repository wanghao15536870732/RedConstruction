package com.example.zhongahiyi.redconstruction.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zhongahiyi.redconstruction.R;

public class MessageActivity extends AppCompatActivity {

    private ImageView mImageView,imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message );
        mImageView = (ImageView) findViewById( R.id.imageView );
        imageView = (ImageView) findViewById( R.id.image_view );
        Glide.with( this ).load( "http://pmb04cwi5.bkt.clouddn.com/image.jpg" )
                .asBitmap().into( mImageView );
        Glide.with( this ).load( "http://pmb04cwi5.bkt.clouddn.com/download.jpg" )
                .asBitmap().into( imageView );
    }


}