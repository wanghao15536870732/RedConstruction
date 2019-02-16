package com.example.zhongahiyi.redconstruction.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.zhongahiyi.redconstruction.R;
import com.example.zhongahiyi.redconstruction.view.fragment.RotateDegrees;

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

    private String mPassword = "";

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
            initPoint();  //初始化
        }
        canvasPoint( canvas );  //开始画点

        //开始画线
        if (mPointList.size() > 0){
            Point b = null;
            Point a = mPointList.get( 0 );
            for (int i = 1; i < mPointList.size(); i++) {
                b = mPointList.get( i );
                canvasLine( a,b,canvas );
                a = b;
            }

            if (!isFinish){  //如果没有连接上该点
                canvasLine( a,mMousePoint,canvas );  //线画到点击位置
            }
        }
    }

    /**
     * 手指点击手机屏幕
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取手指点击位置坐标
        mMoveX = event.getX();
        mMoreY = event.getY();
        //设置移动点的坐标
        mMousePoint.setX( mMoveX );
        mMousePoint.setY( mMoreY );
        Point mPoint = null;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isLineState = true;
                isFinish = false;
                //每次点击是就会将pointList中的point转化成正常状态
                for (int i = 0; i < mPointList.size(); i++) {
                    mPointList.get( i ).setState( Point.BITMAP_NORMAL );
                }
                //将pointList中的元素清除掉
                mPointList.clear();
                //判断是否点击了九宫格中的点
                mPoint = getIsSelectedPoint(mMoveX,mMoreY );
                if (mPoint != null){  //按钮被点击
                    isSelect = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSelect == true){
                    mPoint = getIsSelectedPoint( mMoveX,mMoreY );
                }
                break;
            case MotionEvent.ACTION_UP:
                isFinish = true;
                isSelect =false;
                //规定至少要有四个点连线才有可能是正确的
                if (mPointList.size() >= 4){
                    for (int i = 0; i < mPointList.size(); i++) {
                        mPassword += mPointList.get(i).getIndex();
                    }
                    mListener.getStringPassword( mPassword );
                    mPassword = "";
                    if (mListener.isPassword()){
                        for (int i = 0; i < mPointList.size(); i++) {
                            mPointList.get( i ).setState( Point.BITMAP_PRESS );
                        }
                    }else {
                        for (int i = 0; i < mPointList.size();i ++){
                            mPointList.get( i ).setState( Point.BITMAP_ERROR );
                        }
                        isLineState = false;
                    }
                }else if (mPointList.size() < 4 && mPointList.size() > 1){
                    for (int i = 0; i < mPointList.size(); i++) {
                        mPointList.get( i ).setState( Point.BITMAP_ERROR );
                    }
                    isLineState = false;
                    // 如果只有一个点被点中时为正常情况
                }else if (mPointList.size() == 1){
                    for (int i = 0; i < mPointList.size(); i++) {
                        mPointList.get( i).setState( Point.BITMAP_NORMAL );
                    }
                }
                break;
        }
        //将mPoint添加到pointList当中
        if (isSelect && mPoint != null){
            if (mPoint.getState() == Point.BITMAP_NORMAL){
                mPoint.setState( Point.BITMAP_PRESS );
                mPointList.add( mPoint );
            }
        }
        //每次发生OnTouchEvent()后都刷新View
        postInvalidate();
        return true;
    }

    /**
     * 判断九个点中某个点知否被点中了，能否被连线
     */
    private Point getIsSelectedPoint(float moveX,float moveY){
        Point myPoint = null;
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                if (mPoints[i][j].isWith( mPoints[i][j],moveX,moveY,
                        mPointRadius)){
                    myPoint = mPoints[i][j];  //记录下该点
                }
            }
        }
        return myPoint;
    }

    /**
     * 画线
     */

    private void canvasLine(Point a,Point b,Canvas canvas){
        //Math.sqrt(平方+平方,勾股定理)
        float abInstance = (float) Math.sqrt(
                (a.getX() - b.getX()) * (a.getX() - b.getX())
                + (a.getY() - b.getY()) * (a.getX() - b.getY())
        );
        //以a.getX(),a.getY()为原点旋转
        canvas.rotate( RotateDegrees.getDegrees(a,b),a.getX(),a.getY());
        //设置矩阵的大小
        mMatrix.setScale( abInstance / mLineHeight ,1);
        mMatrix.postTranslate( a.getX(),a.getY() );
        if (isLineState){  //线的状态被触发
            canvas.drawBitmap( mLinePressed,mMatrix,mPaint );
        }else {
            canvas.drawBitmap( mLineError,mMatrix,mPaint );
        }
        canvas.rotate( -RotateDegrees.getDegrees( a,b ),a.getX(),a.getY());
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
