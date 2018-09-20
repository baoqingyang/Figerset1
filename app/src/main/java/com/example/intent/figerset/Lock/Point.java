package com.example.intent.figerset.Lock;

public class Point {


    /**
     *三个参数负责检测点的状态
     */
    public static int STATE_NORMAL = 0;
    public static int STATE_PRESSED = 1;
    public static int STATE_ERROR = 2;

    /*
    * 定义表示点作别的两个参数 x和y
     */
    public float x;
    public float y;

    int state = STATE_NORMAL;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    /*
    检测点与点之间的坐标距离
     */
    public float distance(Point a){
        return (float)Math.sqrt((x-a.x)*(x- a.x)+(y-a.y)*(y-a.y));
    }
}
