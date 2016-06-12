package me.nereo.multi_image_selector.widget;

import android.content.Context;
import android.util.AttributeSet;

import me.nereo.multi_image_selector.widget.zoomview.GFImageView;

/**
 * Created by Haoxunwang on 2016/1/25.
 */
public class SquaredImageView extends GFImageView {


    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
