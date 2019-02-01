package com.example.zhongahiyi.redconstruction.bean;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.List;

public class MySubject implements ScheduleEnable{

    //广告
    public static final String EXTRAS_ID = "extras_id";
    public static final String EXTRAS_AD_URL = "extras_ad_url";

    private int id = 0;

    //课程名
    private String name;

    //教室
    private String room;

    //教师
    private String teacher;

    //上课周期（第几周到第几周上）
    private List<Integer> weekList;

    //开始上课的节次
    private int start;

    //上课节数
    private int step;

    //当前上课周
    private int day;

    //当前学期
    private String term;

    //一个随机函数，用于对应课程的颜色
    private int colorRandom = 0;
    private String url;

    public static String getExtrasId() {
        return EXTRAS_ID;
    }

    public static String getExtrasAdUrl() {
        return EXTRAS_AD_URL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Integer> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getColorRandom() {
        return colorRandom;
    }

    public void setColorRandom(int colorRandom) {
        this.colorRandom = colorRandom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MySubject(){

    }

    public MySubject(String name, String room, String teacher, List<Integer> weekList, int start, int step, int day, String term, int colorRandom) {
        this.name = name;
        this.room = room;
        this.teacher = teacher;
        this.weekList = weekList;
        this.start = start;
        this.step = step;
        this.day = day;
        this.term = term;
        this.colorRandom = colorRandom;
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule = new Schedule(  );
        schedule.setDay( getDay() );
        schedule.setName( getName() );
        schedule.setRoom( getRoom() );
        schedule.setStart( getStart() );
        schedule.setStep( getStep() );
        schedule.setTeacher( getTeacher());
        schedule.setWeekList( getWeekList() );
        schedule.setColorRandom( 2 );
        schedule.putExtras( EXTRAS_ID,getId() );
        schedule.putExtras( EXTRAS_AD_URL,getUrl() );
        return schedule;
    }
}
