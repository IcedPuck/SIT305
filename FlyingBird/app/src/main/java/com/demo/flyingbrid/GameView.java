package com.demo.flyingbrid;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends View {
    //4x4 map
    public static final int count = 4;
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
    public GameView(Context context) {
        super(context);
    }
}
