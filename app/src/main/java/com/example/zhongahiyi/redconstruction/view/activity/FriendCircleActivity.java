package com.example.zhongahiyi.redconstruction.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.adapter.NineGridAdapter;
import com.example.zhongahiyi.redconstruction.bean.Comment;
import com.example.zhongahiyi.redconstruction.bean.NineGridModel;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.util.V;

public class FriendCircleActivity extends AppCompatActivity implements
        View.OnClickListener{

    private List<NineGridModel> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NineGridAdapter mAdapter;
    private ImageView mImageView,imageView,avatar,ic_back;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private View mCommentView;

    /*与悬浮按钮相关*/
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton mAddDynamic,mScrollToUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_friend_circle );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
        initListData();
        initView();
        initFloatButton();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd( true );  //列表从底部开始展示
        mLayoutManager.setReverseLayout( true );  //列表反转
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NineGridAdapter(this);
        mAdapter.setList(mList);
        mAdapter.setCommentListener( new NineGridAdapter.OnCommentListener() {
            @Override
            public void onComment(final int position) {
                mCommentView.setVisibility( View.VISIBLE );
                mCommentView.findViewById( R.id.submit ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText et = (EditText) mCommentView.findViewById( R.id.edit );
                        String s = et.getText().toString();

                        if (!TextUtils.isEmpty( s )){
                            Comment comment = new Comment( s );
                            comment.save( new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {

                                }
                            } );
                            mList.get( position ).getComments().add( comment);
                            et.setText("");
                            mCommentView.setVisibility( View.GONE );
                        }
                    }
                } );
            }
        } );
        mRecyclerView.setAdapter(mAdapter);
        mImageView = (ImageView) findViewById( R.id.imageView_friend );
        imageView = (ImageView) findViewById( R.id.view_back );
        avatar = (ImageView) findViewById( R.id.avatar_mine );
        ic_back = (ImageView) findViewById( R.id.view_back );
        mCommentView = findViewById( R.id.comment_view );
        ic_back.setOnClickListener( this );
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById( R.id.collapsing_friend );
        mAppBarLayout = (AppBarLayout) findViewById( R.id.appBarLayout_friend );
        mCollapsingToolbarLayout.setTitle( " " );
        mCollapsingToolbarLayout.setExpandedTitleMarginStart( 0 );
        mAppBarLayout.addOnOffsetChangedListener( new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs( verticalOffset ) >= mAppBarLayout.getTotalScrollRange()){
                    mCollapsingToolbarLayout.setTitle( "朋友圈" );
                    imageView.setImageResource( R.drawable.ic_backs );
                }else {
                    mCollapsingToolbarLayout.setTitle( " " );
                    imageView.setImageResource( R.drawable.ic_back);
                }
            }
        } );
        Glide.with( this ).load( "http://pmb04cwi5.bkt.clouddn.com/image.jpg" )
                .into( mImageView );
        Glide.with( this ).load( "http://pmb04cwi5.bkt.clouddn.com/uri.jpg" )
                .into( avatar );
    }

    private void initFloatButton(){
        mFloatingActionsMenu = (FloatingActionsMenu) findViewById( R.id.main_actions_menu );
        mAddDynamic = (FloatingActionButton) findViewById( R.id.add_dynamic );
        mScrollToUp = (FloatingActionButton) findViewById( R.id.scroll_to_up );
        mAddDynamic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(FriendCircleActivity.this,DynamicActivity.class ));
            }
        } );
    }


    private String[] mUrls = new String[]{
            "http://pmb04cwi5.bkt.clouddn.com/pic1.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_2.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_3.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_4.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_5.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_6.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_7.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_8.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_9.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_10.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_11.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_12.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_13.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_14.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_15.jpg",
            "http://pmb04cwi5.bkt.clouddn.com/pic_16.jpg"
    };


    private void initListData() {
        NineGridModel model1 = new NineGridModel();
        model1.urlList.add(mUrls[0]);
        model1.setName( "水星记" );
        model1.setAvatar( "http://pmb04cwi5.bkt.clouddn.com/uri.jpg" );
        model1.setTime( "刚刚" );
        mList.add(model1);

        NineGridModel model2 = new NineGridModel();
        model2.urlList.add(mUrls[4]);
        mList.add(model2);

        NineGridModel model3 = new NineGridModel();
        model3.urlList.add(mUrls[2]);
        mList.add(model3);

        NineGridModel model4 = new NineGridModel();
        for (int i = 0; i < mUrls.length; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = false;
        mList.add(model4);

        NineGridModel model5 = new NineGridModel();
        for (int i = 6; i < mUrls.length; i++) {
            model5.urlList.add(mUrls[i]);
        }
        model5.isShowAll = true;//显示全部图片
        mList.add(model5);

        NineGridModel model6 = new NineGridModel();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        mList.add(model6);

        NineGridModel model7 = new NineGridModel();
        for (int i = 3; i < 7; i++) {
            model7.urlList.add(mUrls[i]);
        }
        mList.add(model7);

        NineGridModel model8 = new NineGridModel();
        for (int i = 3; i < 6; i++) {
            model8.urlList.add(mUrls[i]);
        }
        mList.add(model8);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_back:
                finish();
                break;
            default:
                break;
        }
    }
}