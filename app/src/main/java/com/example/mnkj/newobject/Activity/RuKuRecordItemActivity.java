package com.example.mnkj.newobject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RuKuRecordItemActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_ku_record_item);
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
