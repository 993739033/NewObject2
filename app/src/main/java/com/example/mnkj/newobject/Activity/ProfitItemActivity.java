package com.example.mnkj.newobject.Activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mnkj.newobject.Adapter.ProfitItemAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ProfitItemBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//利润
public class ProfitItemActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.profit_item_recy)
    RecyclerView profit_item_recy;
    @Bind(R.id.tv_danju)
    TextView tv_danju;
    @Bind(R.id.tv_sale_date)
    TextView tv_sale_date;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;

    private List<ProfitItemBean> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_item);
        ButterKnife.bind(this);
        initListener();
        initData();
        initView();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String time = i + "-" + i1 + "-" + i2;
                tv_danju.setText(time);
            }
        }, 2017, 7, 7);
        datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String time = i + "-" + i1 + "-" + i2;
                tv_sale_date.setText(time);
            }
        }, 2017, 7, 7);
        tv_danju.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        tv_sale_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                datePickerDialog1.show();
            }
        });

    }

    private void initListener() {
        btn_back.setOnClickListener(this);
    }

    private void initData() {
//        英特威国际有限公司	NOBIVAC稀释液	1		  D383206	1	hyuhyu	sdfsdf	2017/9/13	瓶
// 1000	1000	0	销售	进口生物制品
        ProfitItemBean bean = new ProfitItemBean();
        bean.setCompany("梅里亚有限公司");
        bean.setNormalname("NOBIVAC稀释液");
        bean.setSpec("2000");
        bean.setGoodsname("vHVT-013-69株");
        bean.setPznumber("（2010）外兽药证字10号 RI608");
        bean.setSalecount("1");
        bean.setGysname("梅里亚有限公司");
        bean.setScnumber("13451");
        bean.setScdate("2017/9/13");
        bean.setUnit("瓶");
        bean.setBuyprice("1100");
        bean.setSaleprice("1000");
        bean.setState("销售");
        bean.setGoodstype("进口生物制品");
        list.add(bean);
        ProfitItemBean bean1 = new ProfitItemBean();
        bean1.setCompany("科瑞有限公司");
        bean1.setNormalname("NOVXAC稀释液");
        bean1.setSpec("2010");
        bean1.setGoodsname("adjfkj-69株");
        bean1.setPznumber("（2010）外兽药证字10号 RI608");
        bean1.setSalecount("1");
        bean1.setGysname("梅里亚有限公司");
        bean1.setScnumber("13451");
        bean1.setScdate("2017/9/13");
        bean1.setUnit("瓶");
        bean1.setBuyprice("1100");
        bean1.setSaleprice("1000");
        bean1.setState("销售");
        bean1.setGoodstype("进口生物制品");
        list.add(bean1);
        ProfitItemBean bean2 = new ProfitItemBean();
        bean2.setCompany("科瑞有限公司");
        bean2.setNormalname("NOVXAC稀释液");
        bean2.setSpec("2010");
        bean2.setGoodsname("adjfkj-69株");
        bean2.setPznumber("（2010）外兽药证字10号 RI608");
        bean2.setSalecount("1");
        bean2.setGysname("梅里亚有限公司");
        bean2.setScnumber("13451");
        bean2.setScdate("2017/9/13");
        bean2.setUnit("瓶");
        bean2.setBuyprice("1100");
        bean2.setSaleprice("1000");
        bean2.setState("销售");
        bean2.setGoodstype("进口生物制品");
        list.add(bean2);
        ProfitItemBean bean3 = new ProfitItemBean();
        bean3.setCompany("科瑞有限公司");
        bean3.setNormalname("NOVXAC稀释液");
        bean3.setSpec("2010");
        bean3.setGoodsname("adjfkj-69株");
        bean3.setPznumber("（2010）外兽药证字10号 RI608");
        bean3.setSalecount("1");
        bean3.setGysname("梅里亚有限公司");
        bean3.setScnumber("13451");
        bean3.setScdate("2017/9/13");
        bean3.setUnit("瓶");
        bean3.setBuyprice("1100");
        bean3.setSaleprice("1000");
        bean3.setState("销售");
        bean3.setGoodstype("进口生物制品");
        list.add(bean3);
    }

    private void initView() {
        profit_item_recy.setAdapter(new ProfitItemAdapter(ProfitItemActivity.this, list));
        profit_item_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
