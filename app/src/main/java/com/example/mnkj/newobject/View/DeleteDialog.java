package com.example.mnkj.newobject.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/27.
 */

public class DeleteDialog extends AlertDialog {
    @Bind(R.id.dialog_cancel)
    TextView dialog_cancel;
    @Bind(R.id.dialog_enter)
    TextView dialog_enter;
    Context mContext;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    CallBack callBack;
    public interface CallBack{
        void enter();
        void cancel();
    }
    protected DeleteDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected DeleteDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public DeleteDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }


    private void initView() {
        View parent = LayoutInflater.from(getContext()).inflate(R.layout.layout_delete_dialog, null);
        ButterKnife.bind(this,parent);
        setContentView(parent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        if (callBack != null) {
            dialog_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.enter();
                }
            });
            dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.cancel();
                }
            });

        }
    }
}
