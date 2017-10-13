package com.example.mnkj.newobject.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ScanInputBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//入库列表修改界面
public class RuKuItemActivity extends BaseActivity implements View.OnClickListener {
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
    @Bind(R.id.et_grsl)
    EditText et_grsl;
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
    DatePickerDialog scrqDialog;//生产日期 选择
    DatePickerDialog yxrqDialog;//有效日期 选择
    private int scrqyear, scrqmonth, scrqday;//生产日期年月日
    private int yxrqyear, yxrqmonth, yxrqday;//有效日期年月日
    private ScanInputBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku_item);
        ButterKnife.bind(this);
        bindSp();
        initListener();
        bean = (ScanInputBean) getIntent().getSerializableExtra(Contance.DATA);
        initView(bean);
    }

    private void initView(ScanInputBean bean) {
        et_product_type.setText(bean.getYplxname());//商品类型
        et_dljg.setText(bean.getFDljg());//代理机构
        et_qiye_name.setText(bean.getName());//企业名称
        et_normal_name.setText(bean.getTongYongMing());//通用名称
        et_product_name.setText(bean.getCpname());//商品名称
        et_product_name.setText(bean.getCpname());//商品名称
        et_spec.setText(bean.getSpecification());//规格
        et_pzwh.setText(bean.getWenHao());//批准文号
        et_scph.setText(bean.getProductNumber());//生产批号
        et_scrq.setText(bean.getProductionDate());//生产日期
        et_yxrq.setText(bean.getExpiryDate());//有效日期
        et_grsl.setText(bean.getCount());//购入数量
        et_cgjg.setText(bean.getPurchasePrice() + "");//购买价格
        et_hdsj.setText(bean.getPresellPrice());//核定售价
        et_jezj.setText(bean.getFJesum());//金额总计
        et_cfqy.setText(bean.getYpArea());//存放区域
        et_cchj.setText(bean.getFStoreHj());//存放环境
        et_nm.setText(bean.getNm());//内码
        et_zsm.setText(bean.getZhuiSuMa());//追溯码

        int count = sp_unit.getCount();
        for (int i = 0; i < count; i++) {
            if (sp_unit.getAdapter().getItem(i).equals(bean.getUnit())) {
                sp_unit.setSelection(i);
            }
        }
        String[] scrq = new String[0];
        //初始化生产日期
        if (bean.getProductionDate().contains("-")) {
            scrq = bean.getProductionDate().split("-");
        } else if (bean.getProductionDate().contains("/")) {
            scrq = bean.getProductionDate().split("/");
        }
        if (scrq.length >= 3) {
            scrqyear = Integer.parseInt(scrq[0]);
            scrqmonth = Integer.parseInt(scrq[1]);
            scrqday = Integer.parseInt(scrq[2]);
        }
        scrqDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                scrqyear = i;
                scrqmonth = i1 + 1;
                scrqday = i2;
                String scrq = scrqyear + "-" + scrqmonth + "-" + scrqday;
                if (DateUtil.compareNewDate(scrq)) {
                    ToastUtils.showShort(RuKuItemActivity.this, "生产日期不能大于当前时间!");
                    return;
                }
                if (DateUtil.compareDate(scrq, et_yxrq.getText().toString())) {
                    ToastUtils.showShort(RuKuItemActivity.this, "生产日期不能大于有效日期!");
                    return;
                }
                et_scrq.setText(scrq);
            }
        }, scrqyear, scrqmonth, scrqday);

        //初始化有效日期
        String[] yxrq = new String[0];
        //初始化有效日期
        if (bean.getExpiryDate().contains("-")) {
            yxrq = bean.getExpiryDate().split("-");
        } else if (bean.getExpiryDate().contains("/")) {
            yxrq = bean.getExpiryDate().split("-");
        }
        if (yxrq.length >= 3) {
            yxrqyear = Integer.parseInt(yxrq[0]);
            yxrqmonth = Integer.parseInt(yxrq[1]);
            yxrqday = Integer.parseInt(yxrq[2]);
        }
        yxrqDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                yxrqyear = i;
                yxrqmonth = i1 + 1;
                yxrqday = i2;
                String yxrq = yxrqyear + "-" + yxrqmonth + "-" + yxrqday;
                if (!DateUtil.compareDate(yxrq, et_scrq.getText().toString())) {
                    ToastUtils.showShort(RuKuItemActivity.this, "有效日期不能小于生产日期!");
                    return;
                }
                et_yxrq.setText(yxrq);
            }
        }, yxrqyear, yxrqmonth, yxrqday);
    }

    private void bindSp() {
        List<String> unitList = new ArrayList<>();
        unitList.add("盒");
        unitList.add("袋");
        unitList.add("瓶");
        unitList.add("支");
        unitList.add("桶");
        unitList.add("包");
        unitList.add("箱");
        ArrayAdapter adapter = new ArrayAdapter<String>(RuKuItemActivity.this,
                android.R.layout.simple_spinner_item, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        et_scrq.setOnClickListener(this);
        et_yxrq.setOnClickListener(this);
        //添加生产批号监听
        et_scph.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //根据输入生产批号改变生产日期值
                if (charSequence.length() > 8) return;
                if (charSequence.length() >= 4) {
                    String year = String.valueOf(charSequence).substring(0, 4);
                    if (DateUtil.compareNewDate(year + "-" + 00 + "-" + 00)) {
                        return;
                    }
                    if (Integer.parseInt(year) != scrqyear && Integer.parseInt(year) < yxrqyear) {
                        et_scrq.setText(year + "-" + scrqmonth + "-" + scrqday);
                        et_yxrq.setText((Integer.parseInt(year) + 2) + "-" + scrqmonth + "-" + scrqday);
                    }
                    if (charSequence.length() >= 6) {
                        String month = String.valueOf(charSequence).substring(4, 6);
                        if (DateUtil.compareNewDate(year + "-" + month + "-" + 00)) {
                            return;
                        }
                        if (Integer.parseInt(month) != scrqmonth && Integer.parseInt(month) <= 12) {
                            et_scrq.setText(year + "-" + month + "-" + scrqday);
                            et_yxrq.setText((Integer.parseInt(year) + 2) + "-" + month + "-" + scrqday);
                        }
                        if (charSequence.length() == 8) {
                            String day = String.valueOf(charSequence).substring(6, 8);
                            if (DateUtil.compareNewDate(year + "-" + month + "-" + day)) {
                                return;
                            }
                            if (Integer.parseInt(day) != scrqday && !DateUtil.isValidDates(year + month + day)) {
                                et_scrq.setText(year + "-" + month + "-" + day);
                                et_yxrq.setText((Integer.parseInt(year) + 2) + "-" + month + "-" + day);
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //购入数量X采购价格=金额总计
        et_grsl.addTextChangedListener(new TextWatcher() {
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
                    ToastUtils.showShort(RuKuItemActivity.this, "采购价格输入有误");
                }
            }
        });
        et_cgjg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(".")) {
                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                        charSequence = charSequence.toString().subSequence(0,
                                charSequence.toString().indexOf(".") + 3);
                        et_cgjg.setText(charSequence);
                        et_cgjg.setSelection(charSequence.length());
                    }
                }
                if (charSequence.toString().trim().substring(0).equals(".")) {
                    charSequence = "0" + charSequence;
                    et_cgjg.setText(charSequence);
                    et_cgjg.setSelection(2);
                }

                if (charSequence.toString().startsWith("0") && charSequence.toString().trim().length() > 1) {
                    if (!charSequence.toString().substring(1, 2).equals(".")) {
                        et_cgjg.setText(charSequence.subSequence(0, 1));
                        et_cgjg.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (checkPrice(et_cgjg.getText().toString())) {
                    priceCompute();
                } else {
                    ToastUtils.showShort(RuKuItemActivity.this, "采购价格输入有误");
                }

            }
        });
        et_hdsj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(".")) {
                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                        charSequence = charSequence.toString().subSequence(0,
                                charSequence.toString().indexOf(".") + 3);
                        et_hdsj.setText(charSequence);
                        et_hdsj.setSelection(charSequence.length());
                    }
                }
                if (charSequence.toString().trim().substring(0).equals(".")) {
                    charSequence = "0" + charSequence;
                    et_hdsj.setText(charSequence);
                    et_hdsj.setSelection(2);
                }

                if (charSequence.toString().startsWith("0") && charSequence.toString().trim().length() > 1) {
                    if (!charSequence.toString().substring(1, 2).equals(".")) {
                        et_hdsj.setText(charSequence.subSequence(0, 1));
                        et_hdsj.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        if (!TextUtils.isEmpty(et_grsl.getText().toString())
                && !TextUtils.isEmpty(et_cgjg.getText().toString()) && checkPrice(et_cgjg.getText().toString())) {
            //总记
            double zj = (double) (Integer.parseInt(et_grsl.getText().toString()) * Float.parseFloat(et_cgjg.getText().toString()));
            BigDecimal d1 = new BigDecimal(Double.toString(zj));
            BigDecimal d2 = new BigDecimal(Integer.toString(1));
            et_jezj.setText(d1.divide(d2,2,BigDecimal.ROUND_HALF_UP).toString());
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
            case R.id.et_scrq:
                String[] scrq = et_scrq.getText().toString().split("-");
                scrqyear = Integer.parseInt(scrq[0]);
                scrqmonth = Integer.parseInt(scrq[1]);
                scrqday = Integer.parseInt(scrq[2]);
                scrqDialog.show();
                break;
            case R.id.et_yxrq:
                String[] yxrq = et_yxrq.getText().toString().split("-");
                yxrqyear = Integer.parseInt(yxrq[0]);
                yxrqmonth = Integer.parseInt(yxrq[1]);
                yxrqday = Integer.parseInt(yxrq[2]);
                yxrqDialog.show();
                break;
        }
    }

    private Boolean checkData() {
        if (TextUtils.isEmpty(et_scph.getText().toString())) {
            ToastUtils.showShort(this, "生产批号不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_grsl.getText().toString())) {
            ToastUtils.showShort(this, "购入数量不能为空");
            return false;
        } else if (Integer.parseInt(et_grsl.getText().toString()) == 0) {
            ToastUtils.showShort(this, "购入数量不能为零");
            return false;
        } else {
            if (TextUtils.isEmpty(et_hdsj.getText().toString())) {
                ToastUtils.showShort(this, "核定售价不能为空");
                return false;
            }
        }
        return true;
    }

    private ScanInputBean getBean() {
        bean.setFDljg(et_dljg.getText().toString());
        bean.setProductNumber(et_scph.getText().toString());
        bean.setProductionDate(et_scrq.getText().toString());
        bean.setExpiryDate(et_yxrq.getText().toString());
        bean.setCount(et_grsl.getText().toString());
        bean.setUnit(sp_unit.getSelectedItem().toString());
        bean.setPurchasePrice(et_cgjg.getText().toString());
        bean.setPresellPrice(et_hdsj.getText().toString());
        bean.setFJesum(et_jezj.getText().toString());
        bean.setYpArea(et_cfqy.getText().toString());
        bean.setFStoreHj(et_cchj.getText().toString());
        bean.setNm(et_nm.getText().toString());
        bean.setBemodfiyed(true);
        return bean;
    }


}
