package com.demo.flyingbrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int width;
    int height;
    //ball value
    float ball_size = 16;
    float ball_speed = 3;
    float ball_up = 90;
    float ball_x;
    float ball_y;
    //pillars value
    float pillars_speed;
    //pillars 1
    float pillars_height_1;
    float pillars_width_1;
    float pillars_x_1;
    float pillars_y_1;
    //pillars 2
    float pillars_height_2;
    float pillars_width_2;
    float pillars_x_2;
    float pillars_y_2;
    //pass pillars number
    int num;
    int level;
    //check this project finish or not
    boolean Finish = false;
    boolean flag = true;
    //Game View
    MyGameView myGameView;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get Game View
        myGameView = new MyGameView(this);
        setContentView(myGameView);
        //get Windows manager
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //get windows height and width
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        //play function
        Play();
    }
    public void Play(){
        Finish = false;
        //two pillars start place
        pillars_height_1 = (float) (Math.random() * (height - 200) % (height -199) - 200);
        pillars_width_1 = 100;
        pillars_x_1 = width;
        pillars_y_1 = 0;

        pillars_height_2 = height - pillars_height_1 - 200;
        pillars_width_2 = pillars_width_1;
        pillars_x_2 = width;
        pillars_y_2 = height;

        num = 0; //init number
        flag = true;

        pillars_speed = 5;

        ball_speed = 2.0f;
        ball_up = 90;
        ball_x = 400;
        ball_y = height >> 1;

        level = 5;

        myGameView.setOnTouchListener(Click);
        handler.sendEmptyMessage(0x123);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //set ball's X and Y
                ball_y = ball_y + ball_speed;
                pillars_x_1 = pillars_x_1 - pillars_speed;
                pillars_x_2 = pillars_x_2 - pillars_speed;
                //pillars loop
                if(pillars_x_1 + pillars_width_1 <=0){
                    pillars_x_1 = width;
                    pillars_x_2 = width;
                    //随机下个柱子的高度
                    pillars_height_1 = (float) (Math.random() * (height - 200) % (height - 199) - 200);
                    pillars_width_1 = 100;

                    pillars_height_2 = height - pillars_height_1 - 200;
                    pillars_width_2 = pillars_width_1;
                    flag = true;
                }
                //check ball

                if(ball_y >= height || ball_y <= 0){
                    Finish = true;
                    timer.cancel();
                }


                if(ball_x >= pillars_x_1 && ball_x <= pillars_x_1 + pillars_width_1)
                {
                    if(ball_y < pillars_height_1 || ball_y > pillars_height_1 + 200){
                        Finish = true;
                        timer.cancel();
                    }
                }
                //积分增加
                if (flag){
                    if(ball_x > pillars_x_1 + pillars_width_1){
                        num++;
                        flag = false;
                    }
                }
                if(num == level){
                    level = level + 2;
                    pillars_speed = pillars_speed + 3;
                }

                handler.sendEmptyMessage(0x123);
            }
        }, 0, 15);
    }
    //Message
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x123){
                myGameView.invalidate();
            }
        }
    };
    //On touch function
    View.OnTouchListener Click = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    ball_y = ball_y - ball_up;
                    handler.sendEmptyMessage(0x123);
                    break;
            }
            return true;
        }
    };
    class MyGameView extends View {
        //get paint function
        Paint paint = new Paint();
        public MyGameView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //set paint attribute
            paint.setColor(Color.RED);
            //set AntiAlias
            paint.setAntiAlias(true);

            if (Finish) {
                paint.setColor(Color.RED);
                paint.setTextSize(80);
                canvas.drawText("Game Over， you get: " + num, width >> 1, (height >> 1) + 40, paint);
                this.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()){
                            case MotionEvent.ACTION_DOWN:
                                Play();
                                break;
                        }
                        return true;
                    }
                });
            } else {
                //Reset Game Attribute
                paint.setColor(Color.RED);
                paint.setTextSize(80);
                //open canvas
                canvas.drawCircle(ball_x, ball_y, ball_size, paint);
                // draw pillars
                paint.setColor(Color.rgb(80, 80, 200));
                //top pillars
                canvas.drawRect(pillars_x_1, pillars_y_1, pillars_x_1 + pillars_width_1, pillars_y_1 + pillars_height_1, paint);
                //button pillars
                canvas.drawRect(pillars_x_2, pillars_y_2 - pillars_height_2, pillars_x_2 + pillars_width_2, pillars_y_2 + pillars_height_2, paint);

                //number
                paint.setColor(Color.RED);
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(num + "", width >> 1, 80, paint);
            }
        }
    }
}
