package com.example.zhongahiyi.redconstruction.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zhongahiyi.redconstruction.R;

import java.util.ArrayList;
import java.util.List;

public class LockPatternView extends View {

    //判断线的状态
    private static boolean isLineState = true;
    //p按段点是否被实例化了
    private static boolean isInitPoint = false;
    //判断手指是否离开了屏幕
    private static boolean isFinish = false;
    //判断点击屏幕是是否选中了九宫格中的点
    private static boolean isSelect = false;
    //创建MyPoint的数组
    private Point[][] mPoints = new Point[3][3];  // 创建九宫格
    //声明屏幕的宽跟高
    private int mScreenHeight;
    private int mScreenWidth;
    //声明线的角度
    private float mPointRadius;
    //声明线的长度
    private float mLineHeight;
    //声明移动的下，y坐标
    private float mMoveX,mMoreY;
    //声明屏幕上的宽和高的偏移量
    private int mScreenHeightOffSet = 0;
    private int mScreenWidthOffSet = 0;


    // 创建一个画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 声明资源图片
    private Bitmap mBitmapNormal;
    private Bitmap mBitmapPressed;
    private Bitmap mBitmapError;
    private Bitmap mLinePressed;
    private Bitmap mLineError;
    // 创建一个矩阵
    private Matrix mMatrix = new Matrix();
    // 创建MyPoint的列表
    private List<Point> mPointList = new ArrayList<Point>();
    // 实例化鼠标点
    private Point mMousePoint = new Point();
    private Context mContext;

    private OnLockListener mListener;

    public LockPatternView(Context context) {
        super( context );

    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        super( context, attrs );
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super( context, attrs, defStyleAttr );
    }

    /**
     * 画点和画线
     */
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw( canvas );
        if (!isInitPoint) { //如果没被初始化

        }
    }

    /**
     * 画点
     */

    private void canvasPoint(Canvas canvas){
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                if (mPoints[i][j].getState() == Point.BITMAP_NORMAL){
                    canvas.drawBitmap( mBitmapNormal,
                            mPoints[i][j].getX() - mPointRadius,
                            mPoints[i][j].getY() - mPointRadius,mPaint);
                }else if (mPoints[i][j].getState() == Point.BITMAP_PRESS){
                    canvas.drawBitmap( mBitmapPressed,
                            mPoints[i][j].getX() - mPointRadius,
                            mPoints[i][j].getY() - mPointRadius,mPaint);
                }else {
                    canvas.drawBitmap( mBitmapError,
                            mPoints[i][j].getX() - mPointRadius,
                            mPoints[i][j].getY() - mPointRadius,mPaint);
                }
            }
        }
    }

    /**
     * 实例化所有点和资源图片
     */
    private void initPoint(){
        //获取View的宽和高
        mScreenWidth = getWidth();
        mScreenHeight = getHeight();
        if (mScreenHeight > mScreenWidth){
            //获取y轴上的偏移量
            mScreenHeightOffSet = (mScreenHeight - mScreenWidth) / 2;
            //将屏幕高的变量设置与宽相等
            mScreenHeight = mScreenWidth;
        }else {
            //获取x轴上的偏移量
            mScreenWidthOffSet = (mScreenWidth - mScreenHeight) / 2;
            //将屏幕高的变量设置与宽相等
            mScreenWidth = mScreenHeight;
        }

        /**
         * 实例化所有的资源图片
         */
        mBitmapError = BitmapFactory.decodeResource( getResources(), R.drawable.bitmap_error );
        mBitmapNormal = BitmapFactory.decodeResource(getResources(),R.drawable.bitmap_normal );
        mBitmapPressed = BitmapFactory.decodeResource( getResources(),R.drawable.bitmap_pressed );

        mLineError = BitmapFactory.decodeResource( getResources(),R.drawable.line_error);
        mLinePressed = BitmapFactory.decodeResource( getResources(),R.drawable.line_pressed );

        mPointRadius = mBitmapNormal.getWidth() / 2;
        mLineHeight = mLinePressed.getHeight();

        /**
         * 开始实例化九宫格中的点
         */
        mPoints[0][0] = new Point( mScreenWidthOffSet + mScreenWidth / 4,
                mScreenHeightOffSet + mScreenHeight / 4);
        mPoints[0][1] = new Point( mScreenWidthOffSet + mScreenWidth / 2,
                mScreenHeightOffSet + mScreenHeight / 4 );
        mPoints[0][2] = new Point( mScreenWidthOffSet + mScreenWidth * 3 / 4,
                mScreenHeightOffSet + mScreenHeight / 4);
        mPoints[1][0] = new Point( mScreenWidthOffSet + mScreenWidth / 4,
                mScreenHeightOffSet + mScreenHeight / 2);
        mPoints[1][1] = new Point( mScreenWidthOffSet + mScreenWidth / 2,
                mScreenHeightOffSet + mScreenHeight / 2);
        mPoints[1][2] = new Point( mScreenWidthOffSet + mScreenWidth * 3 / 4,
                mScreenHeightOffSet + mScreenHeight / 2);
        mPoints[2][0] = new Point( mScreenWidthOffSet + mScreenWidth / 4,
                mScreenHeightOffSet + mScreenHeight * 3 / 4);
        mPoints[2][1] = new Point( mScreenWidthOffSet + mScreenWidth / 2 ,
                mScreenHeightOffSet + mScreenHeight * 3 / 4);
        mPoints[2][2] = new Point( mScreenWidthOffSet + mScreenWidth * 3 / 4,
                mScreenHeightOffSet + mScreenHeight * 3 / 4);

        /**
         * 设置九宫格中的各个index
         */
        int index = 1;
        for (int i = 0;i < mPoints.length;i ++){
            for (int j = 0;i < mPoints[i].length;j ++){
                mPoints[i][j].setIndex( index + "" );
                mPoints[i][j].setState( Point.BITMAP_NORMAL );
                index++;
            }
        }
        isInitPoint = true;
    }


    public interface OnLockListener {
        public void getStringPassword(String password);

        public boolean isPassword();


    }

    public void setLockListener(OnLockListener listener){
        this.mListener = listener;
    }
}
