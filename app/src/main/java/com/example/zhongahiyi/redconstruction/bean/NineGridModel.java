package com.example.zhongahiyi.redconstruction.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

public class NineGridModel extends BmobObject implements Serializable{

    private static final long serialVersionUID = 2189052605715370758L;

    public List<String> urlList = new ArrayList<>();

    public boolean isShowAll = false;

    private String mAvatar, mContent, mName,mTime;

    private ArrayList<Comment> comments = new ArrayList<>(); // 发布时间


    public NineGridModel(){

    }

   public NineGridModel(String name,String avatar,String content,String time){
       this.mName = name;
       this.mAvatar = avatar;
       this.mContent = content;
       this.mTime = time;
   }

   public boolean hasComment(){
       return comments.size() > 0;
   }

    public ArrayList<Comment> getComments() {
        return comments;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }
}
