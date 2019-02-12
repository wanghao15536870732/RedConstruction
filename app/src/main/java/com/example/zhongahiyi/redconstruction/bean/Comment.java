package com.example.zhongahiyi.redconstruction.bean;

import android.text.Html;
import android.text.Spanned;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject{
    private Spanned comment;
    public Comment(String comment){
        this.comment = Html.fromHtml( "<font color='#4A766E'>zhaizu: </font>" + comment );
    }

    public Spanned getComment() {
        return comment;
    }
}
