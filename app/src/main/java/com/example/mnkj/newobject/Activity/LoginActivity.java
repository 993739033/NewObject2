package com.example.mnkj.newobject.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.UserBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.btn_register)
    Button btn_register;
    @Bind(R.id.tv_forgetPassword)
    TextView tv_forgetPassword;
    @Bind(R.id.et_acount)
    EditText et_acount;
    @Bind(R.id.et_password)
    EditText et_password;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        initView();
        initListener();
//        requestPermission();
    }

    private void initView() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("登录中");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        String name = SPUtils.getInstance().getData(Contance.USERNAME, "", String.class);
        String psw = SPUtils.getInstance().getData(Contance.PASSWORD, "", String.class);

        et_acount.setText(name);
        et_password.setText(psw);
    }

//    private void requestPermission() {
//        //权限判断
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!MPermissions.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 100)) {
//                MPermissions.requestPermissions(this, 100, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
//            }
//        } else {
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        MPermissions.onRequestPermissionsResult(this, 100, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @PermissionGrant(100)
//    public void permissionSuccess() {
//        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
//    }
//
//    @PermissionDenied(100)
//    public void permissionFail() {
//        Toast.makeText(this, "权限申请失败,程序将不能正常运行", Toast.LENGTH_SHORT).show();
//    }


    private void initListener() {
        btn_login.setOnClickListener(this);
    }


    private void attemptLogin() {
        final String account = et_acount.getText().toString();
        final String password = et_password.getText().toString();

        if (TextUtils.isEmpty(et_acount.getText())) {
            ToastUtils.showShort(this, "账号不能为空");
            return;
        } else if (TextUtils.isEmpty(et_password.getText())) {
            ToastUtils.showShort(this, "密码不能为空");
            return;
        }
        dialog.show();
        RequestParams params = new RequestParams();
        params.addFormDataPart("uAccount", account);
        params.addFormDataPart("pwd", password);
        HttpRequest.post(Contance.BASE_URL + "Login.ashx", params, new RequestCallBack<UserBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.dismiss();
                SPUtils.getInstance().saveData(Contance.ISLOGIN, false);
                ToastUtils.showShort(LoginActivity.this, e.getMessage());
            }

            @Override
            public void getData(UserBean user) {
                dialog.dismiss();
//
//        "USERID": 1,
//        "USERNAME": "超级管理员",
//        "UNITUSTRID": "01",
//        "USEENAME": "超级管理员单位",
//        "UNAME": "超级管理员",
//        "UPHONE": "电话号码",
//        "M1": "1"
                SPUtils.getInstance().saveData(Contance.ISLOGIN, true);
                SPUtils.getInstance().saveData(Contance.USERNAME, account);
                SPUtils.getInstance().saveData(Contance.PASSWORD, password);
                SPUtils.getInstance().saveData(Contance.USERID, user.getData().getUSERID() + "");
                ToastUtils.showShort(LoginActivity.this, "登录成功");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                attemptLogin();
//                   startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.btn_register:
                Intent intent1 = new Intent(this, RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_forgetPassword:
                Intent intent2 = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
