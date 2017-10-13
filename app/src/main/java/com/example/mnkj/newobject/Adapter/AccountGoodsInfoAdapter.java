package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.GoodsInfoBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//商品信息
public class AccountGoodsInfoAdapter extends RecyclerView.Adapter {
    private List<GoodsInfoBean> list = new LinkedList<>();
    private LayoutInflater inflater;
    private Context context;

    public AccountGoodsInfoAdapter(List<GoodsInfoBean> list, Context context) {
        this.list = list;
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
        GoodsInfoBean bean = list.get(position);
        ((viewHolder) holder).bindView(bean);
    }

    @Override
    public int getItemCount() {
        return list.size();
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

        private void bindView(GoodsInfoBean bean) {
            tv_company_name.setText(bean.getCompany());
            tv_normal_name.setText(bean.getNormalname());
            tv_goods_name.setText(bean.getGoodsname());
            tv_spec.setText(bean.getSpec());
            tv_pz_number.setText(bean.getPznumber());
            tv_goods_type.setText(bean.getGoodstype());
        }

        private void setClickListener(View.OnClickListener listener) {
            layout_goods_info_item.setOnClickListener(listener);
        }
    }
}
