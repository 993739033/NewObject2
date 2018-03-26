package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
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
import com.example.mnkj.newobject.Bean.ScanOutputNetworkBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

//出库列表修改界面
public class ChuKuItemActivity extends BaseActivity implements View.OnClickListener {
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
    EditText et_xssl;
    @Bind(R.id.et_xsdj)
    EditText et_xsdj;
    @Bind(R.id.et_jezj)
    EditText et_jezj;

    @Bind(R.id.et_cchjyq)
    EditText et_cchjyq;
    @Bind(R.id.et_nm)
    EditText et_nm;
    @Bind(R.id.et_zsm)
    EditText et_zsm;
    @Bind(R.id.et_gys_name)
    EditText et_gys_name;
    private ScanOutputNetworkBean bean;

    private ProgressDialog dialog;
    private int KCCount = 0;//用于保存库存数量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku_item);
        ButterKnife.bind(this);
        bindSp();
        initListener();
        bean = (ScanOutputNetworkBean) getIntent().getSerializableExtra(Contance.DATA);
        dialog = new ProgressDialog(this);
        dialog.setMessage("获取当前库存中。。");
        dialog.show();
        getKuCun();
        initView(bean);
    }

    private void initView(ScanOutputNetworkBean bean) {
        et_product_type.setText(bean.getDataList().get(0).getYplx());//商品类型
        et_dljg.setText(bean.getDataList().get(0).getFDljg());//代理机构
        et_qiye_name.setText(bean.getDataList().get(0).getFProductEnterprise());//企业名称
        et_normal_name.setText(bean.getDataList().get(0).getFTyName());//通用名称
        et_gys_name.setText(bean.getDataList().get(0).getFGysmc());//供应商名称
        et_spec.setText(bean.getDataList().get(0).getFGuige());//规格
        et_pzwh.setText(bean.getDataList().get(0).getFPzwh());//批准文号
        et_scph.setText(bean.getDataList().get(0).getFScph());//生产批号
        et_scrq.setText(bean.getDataList().get(0).getFScDate());//生产日期
        et_yxrq.setText(bean.getDataList().get(0).getFYxqDate());//有效日期
        et_xssl.setText(bean.getDataList().get(0).getFXsNum());//销售数量
        et_xsdj.setText(bean.getDataList().get(0).getFHdjg() + "");//销售价格
        et_jezj.setText(bean.getDataList().get(0).getFJesum());//金额总计
        et_nm.setText(bean.getDataList().get(0).getFNmcode());//内码
        et_zsm.setText(bean.getDataList().get(0).getFSm1());//追溯码
        et_cchjyq.setText(bean.getDataList().get(0).getFStoreHj());//存储环境要求

        int count = sp_unit.getCount();
        for (int i = 0; i < count; i++) {
            if (sp_unit.getAdapter().getItem(i).equals(bean.getDataList().get(0).getFDw())) {
                sp_unit.setSelection(i);
            }
        }
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
        ArrayAdapter adapter = new ArrayAdapter<String>(ChuKuItemActivity.this,
                android.R.layout.simple_spinner_item, unitList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_unit.setAdapter(adapter);
        sp_unit.setEnabled(false);
        sp_unit.setClickable(false);
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);

        //购入数量X采购价格=金额总计
        et_xssl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (checkPrice(et_xsdj.getText().toString())) {
                    priceCompute();
                } else {
                    ToastUtils.showShort(ChuKuItemActivity.this, "销售价格输入有误");
                }
            }
        });
        et_xsdj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().contains(".")) {
                    if (charSequence.length() - 1 - charSequence.toString().indexOf(".") > 2) {
                        charSequence = charSequence.toString().subSequence(0,
                                charSequence.toString().indexOf(".") + 3);
                        et_xsdj.setText(charSequence);
                        et_xsdj.setSelection(charSequence.length());
                    }
                }
                if (charSequence.toString().trim().substring(0).equals(".")) {
                    charSequence = "0" + charSequence;
                    et_xsdj.setText(charSequence);
                    et_xsdj.setSelection(2);
                }

                if (charSequence.toString().startsWith("0") && charSequence.toString().trim().length() > 1) {
                    if (!charSequence.toString().substring(1, 2).equals(".")) {
                        et_xsdj.setText(charSequence.subSequence(0, 1));
                        et_xsdj.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (checkPrice(et_xsdj.getText().toString())) {
                    priceCompute();
                } else {
                    ToastUtils.showShort(ChuKuItemActivity.this, "销售价格输入有误");
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
        if (!TextUtils.isEmpty(et_xssl.getText().toString())
                && !TextUtils.isEmpty(et_xsdj.getText().toString()) && checkPrice(et_xsdj.getText().toString())) {
            //总记
            float zj = Integer.parseInt(et_xssl.getText().toString()) * Float.parseFloat(et_xsdj.getText().toString());
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
        if (TextUtils.isEmpty(et_scph.getText().toString())) {
            ToastUtils.showShort(this, "生产批号不能为空");
            return false;
        } else if (TextUtils.isEmpty(et_xssl.getText().toString())) {
            ToastUtils.showShort(this, "销售数量不能为空");
            return false;
        } else if (Integer.parseInt(et_xssl.getText().toString()) == 0) {
            ToastUtils.showShort(this, "销售数量不能为零");
            return false;
        } else if (Integer.parseInt(et_xssl.getText().toString()) > KCCount) {
            ToastUtils.showLong(this, "销售数量不能大于当前库存数量（当前库存" + KCCount + ")");
            return false;
        }
        return true;
    }

    private ScanOutputNetworkBean getBean() {
        bean.getDataList().get(0).setFXsNum(et_xssl.getText().toString());
        bean.getDataList().get(0).setFJesum(et_jezj.getText().toString());
        bean.getDataList().get(0).setFHdjg(et_xsdj.getText().toString());
        bean.getDataList().get(0).setBemodfiyed(true);
        return bean;
    }

    //获取当前库存
    private void getKuCun() {
        RequestParams params = new RequestParams();
        params.addFormDataPart("FSuserId", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("FSm1", bean.getDataList().get(0).getFSm1());
        dialog.show();
        HttpRequest.post(Contance.BASE_URL + "GetAPPKC.ashx", params, new RequestCallBack<KuCunBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                ToastUtils.showShort(ChuKuItemActivity.this, "获取当前库存数量失败!请重试");
                finish();
            }

            @Override
            public void getData(KuCunBean bean) {
                dialog.dismiss();
                KCCount = Integer.parseInt(bean.getDataList().get(0).getFKcsl());
                et_xssl.setHint("当前库存为:" + KCCount);
            }
        });

    }

}
