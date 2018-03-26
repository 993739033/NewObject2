package com.example.mnkj.newobject.View;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/27.
 */
//选项弹出dialog
public class SelectDialog extends AlertDialog implements View.OnClickListener {
    @Bind(R.id.tv_select_pz)
    TextView tv_select_pz;
    @Bind(R.id.tv_select_xc)
    TextView tv_select_xc;
    @Bind(R.id.btn_cancel)
    Button btn_cancel;
    Context mContext;

    CallBack callBack;

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onClick(View view) {
        if (callBack == null) return;
        switch (view.getId()) {
            case R.id.tv_select_pz:
                callBack.pz();
                break;
            case R.id.tv_select_xc:
                callBack.xc();
                break;
            case R.id.btn_cancel:
                callBack.cancel();
                break;
        }
    }


    public interface CallBack {
        void pz();//拍照

        void xc();//相册

        void cancel();//取消
    }

    protected SelectDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected SelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public SelectDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }


    private void initView() {
        View parent = LayoutInflater.from(getContext()).inflate(R.layout.layout_select_dialog, null);
        ButterKnife.bind(this, parent);
        setContentView(parent);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(attributes);
        getWindow().setWindowAnimations(R.style.SelecteDialogAnim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        tv_select_pz.setOnClickListener(this);
        tv_select_xc.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }
}
