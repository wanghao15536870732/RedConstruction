package com.example.zhongahiyi.redconstruction.view.fragment;

import com.example.zhongahiyi.redconstruction.view.Point;

//旋转角度，判断是否连线
public class RotateDegrees {

    public static float getDegrees(Point a,Point b){
        float degrees = 0;
        // 获取两点的坐标
        float ax = a.getX();
        float ay = a.getY();
        float bx = b.getX();
        float by = b.getY();

        //以Point a为原点计算角度
        if (ax == bx){  //两点的横坐标相同
            if (by > ay){  //后点比前代点的纵坐标高
                degrees = 90;  //即向上垂直
            }else {   //后点比前代点的纵坐标高
                degrees = 270;  //即向下垂直
            }
        }else if (by == ay){  //纵坐标相等
            if (ax > bx){
                degrees = 180;
            }else {
                degrees = 0;
            }
        }else {   //横纵坐标不相等
            if (ax > bx){
                if (ay > by){  //第三象限
                    degrees = 180 + (float) (Math.atan2( ay - by,ax - bx ) * 180 / Math.PI);
                }else {  //第二象限
                    degrees = 180 - (float) (Math.atan2( by - ay,ax - bx ) * 180 / Math.PI);
                }
            }else {
                if (ay > by){  //第四象限
                    degrees = 360 - (float) (Math.atan2( ay - by,bx - ax ) * 180 / Math.PI);
                }else {   //第一象限
                    degrees = (float) (Math.atan2( by - ay,bx - ax ) * 180 / Math.PI);
                }
            }
        }
        return degrees;
    }
}
