package com.example.mnkj.newobject.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mnkj.newobject.Adapter.ClassifyAdapter;
import com.example.mnkj.newobject.Bean.ClassifyBean;
import com.example.mnkj.newobject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


//分类
public class Fragment_Classify extends Fragment {
    @Bind(R.id.classify_recy)
    RecyclerView classify_recy;
    List<ClassifyBean> beanList = new ArrayList<>();

    public Fragment_Classify() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        classify_recy.setAdapter(new ClassifyAdapter(getContext(), beanList));
        classify_recy.setLayoutManager(new GridLayoutManager(getContext(), 3));

    }

    private void initData() {
        {
            ClassifyBean bean = new ClassifyBean();
            bean.setContent("模块1");
            bean.setIcon(R.drawable.z1);
            beanList.add(bean);
        }
        {
            ClassifyBean bean = new ClassifyBean();
            bean.setContent("模块2");
            bean.setIcon(R.drawable.z2);
            beanList.add(bean);
        }
        {
            ClassifyBean bean = new ClassifyBean();
            bean.setContent("模块3");
            bean.setIcon(R.drawable.z3);
            beanList.add(bean);
        }
        {
            ClassifyBean bean = new ClassifyBean();
            bean.setContent("模块4");
            bean.setIcon(R.drawable.z4);
            beanList.add(bean);
        }
        {
            ClassifyBean bean = new ClassifyBean();
            bean.setContent("模块5");
            bean.setIcon(R.drawable.z5);
            beanList.add(bean);
        }
        {
            ClassifyBean bean = new ClassifyBean();
            bean.setContent("模块6");
            bean.setIcon(R.drawable.z6);
            beanList.add(bean);
        }
    }

}
