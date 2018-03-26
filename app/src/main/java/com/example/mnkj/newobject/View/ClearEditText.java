package com.example.mnkj.newobject.View;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.R;

public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements
        OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private static final int mWH = 60;
    private int windowWidth = 0;//屏幕
    private int contentWidth = 400;//内容
    private Context mContext;
    private Boolean change = false;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }


    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources()
                    .getDrawable(R.drawable.et_delect_btn);
        }
        mClearDrawable.setBounds(0, 0, mWH, mWH);
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth()
                        - getPaddingRight() - mWH)
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        setAnimator();
    }


    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        setClearIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public void setAnimator() {
        //为空时使用动画
        if (TextUtils.isEmpty(this.getText().toString())) {
            final ValueAnimator valueanim = getAnimator();
            valueanim.start();
            valueanim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int padding = (int) valueAnimator.getAnimatedValue();
                    setPadding(padding, 0, 0, 0);
                }
            });
        }
    }

    public ValueAnimator getAnimator() {
        change = !change;
        WindowManager wm = ((Activity) mContext).getWindowManager();
        windowWidth = wm.getDefaultDisplay().getWidth();
        int mpadding = (windowWidth - contentWidth) / 2;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (change) {
            valueAnimator.setIntValues(0, mpadding);
        } else {
            valueAnimator.setIntValues(mpadding, 0);
        }
        valueAnimator.setDuration(300);
        return valueAnimator;
    }


}
