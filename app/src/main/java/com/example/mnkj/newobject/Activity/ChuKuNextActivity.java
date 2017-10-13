package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Bean.KHBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//出库下一步
public class ChuKuNextActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.btn_kh_select)
    Button btn_kh_select;
    @Bind(R.id.sp_xs_type)
    Spinner sp_xs_type;
    @Bind(R.id.et_ph)
    EditText et_ph;
    @Bind(R.id.et_xs_riqi)
    EditText et_xs_riqi;
    @Bind(R.id.et_gmr_name)
    EditText et_gmr_name;
    @Bind(R.id.et_gmr_address)
    EditText et_gmr_address;
    @Bind(R.id.et_yzc)
    EditText et_yzc;
    @Bind(R.id.et_yzcd)
    EditText et_yzcd;
    @Bind(R.id.et_gmr_phone)
    EditText et_gmr_phone;
    @Bind(R.id.tv_bj)
    TextView tv_bj;
    @Bind(R.id.layout_title)
    View layout_title;
    String chukuJson;
    private ProgressDialog dialog;
    private Boolean canInput = false;//判断是否处于编辑状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku_next);
        ButterKnife.bind(this);
        initInfo();
        bindSp();
        initListener();
        chukuJson = getIntent().getStringExtra(Contance.EXTRA_CHUKU_JSON);
        dialog = new ProgressDialog(this);
        dialog.setMessage("上传中");
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        btn_kh_select.setOnClickListener(this);
        tv_bj.setOnClickListener(this);
    }

    private void bindSp() {
        List<String> typeList = new ArrayList<>();
        typeList.add("销售");
        typeList.add("退回厂家");
        // 初始化下拉列表加载数据适配器
        ArrayAdapter adapter = new ArrayAdapter<String>(ChuKuNextActivity.this,
                android.R.layout.simple_spinner_item, typeList);
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_xs_type.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_enter:
                //如果为编辑状态则需判断是否为空
                if (canInput) {
                    if (!checkKHData()) {
                        return;
                    }
                }
                if (checkData()) {
                    try {
                        if (canInput) {
                            uploadKHInfo();
                        } else {
                            uploadItem();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_kh_select:
                changeState(false);
                Intent intent1 = new Intent(this, KHSelectActivity.class);
                startActivityForResult(intent1, Contance.RequestCode);
                break;
            case R.id.tv_bj:
                canInput = !canInput;
                changeState(canInput);
                break;

        }
    }

    private Boolean checkData() {
        if (TextUtils.isEmpty(chukuJson)) {
            ToastUtils.showShort(ChuKuNextActivity.this, "入库的数据不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_gmr_name.getText().toString())) {
            ToastUtils.showShort(ChuKuNextActivity.this, "供应商选择不能为空");
            return false;
        }
        return true;
    }

    //改变是否处可添加的状态
    private void changeState(Boolean state) {
        if (state) {
            tv_bj.setText("取消编辑");
            et_gmr_name.setEnabled(true);
            et_gmr_address.setEnabled(true);
            et_gmr_phone.setEnabled(true);
            et_yzc.setEnabled(true);
            et_yzcd.setEnabled(true);
        } else {
            tv_bj.setText("编辑信息");
            et_gmr_name.setEnabled(false);
            et_gmr_address.setEnabled(false);
            et_gmr_phone.setEnabled(false);
            et_yzc.setEnabled(false);
            et_yzcd.setEnabled(false);
            et_gmr_name.setText("");
            et_gmr_address.setText("");
            et_gmr_phone.setText("");
            et_yzc.setText("");
            et_yzcd.setText("");
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
                if (resultCode == Contance.ResultCode) {
                    if (data != null) {
                        KHBean.DataList bean = (KHBean.DataList) data.getSerializableExtra(Contance.Result);
                        et_gmr_name.setText(bean.getFBuyName());
                        et_gmr_phone.setText(bean.getFBuyTel());
                        et_gmr_address.setText(bean.getFBuyAddress());
                        et_yzc.setText(bean.getFGmyzc());
                        et_yzcd.setText(bean.getFGmyzcd());
                    }
                }
                break;
        }
    }

    private void uploadItem() throws JSONException {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("TableName", "SY_Xmarket");
        JSONObject json = getJson();
        params.addFormDataPart("fileJson", json.toString());
        HttpRequest.post(Contance.BASE_URL + "UploadTemfile.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                ToastUtils.showShort(ChuKuNextActivity.this, e.getMessage());
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                ToastUtils.showShort(ChuKuNextActivity.this, "数据上传成功!");
                Intent intent = getIntent();
                setResult(Contance.ResultCode, intent);
                finish();
            }
        });
    }

    //新增客户
    private void uploadKHInfo() throws JSONException {
        RequestParams params = new RequestParams();
        String json = getKHJson().toString();
        params.addFormDataPart("TableName", "SY_FBuyPerson");
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("FBuyTel", et_gmr_phone.getText().toString());//电话号码
        params.addFormDataPart("fileJson", json);
        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "UploadFBuyPerson.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    Toast.makeText(ChuKuNextActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                } else if (e instanceof SocketTimeoutException) {
                    Toast.makeText(ChuKuNextActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showShort(ChuKuNextActivity.this, "数据上传失败");
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

    //初始化票号、销售日期
    private void initInfo() {
        et_ph.setText(DateUtil.getDDBH());//订单编号
        et_xs_riqi.setText(DateUtil.getNowTime());//销售日期
    }


    //检查供应商数据
    private boolean checkKHData() {
        if (TextUtils.isEmpty(et_gmr_name.getText().toString())) {
            ToastUtils.showShort(this, "购买人姓名不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_gmr_phone.getText().toString())) {
            ToastUtils.showShort(this, "购买人手机电话不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_gmr_address.getText().toString())) {
            ToastUtils.showShort(this, "购买人地址不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_yzc.getText().toString())) {
            ToastUtils.showShort(this, "规模养殖场不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_yzcd.getText().toString())) {
            ToastUtils.showShort(this, "规模养殖场地不能为空");
            return false;
        }
        return true;
    }

    private JSONObject getJson() throws JSONException {
        JSONObject json = new JSONObject(chukuJson);
//        FBuyTel
//                FBuyName
//        FBuyAddress
//                FGmyzc
//        FGmyzcd
        json.put("FBuyTel", et_gmr_phone.getText().toString());//电话
        json.put("FBuyName", et_gmr_name.getText().toString());//购买人

        json.put("FCodeID", et_ph.getText().toString());//订单编号
        json.put("FXsDate", et_xs_riqi.getText().toString());//销售日期
        json.put("FStatus", sp_xs_type.getSelectedItem().toString());//销售类型
        return json;
    }

    private JSONObject getKHJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("FBuyTel", et_gmr_phone.getText().toString());
        json.put("FBuyName", et_gmr_name.getText().toString());
        json.put("FBuyAddress", et_gmr_address.getText().toString());
        json.put("FGmyzc", et_yzc.getText().toString());
        json.put("FGmyzcd", et_yzcd.getText().toString());
        return json;
    }
}
