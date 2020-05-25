package com.demo.flyingbrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }
    private void initGameView(){
        //set on touch listener to read player's finger movement
        setOnTouchListener(new View.OnTouchListener() {
            // get
            private float sX,sY,offsetX,offsetY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //get the position when user click
                        sX = event.getX();
                        sY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        // get the move distance when finger off the screen
                        offsetX = event.getX()-sX;
                        offsetY = event.getY()-sY;
                        if (Math.abs(offsetX)>Math.abs(offsetY)) {
                            //decide the moving directions
                            if (offsetX<-5) {
                                System.out.println("left");
                            }else if (offsetX>5) {
                                System.out.println("right");
                            }
                        }else{
                            if (offsetY<-5) {
                                System.out.println("up");
                            }else if (offsetY>5) {
                                System.out.println("down");
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }
}
