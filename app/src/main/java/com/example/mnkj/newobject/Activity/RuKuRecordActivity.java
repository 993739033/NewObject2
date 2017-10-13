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

import com.example.mnkj.newobject.Adapter.RukuRecordAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.RuKuRecordBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//入库记录
public class RuKuRecordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.ruku_record_recy)
    RecyclerView ruku_record_recy;
    @Bind(R.id.btn_back)
    ImageView btn_back;
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
    private List<RuKuRecordBean> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku_record);
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
        RuKuRecordBean bean = new RuKuRecordBean();
        bean.setBuydate("16点17分");
        bean.setNormalname("MOHAA稀释液");
        bean.setGysname("金士顿国际有限公司");
        bean.setJhcount("1000");
        bean.setPrice("998");
        list.add(bean);
        RuKuRecordBean bean1 = new RuKuRecordBean();
        bean1.setBuydate("16点27分");
        bean1.setNormalname("MOA稀释液");
        bean1.setGysname("英特威国际有限公司");
        bean1.setJhcount("1200");
        bean1.setPrice("998");
        list.add(bean1);
        RuKuRecordBean bean2 = new RuKuRecordBean();
        bean2.setBuydate("16点27分");
        bean2.setNormalname("OASG稀释液");
        bean2.setGysname("舒肤佳国际有限公司");
        bean2.setJhcount("1300");
        bean2.setPrice("978");
        list.add(bean2);
        list.add(bean1);
        RuKuRecordBean bean3 = new RuKuRecordBean();
        bean3.setBuydate("16点27分");
        bean3.setNormalname("OASG稀释液");
        bean3.setGysname("舒肤佳国际有限公司");
        bean3.setJhcount("1300");
        bean3.setPrice("978");
        list.add(bean3);
        RuKuRecordBean bean4 = new RuKuRecordBean();
        bean4.setBuydate("16点27分");
        bean4.setNormalname("OASG稀释液");
        bean4.setGysname("舒肤佳国际有限公司");
        bean4.setJhcount("1300");
        bean4.setPrice("978");
        list.add(bean4);
        RuKuRecordBean bean5 = new RuKuRecordBean();
        bean5.setBuydate("16点27分");
        bean5.setNormalname("OASG稀释液");
        bean5.setGysname("舒肤佳国际有限公司");
        bean5.setJhcount("1300");
        bean5.setPrice("978");
        list.add(bean5);
    }

    private void initView() {
        ruku_record_recy.setAdapter(new RukuRecordAdapter(RuKuRecordActivity.this, list));
        ruku_record_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    private void bindSp() {
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "内码", "库存数量", "单价"};
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
}
