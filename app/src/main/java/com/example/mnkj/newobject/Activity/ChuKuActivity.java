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

import com.example.mnkj.newobject.Adapter.ChukuAdapter;
import com.example.mnkj.newobject.Adapter.RukuAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.PZWHScanBean;
import com.example.mnkj.newobject.Bean.ScanInputBean;
import com.example.mnkj.newobject.Bean.ScanInputNetworkBean;
import com.example.mnkj.newobject.Bean.ScanOutputBean;
import com.example.mnkj.newobject.Bean.ScanOutputNetworkBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;
import com.example.mnkj.newobject.Net.RequestCallBack;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//入库
public class ChuKuActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.chuku_recy)
    RecyclerView chuku_recy;
    ChukuAdapter adapter;
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
    DeleteDialog deleteDialog;

    private Boolean inRequest = false;//由于判断当前是否处于请求状态 防止因多次扫描开启多个请求

    public static final String SCN_CUST_ACTION_SCODE = "com.mingnong.scannerappnew";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";

    private String[] repString;
    private String zsm;//追溯码

    ProgressDialog dialog;

    private List<ScanOutputNetworkBean> list = new LinkedList<>();

    public interface CallBack {
        void showHint();

        void hideHint();

        void onItemClicked(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku);
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
                ((ChukuAdapter) chuku_recy.getAdapter()).deleteAll();
            }

            @Override
            public void cancel() {
                deleteDialog.cancel();
            }
        });


        adapter = new ChukuAdapter(ChuKuActivity.this, list, new ChuKuActivity.CallBack() {
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
                Intent intent = new Intent(ChuKuActivity.this, ChuKuItemActivity.class);
                intent.putExtra(Contance.DATA, adapter.getmDatas().get(position));
                startActivityForResult(intent, Contance.TYPE_A);
            }
        });
        chuku_recy.setAdapter(adapter);
        chuku_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.layout_next:
                try {
                    if (chuku_recy.getAdapter().getItemCount() == 0) return;
                    if (!((ChukuAdapter) chuku_recy.getAdapter()).checkItem()) {
                        ToastUtils.showShort(ChuKuActivity.this, "请完善红框中的数据");
                        return;//检查所有数据是否完善
                    }
                    Intent intent = new Intent(this, ChuKuNextActivity.class);
                    String json = adapter.getJson();
                    intent.putExtra(Contance.EXTRA_CHUKU_JSON, json);
                    startActivityForResult(intent, Contance.RequestCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_select_all:
                ((ChukuAdapter) chuku_recy.getAdapter()).selectAll();
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
            case Contance.RequestCode:
                if (resultCode == Contance.ResultCode) {
                    ((ChukuAdapter) chuku_recy.getAdapter()).clear();
                }
                break;
            case Contance.TYPE_A:
                if (resultCode == Contance.ResultCode) {
                    ScanOutputNetworkBean bean = (ScanOutputNetworkBean) data.getSerializableExtra(Contance.DATA);
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
                            Toast.makeText(ChuKuActivity.this, "二维码不符合条件:" + result, Toast.LENGTH_SHORT).show();
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
        //入库
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("tradeCode", repString[0]);
        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "GetSY_RgodownThe.ashx", params, new RequestCallBack<ScanOutputNetworkBean>() {
            @Override
            public void onFailure(Exception e) {
                inRequest = false;
                dialog.dismiss();
                if (e.getMessage().contains("Internal Server Error")) {
                    ToastUtils.showShort(ChuKuActivity.this, "没有查到当前药品数据,请重新扫描");
                } else if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    ToastUtils.showShort(ChuKuActivity.this, "服务器无响应");
                } else if (e instanceof SocketTimeoutException) {
                    ToastUtils.showShort(ChuKuActivity.this, "网络没有连接");
                } else {
                    ToastUtils.showShort(ChuKuActivity.this, e.getMessage());
                }
            }

            @Override
            public void getData(ScanOutputNetworkBean bean) {
                dialog.dismiss();
                inRequest = false;
                if (bean != null) {
                    String scrq = bean.getDataList().get(0).getFScDate().replace("/", "-");
                    String yxrq = bean.getDataList().get(0).getFYxqDate().replace("/", "-");
                    scrq = scrq.substring(0, scrq.indexOf(":") - 1);
                    yxrq = yxrq.substring(0, yxrq.indexOf(":") - 1);
                    bean.getDataList().get(0).setFScDate(scrq);
                    bean.getDataList().get(0).setFYxqDate(yxrq);
                    show(bean);
                }

            }
        });
    }

    public void show(ScanOutputNetworkBean bean) {
        adapter.addItem(bean);
    }
}
