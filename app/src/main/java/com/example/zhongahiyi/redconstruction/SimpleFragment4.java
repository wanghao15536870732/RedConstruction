package com.example.zhongahiyi.redconstruction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleFragment4 extends Fragment{

    private TextView mTextView;

    public static SimpleFragment4 newInstance(){
        Bundle args = new Bundle();
        SimpleFragment4 fragment = new SimpleFragment4();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate( R.layout.simple_fragment1,container,false );
       mTextView = view.findViewById( R.id.fragment );
        mTextView.setText("fragment4");
       return view;
    }
}
