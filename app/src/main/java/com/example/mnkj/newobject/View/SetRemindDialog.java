package com.example.mnkj.newobject.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.KuCunActivity;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.KeyBoard;
import com.example.mnkj.newobject.Utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/27.
 */
//设置提醒
public class SetRemindDialog extends Dialog {
    TextView dialog_cancel;
    TextView dialog_enter;

    EditText et_normal_name;
    EditText et_remind_count;

    Context mContext;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
        initListener();
    }

    CallBack callBack;

    public void setShowName(String name) {
        if (TextUtils.isEmpty(name)) return;
        et_normal_name.setText(name);
    }


    public void setShowCount(String count) {
        if (TextUtils.isEmpty(count)) return;
        et_remind_count.setText(count);
    }

    public interface CallBack {

        void enter(String name, String count);

        void cancel();
    }

    protected SetRemindDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected SetRemindDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public SetRemindDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }


    private void initView() {
        View parent = LayoutInflater.from(getContext()).inflate(R.layout.layout_set_remind_dialog, null);
        et_normal_name = parent.findViewById(R.id.et_normal_name);
        et_remind_count = parent.findViewById(R.id.et_remind_count);
        dialog_cancel = parent.findViewById(R.id.dialog_cancel);
        dialog_enter = parent.findViewById(R.id.dialog_enter);
        setContentView(parent);
    }

    private void initListener() {
        if (callBack != null) {
            dialog_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(et_remind_count.getText().toString())) {
                        ToastUtils.showShort(mContext, "最小提醒数量不能为空");
                        return;
                    }
                    KeyBoard.closeInputMethod(mContext, et_remind_count);
                    callBack.enter(et_normal_name.getText().toString(), et_remind_count.getText().toString());
                }
            });
            dialog_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KeyBoard.closeInputMethod(mContext, et_remind_count);
                    callBack.cancel();
                }
            });

        }
    }
}
