package com.example.zhongahiyi.redconstruction.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhongahiyi.redconstruction.R;

public class SimpleFragment5 extends Fragment{

    private TextView mTextView;

    public static SimpleFragment5 newInstance(){
        Bundle args = new Bundle();
        SimpleFragment5 fragment = new SimpleFragment5();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate( R.layout.simple_fragment5,container,false );
       mTextView = view.findViewById( R.id.fragment );
        mTextView.setText("fragment5");
       return view;
    }
}
