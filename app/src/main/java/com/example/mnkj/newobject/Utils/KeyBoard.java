package com.example.mnkj.newobject.Utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by mnkj on 2017/9/20.
 */
//关闭键盘
public class KeyBoard {
    public static void closeSoftKeyBoard(Activity context) {
        View view = context.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
