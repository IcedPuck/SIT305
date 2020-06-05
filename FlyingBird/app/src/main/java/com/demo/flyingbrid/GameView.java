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
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import java.util.Random;

public class GameView extends View {
    //set variables
    private int screen_width;
    private int screen_height;
    private Paint paint;
    private float paint_width;
    private int startX;
    private int startY;
    private int block_size;
    private int[][] map;
    private float x_click;
    private float y_click;
    private float x_move;
    private float y_move;
    private boolean isPressed;
    private boolean isMoved;
    private Random random;
    private int score;
    // create 4*4 map
    public static final int COUNT = 4;

    //create block color
    private static SparseIntArray colors;
    static {
        colors = new SparseIntArray();
        colors.put(2, Color.parseColor("#EEE4DA"));
        colors.put(4, Color.parseColor("#EDE0C8"));
        colors.put(8, Color.parseColor("#F2B179"));
        colors.put(16, Color.parseColor("#F59563"));
        colors.put(32, Color.parseColor("#F67C5F"));
        colors.put(64, Color.parseColor("#F65E3B"));
        colors.put(128, Color.parseColor("#DCBF65"));
        colors.put(256, Color.parseColor("#EDCC61"));
        colors.put(512, Color.parseColor("#EDC850"));
        colors.put(1024, Color.parseColor("#DBB732"));
        colors.put(2048, Color.parseColor("#EFC329"));
        colors.put(4096, Color.parseColor("#FF3C39"));
    }


    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.paint = new Paint();
        this.paint_width = 15f;
        this.random = new Random();
        this.map = new int[COUNT][COUNT];

        // 默认初始化3个方块
        for (int i = 0; i < 3; i++) {
            createRandomRect();
        }

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screen_width = outMetrics.widthPixels;
        screen_height = outMetrics.heightPixels;

        startY = (screen_height - screen_width) >> 1;
        startX = 0;

