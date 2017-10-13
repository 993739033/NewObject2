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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Bean.ScanInputNetworkBean;
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

//供应商数据新增界面
public class GYSInfoAddActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.et_gys_name)
    EditText et_gys_name;
    @Bind(R.id.et_name)
    EditText et_name;
    @Bind(R.id.et_phone)
    EditText et_phone;
    @Bind(R.id.et_syxkz)
    EditText et_syxkz;
    @Bind(R.id.et_gmp)
    EditText et_gmp;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gysinfo_add);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("数据上传中");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

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
        params.addFormDataPart("TableName", "SY_Supplier");
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("Lxrdh", et_phone.getText().toString());//电话号码
        params.addFormDataPart("Gysmc", et_gys_name.getText().toString());//供应商名
        params.addFormDataPart("fileJson", json);


        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "UploadGetgys.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                dialog.cancel();
                if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    Toast.makeText(GYSInfoAddActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                } else if (e instanceof SocketTimeoutException) {
                    Toast.makeText(GYSInfoAddActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showShort(GYSInfoAddActivity.this, e.getMessage());
                }
                setBeClicked(false);
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                dialog.cancel();
                ToastUtils.showShort(GYSInfoAddActivity.this,"上传成功");
                setBeClicked(false);
                Intent intent = getIntent();
                setResult(Contance.ResultCode, intent);
                finish();
            }
        });
    }

    //检查数据
    private boolean checkData() {
        if (TextUtils.isEmpty(et_gys_name.getText().toString())) {
            ToastUtils.showShort(this, "供应商名称不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_name.getText().toString())) {
            ToastUtils.showShort(this, "联系人不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_phone.getText().toString())) {
            ToastUtils.showShort(this, "联系电话不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_syxkz.getText().toString())) {
            ToastUtils.showShort(this, "兽药许可证不能为空");
            return false;
        }
        return true;
    }

    private JSONObject getJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Gysmc", et_gys_name.getText().toString());
        json.put("LxrName", et_name.getText().toString());
        json.put("Lxrdh", et_phone.getText().toString());
        json.put("Xgzsh", et_syxkz.getText().toString());
        json.put("SyGmp", et_gmp.getText().toString());
        return json;
    }
}
