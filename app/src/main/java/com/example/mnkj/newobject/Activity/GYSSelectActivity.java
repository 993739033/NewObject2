package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.example.mnkj.newobject.Adapter.GYSSelectAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.GYSBean;
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

//供应商列表数据请求
public class GYSSelectActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout layout_swipe;
    @Bind(R.id.gys_select_recy)
    RecyclerView gys_select_recy;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.layout_more)
    View layout_more;
    @Bind(R.id.tv_condition)
    Spinner sp_condition;
    GYSBean bean = new GYSBean();
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gysselect);
        ButterKnife.bind(this);
        initView();
        initListener();
        requestData();
        initSearchView();
        bindSp();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在请求供应商数据");
        layout_swipe.setOnRefreshListener(this);
        layout_swipe.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        gys_select_recy.setLayoutManager(new LinearLayoutManager(this));
        gys_select_recy.setAdapter(new GYSSelectAdapter(this, bean));
        dialog.show();
    }


    private void requestData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        HttpRequest.post(Contance.BASE_URL + "Getgys.ashx", params, new RequestCallBack<GYSBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                ToastUtils.showShort(GYSSelectActivity.this, e.getMessage());
            }

            @Override
            public void getData(GYSBean bean) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                if (bean.getErrCode() == 0) {
                    ToastUtils.showShort(GYSSelectActivity.this, "数据获取成功");
                    ((GYSSelectAdapter) gys_select_recy.getAdapter()).setBean(bean);
                } else {
                    ToastUtils.showShort(GYSSelectActivity.this, bean.getErrMsg());
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_search:
                ((GYSSelectAdapter) gys_select_recy.getAdapter()).search(sp_condition.getSelectedItem().toString()
                        , et_search.getText().toString());
                break;
        }
    }

    private void bindSp() {
        String[] s1 = {"供应商名称", "联系人", "联系电话"};
        ArrayAdapter adapter = new ArrayAdapter<String>(GYSSelectActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition.setAdapter(adapter);
    }

    //初始化搜索栏
    private void initSearchView() {
        ViewGroup.LayoutParams params = layout_more.getLayoutParams();
        final int height = params.height;
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    if (layout_more.getVisibility() == View.GONE) {
                        HiddenAnimUtils.newInstance(GYSSelectActivity.this, layout_more, null, height).toggle();
                    }
                } else {
                    KeyBoard.closeSoftKeyBoard(GYSSelectActivity.this);
                    if (layout_more.getVisibility() == View.VISIBLE) {
                        HiddenAnimUtils.newInstance(GYSSelectActivity.this, layout_more, null, height).toggle();
                    }
                }
            }
        });
        gys_select_recy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gys_select_recy.setFocusable(true);
                gys_select_recy.setFocusableInTouchMode(true);
                gys_select_recy.requestFocus();
                return false;
            }
        });
    }
}
