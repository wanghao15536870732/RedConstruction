package com.example.zhongahiyi.redconstruction.bean;

import android.content.Intent;
import android.media.ExifInterface;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubjectRepertory {

    public static List<MySubject> loadDefaultSubjects(){

        //json
        String json="[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1周上\", 1, 1, 2, \"\", \"计算机综合楼106\", \"\"]," +
                "[\"2017-2018学年秋\", \"\", \"\", \"hahaha\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"2周上\", 1, 1, 4, \"\", \"计算机综合楼106\", \"\"],"+
                " [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1周\", 1, 3, 2, \"\", \"计算机综合楼205\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 1, 5, 2, \"\", \"3号教学楼3208\", \"\"]," +
                " [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 1, 9, 2, \"\", \"3号教学楼3101\", \"\"]," +
                " [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 2, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 2, 3, 2, \"\", \"计算机综合楼106\", \"\"]," +
                " [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 2, 9, 2, \"\", \"计算机综合楼205\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-17周上\", 3, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-12周\", 3, 3, 2, \"\", \"计算机综合楼205\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 3, 5, 2, \"\", \"3号教学楼3208\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-4周上\", 3, 7, 2, \"\", \"3号教学楼3101\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 3, 9, 2, \"\", \"计算机综合楼202\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 4, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\",4, 3, 2, \"\", \"计算机综合楼102\", \"\"]," +
                " [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 4, 5, 2, \"\", \"计算机综合楼202\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 4, 7, 2, \"\", \"3号教学楼3101\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 4, 9, 2, \"\", \"计算机综合楼205\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-18周上\", 5, 1, 2, \"\", \"计算机综合楼106\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 5, 3, 2, \"\", \"计算机综合楼106\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 5, 5, 2, \"\", \"3号教学楼3208\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 5, 7, 2, \"\", \"3号教学楼3101\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 5, 9, 2, \"\", \"计算机综合楼102\", \"\"], " +
                "[\"2017-2018学年秋\", \"\", \"\", \"形势与政策\", \"\", \"\", \"\", \"\", \"孔祥增\", \"\", \"\", \"9周上\", 7, 5, 4, \"\", \"3号教学楼3311\", \"\"]]";

        return parse(json);
    }

    private static List<MySubject> parse(String json) {
        List<MySubject> courses = new ArrayList<>(  );
        try{
            JSONArray array = new JSONArray( json );  //获取整个json
            for (int i = 0;i < array.length();i ++) {  //遍历
                JSONArray array2 = array.getJSONArray( i );  //第i节课
                String term = array2.getString( 0); //学期
                String name = array2.getString( 3 ); //课程名字
                String teacher = array2.getString( 8 ); //老师姓名

                if(array2.length() <= 10){
                    courses.add( new MySubject(name,null,teacher,null,-1,-1,-1,term,-1) );
                    continue;
                }
                String string = array2.getString( 17 );//获取
                if(string != null){
                    string = string.replaceAll("\\(.*?\\)","");
                }
                String room = array2.getString( 16 ) + string;  //教室位置
                String weeks = array2.getString( 11 );   //上课周数
                int day,start,step; //具体哪天，开始时间，结束时间
                try{
                    day = Integer.parseInt( array2.getString( 12 ) );  //转为Int型的数据
                    start = Integer.parseInt( array2.getString( 13 ) );
                    step = Integer.parseInt( array2.getString( 14 ) );
                }catch (Exception e){
                    day = -1;
                    start = -1;
                    step = -1;
                }
                courses.add( new MySubject(name,room,teacher,getWeekList(weeks), start, step, day, term,-1));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return courses;
    }

    private static List<Integer> getWeekList(String weeks) {
        List<Integer> weekList = new ArrayList<>( );
        if(weeks == null || weeks.length() == 0)
            return weekList;
        weeks = weeks.replaceAll("[^\\d\\-\\,]", "");
        if(weeks.indexOf( ",") != -1){  //存在"，"  即周内不止一天由课
            String[] arr = weeks.split( "," ); //由逗号分隔开
            for(int i = 0;i < arr.length;i ++){
                weekList.addAll( getWeekList2(  arr[i]));
            }
        }else{
            weekList.addAll( getWeekList2( weeks ) ); //只有一天由有课
        }
        return weekList;
    }

    private static List<Integer> getWeekList2(String weeksString) {
        List<Integer> weekList = new ArrayList<>(  );
        int first = -1,end = -1,index = -1;
        if((index = weeksString.indexOf( "-"))!= -1){  //判断是否有"-"
            first = Integer.parseInt( weeksString.substring( 0,index ) );  //截取"-"前面的
            end = Integer.parseInt( weeksString.substring( index + 1 ) ); //截取"-"后面的
        }else {
            first = Integer.parseInt( weeksString );
            end = first;
        }
        for (int i = first;i < end;i ++)
            weekList.add( i );
        return weekList;
    }
}
