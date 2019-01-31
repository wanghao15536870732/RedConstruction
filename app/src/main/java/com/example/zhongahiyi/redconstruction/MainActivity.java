package com.example.zhongahiyi.redconstruction;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Toolbar toolbar =  (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,mDrawerLayout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator( R.drawable.ic_menu );
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setHomeButtonEnabled( true);
        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );

//        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
//        fab.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make( view,"Click to add new writings.",Snackbar.LENGTH_LONG)
//                        .setAction( "Action",null ).show();
//            }
//        } );
        initViewPager();
    }

    private void initViewPager() {
        final ViewPager viewPager = (ViewPager) findViewById( R.id.vp_horizontal_ntb);
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter( adapter);
        final String[] colors = getResources().getStringArray( R.array.default_preview );
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById( R.id.ntb_horizontal );
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable( R.drawable.model1_pic),
                        Color.parseColor( colors[0])).
                        selectedIcon( getResources().getDrawable( R.drawable.model1_pic_change ))
                        .title( "党课课表" )
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.model2_pic ),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.model2_pic_change ))
                        .title("法律新闻")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.model2_pic ),
                        Color.parseColor(colors[3]))
                   //     .selectedIcon(getResources().getDrawable(R.drawable.model2_pic_change ))
                        .title("法律新闻")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.model2_pic ),
                        Color.parseColor(colors[4]))
                       // .selectedIcon(getResources().getDrawable(R.drawable.model2_pic_change ))
                        .title("法律新闻")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.model2_pic ),
                        Color.parseColor(colors[3]))
                        //     .selectedIcon(getResources().getDrawable(R.drawable.model2_pic_change ))
                        .title("法律新闻")
                        .badgeTitle("new")
                        .build()
        );
        navigationTabBar.setModels( models );
        navigationTabBar.setViewPager( viewPager,0 );
        navigationTabBar.setBehaviorEnabled( true );
        navigationTabBar.setOnTabBarSelectedIndexListener( new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                model.hideBadge();
            }
        } );

        navigationTabBar.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationTabBar.getModels().get( position ).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );

        navigationTabBar.postDelayed( new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < navigationTabBar.getModels().size();i ++){
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get( i );
                    navigationTabBar.postDelayed( new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    } ,i * 100);
                }
            }
        },500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menus,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //菜单栏点击事件
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        mDrawerLayout.closeDrawer( GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
    }
}
