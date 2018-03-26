package com.example.mnkj.newobject.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mnkj.newobject.Adapter.DividerDecoration;
import com.example.mnkj.newobject.Adapter.MainAdapter;
import com.example.mnkj.newobject.Bean.ImgBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;


//主页
public class Fragment_HomePage extends Fragment {

    List<ImgBean> list = new LinkedList<>();
    private RecyclerView recy;

    public Fragment_HomePage() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment1, container, false);
        recy = inflate.findViewById(R.id.main_recy);
        initData();
        MainAdapter adapter = new MainAdapter(list,getContext());
        recy.setAdapter(adapter);
        recy.setLayoutManager(new LinearLayoutManager(getContext()));
        recy.addItemDecoration(new DividerDecoration(getContext(), DividerDecoration.VERTICAL_LIST));
        return inflate;
    }


    private void initData() {
        list.clear();
        for (int i = 1; i < 10; i++) {
            ImgBean bean = new ImgBean();
            bean.setContent("测试测试测试测试测试测试");
            bean.setPath(R.drawable.animal_1);
            list.add(bean);
            ImgBean bean1 = new ImgBean();
            bean1.setContent("测试测试测试测试测试测试");
            bean1.setPath(R.drawable.animal_2);
            list.add(bean1);
            ImgBean bean2 = new ImgBean();
            bean2.setContent("测试测试测试测试测试测试");
            bean2.setPath(R.drawable.animal_3);
            list.add(bean2);
            ImgBean bean3 = new ImgBean();
            bean3.setContent("测试测试测试测试测试测试");
            bean3.setPath(R.drawable.animal_4);
            list.add(bean3);
        }

    }

}
