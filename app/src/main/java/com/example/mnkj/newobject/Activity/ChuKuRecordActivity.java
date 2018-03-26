package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.example.mnkj.newobject.Adapter.ChukuRecordAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//出库记录
public class ChuKuRecordActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.chuku_record_recy)
    RecyclerView chuku_record_recy;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.tv_condition)
    Spinner sp_condition;
    @Bind(R.id.tv_condition_1)
    Spinner sp_condition_1;
    @Bind(R.id.sp_condition_2)
    Spinner sp_condition_2;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.layout_more)
    View layout_more;

    private ChuKuRecordBean bean = new ChuKuRecordBean();

    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout layout_swipe;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku_record);
        ButterKnife.bind(this);
        initView();
        initListener();
        bindSp();
        initSearchView();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }


    private void initView() {
        chuku_record_recy.setAdapter(new ChukuRecordAdapter(ChuKuRecordActivity.this, bean));
        chuku_record_recy.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在请求出库记录数据");
        layout_swipe.setOnRefreshListener(this);
        layout_swipe.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        dialog.show();
        requestData();
    }

    private void bindSp() {
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "企业内码", "供应商名称", "购买人姓名"};
        String[] s2 = {"包括", "等于"};
        String[] s3 = {"销售", "退回厂家"};
        // 初始化下拉列表加载数据适配器
        ArrayAdapter adapter = new ArrayAdapter<String>(ChuKuRecordActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_condition.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(ChuKuRecordActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s2));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_condition_1.setAdapter(adapter1);
        ArrayAdapter adapter2 = new ArrayAdapter<String>(ChuKuRecordActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s3));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition_2.setAdapter(adapter2);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.layout_next:
                Intent intent = new Intent(this, RuKuNextActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_search:
                ((ChukuRecordAdapter) chuku_record_recy.getAdapter()).search(sp_condition.getSelectedItem().toString(),
                        sp_condition_1.getSelectedItem().toString(), sp_condition_2.getSelectedItem().toString()
                        , et_search.getText().toString());
                break;
        }
    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        HttpRequest.post(Contance.BASE_URL + "GetCKHistory.ashx", params, new RequestCallBack<ChuKuRecordBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                ToastUtils.showShort(ChuKuRecordActivity.this, e.getMessage());
            }

            @Override
            public void getData(ChuKuRecordBean bean) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                if (bean.getErrCode() == 0) {
                    ToastUtils.showShort(ChuKuRecordActivity.this, "数据获取成功");
                    ((ChukuRecordAdapter) chuku_record_recy.getAdapter()).setBean(bean);
                } else {
                    ToastUtils.showShort(ChuKuRecordActivity.this, bean.getErrMsg());
                }
            }
        });
    }

    private void initSearchView() {
        ViewGroup.LayoutParams params = layout_more.getLayoutParams();
        final int height = params.height;
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                KeyBoard.closeSoftKeyBoard(ChuKuRecordActivity.this);
                if (b) {
                    if (layout_more.getVisibility() == View.GONE) {
                        HiddenAnimUtils.newInstance(ChuKuRecordActivity.this, layout_more, null, height).toggle();
                    }
                } else {
                    if (layout_more.getVisibility() == View.VISIBLE) {
                        HiddenAnimUtils.newInstance(ChuKuRecordActivity.this, layout_more, null, height).toggle();
                    }
                }
            }
        });
        chuku_record_recy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chuku_record_recy.setFocusable(true);
                chuku_record_recy.setFocusableInTouchMode(true);
                chuku_record_recy.requestFocus();
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
