package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.ClassifyBean;
import com.example.mnkj.newobject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/25.
 */

public class ClassifyAdapter extends RecyclerView.Adapter {
    List<ClassifyBean> beanList = new ArrayList<>();
    private Context mContext;

    public ClassifyAdapter(Context context, List<ClassifyBean> beanList) {
        this.beanList = beanList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_classify_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClassifyBean bean = beanList.get(position);
        ((viewHolder) holder).BindView(bean);
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView iv_icon;
        @Bind(R.id.tv_content)
        TextView tv_content;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void BindView(ClassifyBean bean) {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bean.getIcon());
            iv_icon.setImageBitmap(bitmap);
            tv_content.setText(bean.getContent());
        }
    }
}
