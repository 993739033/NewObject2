package com.example.mnkj.newobject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;
//出库list 中的list 中的item
public class ChuKuRecordListItemActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_ku_record_list_item);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
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
