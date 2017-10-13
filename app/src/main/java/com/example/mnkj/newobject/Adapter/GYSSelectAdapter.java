package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.GYSSelectActivity;
import com.example.mnkj.newobject.Activity.RuKuNextActivity;
import com.example.mnkj.newobject.Bean.GYSBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/27.
 */
//供应商选择
public class GYSSelectAdapter extends RecyclerView.Adapter {
    private GYSBean bean;
    private Context mContext;
    private String condition, content;

    public GYSBean getBean() {
        return bean;
    }

    public void setBean(GYSBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public GYSSelectAdapter(Context context, GYSBean bean) {
        mContext = context;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_gys_select_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GYSBean.DataList item = bean.getDataList().get(position);
        ((viewHolder) holder).bindView(item, condition, content);

        ((viewHolder) holder).layout_gys_select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ((GYSSelectActivity) mContext).getIntent();
                intent.putExtra(Contance.Result, item);
                ((GYSSelectActivity) mContext).setResult(Contance.ResultCode, intent);
                ((GYSSelectActivity) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }

    public void search(String condition, String content) {
        this.condition = condition;
        this.content = content;
        notifyDataSetChanged();
    }


    static class viewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_gys_name)
        TextView tv_gys_name;
        @Bind(R.id.tv_people_name)
        TextView tv_people_name;
        @Bind(R.id.tv_phone)
        TextView tv_phone;
        @Bind(R.id.tv_sy_xkz)
        TextView tv_sy_xkz;
        @Bind(R.id.tv_gmp_zsh)
        TextView tv_gmp_zsh;
        @Bind(R.id.layout_gys_select_item)
        View layout_gys_select_item;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(GYSBean.DataList bean, String condition, String content) {
            tv_gys_name.setText(bean.getGysmc());
            tv_people_name.setText(bean.getLxrName());
            tv_phone.setText(bean.getLxrdh());
            tv_sy_xkz.setText(bean.getXgzsh());
            tv_gmp_zsh.setText(bean.getSyGmp());
            if (!TextUtils.isEmpty(content)) {
                switch (condition) {
                    case "供应商名称":
                        if (bean.getGysmc().indexOf(content) != -1) {
                            layout_gys_select_item.setVisibility(View.VISIBLE);
                        } else {
                            layout_gys_select_item.setVisibility(View.GONE);
                        }
                        break;
                    case "联系人":
                        if (bean.getLxrName().indexOf(content) != -1) {
                            layout_gys_select_item.setVisibility(View.VISIBLE);
                        } else {
                            layout_gys_select_item.setVisibility(View.GONE);
                        }
                        break;
                    case "联系电话":
                        if (bean.getLxrdh().indexOf(content) != -1) {
                            layout_gys_select_item.setVisibility(View.VISIBLE);
                        } else {
                            layout_gys_select_item.setVisibility(View.GONE);
                        }
                        break;
                }
            } else {
                layout_gys_select_item.setVisibility(View.VISIBLE);
            }
        }

    }
}

