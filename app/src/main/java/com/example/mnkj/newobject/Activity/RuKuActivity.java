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
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Adapter.RukuAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Base.BaseMsg;
import com.example.mnkj.newobject.Bean.PZWHScanBean;
import com.example.mnkj.newobject.Bean.ScanInputBean;
import com.example.mnkj.newobject.Bean.ScanInputNetworkBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//入库
public class RuKuActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.ruku_recy)
    RecyclerView ruku_recy;
    RukuAdapter adapter;
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

    private List<ScanInputBean> list = new LinkedList<>();

    public interface CallBack {
        void showHint();

        void hideHint();

        void onItemClicked(int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku);
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
                ((RukuAdapter) ruku_recy.getAdapter()).deleteAll();
            }

            @Override
            public void cancel() {
                deleteDialog.cancel();
            }
        });


        adapter = new RukuAdapter(RuKuActivity.this, list, new CallBack() {
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
                Intent intent = new Intent(RuKuActivity.this, RuKuItemActivity.class);
                intent.putExtra(Contance.DATA, adapter.getmDatas().get(position));
                startActivityForResult(intent, Contance.TYPE_A);
            }
        });
        ruku_recy.setAdapter(adapter);
        ruku_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.layout_next:
                try {
                    if (ruku_recy.getAdapter().getItemCount() == 0) return;
                    if (!((RukuAdapter) ruku_recy.getAdapter()).checkItem()) {
                        ToastUtils.showShort(RuKuActivity.this, "请完善红框中的数据");
                        return;//检查所有数据是否完善
                    }
                    Intent intent = new Intent(this, RuKuNextActivity.class);
                    String json = adapter.getJson();
                    intent.putExtra(Contance.EXTRA_RUKU_JSON, json);
                    startActivityForResult(intent, Contance.RequestCode);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_select_all:
                ((RukuAdapter) ruku_recy.getAdapter()).selectAll();
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
                    ((RukuAdapter) ruku_recy.getAdapter()).clear();
                }
                break;
            case Contance.TYPE_A:
                if (resultCode == Contance.ResultCode) {
                    ScanInputBean bean = (ScanInputBean) data.getSerializableExtra(Contance.DATA);
                    adapter.update(bean);
                }
                break;
            case Contance.TYPE_B:
                if (resultCode == Contance.ResultCode) {
                    PZWHScanBean.DataList bean = (PZWHScanBean.DataList) data.getSerializableExtra(Contance.DATA);
                    zsm = data.getStringExtra(Contance.DATA_1);
                    show(initData(bean));
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
                            Toast.makeText(RuKuActivity.this, "二维码不符合条件:" + result, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    inRequest = true;
//                    requestPZWH(repString[2]);
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
            params.addFormDataPart("httpType", "1");
            params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
            params.addFormDataPart("ApprovalNum", repString[2]);
            params.addFormDataPart("tradeCode", repString[0]);
            dialog.show();
            HttpRequest.post(Contance.BASE_URL + "getNYBProductT.ashx", params, new RequestCallBack<ScanInputNetworkBean>() {
                @Override
                public void onFailure(Exception e) {
                    if (e.getMessage().contains("Internal Server Error")) {
                        inRequest = false;
                        dialog.dismiss();
                        Toast.makeText(RuKuActivity.this, "没有查到当前药品数据,请重新扫描", Toast.LENGTH_SHORT).show();
                    } else if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                        inRequest = false;
                        dialog.dismiss();
                        Toast.makeText(RuKuActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                    } else if (e instanceof SocketTimeoutException) {
                        inRequest = false;
                        dialog.dismiss();
                        Toast.makeText(RuKuActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                    }
                    else if (e.getMessage().contains("当前追溯码已经入库")) { //当前追溯码已经入库 请重新扫描
                        inRequest = false;
                        dialog.dismiss();
                        ToastUtils.showShort(RuKuActivity.this, e.getMessage());
                    }
                    else {
                        requestPZWH(repString[2]);
                    }
                }

                @Override
                public void getData(ScanInputNetworkBean bean) {
                    dialog.dismiss();
                    inRequest = false;
                    if (bean.getErrCode() == 0) {
                        ScanInputNetworkBean.DataBean beanData = bean.getData();
                        ScanInputBean scanInputBean = new ScanInputBean();
                        scanInputBean.setTradeCodeList(beanData.getTradeCode());
                        scanInputBean.setRukutime(DateUtil.getNowTime());//入库时间
                        scanInputBean.setName(beanData.getRows().get(0).getQyname());//企业名称
                        scanInputBean.setPhone(repString[4]);//电话
                        scanInputBean.setExpiryDate(beanData.getRows().get(0).getSxrq());//有效期
                        scanInputBean.setProductionDate(beanData.getRows().get(0).getScrq());//生产日期
                        scanInputBean.setZhuiSuMa(repString[0]);//追溯码
                        scanInputBean.setUnit(beanData.getRows().get(0).getMinpackunit());//单位
                        scanInputBean.setTongYongMing(beanData.getRows().get(0).getCpname());//通用名
                        scanInputBean.setWenHao(beanData.getRows().get(0).getPzwh());//批准文号
                        scanInputBean.setProductNumber(beanData.getRows().get(0).getPh());//生产批号
                        scanInputBean.setYplxname(beanData.getRows().get(0).getYplxname());//隐藏字段 抗生素
                        scanInputBean.setPresellPrice("0");//预售价格
                        scanInputBean.setPurchasePrice("0");//采购价格

                        scanInputBean.setFDljg("");//代理机构
                        scanInputBean.setCpname("");//商品名称
                        scanInputBean.setSpecification(beanData.getRows().get(0).getSpecification());//规格
                        scanInputBean.setFJesum("0");//金额总计
                        scanInputBean.setYpArea("");//存放区域
                        scanInputBean.setFStoreHj("");//存储环境
                        scanInputBean.setNm("");//内码

                        scanInputBean.setFCodeID(DateUtil.getDDBH());//订单编号
                        //计算数量 计算规则问后台
                        try {
                            String[] split = beanData.getRows().get(0).getTagratio().split(":");
                            if (beanData.getRows().get(0).getTmjb() == 1) {
                                scanInputBean.setCount("1");//数量
                            } else {
                                scanInputBean.setCount(String.valueOf(Integer.valueOf(split[split.length - 1]) /
                                        Integer.valueOf(split[split.length - beanData.getRows().get(0).getTmjb()])));//数量
                            }
                        } catch (Exception e) {
                            Toast.makeText(RuKuActivity.this, "数量解析出错", Toast.LENGTH_SHORT).show();
                        }
                        printLog(scanInputBean);//打印log日志
                        //没有预售价格 没有购买价格
                        show(scanInputBean);
                    } else {
                        Toast.makeText(RuKuActivity.this, bean.getErrMsg(), Toast.LENGTH_SHORT).show();
                        initData();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(RuKuActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    //当通过批准文号查询时 可能返回多条商品信息 弹列表选择
    private void requestPZWH(String pzwh) {
        RequestParams params = new RequestParams();
        params.addFormDataPart("ApprovalNum", pzwh);
        HttpRequest.post(Contance.BASE_URL + "GetList.ashx", params, new RequestCallBack<PZWHScanBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                inRequest = false;
                if (e.getMessage().contains("Internal Server Error")) {
                    Toast.makeText(RuKuActivity.this, "没有查到当前药品数据,请重新扫描", Toast.LENGTH_SHORT).show();
                } else if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    Toast.makeText(RuKuActivity.this, "服务器无响应", Toast.LENGTH_SHORT).show();
                } else if (e instanceof SocketTimeoutException) {
                    Toast.makeText(RuKuActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
                } else {
                    ToastUtils.showShort(RuKuActivity.this, "请求失败");
                }
            }

            @Override
            public void getData(PZWHScanBean bean) {
                dialog.dismiss();
                inRequest = false;
                if (bean.getDataList() != null) {
                    if (bean.getDataList().size() >= 2) {
                        Intent intent = new Intent(RuKuActivity.this, RuKuPZWHResultSelectActivity.class);
                        intent.putExtra(Contance.DATA_1, repString[0]);//追溯码
                        intent.putExtra(Contance.DATA, bean);
                        startActivityForResult(intent, Contance.TYPE_B);
                    } else {
                        zsm = repString[0];
                        show(initData(bean.getDataList().get(0)));
                    }
                }
            }
        });
    }


    //201512270003700019840759，注射用硫酸链霉素，兽药字（2015）220442658，泰3信，0825-5895608
    //请求不到数据时 初始列表数据
    private void initData() {
        ScanInputBean scanInputBean = new ScanInputBean();
        scanInputBean.setRukutime(DateUtil.getNowTime());//入库时间
        scanInputBean.setTradeCodeList(repString[0]);
        scanInputBean.setName(repString[3]);//企业名称
        scanInputBean.setPhone(repString[4]);//电话
        String yxrq = new String(Integer.parseInt(repString[0].substring(0, 4)) + 2 + "-" + repString[0].substring(4, 6) + "-" + repString[0].substring(6, 8));
        String scrq = new String(repString[0].substring(0, 4) + "-" + repString[0].substring(4, 6) + "-" + repString[0].substring(6, 8));
        scanInputBean.setExpiryDate(yxrq);//有效期
        scanInputBean.setProductionDate(scrq);//生产日期
        scanInputBean.setZhuiSuMa(repString[0]);//追溯码
        scanInputBean.setUnit("盒");//单位
        scanInputBean.setTongYongMing(repString[1]);//通用名
        scanInputBean.setWenHao(repString[2]);//批准文号
        scanInputBean.setProductNumber(repString[0].substring(0, 9));//生产批号
        scanInputBean.setYplxname("");//隐藏字段 抗生素
        scanInputBean.setPresellPrice("0");//预售价格
        scanInputBean.setPurchasePrice("0");//采购价格
        scanInputBean.setCount("1");//数量
        //没有预售价格 没有购买价格
        scanInputBean.setFDljg("");//代理机构
        scanInputBean.setCpname("");//商品名称
        scanInputBean.setSpecification("");//规格
        scanInputBean.setFJesum("0");//金额总计
        scanInputBean.setYpArea("");//存放区域
        scanInputBean.setFStoreHj("");//存储环境
        scanInputBean.setNm("");//内码

        scanInputBean.setFCodeID(DateUtil.getDDBH());//订单编号


        printLog(scanInputBean);//打印log日志

        show(scanInputBean);
    }

    //根据批准文号 查询当数据 转换为ScanInputBean
    private ScanInputBean initData(PZWHScanBean.DataList bean) {
        ScanInputBean scanInputBean = new ScanInputBean();
        scanInputBean.setName(bean.getProductEnterPrise());
        scanInputBean.setTongYongMing(bean.getFTyName());
        scanInputBean.setCpname(bean.getProductName());
        scanInputBean.setSpecification(bean.getGuige());
        scanInputBean.setWenHao(bean.getPzwh());
        scanInputBean.setYplxname(bean.getYplx());
        scanInputBean.setZhuiSuMa(zsm);
        scanInputBean.setWenHao(repString[2]);//批准文号
        scanInputBean.setProductNumber(repString[0].substring(0, 9));//生产批号
        String yxrq = new String(Integer.parseInt(repString[0].substring(0, 4)) + 2 + "-" + repString[0].substring(4, 6) + "-" + repString[0].substring(6, 8));
        String scrq = new String(repString[0].substring(0, 4) + "-" + repString[0].substring(4, 6) + "-" + repString[0].substring(6, 8));
        scanInputBean.setProductionDate(scrq);//生产日期
        scanInputBean.setExpiryDate(yxrq);//有效期
        scanInputBean.setRukutime(DateUtil.getNowTime());//入库时间
        scanInputBean.setPresellPrice("0");//预售价格
        scanInputBean.setPurchasePrice("0");//采购价格

        scanInputBean.setFCodeID(DateUtil.getDDBH());//订单编号

        return scanInputBean;
    }


    //打印扫描后的数据信息
    private void printLog(ScanInputBean bean) {
        String Tag = "inputbean";
        Log.d(Tag, "追溯码:" + bean.getZhuiSuMa());
        Log.d(Tag, "通用名:" + bean.getTongYongMing());
        Log.d(Tag, "批准文号:" + bean.getWenHao());
        Log.d(Tag, "企业名称:" + bean.getName());
        Log.d(Tag, "数量:" + bean.getCount());
        Log.d(Tag, "生产日期:" + bean.getProductionDate());
        Log.d(Tag, "有效期:" + bean.getExpiryDate());
        Log.d(Tag, "生产批号:" + bean.getProductNumber());
        Log.d(Tag, "购买价格:" + bean.getPurchasePrice());
        Log.d(Tag, "预售价格:" + bean.getPresellPrice());
        Log.d(Tag, "抗生素:" + bean.getYplxname());
        Log.d(Tag, "tradeCodeList:" + bean.getTradeCodeList());
        Log.d(Tag, "代理机构:" + bean.getFDljg());
        Log.d(Tag, "商品名称:" + bean.getCpname());
        Log.d(Tag, "规格:" + bean.getSpecification());
        Log.d(Tag, "金额总计:" + bean.getFJesum());
        Log.d(Tag, "存放区域:" + bean.getYpArea());
        Log.d(Tag, "存储环境:" + bean.getFStoreHj());

    }

    public void show(ScanInputBean bean) {
        adapter.addItem(bean);
    }

}
