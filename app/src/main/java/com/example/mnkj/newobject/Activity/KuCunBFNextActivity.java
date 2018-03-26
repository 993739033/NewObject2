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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class KuCunBFNextActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_ph)
    EditText et_ph;
    @Bind(R.id.et_xs_riqi)
    EditText et_xs_riqi;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    ProgressDialog dialog;

    JSONObject jsonobject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_cun_bfnext);
        ButterKnife.bind(this);
        try {
            initInfo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initView();
        initListener();
    }

    private void initListener() {
        btn_enter.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setMessage("报废数据上传中..");
    }

    //初始化票号、销售日期
    private void initInfo() throws JSONException {
        et_ph.setText(DateUtil.getDDBH());//订单编号
        et_xs_riqi.setText(DateUtil.getNowTime());//销毁日期
        String json = getIntent().getStringExtra(Contance.DATA);
        jsonobject = new JSONObject(json);
        jsonobject.put("FCodeID", et_ph.getText().toString());
        jsonobject.put("FXHDate", et_xs_riqi.getText().toString());
    }

    //新增客户
    private void uploadBFData() throws JSONException {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("TableName", "SY_XIAOHUI");
        params.addFormDataPart("fileJson", jsonobject.toString());
        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "UploadXiaoHui.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    Toast.makeText(KuCunBFNextActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                } else if (e instanceof SocketTimeoutException) {
                    Toast.makeText(KuCunBFNextActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showShort(KuCunBFNextActivity.this, e.getMessage());
                }
                dialog.cancel();
                setBeClicked(false);
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                ToastUtils.showShort(KuCunBFNextActivity.this, "报废数据上传成功");
                Intent intent = new Intent(KuCunBFNextActivity.this, KuCunActivity.class);
                startActivity(intent);
                finish();
                dialog.cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enter:

                if (getBeClicked()) return;
                try {
                    setBeClicked(true);
                    uploadBFData();
                } catch (JSONException e) {
                    setBeClicked(false);
                    e.printStackTrace();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }

    }
}
