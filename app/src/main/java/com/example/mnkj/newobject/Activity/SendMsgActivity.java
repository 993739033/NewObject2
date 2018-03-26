package com.example.mnkj.newobject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//发短信
public class SendMsgActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
    }

    private void initView() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
//
//    /**
//     * 直接调用短信接口发短信
//     *
//     * @param phoneNumber
//     * @param message
//     */
//    public void sendSMS(String phoneNumber, String message) {
//        //获取短信管理器
//        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
//        //拆分短信内容（手机短信长度限制）
//        List<String> divideContents = smsManager.divideMessage(message);
//        for (String text : divideContents) {
//            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, deliverPI);
//        }
//    }
}
