package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by dabing on 2017/10/6.
 */

public class CustomerHorizontalScrollView extends HorizontalScrollView {
    private LinearLayout mWapper;
    private LinearLayout mLeftView;
    private LinearLayout mRightView;
    private int widthPixels;

    public CustomerHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomerHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWapper = (LinearLayout) getChildAt(0);
        mLeftView = (LinearLayout) mWapper.getChildAt(0);
        mRightView = (LinearLayout) mWapper.getChildAt(1);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        widthPixels = displayMetrics.widthPixels;
        mLeftView.getLayoutParams().width = widthPixels;
        mRightView.getLayoutParams().width = widthPixels;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
