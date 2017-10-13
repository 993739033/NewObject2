package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Adapter.RukuAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Bean.GYSBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class RuKuNextActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.btn_gys_select)
    Button btn_gys_select;
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
    @Bind(R.id.tv_bj)
    TextView tv_bj;
    @Bind(R.id.layout_title)
    View layout_title;
    String rukuJson;
    private ProgressDialog dialog;
    private Boolean canInput = false;//判断是否处于编辑状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku_next);
        ButterKnife.bind(this);
        initListener();
        rukuJson = getIntent().getStringExtra(Contance.EXTRA_RUKU_JSON);
        dialog = new ProgressDialog(this);
        dialog.setMessage("上传中");
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        btn_gys_select.setOnClickListener(this);
        tv_bj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent1 = getIntent();
                setResult(Contance.ResultCodeFailed, intent1);
                finish();
                break;
            case R.id.btn_enter:
                //如果为编辑状态则需判断是否为空
                if (canInput) {
                    if (!checkGYSData()) {
                        return;
                    }
                }
                if (checkData()) {
                    try {
                        if (canInput) {
                            uploadGYSInfo();
                        } else {
                            uploadItem();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_gys_select:
                changeState(false);
                Intent intent = new Intent(this, GYSSelectActivity.class);
                startActivityForResult(intent, Contance.RequestCode);
                break;
            case R.id.tv_bj:
                canInput = !canInput;
                changeState(canInput);
                break;
        }
    }

    private Boolean checkData() {
        if (TextUtils.isEmpty(rukuJson)) {
            ToastUtils.showShort(RuKuNextActivity.this, "入库的数据不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_gys_name.getText().toString())) {
            ToastUtils.showShort(RuKuNextActivity.this, "供应商选择不能为空");
            return false;
        }
        return true;
    }

    //改变是否处可添加的状态
    private void changeState(Boolean state) {
        if (state) {
            tv_bj.setText("取消编辑");
            et_gys_name.setEnabled(true);
            et_name.setEnabled(true);
            et_phone.setEnabled(true);
            et_syxkz.setEnabled(true);
            et_gmp.setEnabled(true);
        } else {
            tv_bj.setText("编辑信息");
            et_gys_name.setEnabled(false);
            et_name.setEnabled(false);
            et_phone.setEnabled(false);
            et_syxkz.setEnabled(false);
            et_gmp.setEnabled(false);
            et_gys_name.setText("");
            et_name.setText("");
            et_phone.setText("");
            et_syxkz.setText("");
            et_gmp.setText("");
            layout_title.setFocusable(true);
            layout_title.setFocusableInTouchMode(true);
            layout_title.requestFocus();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Contance.RequestCode:
                if (data != null) {
                    GYSBean.DataList bean = (GYSBean.DataList) data.getSerializableExtra(Contance.Result);
                    et_gys_name.setText(bean.getGysmc());
                    et_name.setText(bean.getLxrName());
                    et_syxkz.setText(bean.getXgzsh());
                    et_phone.setText(bean.getLxrdh());
                    et_gmp.setText(bean.getSyGmp());
                }
                break;
        }
    }

    private void uploadItem() throws JSONException {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        JSONObject json = getJson();
        params.addFormDataPart("fileJson", json.toString());
        params.addFormDataPart("TableName", "SY_RgodownThe");
        HttpRequest.post(Contance.BASE_URL + "UploadTemfile.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                ToastUtils.showShort(RuKuNextActivity.this, e.getMessage());
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                ToastUtils.showShort(RuKuNextActivity.this, "数据上传成功!");
                Intent intent = getIntent();
                setResult(Contance.ResultCode, intent);
                finish();
            }
        });
    }

    //新增供应商
    private void uploadGYSInfo() throws JSONException {
        RequestParams params = new RequestParams();
        String json = getGYSJson().toString();
        params.addFormDataPart("TableName", "SY_Supplier");
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("fileJson", json);
        params.addFormDataPart("FBuyTel", et_phone.getText().toString());//电话号码
        params.addFormDataPart("Gysmc", et_gys_name.getText().toString());//电话号码
        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "UploadGetgys.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    Toast.makeText(RuKuNextActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                } else if (e instanceof SocketTimeoutException) {
                    Toast.makeText(RuKuNextActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showShort(RuKuNextActivity.this, e.getMessage());
                }
                dialog.cancel();
                setBeClicked(false);
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                try {
                    uploadItem();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //检查供应商数据
    private boolean checkGYSData() {
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
        JSONObject json = new JSONObject(rukuJson);
        json.put("FGysmc", et_gys_name.getText().toString());
        json.put("FLxr", et_name.getText().toString());
        json.put("FLxrdh", et_phone.getText().toString());
        json.put("FXgzsh", et_syxkz.getText().toString());
        json.put("SyGmp", et_gmp.getText().toString());
        return json;
    }

    private JSONObject getGYSJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Gysmc", et_gys_name.getText().toString());
        json.put("LxrName", et_name.getText().toString());
        json.put("Lxrdh", et_phone.getText().toString());
        json.put("Xgzsh", et_syxkz.getText().toString());
        json.put("SyGmp", et_gmp.getText().toString());
        return json;
    }
}
