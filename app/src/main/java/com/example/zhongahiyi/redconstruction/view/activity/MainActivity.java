package com.example.zhongahiyi.redconstruction.view.activity;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.zhongahiyi.redconstruction.adapter.PageAdapter;
import com.example.zhongahiyi.redconstruction.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private DrawerLayout mDrawerLayout;
    private Uri imageuri;
    private ImageView avatar;

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar =  (Toolbar) findViewById( R.id.toolbar_main );
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
        navigationView = (NavigationView) findViewById( R.id.nav_view );
        View headerView = navigationView.getHeaderView( 0 );
        navigationView.setCheckedItem( R.id.nav_home );
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //如果已经拉到左边，则关闭
                if(mDrawerLayout.isDrawerOpen( Gravity.LEFT )){
                    mDrawerLayout.closeDrawers( );
                }
                switch (item.getItemId()){
                    case R.id.nav_home:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_messages:
                        startActivity( new Intent( MainActivity.this,MessageActivity.class ) );
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_friends:
                        startActivity( new Intent( MainActivity.this,FriendCircleActivity.class ) );
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
                return true;
            }
        } );
        avatar = (ImageView) headerView.findViewById( R.id.nav_circle_image);
        avatar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder( MainActivity.this )
                        .title( "选择照片来源" )
                        .sheet( R.menu.change_avatar )
                        .listener( new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case R.id.choose_take:
                                        File outputImage = new File( getExternalCacheDir(), "output_iamge.jpg" );
                                        try {
                                            if (outputImage.exists()) {
                                                outputImage.delete();
                                            }
                                            outputImage.createNewFile();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if (Build.VERSION.SDK_INT >= 24) {
                                            imageuri = FileProvider.getUriForFile( MainActivity.this,
                                                    "com.example.zhongahiyi.redconstruction.fileprovider", outputImage );
                                        } else {
                                            imageuri = Uri.fromFile( outputImage );
                                        }

                                        Intent intent = new Intent( "android.media.action.IMAGE_CAPTURE" );
                                        intent.putExtra( MediaStore.EXTRA_OUTPUT, imageuri );
                                        startActivityForResult( intent, TAKE_PHOTO );
                                        break;
                                    case R.id.choose_album:
                                        if (ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) !=
                                                PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions( MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1 );
                                        } else {
                                            openAlbum();
                                        }
                                        break;
                                }
                            }
                        } ).show();
            }
        } );
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
            //actionBar的单击事件
            case R.id.action_night:
//                startActivity( new Intent( this,FriendCircleActivity.class));
                break;
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
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream( getContentResolver().openInputStream( imageuri ) );
                        avatar.setImageBitmap( bitmap );
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat( data );
                    } else {
                        handleImageBeforeKitKat( data );
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText( this, "you denied the permission", Toast.LENGTH_SHORT );
                }
                break;
            default:
        }
    }

    private void openAlbum() {
        Intent intent = new Intent( "android.intent.action.GET_CONTENT" );
        intent.setType( "image/*" );
        startActivityForResult( intent, CHOOSE_PHOTO );
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagepath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri( this, uri )) {
            String docId = DocumentsContract.getDocumentId( uri );
            if ("com.android.providers.media.documents".equals( uri.getAuthority() )) {
                String id = docId.split( ":" )[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagepath = getImagePath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection );
            } else if ("com.android.providers.downloads.documents".equals( uri.getAuthority() )) {
                Uri contenturi = ContentUris.withAppendedId( Uri.parse( "content://downloads/public_downloads" ), Long.valueOf( docId ) );
                imagepath = getImagePath( contenturi, null );
            }
        } else if ("content".equalsIgnoreCase( uri.getScheme() )) {
            imagepath = getImagePath( uri, null );
        } else if ("file".equalsIgnoreCase( uri.getScheme() )) {
            imagepath = uri.getPath();
        }
        Bitmap bitmap = BitmapFactory.decodeFile( imagepath );
        avatar.setImageBitmap( bitmap );
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagepath = getImagePath( uri, null );
        Bitmap bitmap = BitmapFactory.decodeFile( imagepath );
        avatar.setImageBitmap( bitmap );
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query( uri, null, selection, null, null );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString( cursor.getColumnIndex( MediaStore.Images.Media.DATA ) );
            }
            cursor.close();
        }
        return path;
    }
}
