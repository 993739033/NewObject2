package com.example.mnkj.newobject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.btn_huoquyanzhengma)
    Button btn_huoquyanzhengma;
    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Bind(R.id.btn_back)
    ImageView btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        btn_submit.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        getTime();

    }

    private void getTime() {
        //new倒计时对象,总共的时间,每隔多少秒更新一次时间
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
        //给Button设置点击时间,触发倒计时
        btn_huoquyanzhengma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCountDownTimer.start();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                startActivity(new Intent(this,ModifyPasswordActivity.class));
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }


    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            btn_huoquyanzhengma.setClickable(false);
            btn_huoquyanzhengma.setText(l/1000+"s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            btn_huoquyanzhengma.setText("重新获取验证码");
            //设置可点击
            btn_huoquyanzhengma.setClickable(true);
        }
    }
}
