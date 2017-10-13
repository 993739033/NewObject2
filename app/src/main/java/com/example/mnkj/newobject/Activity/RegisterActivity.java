package com.example.mnkj.newobject.Activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    ArrayAdapter adapter;
    @Bind(R.id.sp_sheng)
    Spinner sp_sheng;
    @Bind(R.id.sp_shi)
    Spinner sp_shi;
    @Bind(R.id.sp_qu)
    Spinner sp_qu;
    @Bind(R.id.sp_xukezheng)
    Spinner sp_xukezheng;
    @Bind(R.id.sp_xukezheng2)
    Spinner sp_xukezheng2;
    @Bind(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        spinnerData();
        initView();
    }

    private void initView(){
        btn_back.setOnClickListener(this);

    }
    private void spinnerData() {
        List<String> ShengShiQuList = new ArrayList<>();
        ShengShiQuList.add("上海");
        ShengShiQuList.add("北京");
        ShengShiQuList.add("深圳");
        ShengShiQuList.add("广州");
        ShengShiQuList.add("浙江");
        ShengShiQuList.add("杭州");
        // 初始化下拉列表加载数据适配器
        adapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_spinner_item, ShengShiQuList);
        // 设置下拉列表的样式，下为设置为简单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将数据加载进下拉列表当中
        sp_sheng.setAdapter(adapter);
        sp_shi.setAdapter(adapter);
        sp_qu.setAdapter(adapter);
        sp_xukezheng.setAdapter(adapter);
        sp_xukezheng2.setAdapter(adapter);

        // 添加事件Spinner事件监听，当点击下拉列表中的某一选项之后触发该事件
        sp_sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_qu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_xukezheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        sp_xukezheng2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
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
