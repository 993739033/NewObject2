package com.example.mnkj.newobject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mnkj.newobject.Activity.AccountListActivity;
import com.example.mnkj.newobject.Activity.GoodsInfoActivity;
import com.example.mnkj.newobject.Activity.LoginActivity;
import com.example.mnkj.newobject.Activity.MainActivity;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Utils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

//账户
public class Fragment_Account extends Fragment implements View.OnClickListener {
    @Bind(R.id.layout_account_gys)
    LinearLayout layout_account_gys;
    @Bind(R.id.layout_account_kh)
    LinearLayout layout_account_kh;
    @Bind(R.id.layout_account_sp)
    LinearLayout layout_account_sp;
    @Bind(R.id.btn_quit)
    Button btn_quit;

    public Fragment_Account() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    private void initListener() {
        layout_account_gys.setOnClickListener(this);
        layout_account_kh.setOnClickListener(this);
        layout_account_sp.setOnClickListener(this);
        btn_quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_account_gys:
                Intent intent = new Intent(getContext(), AccountListActivity.class);
                intent.putExtra("title", "供应商信息");
                getContext().startActivity(intent);
                break;
            case R.id.layout_account_kh:
                Intent intent1 = new Intent(getContext(), AccountListActivity.class);
                intent1.putExtra("title", "客户资料");
                getContext().startActivity(intent1);
                break;
            case R.id.layout_account_sp:
                Intent intent2 = new Intent(getContext(), GoodsInfoActivity.class);
                getContext().startActivity(intent2);
                break;
            case R.id.btn_quit:
                getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                SPUtils.getInstance().saveData(Contance.ISLOGIN, false);
                ((MainActivity) getContext()).finish();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
