package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class KHInfoAddActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.et_gmr_name)
    EditText et_gmr_name;
    @Bind(R.id.et_gmr_phone)
    EditText et_gmr_phone;
    @Bind(R.id.et_yzc)
    EditText et_yzc;
    @Bind(R.id.et_gmr_address)
    EditText et_gmr_address;
    @Bind(R.id.et_yzcd)
    EditText et_yzcd;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khinfo_add);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据上传中");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_enter:
                if (checkData() && !getBeClicked()) {
                    setBeClicked(true);
                    try {
                        upload();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    //数据上传
    private void upload() throws JSONException {
        RequestParams params = new RequestParams();
        String json = getJson().toString();
        params.addFormDataPart("TableName", "SY_FBuyPerson");
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("FBuyTel", et_gmr_phone.getText().toString());
        params.addFormDataPart("fileJson", json);
        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "UploadFBuyPerson.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                dialog.cancel();
                if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    Toast.makeText(KHInfoAddActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                } else if (e instanceof SocketTimeoutException) {
                    Toast.makeText(KHInfoAddActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showShort(KHInfoAddActivity.this, e.getMessage());
                }
                setBeClicked(false);
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                dialog.cancel();
                ToastUtils.showShort(KHInfoAddActivity.this, "上传成功");
                setBeClicked(false);
                Intent intent = getIntent();
                setResult(Contance.ResultCode, intent);
                finish();
            }
        });
    }

    //检查数据
    private boolean checkData() {
        if (TextUtils.isEmpty(et_gmr_name.getText().toString())) {
            ToastUtils.showShort(this, "购买人姓名不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_gmr_phone.getText().toString())) {
            ToastUtils.showShort(this, "购买人电话不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_yzc.getText().toString())) {
            ToastUtils.showShort(this, "规模养殖场名不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_gmr_address.getText().toString())) {
            ToastUtils.showShort(this, "购买人地址不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_yzcd.getText().toString())) {
            ToastUtils.showShort(this, "规模养殖场地不能为空");
            return false;
        }
        return true;
    }

    private JSONObject getJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("FBuyName", et_gmr_name.getText().toString());
        json.put("FBuyTel", et_gmr_phone.getText().toString());
        json.put("FBuyAddress", et_gmr_address.getText().toString());
        json.put("FGmyzc", et_yzc.getText().toString());
        json.put("FGmyzcd", et_yzcd.getText().toString());
        return json;
    }
}
