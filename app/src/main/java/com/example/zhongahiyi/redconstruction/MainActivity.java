package com.example.zhongahiyi.redconstruction;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar =  (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator( R.drawable.ic_menu );
        actionBar.setDisplayHomeAsUpEnabled( true );

        mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        if (navigationView != null){
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById( R.id.viewpager );
        if (viewPager != null){
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view,"Click to add new writings.",Snackbar.LENGTH_LONG)
                        .setAction( "Action",null ).show();
            }
        } );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menus,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()){
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            mDrawerLayout.openDrawer( GravityCompat.START);
            return true;
        }else if(item.getItemId() == R.id.menu_night_mode_system){
            setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }else if(item.getItemId() == R.id.menu_night_mode_day){
            setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else if(item.getItemId() == R.id.menu_night_mode_night){
            setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else if(item.getItemId() == R.id.menu_night_mode_auto){
            setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        }
        return super.onOptionsItemSelected( item );
    }

    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    private void setupViewPager(ViewPager viewPager) {

    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                  item.setCheckable( true );
                  mDrawerLayout.closeDrawers( );
                  return true;
              }
          }
        );
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
