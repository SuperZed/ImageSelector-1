package me.nereo.multi_image_selector.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Haoxunwang on 2016/1/25.
 */
public class SquaredLayout extends FrameLayout {

    public SquaredLayout(Context context) {
        super(context);
    }

    public SquaredLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
