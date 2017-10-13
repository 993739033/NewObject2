package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.ProfitItemBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//利润
public class ProfitItemAdapter extends RecyclerView.Adapter {
    private List<ProfitItemBean> list = new LinkedList<>();
    private Context mContext;
    int height;

    public ProfitItemAdapter(Context context, List<ProfitItemBean> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_profit_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfitItemBean bean = list.get(position);
        ((ViewHolder) holder).layout_more.setTag(position);
        if (((int)((ViewHolder) holder).layout_more.getTag()) != position)
            return;
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_company_name)
        TextView tv_company_name;
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_spec)
        TextView tv_spec;
        @Bind(R.id.tv_goods_name)
        TextView tv_goods_name;
        @Bind(R.id.tv_pz_number)
        TextView tv_pz_number;
        @Bind(R.id.tv_gys_name)
        TextView tv_gys_name;
        @Bind(R.id.tv_sc_number)
        TextView tv_sc_number;
        @Bind(R.id.tv_sc_date)
        TextView tv_sc_date;
        @Bind(R.id.tv_unit)
        TextView tv_unit;
        @Bind(R.id.tv_sale_price)
        TextView tv_sale_price;
        @Bind(R.id.tv_buy_price)
        TextView tv_buy_price;
        @Bind(R.id.tv_sale_profit)
        TextView tv_sale_profit;
        @Bind(R.id.tv_state)
        TextView tv_state;
        @Bind(R.id.tv_goods_type)
        TextView tv_goods_type;
        @Bind(R.id.layout_more)
        LinearLayout layout_more;
        @Bind(R.id.layout_profit_list_item)
        LinearLayout layout_profit_list_item;
        @Bind(R.id.layout_show_more)
        View layout_show_more;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(ProfitItemBean bean) {
            tv_company_name.setText(bean.getCompany());
            tv_normal_name.setText(bean.getNormalname());
            tv_spec.setText(bean.getSpec());
            tv_goods_name.setText(bean.getGoodsname());
            tv_pz_number.setText(bean.getPznumber());
            tv_gys_name.setText(bean.getGysname());
            tv_sc_number.setText(bean.getScnumber());
            tv_sc_date.setText(bean.getScdate());
            tv_sale_price.setText(bean.getSaleprice());
            tv_buy_price.setText(bean.getBuyprice());
            tv_sale_profit.setText(bean.getSaleprice());
            tv_state.setText(bean.getState());
            tv_goods_type.setText(bean.getGoodstype());
            height = layout_more.getHeight();
        }

        private void addClickListener(View.OnClickListener listener) {
            ViewGroup.LayoutParams layoutParams = layout_more.getLayoutParams();
            final int mHeight = layoutParams.height;
            layout_profit_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });

            layout_show_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });

        }
    }

}