        block_size = screen_width / COUNT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameArea(canvas, startX, startY);
        gameRect(canvas, map);
        Score(canvas);
    }

    private void gameArea(Canvas canvas, int x, int y) {
        // draw background area
        paint.reset();
        paint.setColor(Color.parseColor("#CDC7BB"));
        canvas.drawRect(x, y, screen_width, y + screen_width, paint);

        // draw divide line
        paint.reset();
        paint.setColor(Color.parseColor("#CDB599"));
        paint.setStrokeWidth(paint_width);
        for (int i = 0; i <= COUNT; i++) {
            canvas.drawLine(x + i, block_size * i + y, screen_width, block_size * i + y, paint);
        }
        for (int i = 0; i <= COUNT; i++) {
            canvas.drawLine(block_size * i, i + y, block_size * i, screen_width + y, paint);
        }
    }

    //draw blocks
    private void gameRect(Canvas canvas, int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != 0) {
                    drawAllRect(canvas, i, j);
                }
            }
        }
    }

    // draw all blocks
    private void drawAllRect(Canvas canvas, int i, int j) {
        // draw block background color
        paint.reset();
        paint.setColor(colors.get(map[i][j], colors.get(4096)));
        canvas.drawRect(block_size * j + startX + paint_width / 2, block_size * i + startY
                + paint_width / 2, block_size * j + startX - paint_width / 2 + block_size, block_size
                * i + startY - paint_width / 2 + block_size, paint);

        // divide blocks
        paint.reset();
        if (map[i][j] <= 4) {
            paint.setColor(Color.BLACK);
        } else {
            paint.setColor(Color.WHITE);
        }
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(paint_width);
        paint.setTextSize(block_size * 5 / 12);
        canvas.drawText(String.valueOf(map[i][j]), block_size * j + startX + block_size / 2,
                block_size * i + startY + block_size * 2 / 3, paint);
    }

    //display scores
    private void Score(Canvas canvas) {
        paint.reset();
        paint.setColor(Color.GRAY);
        paint.setTextSize(block_size * 5 / 12);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStrokeWidth(paint_width);
        canvas.drawText("Scores：" + score, screen_width / 2, startY / 2, paint);
    }

    //create random blocks
    private void createRandomRect() {
        // random block contains number 2 or 4
        int num = Math.random() < 0.9 ? 2 : 4;

        int x;
        int y;
        //find an empty block randomly, and fill it until no empty block
        do {
            x = random.nextInt(COUNT);
            y = random.nextInt(COUNT);
        } while (map[x][y] != 0);
        // put new number into the map
        map[x][y] = num;
    }

    //decide whether two blocks are same number
    private boolean isEquals(int[][] tempMap, int[][] map) {
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < COUNT; j++) {
                if (tempMap[i][j] != map[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //copy game data
    private int[][] copyMap(int[][] map) {
        int[][] tempMap = new int[COUNT][COUNT];
        for (int i = 0; i < COUNT; i++) {
            tempMap[i] = map[i].clone();
        }
        return tempMap;
    }
    //handle ontouch function
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressed = true;
                x_click = event.getRawX();
                y_click = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                x_move = event.getRawX();
                y_move = event.getRawY();

                // move distance
                int distanceX = (int) (x_move - x_click);
                int distanceY = (int) (y_move - y_click);

                // abs value for move distance
                int absX = Math.abs(distanceX);
                int absY = Math.abs(distanceY);

                // If you move more than a quarter of the width of the screen, it counts as effective movement
                if (absX > (screen_width >> 2) || absY > (screen_width >> 2)) {
                    if (absX > absY) {
                        if (distanceX < 0) {
                            moveToLeft();
                        } else {
                            moveToRight();
                        }
                    } else {
                        if (distanceY < 0) {
                            moveToUp();
                        } else {
                            moveToDown();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isPressed = false;
                isMoved = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    private boolean canNotMove() {
        //finger down state, and has already used the move method, will not continue to call, to prevent the press down multiple calls to move method
        return isPressed && isMoved;
    }

    //implement motions
    private void moveToUp() {
        if (canNotMove()) {
            return;
        }
        // Save the pre-move state
        int[][] mapTemp = copyMap(map);

        // eliminate the blank square in the middle
        int k;
        int temp;
        for (int j = 0; j < COUNT; j++) {
            for (int i = 0; i < COUNT - 1; i++) {
                k = i;
                while (k < COUNT - 1 && map[k][j] == 0) {
                    temp = map[k][j];
                    map[k][j] = map[k + 1][j];
                    map[k + 1][j] = temp;
                    k++;
                }
            }
        }

        // Merges squares that can be added
        for (int j = 0; j < COUNT; j++) {
            for (int i = 0; i < COUNT - 1; i++) {
                if (map[i][j] == map[i + 1][j]) {
                    map[i][j] *= 2;
                    score += map[i + 1][j];
                    map[i + 1][j] = 0;
                }
            }
        }

        // Get rid of the white space again
        for (int j = 0; j < COUNT; j++) {
            for (int i = 0; i < COUNT - 1; i++) {
                k = i;
                while (k < COUNT - 1 && map[k][j] == 0) {
                    temp = map[k][j];
                    map[k][j] = map[k + 1][j];
                    map[k + 1][j] = temp;
                    k++;
                }
            }
        }

        // If the cube data is not equal after the move, the move is successful and a new cube should be created
        if (!isEquals(mapTemp, map)) {
            createRandomRect();
        }

        // Refresh the screen
        invalidate();
        isMoved = true;
    }
    // move actions 
    private void moveToDown() {
        if (canNotMove()) {
            return;
        }

        int[][] mapTemp = copyMap(map);

        int k;
        int temp;
        for (int j = 0; j < COUNT; j++) {
            for (int i = COUNT - 1; i > 0; i--) {
                k = i;
                while (k > 0 && map[k][j] == 0) {
                    temp = map[k][j];
                    map[k][j] = map[k - 1][j];
                    map[k - 1][j] = temp;
                    k--;
                }
            }
        }

        for (int j = 0; j < COUNT; j++) {
            for (int i = COUNT - 1; i > 0; i--) {
                if (map[i][j] == map[i - 1][j]) {
                    map[i][j] *= 2;
                    score += map[i - 1][j];
                    map[i - 1][j] = 0;
                }
            }
        }

        for (int j = 0; j < COUNT; j++) {
            for (int i = COUNT - 1; i > 0; i--) {
                k = i;
                while (k > 0 && map[k][j] == 0) {
                    temp = map[k][j];
                    map[k][j] = map[k - 1][j];
                    map[k - 1][j] = temp;
                    k--;
                }
            }
        }
        if (!isEquals(mapTemp, map)) {
            createRandomRect();
        }
        invalidate();
        isMoved = true;
    }

    private void moveToLeft() {
        if (canNotMove()) {
            return;
        }
        int[][] mapTemp = copyMap(map);

        int k;
        int temp;
        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < map[i].length - 1; j++) {
                k = j;
                while (k < COUNT - 1 && map[i][k] == 0) {
                    temp = map[i][k];
                    map[i][k] = map[i][k + 1];
                    map[i][k + 1] = temp;
                    k++;
                }
            }
        }

        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < map[i].length - 1; j++) {
                if (map[i][j] == map[i][j + 1]) {
                    map[i][j] *= 2;
                    score += map[i][j + 1];
                    map[i][j + 1] = 0;
                }
            }
        }

        for (int i = 0; i < COUNT; i++) {
            for (int j = 0; j < map[i].length - 1; j++) {
                k = j;
                while (k < COUNT - 1 && map[i][k] == 0) {
                    temp = map[i][k];
                    map[i][k] = map[i][k + 1];
                    map[i][k + 1] = temp;
                    k++;
                }
            }
        }
        if (!isEquals(mapTemp, map)) {
            createRandomRect();
        }
        invalidate();
        isMoved = true;
    }

    private void moveToRight() {
        if (canNotMove()) {
            return;
        }
        int[][] mapTemp = copyMap(map);

        int k;
        int temp;
        for (int i = 0; i < COUNT; i++) {
            for (int j = map[i].length - 1; j > 0; j--) {
                k = j;
                while (k > 0 && map[i][k] == 0) {
                    temp = map[i][k];
                    map[i][k] = map[i][k - 1];
                    map[i][k - 1] = temp;
                    k--;
                }
            }
        }

        for (int i = 0; i < COUNT; i++) {
            for (int j = map[i].length - 1; j > 0; j--) {
                if (map[i][j] == map[i][j - 1]) {
                    map[i][j] *= 2;
                    score += map[i][j - 1];
                    map[i][j - 1] = 0;
                }
            }
        }

        for (int i = 0; i < COUNT; i++) {
            for (int j = map[i].length - 1; j > 0; j--) {
                k = j;
                while (k > 0 && map[i][k] == 0) {
                    temp = map[i][k];
                    map[i][k] = map[i][k - 1];
                    map[i][k - 1] = temp;
                    k--;
                }
            }
        }
        if (!isEquals(mapTemp, map)) {
            createRandomRect();
        }
        invalidate();
        isMoved = true;
    }
}
