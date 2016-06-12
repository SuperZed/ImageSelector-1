package me.nereo.multi_image_selector.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决Android多点触控崩溃的系统bug，捕获IllegalArgumentException异常
 * Created by Haoxunwang on 2016/1/7.
 */
public class GFViewPager extends ViewPager {
    public GFViewPager(Context context) {
        super(context);
    }

    public GFViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super .dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ignored) {
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return false ;
    }
}