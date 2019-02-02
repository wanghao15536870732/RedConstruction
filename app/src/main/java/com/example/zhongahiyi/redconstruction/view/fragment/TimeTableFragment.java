package com.example.zhongahiyi.redconstruction.view.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.bean.MySubject;
import com.example.zhongahiyi.redconstruction.bean.SubjectRepertory;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;
import com.zhuangfei.timetable.view.WeekView;

import java.util.ArrayList;
import java.util.List;

public class TimeTableFragment extends Fragment implements View.OnClickListener{
    public static final String AD_URL="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545749786636&di=fd5483be8b08b2e1f0485e772dadace4&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F5f9fae85770bb289f790e08d778516d128f0492a114a8-TNyOSi_fw658";
    private TimetableView mTimetableView;
    private WeekView mWeekView;

    private LinearLayout mLayout;
    private List<MySubject> mySubjects;

    private TextView titleTextView;
    //记录切换的周次，不一定是当前周
    int target = -1;
    AlertDialog alertDialog;

    public static TimeTableFragment newInstance(){
        Bundle args = new Bundle();
        TimeTableFragment fragment = new TimeTableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate( R.layout.timetable_fragment,container,false );
       mLayout = view.findViewById( R.id.id_layout );
       mLayout.setOnClickListener( this );
       titleTextView = (TextView) view.findViewById( R.id.id_textView );
       initTimetableView(view);
       requestData();
       return view;
    }

    //模拟网络请求
    private void requestData(){
        alertDialog = new AlertDialog.Builder(getContext())
                .setMessage( "模拟网络请求中..." )
                .setTitle( "Tips" ).create();
        alertDialog.show();
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep( 1000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                handler.sendEmptyMessage( 0x123 );
            }
        } ).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage( msg );
            if (alertDialog != null)
                alertDialog.hide();
            mySubjects = SubjectRepertory.loadDefaultSubjects();
            //增加广告
            MySubject adSubject = new MySubject();
            adSubject.setName("【广告】");
            adSubject.setStart(1);
            adSubject.setStep(2);
            adSubject.setDay(7);
            List<Integer> list= new ArrayList<>();
            for(int i = 1;i <= 20;i++)
                list.add(i);
            adSubject.setWeekList(list);
            adSubject.setUrl(AD_URL);
            mySubjects.add(adSubject);

            mWeekView.source( mySubjects ).showView();
            mTimetableView.source( mySubjects ).showView();
        }
    };

    private void initTimetableView(View view) {
        mWeekView = view.findViewById( R.id.id_weekView );
        mTimetableView = view.findViewById( R.id.id_timetableView );
        mTimetableView.setBackgroundResource( R.drawable.timetable_back );
        //设置周次
        mWeekView.curWeek( 1 )
                .callback( new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cir = mTimetableView.curWeek();
                        //更新切换后的日期,从当前cur->切换的week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate( cir,week );
                        //课表切换周次
                        mTimetableView.changeWeekOnly( week );
                    }
                } )
                .callback( new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        onWeekLeftLayoutClick();
                    }
                } )
                .isShow( false ) //取消隐藏，默认显示
                .showView();
        mTimetableView.curWeek(1)
                .curTerm("大二下学期")
                .maxSlideItem( 13 )
                .monthWidthDp( 40 )
                .callback( new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display( scheduleList );
                    }
                } )

                .callback( new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(getContext(),
                                "当前节次为：第" + day  + "周的" + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                } )
                .callback( new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        titleTextView.setText( "第" + curWeek + "周" );
                    }
                } )
                .callback( new OnItemBuildAdapter(){
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate( layout, textView, countTextView, schedule, gd );
                        if(schedule.getName().equals("【广告】")) {
                            layout.removeAllViews();
                            ImageView imageView = new ImageView( getContext() );
                            imageView.setLayoutParams( new FrameLayout.LayoutParams( FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT ) );
                            layout.addView( imageView );
                            String url = (String) schedule.getExtras().get( MySubject.EXTRAS_AD_URL );

                            Glide.with( getContext() )
                                    .load( url )
                                    .into( imageView );

                            imageView.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText( getContext(), "进入广告网页链接", Toast.LENGTH_SHORT ).show();
                                }
                            } );
                        }
                    }
                })
                .callback( new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mTimetableView.hideDateView();
                        Toast.makeText(getContext(),
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                } )
                .showView();
        addColor( R.color.colorPrimary,R.color.app_course_textcolor_blue,R.color.theme_yellow );
        showTime();  //显示时间
    }

    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    public void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }


    protected void onWeekLeftLayoutClick(){
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle( "设置当前周");
        builder.setSingleChoiceItems( items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        target = which;
                    }
                } );
        builder.setPositiveButton( "设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(target != -1){//已经选过
                    mWeekView.curWeek( target + 1).updateView();
                    mTimetableView.changeWeekForce( target + 1 );
                    hideWeekView();
                }
            }
        } );
        builder.setNegativeButton( "取消",null );
        builder.create().show();
    }

    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + ","+bean.getWeekList().toString()+","+bean.getStart()+","+bean.getStep()+"\n";
        }
        Toast.makeText( getContext(), str, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_layout:
                //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                //否则，显示
                if (mWeekView.isShowing()) hideWeekView();
                else showWeekView();
                break;
        }
    }

    /**
     * 隐藏周次选择，此时需要将课表的日期恢复到本周并将课表切换到当前周
     */
    public void hideWeekView(){
        mWeekView.isShow(false);
        titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener()
                .onUpdateDate(cur, cur);
        mTimetableView.changeWeekOnly(cur);
    }

    public void showWeekView(){
        mWeekView.isShow(true);
        titleTextView.setTextColor(getResources().getColor(R.color.app_red));
    }

    //删除课程

    protected void deleteSubject(){
        int size = mTimetableView.dataSource().size();
        int pos = (int) (Math.random() * size);
        if(size > 0){
            mTimetableView.dataSource().remove( pos );
            mTimetableView.updateView();
        }
    }

    //添加课程
    protected void addSubjext(){
        List<Schedule> dataSource = mTimetableView.dataSource();
        int size = dataSource.size();
        if(size > 0){
            Schedule schedule = dataSource.get( 0 );
            dataSource.add( schedule );
            mTimetableView.updateView();
        }
    }

    //非本周课程显示与隐藏
    protected void hideNonThisWeek(){
        mTimetableView.isShowNotCurWeek(false).updateView();
    }

    //显示非本周课程
    protected void showNonThisWeek(){
        mTimetableView.isShowNotCurWeek(true).updateView();
    }


    /**
     * 显示时间
     */
    protected void showTime() {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30","21:30","22:30"
        };
        OnSlideBuildAdapter listener= (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTimes(times)
                .setTimeTextColor( Color.BLACK);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     */
    protected void hideTime() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏周末
     */
    private void hideWeekends() {
        mTimetableView.isShowWeekends(false).updateView();
    }

    /**
     * 显示周末
     */
    private void showWeekends() {
        mTimetableView.isShowWeekends(true).updateView();
    }

    /**
     * 修改侧边栏背景
     */
    protected void modifySlideBgColor(int color) {
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setBackground(color);
        mTimetableView.updateSlideView();
    }

    //颜色池添加颜色
    public void addColor(int... colors){
        mTimetableView.colorPool().add(colors);
        mTimetableView.updateView();
    }
}
