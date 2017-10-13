package com.example.mnkj.newobject.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/7.
 */

public class BaseActivity extends AppCompatActivity {
    private Boolean beClicked=false;//用于锁定上传按钮

    public Boolean getBeClicked() {
        return beClicked;
    }

    public void setBeClicked(Boolean beClicked) {
        this.beClicked = beClicked;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
