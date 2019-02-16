package com.example.zhongahiyi.redconstruction.view;

public class Point {

    //按钮的三种状态
    public static int BITMAP_NORMAL = 0;  //正常
    public static int BITMAP_ERROR = 1; // 错误
    public static int BITMAP_PRESS = 2; //按下

    //九宫格的点的标值
    private String index;

    //点的状态
    private int state;

    //点的坐标
    private float x;
    private float y;

    //两种构造器
    public Point() {
        super();
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //getter、setter
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    /**
     * 判断九宫格中的点是否可以进行连线
     */
    public boolean isWith(Point a,float moveX,float moveY,float radius){
        float result = (float)Math.sqrt(
                (a.getX() - moveX) * (a.getX() - moveX) +
                        (a.getY() - moveY) * (a.getY() - moveY);
        );
        if (result < 5 * radius / 4){
            return true;
        }
        return false;
    }
}
