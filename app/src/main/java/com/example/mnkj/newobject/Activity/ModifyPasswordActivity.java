package com.example.mnkj.newobject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.btn_enter)
    Button btn_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        btn_back.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_enter:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
