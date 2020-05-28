package com.demo.flyingbrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;

import androidx.annotation.Nullable;

import java.util.Random;

public class GameView extends View {
    //4x4 map
    public static final int count = 4;
    //create standard variables
    private int screen_width;
    private int screen_height;
    private Paint paint;
    private float paint_width;
    private float paint_height;
    private int startX;
    private int startY;
    private int block_size;
    private int [][] map;
    private float x_click;
    private float y_click;
    private float x_move;
    private float y_move;
    private boolean Pressed;
    private boolean Moved;
    private Random random;
    private int scores;

    //initial colors
    private static SparseIntArray blocks_colors;
    static {
        blocks_colors = new SparseIntArray();
        blocks_colors.put(2, Color.parseColor("#EEE4DA"));
        blocks_colors.put(4, Color.parseColor("#EDE0C8"));
        blocks_colors.put(8, Color.parseColor("#F2B179"));
        blocks_colors.put(16, Color.parseColor("#F59563"));
        blocks_colors.put(32, Color.parseColor("#F67C5F"));
        blocks_colors.put(64, Color.parseColor("#F65E3B"));
        blocks_colors.put(128, Color.parseColor("#DCBF65"));
        blocks_colors.put(256, Color.parseColor("#EDCC61"));
        blocks_colors.put(512, Color.parseColor("#EDC850"));
        blocks_colors.put(1024, Color.parseColor("#DBB732"));
        blocks_colors.put(2048, Color.parseColor("#EFC329"));
        blocks_colors.put(4096, Color.parseColor("#FF3C39"));
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.paint = new Paint();
        this.paint_width = 15f;
        this.random = new Random();
        this.map = new int[count][count];
        for (int i = 0; i < 3; i++){
            createRandomBlock();
        }
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screen_width = outMetrics.widthPixels;
        screen_height = outMetrics.heightPixels;

        startY = (screen_height - screen_width) >> 1;
        startY = 0;

        block_size = screen_width / count;
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        gameArea();
        gameRect();
        drawScore();
    }
    // draw parts
    private void drawScore(Canvas canvas, int x, int y) {
        // reset when game start
        paint.reset();
        paint.setColor(Color.GRAY);
        paint.setTextSize(block_size * 5/ 12);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(paint_width);
        canvas.drawText("Score: " + scores, screen_width/2, startY/2, paint);
    }

    private void gameRect() {
    }

    private void gameArea() {
    }

    private void createRandomBlock() {
    }
//    public GameView(Context context) {
//        super(context, null);
//    }

}
