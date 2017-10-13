package com.example.mnkj.newobject.Activity;

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
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.GoodsInfoBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.KeyBoard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.account_goods_list_recy)
    RecyclerView account_goods_list_recy;
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
    List<GoodsInfoBean> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
        bindSp();
        initSearchView();
    }

    //添加初始化数据
    private void initData() {
//        西班牙海博莱生物大药厂	猪伪狂犬病灭活疫苗（BarthaK61株）
// 	20	（2010）外兽药证字53号 83JP-1	进口生物制品
        //	硕腾公司美国林肯生产厂	犬细小病毒病活疫苗
        // 	1	（2011）外兽药证字10号 127846B	进口生物制品
        //	印度尼西亚美迪安有限公司	鸡新城疫灭活疫苗（LaSota株）
        // 	1000	（2011）外兽药证字13号 60B57	进口生物制品
        for (int i = 0; i < 4; i++) {
            GoodsInfoBean bean = new GoodsInfoBean();
            bean.setCompany("西班牙海博莱生物大药厂");
            bean.setNormalname("猪伪狂犬病灭活疫苗");
            bean.setGoodsname("BarthaK61株");
            bean.setSpec("20");
            bean.setPznumber("（2010）外兽药证字53号 83JP-1");
            bean.setGoodstype("进口生物制品");
            list.add(bean);
            GoodsInfoBean bean1 = new GoodsInfoBean();
            bean1.setCompany("硕腾公司美国林肯生产厂");
            bean1.setNormalname("犬细小病毒病活疫苗");
            bean1.setGoodsname("BarthaA61株");
            bean1.setSpec("1");
            bean1.setPznumber("（2011）外兽药证字10号 127846B");
            bean1.setGoodstype("进口生物制品");
            list.add(bean1);
            GoodsInfoBean bean2 = new GoodsInfoBean();
            bean2.setCompany("印度尼西亚美迪安有限公司");
            bean2.setNormalname("鸡新城疫灭活疫苗（LaSota株）");
            bean2.setGoodsname("Bas1株");
            bean2.setSpec("1000");
            bean2.setPznumber("（2011）外兽药证字13号 60B57");
            bean2.setGoodstype("进口生物制品");
            list.add(bean2);
        }
    }

    private void bindSp() {
        String[] s1 = {"生产企业名称", "通用名称", "规格", "商品名称", "批准文号", "内码", "库存数量", "单价"};
        String[] s2 = {"包括", "等于"};
        // 初始化下拉列表加载数据适配器
        ArrayAdapter adapter = new ArrayAdapter<String>(GoodsInfoActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s1));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_condition.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(GoodsInfoActivity.this,
                android.R.layout.simple_spinner_item, Arrays.asList(s2));
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_condition_1.setAdapter(adapter1);
    }


    private void initView() {
        account_goods_list_recy.setAdapter(new AccountGoodsInfoAdapter(list, this));
        account_goods_list_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
    private void initSearchView(){
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
