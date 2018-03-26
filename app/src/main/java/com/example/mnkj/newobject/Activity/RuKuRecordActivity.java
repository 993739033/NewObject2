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

import com.example.mnkj.newobject.Adapter.RuKuRecordAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.RuKuRecordBean;
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

//入库记录
public class RuKuRecordActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.jhth_record_recy)
    RecyclerView ruku_record_recy;
    @Bind(R.id.btn_back)
    ImageView btn_back;
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

    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout layout_swipe;
    ProgressDialog dialog;
    private RuKuRecordBean bean = new RuKuRecordBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku_record);
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
        ruku_record_recy.setAdapter(new RuKuRecordAdapter(RuKuRecordActivity.this, bean));
        ruku_record_recy.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在请求入库记录数据");
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
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "企业内码"};
        String[] s2 = {"包括", "等于"};
        ArrayAdapter adapter = new ArrayAdapter<String>(RuKuRecordActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(RuKuRecordActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s2));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition_1.setAdapter(adapter1);
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
                ((RuKuRecordAdapter) ruku_record_recy.getAdapter()).search(sp_condition.getSelectedItem().toString(),
                        sp_condition_1.getSelectedItem().toString(), et_search.getText().toString());
                break;

        }
    }

    private void initSearchView() {
        ViewGroup.LayoutParams params = layout_more.getLayoutParams();
        final int height = params.height;
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (layout_more.getVisibility() == View.GONE) {
                        HiddenAnimUtils.newInstance(RuKuRecordActivity.this, layout_more, null, height).toggle();
                    }
                } else {
                    KeyBoard.closeSoftKeyBoard(RuKuRecordActivity.this);
                    if (layout_more.getVisibility() == View.VISIBLE) {
                        HiddenAnimUtils.newInstance(RuKuRecordActivity.this, layout_more, null, height).toggle();
                    }
                }
            }
        });
        ruku_record_recy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ruku_record_recy.setFocusable(true);
                ruku_record_recy.setFocusableInTouchMode(true);
                ruku_record_recy.requestFocus();
                return false;
            }
        });
    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        HttpRequest.post(Contance.BASE_URL + "GetRKHistory.ashx", params, new RequestCallBack<RuKuRecordBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                ToastUtils.showShort(RuKuRecordActivity.this, e.getMessage());
            }

            @Override
            public void getData(RuKuRecordBean bean) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                if (bean.getErrCode() == 0) {
                    ToastUtils.showShort(RuKuRecordActivity.this, "数据获取成功");
                    ((RuKuRecordAdapter) ruku_record_recy.getAdapter()).setBean(bean);
                } else {
                    ToastUtils.showShort(RuKuRecordActivity.this, bean.getErrMsg());
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
