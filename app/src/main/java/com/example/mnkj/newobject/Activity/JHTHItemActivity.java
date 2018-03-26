package com.example.mnkj.newobject.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.JHTHBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//进货退货列表修改界面
public class JHTHItemActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;
    @Bind(R.id.sp_unit)
    Spinner sp_unit;
    @Bind(R.id.et_product_type)
    EditText et_product_type;
    @Bind(R.id.et_dljg)
    EditText et_dljg;
    @Bind(R.id.et_qiye_name)
    EditText et_qiye_name;
    @Bind(R.id.et_normal_name)
    EditText et_normal_name;
    @Bind(R.id.et_product_name)
    EditText et_product_name;
    @Bind(R.id.et_spec)
    EditText et_spec;
    @Bind(R.id.et_pzwh)
    EditText et_pzwh;
    @Bind(R.id.et_scph)
    EditText et_scph;
    @Bind(R.id.et_scrq)
    EditText et_scrq;
    @Bind(R.id.et_yxrq)
    EditText et_yxrq;
    @Bind(R.id.et_thsl)
    EditText et_thsl;
    @Bind(R.id.et_xsdj)
    EditText et_cgjg;
    @Bind(R.id.et_hdsj)
    EditText et_hdsj;
    @Bind(R.id.et_jezj)
    EditText et_jezj;
    @Bind(R.id.et_cfqy)
    EditText et_cfqy;
    @Bind(R.id.et_cchjyq)
    EditText et_cchj;
    @Bind(R.id.et_nm)
    EditText et_nm;
    @Bind(R.id.et_zsm)
    EditText et_zsm;
    private JHTHBean.DataList bean;

    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jh_th_item);
        ButterKnife.bind(this);
        initListener();
        bean = (JHTHBean.DataList) getIntent().getSerializableExtra(Contance.DATA);
        initView(bean);
        bindSp();
    }

    private void initView(JHTHBean.DataList bean) {
        et_product_type.setText(bean.getYplx());//商品类型
        et_dljg.setText(bean.getFDljg());//代理机构
        et_qiye_name.setText(bean.getFProductEnterprise());//企业名称
        et_normal_name.setText(bean.getFTyName());//通用名称
        et_product_name.setText(bean.getFProductName());//商品名称
        et_spec.setText(bean.getFGuige());//规格
        et_pzwh.setText(bean.getFPzwh());//批准文号
        et_scph.setText(bean.getFScph());//生产批号
        et_scrq.setText(bean.getFScDate());//生产日期
        et_yxrq.setText(bean.getFYxqDate());//有效日期
        et_thsl.setText(bean.getFBuyNum());//购入数量
        et_cgjg.setText(bean.getFDjPrice());//购买价格
        et_hdsj.setText(bean.getFSjPrice());//核定售价
        et_jezj.setText(bean.getFJesum());//金额总计
        et_cfqy.setText(bean.getYpArea());//存放区域
        et_cchj.setText(bean.getFStoreHj());//存放环境
        et_nm.setText(bean.getFNmcode());//内码
        et_zsm.setText(bean.getFSm1());//追溯码

        count = Integer.parseInt(bean.getFKcsl());
        et_thsl.setHint("当前库存数:(" + count + ")");
    }

    private void bindSp() {
        List<String> unitList = new ArrayList<>();
        unitList.add(bean.getFDw());
        ArrayAdapter adapter = new ArrayAdapter<String>(JHTHItemActivity.this,
                android.R.layout.simple_spinner_item, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        //购入数量X采购价格=金额总计
        et_thsl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (checkPrice(et_cgjg.getText().toString())) {
                    priceCompute();
                } else {
                    ToastUtils.showShort(JHTHItemActivity.this, "采购价格输入有误");
                }
            }
        });
    }

    //检查价格的格式是否输入正确
    private boolean checkPrice(String price) {
        char[] c = price.toCharArray();
        int count = 0;
        for (char cm : c) {
            if (String.valueOf(cm).equals(".")) {
                count++;
            }
        }
        //多个小数点错误格式
        if (count >= 2) {
            return false;
        }
        return true;
    }

    //计算价格
    private void priceCompute() {
        if (!TextUtils.isEmpty(et_thsl.getText().toString())
                && !TextUtils.isEmpty(et_cgjg.getText().toString()) && checkPrice(et_cgjg.getText().toString())) {
            //总记
            double zj = (double) (Integer.parseInt(et_thsl.getText().toString()) * Float.parseFloat(et_cgjg.getText().toString()));
            BigDecimal d1 = new BigDecimal(Double.toString(zj));
            BigDecimal d2 = new BigDecimal(Integer.toString(1));
            et_jezj.setText(d1.divide(d2, 2, BigDecimal.ROUND_HALF_UP).toString());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_enter:
                if (checkData()) {
                    Intent intent = getIntent();
                    intent.putExtra(Contance.DATA, getBean());
                    setResult(Contance.ResultCode, intent);
                    finish();
                }
                break;
        }
    }

    private Boolean checkData() {
        if (TextUtils.isEmpty(et_thsl.getText().toString())) {
            ToastUtils.showShort(this, "退货数量不能为空");
            return false;
        } else if (Integer.parseInt(et_thsl.getText().toString()) == 0) {
            ToastUtils.showShort(this, "退货数量不能为零");
            return false;
        } else if (Integer.parseInt(et_thsl.getText().toString()) > count) {
            ToastUtils.showShort(this, "退货数量不能大于当前库存,当前库存("+count+")");
            return false;
        }
        return true;
    }

    private JHTHBean.DataList getBean() {
        bean.setFBuyNum(et_thsl.getText().toString());//退货数量
        bean.setFJesum(et_jezj.getText().toString());//金额总计
        bean.setBemodfiyed(true);
        return bean;
    }


}
