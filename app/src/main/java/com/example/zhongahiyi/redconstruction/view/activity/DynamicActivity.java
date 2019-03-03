package com.example.zhongahiyi.redconstruction.view.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.adapter.FullyGridLayoutManager;
import com.example.zhongahiyi.redconstruction.adapter.GridImageAdapter;
import com.example.zhongahiyi.redconstruction.bean.NineGridModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "DynamicActivity";
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String [] filepaths;
    private List<String> mUriList = new ArrayList<>( );
    private int maxSelecNum = 9;
//    private int themeId;
//    private int chooseMode = PictureMimeType.ofAll();
    private Button send_btn;
    private ImageView back_Btn;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        recyclerView = (RecyclerView) findViewById( R.id.dynamic_recycler );
        FullyGridLayoutManager manager = new FullyGridLayoutManager(
                DynamicActivity.this,
                3,
                GridLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager( manager );
        adapter = new GridImageAdapter( DynamicActivity.this, onAddPicClickListener );
        adapter.setList( selectList );
        recyclerView.setAdapter( adapter);
        adapter.setOnItemClickListener( new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            int themeId = R.style.picture_default_style;
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(DynamicActivity.this)
                                    .themeStyle(themeId)
                                    .maxSelectNum( maxSelecNum )
                                    .openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(DynamicActivity.this)
                                    .externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(DynamicActivity.this)
                                    .externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        } );
        send_btn = (Button) findViewById( R.id.toolbar_send );
        back_Btn = (ImageView) findViewById( R.id.toolbar_back );
        mEditText = (EditText) findViewById( R.id.item_edit );
        send_btn.setOnClickListener( this );
        back_Btn.setOnClickListener( this );
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 单独拍照
            PictureSelector.create(DynamicActivity.this)
                    .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(maxSelecNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(3)// 每行显示个数
                    .selectionMode(
                            PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    public String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_send:
                final NineGridModel nineGridModel = new NineGridModel();
                nineGridModel.setContent(mEditText.getText().toString());
                for (int i = 0; i < selectList.size(); i++) {
                    mUriList.add( selectList.get( i ).getPath() );
                }
                filepaths = (String [])mUriList.toArray(new String[mUriList.size()]);
                nineGridModel.save( new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Toast.makeText(DynamicActivity.this, "添加数据成功，返回ObjectId为：" + s, Toast.LENGTH_SHORT ).show();
                        }else {
                            Toast.makeText( DynamicActivity.this, "创建数据失败," + e.getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );

                BmobFile.uploadBatch( filepaths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> list1) {
                        if (list.size() == filepaths.length){
                            //表示全部都上传成功
                            Toast.makeText( DynamicActivity.this, "上传成功", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total,int totalPercent) {

                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        Toast.makeText( DynamicActivity.this, "错误码：" + statuscode + "错误描述：" + errormsg, Toast.LENGTH_SHORT ).show();
                    }
                } );
                break;
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
