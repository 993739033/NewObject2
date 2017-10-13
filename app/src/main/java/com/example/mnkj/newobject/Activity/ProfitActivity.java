package com.example.mnkj.newobject.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mnkj.newobject.Adapter.ProfitAdapter;
import com.example.mnkj.newobject.Adapter.RukuRecordAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ProfitBean;
import com.example.mnkj.newobject.Bean.RuKuRecordBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//利润
public class ProfitActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.profit_recy)
    RecyclerView profit_recy;
    @Bind(R.id.sp_condition_1)
    TextView sp_condition_1;
    @Bind(R.id.sp_condition)
    TextView sp_condition;
    DatePickerDialog datePickerDialog;
    DatePickerDialog datePickerDialog1;
    private List<ProfitBean> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        ButterKnife.bind(this);
        initListener();
        initData();
        initView();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String time = i + "-" + i1 + "-" + i2;
                sp_condition.setText(time);
            }
        }, 2017, 7, 7);
        datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String time = i + "-" + i1 + "-" + i2;
                sp_condition_1.setText(time);
            }
        }, 2017, 7, 7);
        sp_condition.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        sp_condition_1.setOnClickListener(new View.OnClickListener() {
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
//        查看	梅里亚有限公司	鸡传染性法氏囊病病毒火鸡疱疹病毒载体活疫苗（vHVT-013-69株）
// 	2000		（2010）外兽药证字10号 RI608	1	瓶	1000	1000	0	2017/9/13	进口生物制品	销售
        ProfitBean bean = new ProfitBean();
        bean.setCompany("梅里亚有限公司");
        bean.setNormalname("鸡传染性法氏囊病病毒火鸡疱疹病毒载体活疫苗（vHVT-013-69株）");
        bean.setSpec("2000");
        bean.setGoodsname("vHVT-013-69株");
        bean.setPznumber("（2010）外兽药证字10号 RI608");
        bean.setSalecount("1");
        bean.setUnit("瓶");
        bean.setSaleprice("1000");
        bean.setBuyprice("1100");
        bean.setProfit("100");
        bean.setSaledate("2017/9/13");
        bean.setGoodstype("进口生物制品");
        bean.setState("销售");
        list.add(bean);
//        查看	英特威国际有限公司	NOBIVAC稀释液	1
// 	  D383206	1	瓶	1000	1000	0	2017/9/13	进口生物制品	销售
        ProfitBean bean1 = new ProfitBean();
        bean1.setCompany("英特威国际有限公司");
        bean1.setNormalname("NOBIVAC稀释液");
        bean1.setSpec("2000");
        bean1.setGoodsname("vHVT株");
        bean1.setPznumber("D383206");
        bean1.setSalecount("1");
        bean1.setUnit("瓶");
        bean1.setSaleprice("1300");
        bean1.setBuyprice("1400");
        bean1.setProfit("100");
        bean1.setSaledate("2017/9/13");
        bean1.setGoodstype("进口生物制品");
        bean1.setState("销售");
        list.add(bean1);
    }

    private void initView() {
        profit_recy.setAdapter(new ProfitAdapter(ProfitActivity.this, list));
        profit_recy.setLayoutManager(new LinearLayoutManager(this));
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
