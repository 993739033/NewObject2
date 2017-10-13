package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Adapter.GYSAddAdapter;
import com.example.mnkj.newobject.Adapter.KeHuAddAdapter;
import com.example.mnkj.newobject.Bean.GYSBean;
import com.example.mnkj.newobject.Bean.KHBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//供应商以及客户新增界面
public class AccountListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.account_list_recy)
    RecyclerView account_list_recy;
    @Bind(R.id.layout_add)
    LinearLayout layout_add;
    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout layout_swipe;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        ButterKnife.bind(this);
        initView();
        requestData();
        initListener();
    }

    //添加初始化数据
    private void requestData() {
        if (tv_title.getText().equals("供应商信息")) {
            dialog.setMessage("正在请求供应商数据");
            RequestParams params = new RequestParams();
            params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
            HttpRequest.post(Contance.BASE_URL + "Getgys.ashx", params, new RequestCallBack<GYSBean>() {
                @Override
                public void onFailure(Exception e) {
                    dialog.dismiss();
                    layout_swipe.setRefreshing(false);
                    ToastUtils.showShort(AccountListActivity.this, e.getMessage());
                }

                @Override
                public void getData(GYSBean bean) {
                    dialog.dismiss();
                    layout_swipe.setRefreshing(false);
                    if (bean.getErrCode() == 0) {
                        ToastUtils.showShort(AccountListActivity.this, "数据获取成功");
                        ((GYSAddAdapter) account_list_recy.getAdapter()).setBean(bean);

                    } else {
                        ToastUtils.showShort(AccountListActivity.this, bean.getErrMsg());
                    }
                }
            });

        } else if (tv_title.getText().equals("客户资料")) {
            dialog.setMessage("正在请求客户数据");
            RequestParams params = new RequestParams();
            params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
            HttpRequest.post(Contance.BASE_URL + "GetFBuyPerson.ashx", params, new RequestCallBack<KHBean>() {
                @Override
                public void onFailure(Exception e) {
                    dialog.dismiss();
                    layout_swipe.setRefreshing(false);
                    ToastUtils.showShort(AccountListActivity.this, e.getMessage());
                }

                @Override
                public void getData(KHBean bean) {
                    dialog.dismiss();
                    layout_swipe.setRefreshing(false);
                    if (bean.getErrCode() == 0) {
                        ToastUtils.showShort(AccountListActivity.this, "数据获取成功");
                        account_list_recy.setAdapter(new KeHuAddAdapter(AccountListActivity.this, new KHBean()));
                        ((KeHuAddAdapter) account_list_recy.getAdapter()).setBean(bean);

                    } else {
                        ToastUtils.showShort(AccountListActivity.this, bean.getErrMsg());
                    }
                }
            });
        }
    }

    private void initView() {
        Intent intent = getIntent();
        tv_title.setText(intent.getStringExtra("title"));
        account_list_recy.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(this);

        layout_swipe.setOnRefreshListener(this);
        layout_swipe.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        account_list_recy.setAdapter(new GYSAddAdapter(this, new GYSBean()));
        account_list_recy.setLayoutManager(new LinearLayoutManager(this));
        dialog.show();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        layout_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.layout_add:
                if (tv_title.getText().equals("供应商信息")) {
                    Intent intent = new Intent(this, GYSInfoAddActivity.class);
                    startActivityForResult(intent, Contance.RequestCode);
                } else if (tv_title.getText().equals("客户资料")) {
                    Intent intent = new Intent(this, KHInfoAddActivity.class);
                    startActivityForResult(intent, Contance.RequestCode);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contance.RequestCode && resultCode == Contance.ResultCode) {
            requestData();
        }
    }
}
