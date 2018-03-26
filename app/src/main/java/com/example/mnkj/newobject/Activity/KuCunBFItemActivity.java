package com.example.mnkj.newobject.Activity;

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
import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KuCunBFItemActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;

    @Bind(R.id.et_normal_name)
    EditText et_normal_name;
    @Bind(R.id.et_bfsl)
    EditText et_bfsl;

    @Bind(R.id.sp_unit)
    Spinner sp_unit;
    @Bind(R.id.et_yxrq)
    EditText et_yxrq;
    @Bind(R.id.et_price)
    EditText et_price;
    @Bind(R.id.et_ssje)
    EditText et_ssje;
    @Bind(R.id.et_scrq)
    EditText et_scrq;
    @Bind(R.id.et_product_type)
    EditText et_product_type;
    @Bind(R.id.et_spec)
    EditText et_spec;
    @Bind(R.id.et_gys_name)
    EditText et_gys_name;
    @Bind(R.id.et_qiye_name)
    EditText et_qiye_name;
    @Bind(R.id.et_pzwh)
    EditText et_pzwh;
    @Bind(R.id.et_scph)
    EditText et_scph;
    @Bind(R.id.et_product_name)
    EditText et_product_name;
    @Bind(R.id.et_nm)
    EditText et_nm;
    @Bind(R.id.et_dljg)
    EditText et_dljg;

    KuCunBean.DataList dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_cun_item);
        ButterKnife.bind(this);
        dataList = (KuCunBean.DataList) getIntent().getSerializableExtra(Contance.DATA);
        initView();
        initListener();
    }

    private void initView() {
        et_normal_name.setText(dataList.getFTyName());
        List<String> unitList = new ArrayList<>();
        unitList.add(dataList.getFDw());
        ArrayAdapter adapter = new ArrayAdapter<String>(KuCunBFItemActivity.this,
                android.R.layout.simple_spinner_item, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
        et_yxrq.setText(DateUtil.changeFormat(dataList.getFYxqDate()));
        et_price.setText(dataList.getFDjPrice());
        et_scrq.setText(DateUtil.changeFormat(dataList.getFScDate()));
        et_product_type.setText(dataList.getYplx());
        et_spec.setText(dataList.getFGuige());
        et_gys_name.setText(dataList.getFGysmc());
        et_qiye_name.setText(dataList.getFProductEnterprise());
        et_pzwh.setText(dataList.getFPzwh());
        et_scph.setText(dataList.getFScph());
        et_product_name.setText(dataList.getFProductName());
        et_nm.setText(dataList.getFNmcode());
        et_dljg.setText(dataList.getFDljg());
        et_bfsl.setHint("当前库存数量为:" + dataList.getFKcsl());

        if (TextUtils.isEmpty(et_bfsl.getText().toString()) || TextUtils.isEmpty(et_price.getText().toString()))
            return;
        int bfsl = Integer.parseInt(et_bfsl.getText().toString());
        float dj = Float.parseFloat(et_price.getText().toString());
        float ssje = bfsl * dj;
        et_ssje.setText(ssje + "");

    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        et_bfsl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(et_bfsl.getText().toString()) || TextUtils.isEmpty(et_price.getText().toString()))
                    return;
                int bfsl = Integer.parseInt(et_bfsl.getText().toString());
                float dj = Float.parseFloat(et_price.getText().toString());
                float ssje = bfsl * dj;
                et_ssje.setText(ssje + "");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_enter:
                try {
                    if (checkData()) {
                        Intent intent = getIntent();
                        intent.putExtra(Contance.DATA, getResultData());
                        setResult(Contance.ResultCode, intent);
                        finish();
                    }
                } catch (Exception e) {
                    ToastUtils.showShort(KuCunBFItemActivity.this, "报废数据有误");
                }
                break;
        }
    }


    //检查数据
    public Boolean checkData() throws ClassCastException {
        if (TextUtils.isEmpty(et_bfsl.getText().toString())) {
            ToastUtils.showShort(KuCunBFItemActivity.this, "报废数量不能为空");
            return false;
        } else {
            int bfsl = Integer.parseInt(et_bfsl.getText().toString());
            int kcsl = Integer.parseInt(dataList.getFKcsl());
            if (bfsl == 0) {
                ToastUtils.showShort(KuCunBFItemActivity.this, "报废数量不能为零");
                return false;
            } else if (bfsl > kcsl) {
                ToastUtils.showShort(KuCunBFItemActivity.this, "报废数量不能大于当前库存,当前库存为:(" + dataList.getFKcsl() + ")");
                return false;
            }
        }
        return true;
    }

    //获取编辑后的bean
    public KuCunBean.DataList getResultData() {
        dataList.setFJesum(et_ssje.getText().toString());
        dataList.setFBuyNum(et_bfsl.getText().toString());
        dataList.setBeModfiy(true);
        return dataList;
    }
}
