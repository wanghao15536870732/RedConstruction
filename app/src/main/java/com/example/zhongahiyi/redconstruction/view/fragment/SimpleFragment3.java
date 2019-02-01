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

public class SimpleFragment3 extends Fragment{

    private TextView mTextView;

    public static SimpleFragment3 newInstance(){
        Bundle args = new Bundle();
        SimpleFragment3 fragment = new SimpleFragment3();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate( R.layout.simple_fragment3,container,false );
       mTextView = view.findViewById( R.id.fragment );
        mTextView.setText("fragment3");
       return view;
    }
}
