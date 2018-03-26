package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Adapter.JHTHAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Bean.JHTHBean;
import com.example.mnkj.newobject.Bean.PZWHScanBean;
import com.example.mnkj.newobject.Bean.ScanInputBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//进货退货
public class JHTHActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.jhth_recy)
    RecyclerView jhth_recy;
    JHTHAdapter adapter;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.layout_next)
    TextView layout_next;
    @Bind(R.id.tv_hint)
    TextView tv_hint;
    @Bind(R.id.btn_select_all)
    Button btn_select_all;
    @Bind(R.id.btn_delete)
    Button btn_delete;
    DeleteDialog deleteDialog, uploadDialog;

    private Boolean inRequest = false;//由于判断当前是否处于请求状态 防止因多次扫描开启多个请求

    public static final String SCN_CUST_ACTION_SCODE = "com.mingnong.scannerappnew";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    private String[] repString;
    private String zsm;//追溯码

    ProgressDialog dialog;

    private List<JHTHBean.DataList> list = new LinkedList<>();

    public interface CallBack {
        void showHint();

        void hideHint();

        void onItemClicked(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhth);
        ButterKnife.bind(this);
        initView();
        initListener();
        initBroadcastReciever();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        layout_next.setOnClickListener(this);
        btn_select_all.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        deleteDialog = new DeleteDialog(this, R.style.MyDialog);
        deleteDialog.setCallBack(new DeleteDialog.CallBack() {
            @Override
            public void enter() {
                deleteDialog.cancel();
                ((JHTHAdapter) jhth_recy.getAdapter()).deleteAll();
            }

            @Override
            public void cancel() {
                deleteDialog.cancel();
            }
        });
        uploadDialog = new DeleteDialog(this, R.style.MyDialog);
        uploadDialog.setTitle("确认上传数据？");
        uploadDialog.setCallBack(new DeleteDialog.CallBack() {
            @Override
            public void enter() {
                if (getBeClicked()) return;
                try {
                    setBeClicked(true);
                    dialog.setMessage("数据上传中");
                    dialog.show();
                    uploadItem();
                } catch (JSONException e) {
                    setBeClicked(false);
                    e.printStackTrace();
                }
                uploadDialog.cancel();
            }

            @Override
            public void cancel() {
                uploadDialog.cancel();
            }
        });


        adapter = new JHTHAdapter(JHTHActivity.this, list, new CallBack() {
            @Override
            public void showHint() {
                if (tv_hint.getVisibility() == View.GONE) {
                    tv_hint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void hideHint() {
                if (tv_hint.getVisibility() == View.VISIBLE) {
                    tv_hint.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(JHTHActivity.this, JHTHItemActivity.class);
                intent.putExtra(Contance.DATA, adapter.getmDatas().get(position));
                startActivityForResult(intent, Contance.TYPE_A);
            }
        });
        jhth_recy.setAdapter(adapter);
        jhth_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.layout_next:
                if (jhth_recy.getAdapter().getItemCount() == 0) return;
                if (!((JHTHAdapter) jhth_recy.getAdapter()).checkItem()) {
                    ToastUtils.showShort(JHTHActivity.this, "请完善红框中的数据");
                    return;//检查所有数据是否完善
                }
                uploadDialog.show();
                break;
            case R.id.btn_select_all:
                ((JHTHAdapter) jhth_recy.getAdapter()).selectAll();
                break;
            case R.id.btn_delete:
                if (adapter.hasCheck()) {
                    deleteDialog.show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Contance.TYPE_A:
                if (resultCode == Contance.ResultCode) {
                    JHTHBean.DataList bean = (JHTHBean.DataList) data.getSerializableExtra(Contance.DATA);
                    adapter.update(bean);
                }
                break;
            case Contance.TYPE_B:
                if (resultCode == Contance.ResultCode) {
                    PZWHScanBean.DataList bean = (PZWHScanBean.DataList) data.getSerializableExtra(Contance.DATA);
                    zsm = data.getStringExtra(Contance.DATA_1);
                }
                break;
        }
    }

    /**
     * 初始化广播接收器，AUTOID系列安卓产品上的系统软件扫描工具相对应
     */
    private void initBroadcastReciever() {
        // 发送广播到扫描工具内的应用设置项
        Intent intent = new Intent("com.android.scanner.service_settings");
        // 修改扫描工具内应用设置中的开发者项下的广播名称
        intent.putExtra("action_barcode_broadcast", SCN_CUST_ACTION_SCODE);
        // 修改扫描工具内应用设置下的条码发送方式为 "广播"
        intent.putExtra("barcode_send_mod e", "BROADCAST");
        // 修改扫描工具内应用设置下的结束符为"NONE"
        intent.putExtra("endchar", "NONE");

        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(SCN_CUST_ACTION_SCODE);
        registerReceiver(receiver, filter);
    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!inRequest) {
                String result;
                result = intent.getStringExtra(SCN_CUST_EX_SCODE);
                if (!TextUtils.isEmpty(result)) {
                    ///201512270003700019840759，注射用硫酸链霉素，兽药字（2015）220442658，泰信，0825-5895608
                    final String[] replaceString;
                    try {
                        replaceString = result.replaceAll("，", ",").split(",");
                        repString = replaceString;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    char num[] = replaceString[0].trim().toCharArray();//把字符串转换为字符数组
                    for (int i = 0; i < num.length; i++) {
                        if (!Character.isDigit(num[i])) {
                            Toast.makeText(JHTHActivity.this, "二维码不符合条件:" + result, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    inRequest = true;
                    request();
                }
            }
        }
    };

    //通过追溯码和批准文号查询入库数据
    private void request() {
        try {
            //入库
            RequestParams params = new RequestParams();
            params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
            params.addFormDataPart("tradeCode", repString[0]);
            dialog.show();
            HttpRequest.post(Contance.BASE_URL + "GetYPProduct.ashx", params, new RequestCallBack<JHTHBean>() {
                @Override
                public void onFailure(Exception e) {
                    inRequest = false;
                    dialog.dismiss();
                    if (e.getMessage().contains("Internal Server Error")) {
                        ToastUtils.showShort(JHTHActivity.this, "没有查到当前药品数据,请重新扫描");
                    } else if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                        ToastUtils.showShort(JHTHActivity.this, "服务器无响应");
                    } else if (e instanceof SocketTimeoutException) {
                        ToastUtils.showShort(JHTHActivity.this, "网络没有连接");
                    } else {
                        ToastUtils.showShort(JHTHActivity.this, e.getMessage());
                    }
                }

                @Override
                public void getData(JHTHBean bean) {
                    dialog.dismiss();
                    inRequest = false;
                    bean.getDataList().get(0).setBechecked(false);
                    bean.getDataList().get(0).setBemodfiyed(false);
                    String scrq = DateUtil.changeFormat(bean.getDataList().get(0).getFScDate());
                    String yxrq = DateUtil.changeFormat(bean.getDataList().get(0).getFYxqDate());
                    bean.getDataList().get(0).setFScDate(scrq);
                    bean.getDataList().get(0).setFYxqDate(yxrq);
                    bean.getDataList().get(0).setFJesum("0");
                    bean.getDataList().get(0).setFBuyNum("0");
                    show(bean);
                }
            });
        } catch (Exception e) {
            Toast.makeText(JHTHActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    //上传数据
    private void uploadItem() throws JSONException {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        String json = adapter.getJson();
        params.addFormDataPart("fileJson", json);
        params.addFormDataPart("TableName", "SY_RgodownTheOne");
        HttpRequest.post(Contance.BASE_URL + "UploadTemfile.ashx", params, new RequestCallBack<BaseMsg>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                setBeClicked(false);
                ToastUtils.showShort(JHTHActivity.this, e.getMessage());
            }

            @Override
            public void getData(BaseMsg baseMsg) {
                dialog.dismiss();
                ToastUtils.showShort(JHTHActivity.this, "数据上传成功!");
                finish();
            }
        });
    }

    public void show(JHTHBean bean) {
        adapter.addItem(bean);
    }
}
