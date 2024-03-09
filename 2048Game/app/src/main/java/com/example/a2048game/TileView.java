package com.example.a2048game;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TileView extends FrameLayout {
    private TextView textView;
    public TileView(@NonNull Context context) {
        super(context);
        initView();
    }
    private void initView() {
        textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        params.setMargins(8, 8, 8, 8);
        addView(textView, params);
        setBackgroundColor(Color.WHITE);
    }
    public void updateTile(int value) {
        if (value == 0) {
            textView.setText("");
            textView.setBackgroundColor(Color.WHITE);
        } else {
            textView.setText(String.valueOf(value));
            // Customize background color based on values if needed
            setBackgroundColor(getTileBackgroundColor(value));
        }
    }
    private int getTileBackgroundColor(int value) {
        // Customize background color based on tile value
        // This is a simple example, you can modify it according to your preferences
        switch (value) {
            case 2:
                return Color.parseColor("#eee4da");
            case 4:
                return Color.parseColor("#ede0c8");
            case 8:
                return Color.parseColor("#f2b179");
            case 16:
                return Color.parseColor("#f59563");
            case 32:
                return Color.parseColor("#f67c5f");
            case 64:
                return Color.parseColor("#f65e3b");
            case 128:
                return Color.parseColor("#edcf72");
            case 256:
                return Color.parseColor("#edcc61");
            case 512:
                return Color.parseColor("#edc850");
            case 1024:
                return Color.parseColor("#edc53f");
            case 2048:
                return Color.parseColor("#edc22e");
            default:
                return Color.parseColor("#eeeeee");
        }
    }
    public void updateTile(String text) {
        textView.setText(text);
    }}
