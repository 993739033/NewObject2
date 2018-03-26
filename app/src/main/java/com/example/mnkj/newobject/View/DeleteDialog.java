package com.example.mnkj.newobject.View;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
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
    @Bind(R.id.tv_title)
    TextView tv_title;
    Context mContext;
    String titleContent;//标题

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

    public void setTitle(String title){
        titleContent = title;
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
        if (!TextUtils.isEmpty(titleContent)) {
            tv_title.setText(titleContent);
        }
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
