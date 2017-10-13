package com.example.mnkj.newobject.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

//加载显示更多时动画类
public class HiddenAnimUtils {

    private int mHeight;//伸展高度

    private View hideView, down;//需要展开隐藏的布局，开关控件

    private ObjectAnimator animation;//旋转动画

    /**
     * 构造器(可根据自己需要修改传参)
     *
     * @param context  上下文
     * @param hideView 需要隐藏或显示的布局view
     * @param down     按钮开关的view
     */
    public static HiddenAnimUtils newInstance(Context context, View hideView, View down, int mHeight) {
        return new HiddenAnimUtils(context, hideView, down, mHeight);
    }

    private HiddenAnimUtils(Context context, View hideView, View down, int mHeight) {
        this.hideView = hideView;
        this.down = down;
        this.mHeight = mHeight;
    }

    /**
     * 开关
     */
    public void toggle() {
        startAnimation();
        if (View.VISIBLE == hideView.getVisibility()) {
            closeAnimate(hideView);//布局隐藏
        } else {
            openAnim(hideView);//布局铺开
        }
    }

    /**
     * 开关旋转动画
     */
    private void startAnimation() {
        if (View.VISIBLE == hideView.getVisibility()) {
            animation = ObjectAnimator.ofFloat(down, "rotation", 180, 360);
        } else {
            animation = ObjectAnimator.ofFloat(down, "rotation", 0, 180);
        }
        animation.setDuration(200);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        if (down == null) return;
        animation.start();

    }

    private void openAnim(View v) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, mHeight);
        animator.start();
    }

    private void closeAnimate(final View view) {
        ValueAnimator animator = createDropAnimator(view, mHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, final int start, final int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
