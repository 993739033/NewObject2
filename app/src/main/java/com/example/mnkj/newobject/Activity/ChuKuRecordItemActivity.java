package com.example.mnkj.newobject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mnkj.newobject.Adapter.ChukuRecordAdapter;
import com.example.mnkj.newobject.Adapter.ChukuRecordItemAdapter;
import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.Bean.ChuKuRecordListItemBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

//出库记录item点击后的列表
public class ChuKuRecordItemActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.chuku_record_item_recy)
    RecyclerView chuku_record_item_recy;
    private ChuKuRecordBean bean = new ChuKuRecordBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku_record_item);
        ButterKnife.bind(this);
        initListener();
        initData();
        initView();
    }


    private void initListener() {
        btn_back.setOnClickListener(this);
    }

    //获取intent中bean
    private void initData() {
        Intent intent = getIntent();
        bean = (ChuKuRecordBean) intent.getSerializableExtra(Contance.DATA);
    }

    private void initView() {
        chuku_record_item_recy.setAdapter(new ChukuRecordItemAdapter(ChuKuRecordItemActivity.this, bean));
        chuku_record_item_recy.setLayoutManager(new LinearLayoutManager(this));
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
