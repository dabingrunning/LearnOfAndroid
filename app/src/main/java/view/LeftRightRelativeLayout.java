package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by dabing on 2017/10/5.
 */

public class LeftRightRelativeLayout extends RelativeLayout {

    /**
     * item侧滑操作组件
     */
        // 左边视图
        private View mLeftView;
        // 右侧视图
        private View mRightView;

        // 左侧视图下一次layout时的left
        private int mLeftViewLeft;
        // 左边视图外边距
        private MarginLayoutParams mLeftLp;
        // 右侧视图外边距
        private MarginLayoutParams mRightLp;
        public  LeftRightRelativeLayout(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public  LeftRightRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            if (getChildCount() != 2) {
                throw new IllegalStateException( LeftRightRelativeLayout.class.getSimpleName() + "必须有且只有两个子控件");
            }
            mLeftView = getChildAt(0);
            mRightView = getChildAt(1);
            // 避免底部视图被隐藏时还能获取焦点被点击
//            mRightView.setVisibility(INVISIBLE);

            mLeftLp = (LayoutParams) mLeftView.getLayoutParams();
            mRightLp = (LayoutParams) mRightView.getLayoutParams();
            mLeftViewLeft = getPaddingLeft() + mLeftLp.leftMargin;
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {

//            int leftTop = getPaddingTop() + mLeftLp.topMargin;
//            int leftBottom = getMeasuredHeight() - getPaddingBottom() - mLeftLp.bottomMargin;
//            int leftRight = mLeftViewLeft + mLeftView.getMeasuredWidth();

//            int rightTop = getPaddingTop() + mRightLp.topMargin;
//            int rightBottom = getMeasuredHeight() - getPaddingBottom() - mRightLp.bottomMargin;
//            int rightLeft = getMeasuredWidth() - getPaddingRight() - mRightLp.rightMargin;
//            int rightLeft = leftRight;
//            int rightRight = rightLeft + mRightView.getMeasuredWidth();
//            mLeftView.layout(mLeftViewLeft, leftTop, leftRight, leftBottom);
//            mRightView.layout(rightLeft, rightTop, rightRight, rightBottom);

            //内部子控件居于父控件底部：
                int bottom = getMeasuredHeight();
                int top =getMeasuredHeight() - mLeftView.getMeasuredHeight();
                int leftRight = mLeftViewLeft + mLeftView.getMeasuredWidth();
                int rightRight = leftRight + mRightView.getMeasuredWidth();
                mLeftView.layout(mLeftViewLeft, top, leftRight, bottom);
                mRightView.layout(leftRight, top, rightRight, bottom);

        }

}
