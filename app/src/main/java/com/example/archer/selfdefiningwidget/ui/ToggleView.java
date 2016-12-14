package com.example.archer.selfdefiningwidget.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 完全自定义一个开关的控件
 * Created by Archer on 2016/12/14.
 *
 * 得到三个构造函数，根据传入的参数选择对应的构造函数
 *
 *
 *Android界面的绘制：
 *
 *
 * 1.  measure(测量) -> layout（摆放）-> draw（绘制）。
 *
 * 2. onMeasure()->onLayout()->onDraw()        重写这些方法，实现自定义控件
 *
 * onResume() 方法后执行
 *
 *View：
 *
 *继承 View 没有onLayout方法 ： onMeasure(通过这个方法指定宽高)->onDraw()
 *
 *
 *ViewGroup：
 *这个可以理解为当你要摆放一组view的时候，就需要layout方法来确定哪一个view摆放在哪。
 * onMeasure(通过这个方法指定宽高)->onLayout(摆放views)->onDraw()
 *
 *
 *
 */


public class ToggleView  extends View{

    private Bitmap switchBackground;
    private Bitmap slideButton;
    private Paint paint;

    private boolean mSwitchStatus=true;
    private float currentX;

    /**
     *用于代码创建控件
     *
     * @param context 上下文
     */
    public ToggleView(Context context) {
        super(context);
        initPaint();

    }

    /**
     *用于在xml种使用，可指定自定义属性。
     * @param context 上下文
     * @param attrs  属性
     */
    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    /**
     * 用于代码创建控件
     * *用于在xml种使用，可指定自定义属性。
     * 指定样式
     * @param context  上下文
     * @param attrs 属性
     * @param defStyleAttr  样式
     */
    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();


    }


    /**
     *
     * @param switch_background  设置背景图片
     */

    public void setSwitchBackgroundResource(int switch_background) {

        //使用bitmapFactory创建一个图片对象
        switchBackground = BitmapFactory.decodeResource(getResources(), switch_background);


    }

    /**
     *
     * @param slide_button 设置背景滑动图片资源
     */
    public void setSlideButtonResource(int slide_button) {

        slideButton = BitmapFactory.decodeResource(getResources(), slide_button);

    }

    /**
     *
     * @param mSwitchStatus 开关状态
     */

    public void setSwitchStatus(boolean mSwitchStatus) {

        this.mSwitchStatus=mSwitchStatus;


    }


    /**
     *
     * 1.通过BitmapFactory拿到相应的对象，接着开始绘制图像
     *  onMeasure(通过这个方法指定宽高)->onDraw()
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //得到默认的图片的宽高
        setMeasuredDimension(switchBackground.getWidth(),switchBackground.getHeight());


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1.绘制背景
        canvas.drawBitmap(switchBackground,0,0,paint);

//根据用户触摸到的位置绘制滑块

        if (isTouchMode){



            float newLeft = this.currentX-slideButton.getWidth()/2.0f;//滑块的位置向左移动半个滑块的位置，用户体验更好。

            int MaxRight = switchBackground.getWidth() - slideButton.getWidth();
            //限定滑块范围

            if (newLeft<0){//设置往左滑动的最大范围
                newLeft=0;
            }else if (newLeft>MaxRight){
                newLeft=MaxRight;//右边的最大范围
            }




            canvas.drawBitmap(slideButton,newLeft,0,paint);//绘制滑块

        }else {

            //根据开关状态绘制view
            if (mSwitchStatus){
                //2.绘制slideButton
                int newLeft = switchBackground.getWidth() - slideButton.getWidth();

                canvas.drawBitmap(slideButton,newLeft,0,paint);

            }else {
                canvas.drawBitmap(slideButton,0,0,paint);
            }

        }


    }

    /**
     * 触摸事件处理
     * @param event  事件
     * @return  消费事件
     */

    boolean isTouchMode=false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                isTouchMode=true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();

                break;


            case MotionEvent.ACTION_UP:

              isTouchMode=false;
                currentX = event.getX();

                float center = switchBackground.getWidth() / 2.0f;

                boolean stats;
                stats = currentX > center;

                 mSwitchStatus=stats;

                break;

            default:

                break;
        }


        //重绘界面

        invalidate();//会引发onDraw方法被调用，变量重新生效，界面重绘

        return true;
    }
}
