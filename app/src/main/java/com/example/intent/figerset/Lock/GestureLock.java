package com.example.intent.figerset.Lock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.intent.figerset.R;

import java.util.ArrayList;
import java.util.List;

public class GestureLock extends View {

    /*
    构造方法
     */
    public GestureLock(Context context) {
        super(context);
    }

    public GestureLock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    表示现在键盘上的9个点
    */
    private Point[][] points = new Point[3][3];
    private boolean inited = false;

    /*
    需要是哪个图像来表示按钮被按下、未按、错误
     */
    private Bitmap bitmapPointError;
    private Bitmap bitmapPointNormal;
    private Bitmap bitmapPointPressed;

    /*
    接口；表示用户是否绘制完成
     */

    private OnDrawFinishedListener listener;

    /**
     * 初始化paint画笔,后面的参数为消除锯齿
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);   //paint画笔

    /**
     * 初始化绘制按下时的paint画笔
     * 初始化时绘制错误时的paint画笔
     */
    private Paint pressPaint = new Paint();
    private Paint errorPaint = new Paint();

    //三个圆的半径
    private float bitmapR;

    /**
     * 手指按下时的坐标点的x值和y值
     */
    private float mouseX, mouseY;

    /**
     * 是否在绘制过程中
     */
    private boolean isDraw = false;

    /**
     * 来一个集合存储九个坐标点
     * 存储绘制好的图形的样式
     */
    private ArrayList<Point> pointList = new ArrayList();
    private ArrayList<Integer> passList = new ArrayList<>();

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //如果没有初始化,就先初始化,调用init方法
        if (!inited) {
            init();
        }

        //当咱们初始化完了之后,就该绘制了.咱们定义一个方法叫做drawPoints来绘制点
        drawPoints(canvas);

