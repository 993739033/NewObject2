package com.example.mnkj.newobject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.btn_back)
    ImageView btn_back;
    @Bind(R.id.layout_webView)
    WebView layout_webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra(Contance.DATA);
        layout_webView.loadUrl(url);
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
