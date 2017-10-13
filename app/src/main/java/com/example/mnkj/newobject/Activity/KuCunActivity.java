package com.example.mnkj.newobject.Activity;

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

import com.example.mnkj.newobject.Adapter.KuCunAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//库存
public class KuCunActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.kucun_recy)
    RecyclerView kucun_recy;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_select_all)
    Button btn_select_all;
    @Bind(R.id.sp_condition)
    Spinner sp_condition;
    @Bind(R.id.sp_condition_1)
    Spinner sp_condition_1;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.layout_more)
    View layout_more;
    List<KuCunBean> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_cun);
        ButterKnife.bind(this);
        initListener();
        initData();
        initView();
        bindSp();
        initSearchView();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_select_all.setOnClickListener(this);
    }

    private void initView() {
        kucun_recy.setAdapter(new KuCunAdapter(KuCunActivity.this, list));
        kucun_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindSp() {
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "内码", "库存数量", "单价"};
        String[] s2 = {"包括", "等于"};
        // 初始化下拉列表加载数据适配器
        ArrayAdapter adapter = new ArrayAdapter<String>(KuCunActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_condition.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(KuCunActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s2));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_condition_1.setAdapter(adapter1);
    }

    private void initData() {
        KuCunBean bean = new KuCunBean();
        bean.setNormalname("NOBIVAC稀释液");
        bean.setCount("1000");
        bean.setSelectd(false);
        list.add(bean);
        KuCunBean bean1 = new KuCunBean();
        bean1.setNormalname("鸡传染性法氏囊病病毒火鸡疱疹病毒载体活疫苗（vHVT-013-69株）");
        bean1.setCount("1200");
        bean1.setSelectd(false);
        list.add(bean1);
        KuCunBean bean2 = new KuCunBean();
        bean2.setNormalname("鸡毒支原体活疫苗（TS-11株）");
        bean2.setCount("1300");
        bean2.setSelectd(false);
        list.add(bean2);
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

}
