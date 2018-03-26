package com.example.mnkj.newobject.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Adapter.PhoneSelectAdapter;
import com.example.mnkj.newobject.Base.BaseActivity;
import com.example.mnkj.newobject.Bean.ContactBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.KeyBoard;
import com.example.mnkj.newobject.Utils.PinyinComparator;
import com.example.mnkj.newobject.Utils.PinyinUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.ClearEditText;
import com.example.mnkj.newobject.View.SideBar;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//电话选择界面
public class PhoneSelectActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private SideBar sideBar;
    private TextView dialog;
    private PhoneSelectAdapter adapter;
    private ClearEditText mClearEditText;
    private ImageView btn_back;
    LinearLayoutManager manager;

    String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
    };
    //联系人显示名称
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    //  电话号码
    private static final int PHONES_NUMBER_INDEX = 1;

    private List<ContactBean> SourceDateList;

    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_select);
        //权限判断
        if (Build.VERSION.SDK_INT >= 23) {
            if (!MPermissions.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS, 101)) {
                MPermissions.requestPermissions(this, 101, new String[]{Manifest.permission.READ_CONTACTS});
            }
        }
        initViews();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(101)
    public void permissionSuccess() {
        Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(101)
    public void permissionFail() {
        Toast.makeText(this, "权限申请失败,程序将不能正常运行", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sideBar);
        dialog = (TextView) findViewById(R.id.dialog);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sideBar.setTextView(dialog);

        //设置右侧SideBar触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mRecyclerView.setFocusable(true);
                mRecyclerView.setFocusableInTouchMode(true);
                mRecyclerView.requestFocus();
                KeyBoard.closeSoftKeyBoard(PhoneSelectActivity.this);
                return false;
            }
        });
        SourceDateList = getContactList();

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        //RecyclerView社置manager
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        adapter = new PhoneSelectAdapter(this, SourceDateList);
        mRecyclerView.setAdapter(adapter);
        //item点击事件
        /*adapter.setOnItemClickListener(new PhoneSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, ((ContactBean)adapter.getItem(position)).getName(),Toast.LENGTH_SHORT).show();
            }
        });*/
        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        mClearEditText.setAnimator();
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 将中文转化为拼音
     *
     * @param datas
     * @return
     */
    private void filledData(List<ContactBean> datas) {
        List<ContactBean> mSortList = new ArrayList<>();

        for (int i = 0; i < datas.size(); i++) {
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(datas.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                datas.get(i).setLetters(sortString.toUpperCase());
            } else {
                datas.get(i).setLetters("#");
            }
        }
    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<ContactBean> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (ContactBean contactBean : SourceDateList) {
                String name = contactBean.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(contactBean);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateList(filterDateList);
    }

    private List<ContactBean> getContactList() {
        ContentResolver resolver = getContentResolver();
        List<ContactBean> conlist = new ArrayList<>();
        try {
            // 获取手机联系人
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    ContactBean bean = new ContactBean();
                    // 得到手机号码
                    String phoneNumber = phoneCursor
                            .getString(PHONES_NUMBER_INDEX);
                    // 当手机号码为空的或者为空字段 跳过当前循环
                    if (TextUtils.isEmpty(phoneNumber))
                        continue;
                    bean.setPhone(phoneNumber);
                    // 得到联系人名称
                    String contactName = phoneCursor
                            .getString(PHONES_DISPLAY_NAME_INDEX);
                    bean.setName(contactName);
                    conlist.add(bean);
                }
                filledData(conlist);
                phoneCursor.close();
            }
        } catch (Exception e) {
            ToastUtils.showShort(PhoneSelectActivity.this, "获取通讯录信息失败");
            e.printStackTrace();
        }
        return conlist;
    }
}