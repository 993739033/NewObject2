package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mnkj.newobject.Adapter.ChukuAdapter;
import com.example.mnkj.newobject.Adapter.KuCunAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;
import com.example.mnkj.newobject.View.SetRemindDialog;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//库存
public class KuCunActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.kucun_recy)
    RecyclerView kucun_recy;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_select_all)
    Button btn_select_all;
    @Bind(R.id.tv_condition)
    Spinner sp_condition;
    @Bind(R.id.tv_condition_1)
    Spinner sp_condition_1;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.tv_tx)
    TextView tv_tx;
    @Bind(R.id.layout_more)
    View layout_more;
    @Bind(R.id.btn_bfcl)
    Button btn_bfcl;

    @Bind(R.id.layout_swipe)
    SwipeRefreshLayout layout_swipe;

    ProgressDialog dialog;

    KuCunBean bean = new KuCunBean();

    private SetRemindDialog remindDialog;

    private DeleteDialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_cun);
        ButterKnife.bind(this);
        initListener();
        initView();
        bindSp();
        initSearchView();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_select_all.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        tv_tx.setOnClickListener(this);
        btn_bfcl.setOnClickListener(this);

    }

    private void initView() {
        kucun_recy.setAdapter(new KuCunAdapter(KuCunActivity.this, bean));
        kucun_recy.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在请求存储数据");
        deleteDialog = new DeleteDialog(KuCunActivity.this, R.style.MyDialog);
        deleteDialog.setTitle("是否确认报废所选产品");
        deleteDialog.setCallBack(new DeleteDialog.CallBack() {
            @Override
            public void enter() {
                deleteDialog.dismiss();
                Intent intent = new Intent(KuCunActivity.this, KuCunBFActivity.class);
                if (!((KuCunAdapter) kucun_recy.getAdapter()).isNoEmpty()) {
                    ToastUtils.showShort(KuCunActivity.this, "报废产品中包含库存量为空无法进行报废处理");
                    return;
                }
                intent.putExtra(Contance.DATA, ((KuCunAdapter) kucun_recy.getAdapter()).getSelectBean());
                startActivity(intent);
                finish();
            }

            @Override
            public void cancel() {
                deleteDialog.dismiss();
            }
        });

        layout_swipe.setOnRefreshListener(this);
        layout_swipe.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        dialog.show();

        remindDialog = new SetRemindDialog(this, R.style.MyDialog);
        remindDialog.setCallBack(new SetRemindDialog.CallBack() {

            @Override
            public void enter(String name, String count) {
                SPUtils.getInstance().saveData(name, count);
                ((KuCunAdapter) kucun_recy.getAdapter()).notifyItemAll();
                remindDialog.cancel();
            }

            @Override
            public void cancel() {
                remindDialog.cancel();
            }
        });


        requestData();
    }


    private void bindSp() {
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "企业内码", "库存数量", "单价", "距有效期(天)"};
        String[] s2 = {"包括", "等于"};
        ArrayAdapter adapter = new ArrayAdapter<String>(KuCunActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(KuCunActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s2));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_condition_1.setAdapter(adapter1);
    }

    //请求数据
    private void requestData() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("FSm1", "");//追溯码查询列表时传空
        params.addFormDataPart("FSuserId", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        HttpRequest.post(Contance.BASE_URL + "GetAPPKC.ashx", params, new RequestCallBack<KuCunBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                ToastUtils.showShort(KuCunActivity.this, e.getMessage());
            }

            @Override
            public void getData(KuCunBean bean) {
                dialog.dismiss();
                layout_swipe.setRefreshing(false);
                if (bean.getErrCode() == 0) {
                    ToastUtils.showShort(KuCunActivity.this, "数据获取成功");
                    ((KuCunAdapter) kucun_recy.getAdapter()).setBean(bean);
                } else {
                    ToastUtils.showShort(KuCunActivity.this, bean.getErrMsg());
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
            case R.id.layout_next:
                Intent intent = new Intent(this, RuKuNextActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_select_all:
                ((KuCunAdapter) kucun_recy.getAdapter()).selectAll();
                break;
            case R.id.btn_search:
                ((KuCunAdapter) kucun_recy.getAdapter()).search(sp_condition.getSelectedItem().toString()
                        , sp_condition_1.getSelectedItem().toString(), et_search.getText().toString());
                break;
            case R.id.tv_tx:
                List<String> names = ((KuCunAdapter) kucun_recy.getAdapter()).getSelectedName();
                if (names.size() != 1) {
                    ToastUtils.showShort(KuCunActivity.this, "请选择一条进行设置");
                    return;
                }
                remindDialog.setShowName(names.get(0));
                String count = SPUtils.getInstance().getData(names.get(0), "", String.class);
                if (!TextUtils.isEmpty(count)) {
                    remindDialog.setShowCount(count);
                }
                remindDialog.show();
                break;
            case R.id.btn_bfcl:
                if (((KuCunAdapter) kucun_recy.getAdapter()).hasSelect()) {
                    deleteDialog.show();
                } else {
                    ToastUtils.showShort(KuCunActivity.this, "所选报废产品不能为空");
                }
                break;

        }
    }

    private void initSearchView() {
        ViewGroup.LayoutParams params = layout_more.getLayoutParams();
        final int height = params.height;
        et_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                KeyBoard.closeSoftKeyBoard(KuCunActivity.this);
                if (b) {
                    if (layout_more.getVisibility() == View.GONE) {
                        HiddenAnimUtils.newInstance(KuCunActivity.this, layout_more, null, height).toggle();
                    }
                } else {
                    if (layout_more.getVisibility() == View.VISIBLE) {
                        HiddenAnimUtils.newInstance(KuCunActivity.this, layout_more, null, height).toggle();
                    }
                }
            }
        });
        kucun_recy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                kucun_recy.setFocusable(true);
                kucun_recy.setFocusableInTouchMode(true);
                kucun_recy.requestFocus();
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
