package com.demo.flyingbrid;

import android.content.Context;
import android.util.AttributeSet;
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

    }
}
