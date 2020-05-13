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
    int num = 0;
    //check this project finish or not
    boolean Finish = false;
    //Game View
    MyGameView myGameView;
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

    }
    public void Play(){

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
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                ball_y = ball_y -ball_up;
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
                canvas.drawText("Game Over", width / 2, height / 2, paint);
                this.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                            Play();
                        }
                        return true;
                    }
                });
            } else {
                //Reset Game Attribute
                paint.setColor(Color.RED);
                paint.setTextSize(80);
                //open canvas
                canvas.drawText(num + "", width / 2 - 10, 80, paint);// number
                canvas.drawCircle(ball_x, ball_y, ball_size, paint);
                // draw pillars
                paint.setColor(Color.rgb(80, 80, 200));
                //top pillars
                canvas.drawRect(pillars_x_1, pillars_y_1, pillars_x_1 + pillars_width_1, pillars_y_1 + pillars_height_1, paint);
                //button pillars
                canvas.drawRect(pillars_x_2, pillars_y_2, pillars_x_2 + pillars_width_2, pillars_y_2 + pillars_height_2, paint);

            }
        }
    }
}
