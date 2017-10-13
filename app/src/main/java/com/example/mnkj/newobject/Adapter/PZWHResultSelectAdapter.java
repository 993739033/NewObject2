package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.RuKuPZWHResultSelectActivity;
import com.example.mnkj.newobject.Bean.PZWHScanBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/10/10.
 */
//入库时通过批准文号查询时有多个数据时跳转的选择界面的列表Adapter
public class PZWHResultSelectAdapter extends RecyclerView.Adapter {
    private PZWHScanBean bean;
    private Context mContext;

    public PZWHScanBean getBean() {
        return bean;
    }

    public void setBean(PZWHScanBean bean) {
        this.bean = bean;
    }

    public PZWHResultSelectAdapter(PZWHScanBean bean, Context mContext) {
        this.bean = bean;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pzwh_result_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PZWHScanBean.DataList item = bean.getDataList().get(position);
        ((viewHolder) holder).bindView(item);
        ((viewHolder) holder).layout_pzwh_result_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ((RuKuPZWHResultSelectActivity) mContext).getIntent();
                String zsm = intent.getStringExtra(Contance.DATA_1);
                intent.putExtra(Contance.DATA, item);
                intent.putExtra(Contance.DATA_1, zsm);
                ((RuKuPZWHResultSelectActivity) mContext).setResult(Contance.ResultCode, intent);
                ((RuKuPZWHResultSelectActivity) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bean.getDataList().size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_qiye_name)
        TextView tv_qiye_name;
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_goods_name)
        TextView tv_goods_name;
        @Bind(R.id.tv_spec)
        TextView tv_spec;
        @Bind(R.id.tv_pz_number)
        TextView tv_pz_number;
        @Bind(R.id.tv_goods_type)
        TextView tv_goods_type;
        @Bind(R.id.tv_sc_date)
        TextView tv_sc_date;
        @Bind(R.id.layout_pzwh_result_item)
        View layout_pzwh_result_item;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(PZWHScanBean.DataList item) {
            tv_qiye_name.setText(item.getProductEnterPrise());
            tv_normal_name.setText(item.getFTyName());
            tv_goods_name.setText(item.getProductName());
            tv_sc_date.setText(item.getPzDate());
            tv_spec.setText(item.getGuige());
            tv_pz_number.setText(item.getPzwh());
            tv_goods_type.setText(item.getYplx());
        }
    }

}
