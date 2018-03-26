package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.ProfitItemActivity;
import com.example.mnkj.newobject.Bean.ProfitBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//利润
public class ProfitAdapter extends RecyclerView.Adapter {
    private ProfitBean bean;
    private Context mContext;
    private static int mHeight;
    private String date1, date2;

    public ProfitBean getBean() {
        return bean;
    }

    public void setBean(ProfitBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public ProfitAdapter(Context context, ProfitBean bean) {
        this.bean = bean;
        mContext = context;
    }

    public void search(String date1, String date2) {
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2)) {
            ToastUtils.showShort(mContext, "搜索日期不能为空");
            return;
        }

        this.date1 = date1;
        this.date2 = date2;
        notifyDataSetChanged();
    }

    //获取满足条件的金额总计
    public float getTotal() throws ParseException {
        float total = 0;
        for (ProfitBean.DataList dataList : bean.getDataList()) {
            if (checkDate(DateUtil.changeFormat(dataList.getFXsDate()))) {
                total += Float.parseFloat(dataList.getFProfitSum());
            }
        }
        return total;
    }

    public boolean checkDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2)) {
            return true;
        }
        Date d1 = format.parse(date1);
        Date d2 = format.parse(date2);
        Date d = format.parse(date);
        if (d1.getTime() <= d.getTime() && d.getTime() <= d2.getTime()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_profit_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProfitBean.DataList bean = this.bean.getDataList().get(position);
        ((ViewHolder) holder).layout_more.setTag(position);
        if (((int) ((ViewHolder) holder).layout_more.getTag()) != position)
            return;
        try {
            ((ViewHolder) holder).bindView(bean);
        } catch (ParseException e) {
            ToastUtils.showShort(mContext, "搜索数据异常！");
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_sale_count)
        TextView tv_sale_count;
        @Bind(R.id.tv_sale_profit)
        TextView tv_sale_profit;
        @Bind(R.id.tv_sale_price)
        TextView tv_sale_price;
        @Bind(R.id.tv_buy_price)
        TextView tv_buy_price;
        @Bind(R.id.tv_spec)
        TextView tv_spec;
        @Bind(R.id.tv_unit)
        TextView tv_unit;
        @Bind(R.id.tv_sc_date)
        TextView tv_sc_date;
        @Bind(R.id.tv_qy_name)
        TextView tv_qy_name;
        @Bind(R.id.tv_sp_name)
        TextView tv_sp_name;
        @Bind(R.id.tv_pz_number)
        TextView tv_pz_number;
        @Bind(R.id.tv_sc_number)
        TextView tv_sc_number;
        @Bind(R.id.tv_gys_name)
        TextView tv_gys_name;
        @Bind(R.id.tv_xs_date)
        TextView tv_xs_date;

        @Bind(R.id.layout_more)
        LinearLayout layout_more;
        @Bind(R.id.layout_profit_item)
        LinearLayout layout_profit_item;
        @Bind(R.id.layout_show_more)
        View layout_show_more;
        @Bind(R.id.layout_card)
        View layout_card;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(ProfitBean.DataList bean) throws ParseException {
            tv_normal_name.setText(bean.getFTyName());
            tv_sale_count.setText(bean.getFXsNum());
            tv_sale_profit.setText(bean.getFProfitSum());
            tv_sale_price.setText(bean.getFXsdjPrice());
            tv_buy_price.setText(bean.getFJhprice());
            tv_spec.setText(bean.getFGuige());
            tv_unit.setText(bean.getFDw());
            tv_sc_date.setText(DateUtil.changeFormat(bean.getFScDate()));
            tv_qy_name.setText(bean.getFProductEnterprise());
            tv_sp_name.setText(bean.getFProductName());
            tv_pz_number.setText(bean.getFPzwh());
            tv_sc_number.setText(bean.getFScph());
            tv_gys_name.setText(bean.getFGysmc());
            tv_xs_date.setText(DateUtil.changeFormat(bean.getFXsDate()));

            addClickListener();

            if (checkDate(DateUtil.changeFormat(bean.getFXsDate()))) {
                layout_card.setVisibility(View.VISIBLE);
            } else {
                layout_card.setVisibility(View.GONE);
            }

        }

        private void addClickListener() {
            ViewGroup.LayoutParams layoutParams = layout_more.getLayoutParams();
            if (mHeight == 0) {
                mHeight = layoutParams.height;
            }
            layout_show_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });
            layout_profit_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });

        }
    }

}