        if (pointList.size() > 0) {
            Point a = pointList.get(0);
            for (int j = 1; j < pointList.size(); j++) {
                Point b = pointList.get(j);
                drawLine(canvas, a, b);
                a = b;
            }
            if (isDraw) {
                drawLine(canvas, a, new Point(mouseX, mouseY));
            }
        }
    }

    /**
     * 好了,一切准给工作好了之后呢,咱们就开始来添加事件了.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mouseX = event.getX();  //获得用户手指按下的x点
        mouseY = event.getY();  //获得用户手指按下的y点
        int[] ij;   //来个数组记录一下
        int i;      //坐标点x值
        int j;      //坐标点y值
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //此方法来恢复重新绘制
                resetPoints();
                //此方法来获得用户点击的点是否在我们绘制的距离之内
                //如果是,设置状态为按下,并添加进两个集合
                //一个集合是来装这点的坐标,一个集合是用来存储用户绘制的密码点
                ij = getSelectPoint();
                if (ij != null) {
                    isDraw = true;
                    i = ij[0];
                    j = ij[1];
                    points[i][j].state = Point.STATE_PRESSED;
                    pointList.add(points[i][j]);
                    passList.add(i * 3 + j);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //判断用户是否重复绘制,然后在一次添加进两个集合
                if (isDraw) {
                    ij = getSelectPoint();
                    if (ij != null) {
                        i = ij[0];
                        j = ij[1];
                        if (!pointList.contains(points[i][j])) {
                            points[i][j].state = Point.STATE_PRESSED;
                            pointList.add(points[i][j]);
                            passList.add(i * 3 + j);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //如果手指弹起,不绘制,调用接口判断是否和之前一致,不一致,状态为错误
                boolean valid = false;
                if (listener != null && isDraw){
                    valid = listener.onDrawFinished(passList);
                }
                if (!valid){
                    for (Point p : pointList) {
                        p.state = Point.STATE_ERROR;
                    }
                }
                isDraw = false;
                break;
        }
        this.postInvalidate();
        return true;
    }

    /*
    判断当前的手指停留在哪张图片上，返回它的坐标
     */
    private int[] getSelectPoint() {
        Point pMouse = new Point(mouseX, mouseY);
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                if (points[i][j].distance(pMouse) < bitmapR) {
                    int[] result = new int[2];
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    private void resetPoints() {
        pointList.clear();
        passList.clear();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                points[i][j].state = Point.STATE_NORMAL;
            }
        }
        this.postInvalidate();
    }


    /*
    绘制图中的线
    */
    private void drawLine(Canvas canvas, Point a, Point b) {
        if (a.state == Point.STATE_PRESSED) {
            canvas.drawLine(a.x, a.y, b.x, b.y, pressPaint);
        } else if (a.state == Point.STATE_ERROR) {
            canvas.drawLine(a.x, a.y, b.x, b.y, errorPaint);
        }
    }

    /*
    绘制点的贴图
     */
    private void drawPoints(Canvas canvas) {
        /*
        需要在绘制之前减去图片的半径，因为之前初始化函数当中计算的是点的中心的位置
        但是在画图函数当中需要的是图片左上角的坐标
         */
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                if (points[i][j].state == Point.STATE_NORMAL) {
                    //normal
                    canvas.drawBitmap(bitmapPointNormal, points[i][j].x - bitmapR, points[i][j].y - bitmapR, mPaint);
                } else if (points[i][j].state == Point.STATE_PRESSED) {
                    //press
                    canvas.drawBitmap(bitmapPointPressed, points[i][j].x - bitmapR, points[i][j].y - bitmapR, mPaint);
                } else {
                    //error
                    canvas.drawBitmap(bitmapPointError, points[i][j].x - bitmapR, points[i][j].y - bitmapR, mPaint);
                }
            }
        }

    }


    /*
    对整个界面进行初始化操作
     */
    private void init() {

        /**
         * 对按下和错误时的状态的paint进行初始化
         * 设置圆环的宽度
         */
        pressPaint.setColor(Color.YELLOW);
        pressPaint.setStrokeWidth(6);
        errorPaint.setColor(Color.RED);
        errorPaint.setStrokeWidth(6);

        /**
         * 将资源drawable图片转换为bitmap位图对象
         */
        bitmapPointError = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_error);
        bitmapPointNormal = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_normal);
        bitmapPointPressed = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_pressed);

        /**
         * 因为我的三个图片是一样大的,所以呢半径为任意一个图片的高度除以2即可
         */
        bitmapR = bitmapPointError.getHeight() / 2;


        int width = getWidth();     //获得屏幕的宽度
        int height = getHeight();   //获得屏幕的高度
        int offset = Math.abs(width - height) / 2; //获得横屏和竖屏的偏移量offset
        int offsetX, offsetY;    //水平和垂直的偏移量
        int space;


        if (width > height) {
            //横屏状态下(其中space为每个小正方形的边长)
            space = height / 4;
            offsetX = offset;
            offsetY = 0;

        } else {
            //竖屏状态下
            space = width / 4;
            offsetY = offset;
            offsetX = 0;
        }

        /**
         * 初始化九个点的坐标,横屏竖屏一起初始化了,这里有图,不然是不是有点乱哈
         */
        points[0][0] = new Point(offsetX + space, offsetY + space);
        points[0][1] = new Point(offsetX + space * 2, offsetY + space);
        points[0][2] = new Point(offsetX + space * 3, offsetY + space);

        points[1][0] = new Point(offsetX + space, offsetY + space * 2);
        points[1][1] = new Point(offsetX + space * 2, offsetY + space * 2);
        points[1][2] = new Point(offsetX + space * 3, offsetY + space * 2);

        points[2][0] = new Point(offsetX + space, offsetY + space * 3);
        points[2][1] = new Point(offsetX + space * 2, offsetY + space * 3);
        points[2][2] = new Point(offsetX + space * 3, offsetY + space * 3);

        /**
         * 初始化之后让inited为true就好了,因为咱只初始化一遍就ok了
         */
        inited = true;
    }

    public interface OnDrawFinishedListener {
        boolean onDrawFinished(List<Integer> passList);
    }

    public void setOnDrawFinishedListener(OnDrawFinishedListener listener){
        this.listener = listener;
    }
}
