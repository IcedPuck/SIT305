package com.demo.flyingbrid;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class Card extends FrameLayout {
    public Card(@NonNull Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(32);

        LayoutParams lp = new LayoutParams(-1,-1);
        addView(label,lp);

        setNum(0);
    }

    private int num = 0;

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num=num;
        label.setText(num+"");
    }

    //determine whether duplicate cards
    public boolean equal(Card card){
        return getNum() == card.getNum();
    }
    private TextView label;
}
