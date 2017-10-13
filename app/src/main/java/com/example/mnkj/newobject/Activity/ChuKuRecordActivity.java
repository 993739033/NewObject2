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

import com.example.mnkj.newobject.Adapter.ChukuRecordAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//出库记录
public class ChuKuRecordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.chuku_record_recy)
    RecyclerView chuku_record_recy;
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.sp_condition)
    Spinner sp_condition;
    @Bind(R.id.sp_condition_1)
    Spinner sp_condition_1;
    @Bind(R.id.sp_condition_2)
    Spinner sp_condition_2;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.btn_search)
    Button btn_search;
    @Bind(R.id.layout_more)
    View layout_more;
    private List<ChuKuRecordBean> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku_record);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
        bindSp();
        initSearchView();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
    }

    private void initData() {
        ChuKuRecordBean bean = new ChuKuRecordBean();
        bean.setPiaohao("20177058664");
        bean.setBuytime("2017/9/13");
        bean.setBuypeople("ALison");
        bean.setPhone("13870694126");
        bean.setNeima("15528962552");
        list.add(bean);
        ChuKuRecordBean bean1 = new ChuKuRecordBean();
        bean1.setPiaohao("20191345664");
        bean1.setBuytime("2017/8/13");
        bean1.setBuypeople("Ski");
        bean1.setPhone("138706999126");
        bean1.setNeima("7833216");
        list.add(bean1);
        ChuKuRecordBean bean2 = new ChuKuRecordBean();
        bean2.setPiaohao("20448345664");
        bean2.setBuytime("2018/8/13");
        bean2.setBuypeople("Jki");
        bean2.setPhone("138741999126");
        bean2.setNeima("7453216");
        list.add(bean2);
    }

    private void initView() {
        chuku_record_recy.setAdapter(new ChukuRecordAdapter(ChuKuRecordActivity.this, list));
        chuku_record_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindSp() {
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "内码", "库存数量", "单价"};
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
        }
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
}
