package com.example.mnkj.newobject.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mnkj.newobject.Adapter.KuCunAdapter;
import com.example.mnkj.newobject.Adapter.KuCunBFAdapter;
import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;
import com.example.mnkj.newobject.View.SetRemindDialog;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

//库存报废处理
public class KuCunBFActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.kucun_bf_recy)
    RecyclerView kucun_bf_recy;
    @Bind(R.id.tv_next)
    TextView tv_next;
    @Bind(R.id.btn_back)
    ImageView btn_back;

    KuCunBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ku_cun_bf);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        btn_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        bean = (KuCunBean) intent.getSerializableExtra(Contance.DATA);
    }

    private void initView() {
        kucun_bf_recy.setAdapter(new KuCunBFAdapter(KuCunBFActivity.this, bean));
        kucun_bf_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (!((KuCunBFAdapter) kucun_bf_recy.getAdapter()).canUpload()) {
                    ToastUtils.showShort(KuCunBFActivity.this, "请完善红框中的数据信息");
                    return;
                }
                try {
                    String json = ((KuCunBFAdapter) kucun_bf_recy.getAdapter()).getJosn();
                    Intent intent = new Intent(KuCunBFActivity.this, KuCunBFNextActivity.class);
                    intent.putExtra(Contance.DATA, json);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Contance.RequestCode:
                if (resultCode == Contance.ResultCode) {
                    KuCunBean.DataList bean = (KuCunBean.DataList) data.getSerializableExtra(Contance.DATA);
                    ((KuCunBFAdapter) kucun_bf_recy.getAdapter()).update(bean);
                }
                break;
        }
    }
}
