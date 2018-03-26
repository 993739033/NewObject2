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
import com.example.mnkj.newobject.Activity.KHSelectActivity;
import com.example.mnkj.newobject.Activity.RuKuNextActivity;
import com.example.mnkj.newobject.Bean.GYSBean;
import com.example.mnkj.newobject.Bean.KHBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/27.
 */
//客户选择
public class KHSelectAdapter extends RecyclerView.Adapter {
    private KHBean bean;
    private Context mContext;
    private String condition, content;

    public KHBean getBean() {
        return bean;
    }

    public void setBean(KHBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public KHSelectAdapter(Context context, KHBean bean) {
        mContext = context;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_kh_select_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final KHBean.DataList item = bean.getDataList().get(position);
        ((viewHolder) holder).bindView(item, condition, content);

        ((viewHolder) holder).layout_kh_select_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ((KHSelectActivity) mContext).getIntent();
                intent.putExtra(Contance.Result, item);
                ((KHSelectActivity) mContext).setResult(Contance.ResultCode, intent);
                ((KHSelectActivity) mContext).finish();
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
        TextView tv_gmr_name;
        TextView tv_gmr_sj;
        TextView tv_gmr_dz;
        TextView tv_yzc;
        TextView tv_yzcd;
        View layout_kh_select_item;
        View layout_card;

        public viewHolder(View itemView) {
            super(itemView);
            tv_gmr_name=itemView.findViewById(R.id.tv_gmr_name);
            tv_gmr_sj=itemView.findViewById(R.id.tv_gmr_sj);
            tv_gmr_dz=itemView.findViewById(R.id.tv_gmr_dz);
            tv_yzc=itemView.findViewById(R.id.tv_yzc);
            tv_yzcd=itemView.findViewById(R.id.tv_yzcd);
            layout_kh_select_item=itemView.findViewById(R.id.layout_kh_select_item);
            layout_card=itemView.findViewById(R.id.layout_card);
        }

        public void bindView(KHBean.DataList bean, String condition, String content) {
            tv_gmr_name.setText(bean.getFBuyName());
            tv_gmr_sj.setText(bean.getFBuyTel());
            tv_gmr_dz.setText(bean.getFBuyAddress());
            tv_yzc.setText(bean.getFGmyzc());
            tv_yzcd.setText(bean.getFGmyzcd());
            if (!TextUtils.isEmpty(content)) {
                switch (condition) {
                    case "购买人姓名":
                        if (bean.getFBuyName().indexOf(content) != -1) {
                        layout_kh_select_item.setVisibility(View.VISIBLE);
                        } else {
                        layout_kh_select_item.setVisibility(View.GONE);
                        }
                        break;
                    case "购买人手机":
                        if (bean.getFBuyTel().indexOf(content) != -1) {
                            layout_kh_select_item.setVisibility(View.VISIBLE);
                        } else {
                            layout_kh_select_item.setVisibility(View.GONE);
                        }
                        break;
                    case "购买人地址":
                        if (bean.getFBuyAddress().indexOf(content) != -1) {
                            layout_kh_select_item.setVisibility(View.VISIBLE);
                        } else {
                            layout_kh_select_item.setVisibility(View.GONE);
                        }
                        break;
                }
            } else {
                layout_kh_select_item.setVisibility(View.VISIBLE);
            }
        }

    }
}

