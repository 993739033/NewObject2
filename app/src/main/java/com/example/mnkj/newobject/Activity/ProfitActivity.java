package com.example.mnkj.newobject.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mnkj.newobject.Adapter.ProfitAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ProfitBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//利润
public class ProfitActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.profit_recy)
    RecyclerView profit_recy;
    @Bind(R.id.tv_condition_1)
    TextView tv_condition_1;
    @Bind(R.id.tv_condition)
    TextView tv_condition;
    @Bind(R.id.btn_search)
    TextView btn_search;
    @Bind(R.id.et_total)
    TextView et_total;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;

    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout layout_swipe;
    ProgressDialog dialog;
    private ProfitBean bean = new ProfitBean();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        ButterKnife.bind(this);
        initListener();
        initView();
        requestData();
        int[] times = DateUtil.getNowTimes();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String time = i + "-" + (i1 + 1) + "-" + i2;
                if (!TextUtils.isEmpty(tv_condition_1.getText().toString())) {
                    if (!DateUtil.compareDate(tv_condition_1.getText().toString(), time)) {
                        ToastUtils.showShort(ProfitActivity.this, "初始时间不能大于终止时间");
                        return;
                    }
                }
                tv_condition.setText(time);
            }
        }, times[0], times[1]-1, times[2]);

        datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String time = i + "-" + (i1 + 1) + "-" + i2;
                if (!TextUtils.isEmpty(tv_condition.getText().toString())) {
                    if (!DateUtil.compareDate(time, tv_condition.getText().toString())) {
                        ToastUtils.showShort(ProfitActivity.this, "终止时间不能小于初始时间");
                        return;
                    }
                }
                tv_condition_1.setText(time);
            }
        }, times[0], times[1]-1, times[2]);
        tv_condition.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        tv_condition_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                datePickerDialog1.show();
            }
        });

    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }


    private void initView() {
        profit_recy.setAdapter(new ProfitAdapter(ProfitActivity.this, bean));
        profit_recy.setLayoutManager(new LinearLayoutManager(this));

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

    private void requestData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        HttpRequest.post(Contance.BASE_URL + "GetSYProfit.ashx", params, new RequestCallBack<ProfitBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                ToastUtils.showShort(ProfitActivity.this, e.getMessage());
            }

            @Override
            public void getData(ProfitBean bean) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                if (bean.getErrCode() == 0) {
                    ToastUtils.showShort(ProfitActivity.this, "数据获取成功");
                    ((ProfitAdapter) profit_recy.getAdapter()).setBean(bean);
                    showTotal();
                } else {
                    ToastUtils.showShort(ProfitActivity.this, bean.getErrMsg());
                }
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
                if (TextUtils.isEmpty(tv_condition.getText().toString()) || TextUtils.isEmpty(tv_condition_1.getText().toString())) {
                    ToastUtils.showShort(ProfitActivity.this, "日期选择都不能为空");
                    return;
                }
                ((ProfitAdapter) profit_recy.getAdapter()).search(tv_condition.getText().toString(),
                        tv_condition_1.getText().toString());
                showTotal();

                break;
        }
    }

    private void showTotal() {
        try {
            et_total.setText(((ProfitAdapter) profit_recy.getAdapter()).getTotal() + "(元)");
        } catch (ParseException e) {
            e.printStackTrace();
            ToastUtils.showShort(ProfitActivity.this, "利润计算出错");
        }
    }


    @Override
    public void onRefresh() {
        requestData();
    }
}
