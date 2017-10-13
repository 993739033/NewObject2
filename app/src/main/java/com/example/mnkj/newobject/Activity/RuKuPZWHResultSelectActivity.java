package com.example.mnkj.newobject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.mnkj.newobject.Adapter.PZWHResultSelectAdapter;
import com.example.mnkj.newobject.Bean.PZWHScanBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

//入库时通过批准文号查询时有多个数据时跳转的选择界面
public class RuKuPZWHResultSelectActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.recy_result_select)
    RecyclerView recy_result_select;
    private PZWHScanBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku_pzwhresult_select);
        ButterKnife.bind(this);
        bean = (PZWHScanBean) getIntent().getSerializableExtra(Contance.DATA);
        initView();
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);

    }

    private void initView() {
        recy_result_select.setAdapter(new PZWHResultSelectAdapter(bean, this));
        recy_result_select.setLayoutManager(new LinearLayoutManager(this));

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
