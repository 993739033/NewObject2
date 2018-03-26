package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mnkj.newobject.Adapter.AccountGoodsInfoAdapter;
import com.example.mnkj.newobject.Adapter.ChukuAdapter;
import com.example.mnkj.newobject.Adapter.EndlessRecyclerOnScrollListener;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.GoodsInfoBean;
import com.example.mnkj.newobject.Bean.ScanOutputNetworkBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.account_goods_list_recy)
    RecyclerView account_goods_list_recy;
    @Bind(R.id.tv_condition)
    Spinner sp_condition;
    @Bind(R.id.tv_condition_1)
    Spinner sp_condition_1;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.layout_more)
    View layout_more;
    @Bind(R.id.smart_ref)
    SmartRefreshLayout smart_ref;
    private int page = 1;//用于分页请求

    private String condition, content;//存储条件和内容

    ProgressDialog dialog;
    private GoodsInfoBean bean = new GoodsInfoBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        initView();
        initListener();
        bindSp();
        initSearchView();
        dialog.show();
        request();
    }

    private void bindSp() {
        String[] s1 = {"批准文号", "生产企业名称", "通用名称", "规格", "商品名称", "商品类型"};
        String[] s2 = {"包括"};
        ArrayAdapter adapter = new ArrayAdapter<String>(GoodsInfoActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(GoodsInfoActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s2));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition_1.setAdapter(adapter1);
    }


    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("加载中...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        account_goods_list_recy.setAdapter(new AccountGoodsInfoAdapter(bean, this));
        account_goods_list_recy.setLayoutManager(new LinearLayoutManager(this));

        smart_ref.setEnableRefresh(false);//取消下拉刷新
    }


    private void initListener() {
        btn_back.setOnClickListener(this);
        account_goods_list_recy.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                request();
            }
        });
        btn_search.setOnClickListener(this);

        smart_ref.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                request();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                page = 1;//重置页码
                ((AccountGoodsInfoAdapter) account_goods_list_recy.getAdapter()).clearBean();
                condition = getName(sp_condition.getSelectedItem().toString());
                content = et_search.getText().toString();
                dialog.show();
                request();
                break;
        }
    }


    //通过追溯码和批准文号查询入库数据
    private void request() {
        //入库
        RequestParams params = new RequestParams();
        params.addFormDataPart("Page", page);
        params.addFormDataPart("Name", condition);
        params.addFormDataPart("Cord", content);

        HttpRequest.post(Contance.BASE_URL + "GetYpMes.ashx", params, new RequestCallBack<GoodsInfoBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                smart_ref.finishLoadmore();
                if (e.getMessage().contains("Internal Server Error")) {
                    ToastUtils.showShort(GoodsInfoActivity.this, "没有查到当前药品数据,请重新扫描");
                } else if (e.getMessage().contains("请求失败:  服务器无响应 请重新请求")) {
                    ToastUtils.showShort(GoodsInfoActivity.this, "服务器无响应");
                } else if (e instanceof SocketTimeoutException) {
                    ToastUtils.showShort(GoodsInfoActivity.this, "网络没有连接");
                } else {
                    ToastUtils.showShort(GoodsInfoActivity.this, e.getMessage());
                }
            }

            @Override
            public void getData(GoodsInfoBean bean) {
                dialog.dismiss();
                smart_ref.finishLoadmore();
                page++;
                ((AccountGoodsInfoAdapter) account_goods_list_recy.getAdapter()).addBean(bean);
                ToastUtils.showShort(GoodsInfoActivity.this, "数据获取成功");
            }
        });
    }

    //ProductEnterPrise,FTyName,Guige,ProductName,pzwh,Yplx
    //获取条件对应的字段
    private String getName(String conditionName) {
        switch (conditionName) {
            //"批准文号", "生产企业名称", "通用名称", "规格", "商品名称", "商品类型"
            case "批准文号":
                return "pzwh";
            case "生产企业名称":
                return "ProductEnterPrise";
            case "通用名称":
                return "FTyName";
            case "规格":
                return "Guige";
            case "商品名称":
                return "ProductName";
            case "商品类型":
                return "Yplx";
        }
        return "";
    }

    private void initSearchView() {
        ViewGroup.LayoutParams params = layout_more.getLayoutParams();
        final int height = params.height;
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                KeyBoard.closeSoftKeyBoard(GoodsInfoActivity.this);
                if (b) {
                    if (layout_more.getVisibility() == View.GONE) {
                        HiddenAnimUtils.newInstance(GoodsInfoActivity.this, layout_more, null, height).toggle();
                    }
                } else {
                    if (layout_more.getVisibility() == View.VISIBLE) {
                        HiddenAnimUtils.newInstance(GoodsInfoActivity.this, layout_more, null, height).toggle();
                    }
                }
            }
        });
        account_goods_list_recy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                account_goods_list_recy.setFocusable(true);
                account_goods_list_recy.setFocusableInTouchMode(true);
                account_goods_list_recy.requestFocus();
                return false;
            }
        });
    }
}
