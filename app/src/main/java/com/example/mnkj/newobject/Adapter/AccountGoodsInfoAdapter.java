package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.GoodsInfoBean;
import com.example.mnkj.newobject.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//商品信息
public class AccountGoodsInfoAdapter extends RecyclerView.Adapter {

    private GoodsInfoBean goodBean;
    private LayoutInflater inflater;
    private Context context;

    public void addBean(GoodsInfoBean bean) {
        if (this.goodBean.getDataList() == null) {
            this.goodBean = bean;
        } else {
            this.goodBean.getDataList().addAll(bean.getDataList());
        }
        notifyDataSetChanged();
    }

    public void clearBean(){
        goodBean.getDataList().clear();
    }

    public AccountGoodsInfoAdapter(GoodsInfoBean goodBean, Context context) {
        this.goodBean = goodBean;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_account_goods_info_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsInfoBean.DataList bean = goodBean.getDataList().get(position);
        ((viewHolder) holder).bindView(bean);
    }

    @Override
    public int getItemCount() {
        return goodBean.getDataList() == null ? 0 : goodBean.getDataList().size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_company_name)
        TextView tv_company_name;
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
        @Bind(R.id.layout_goods_info_item)
        View layout_goods_info_item;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(GoodsInfoBean.DataList bean) {
            tv_company_name.setText(bean.getProductEnterPrise());
            tv_normal_name.setText(bean.getFTyName());
            tv_goods_name.setText(bean.getProductName());
            tv_spec.setText(bean.getGuige());
            tv_pz_number.setText(bean.getPzwh());
            tv_goods_type.setText(bean.getYplx());
        }
    }
}
