package com.example.zhongahiyi.redconstruction.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.adapter.SimpleStringRecyclerViewAdapter;
import com.example.zhongahiyi.redconstruction.bean.NewsGson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsFragment extends Fragment {


    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments( args );
        return fragment;
    }

    public interface ApiService{

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.news_fragment, container, false );
        RecyclerView recyclerView = view.findViewById( R.id.recyclerView_news );
        recyclerView.setLayoutManager( new LinearLayoutManager( recyclerView.getContext() ) );
        recyclerView.setAdapter( new SimpleStringRecyclerViewAdapter( getActivity(),
                getRandomSublist( NewsGson.sCheeseStrings, 30 ) ) );
        //添加边框
        return view;
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>( amount );
        Random random = new Random();
        while (list.size() < amount) {
            list.add( array[random.nextInt( array.length )] );
        }
        return list;
    }
}
