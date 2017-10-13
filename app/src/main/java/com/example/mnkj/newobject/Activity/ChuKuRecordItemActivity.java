package com.example.mnkj.newobject.Activity;

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
    @Bind(R.id.btn_select_all)
    Button btn_select_all;
    private List<ChuKuRecordListItemBean> list = new LinkedList<>();

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
        btn_select_all.setOnClickListener(this);
    }

    private void initData() {
        ChuKuRecordListItemBean bean = new ChuKuRecordListItemBean();
        bean.setName("Alion");
        bean.setFarmname("青岛种畜禽场");
        bean.setNormalname("鸡传染性法氏囊病病毒火鸡疱疹病毒载体活疫苗");
        bean.setSalecount("100");
        bean.setIschecked(false);
        list.add(bean);

        ChuKuRecordListItemBean bean1 = new ChuKuRecordListItemBean();
        bean1.setName("Eson");
        bean1.setFarmname("即墨种畜禽场");
        bean1.setNormalname("NOVAVAC稀释液");
        bean1.setSalecount("1080");
        bean1.setIschecked(false);
        list.add(bean1);

        ChuKuRecordListItemBean bean2 = new ChuKuRecordListItemBean();
        bean2.setName("Jseon");
        bean2.setFarmname("克拉种畜禽场");
        bean2.setNormalname("BAVAC稀释液");
        bean2.setSalecount("1028");
        bean2.setIschecked(false);
        list.add(bean2);
    }

    private void initView() {
        chuku_record_item_recy.setAdapter(new ChukuRecordItemAdapter(ChuKuRecordItemActivity.this, list));
        chuku_record_item_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_select_all:
                ((ChukuRecordItemAdapter) chuku_record_item_recy.getAdapter()).selectAll();
                break;

        }
    }
}
