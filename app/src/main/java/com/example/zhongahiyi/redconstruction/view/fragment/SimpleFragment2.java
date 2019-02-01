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

public class SimpleFragment2 extends Fragment{


    public static SimpleFragment2 newInstance(){
        Bundle args = new Bundle();
        SimpleFragment2 fragment = new SimpleFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate( R.layout.simple_fragment2,container,false );
       return view;
    }
}
