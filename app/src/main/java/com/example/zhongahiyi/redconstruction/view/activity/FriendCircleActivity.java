package com.example.zhongahiyi.redconstruction.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.adapter.NineGridAdapter;
import com.example.zhongahiyi.redconstruction.bean.NineGridModel;

import java.util.ArrayList;
import java.util.List;

public class FriendCircleActivity extends AppCompatActivity {

    private List<NineGridModel> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NineGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_friend_circle );
        initListData();
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NineGridAdapter(this);
        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);
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
        mList.add(model1);

        NineGridModel model2 = new NineGridModel();
        model2.urlList.add(mUrls[4]);
        mList.add(model2);
//
//        NineGridTestModel model3 = new NineGridTestModel();
//        model3.urlList.add(mUrls[2]);
//        mList.add(model3);

        NineGridModel model4 = new NineGridModel();
        for (int i = 0; i < mUrls.length; i++) {
            model4.urlList.add(mUrls[i]);
        }
        model4.isShowAll = false;
        mList.add(model4);

        NineGridModel model5 = new NineGridModel();
        for (int i = 0; i < mUrls.length; i++) {
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
}