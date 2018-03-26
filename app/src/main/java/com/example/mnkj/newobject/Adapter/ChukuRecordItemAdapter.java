package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;

/**
 * Created by mnkj on 2017/9/12.
 */
//出库记录item list
public class ChukuRecordItemAdapter extends RecyclerView.Adapter {
    private ChuKuRecordBean bean = new ChuKuRecordBean();
    private Context mContext;
    private static int mHeight;

    public ChukuRecordItemAdapter(Context context, ChuKuRecordBean bean) {
        this.bean = bean;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chu_ku_record_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChuKuRecordBean.DataList bean = this.bean.getDataList().get(position);
        ((ViewHolder) holder).bindView(bean);
    }

    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ph;
        TextView tv_xs_date;
        TextView tv_buy_people;
        TextView tv_phone;

        TextView tv_qiye_name;
        TextView tv_normal_name;
        TextView tv_spec;
        TextView tv_sp_name;
        TextView tv_pz_number;
        TextView tv_sc_number;
        TextView tv_gys_name;
        TextView tv_sale_count;
        TextView tv_unit;
        TextView tv_sale_price;
        TextView tv_sp_type;
        TextView tv_nm;
        TextView tv_xs_type;

        View layout_more;
        View layout_show_more;
        View layout_chuku_record_list_item;
        View layout_card;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_ph = itemView.findViewById(R.id.tv_ph);
            tv_xs_date = itemView.findViewById(R.id.tv_xs_date);
            tv_buy_people = itemView.findViewById(R.id.tv_buy_people);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_qiye_name = itemView.findViewById(R.id.tv_qiye_name);
            tv_normal_name = itemView.findViewById(R.id.tv_normal_name);
            tv_spec = itemView.findViewById(R.id.tv_spec);
            tv_sp_name = itemView.findViewById(R.id.tv_sp_name);
            tv_pz_number = itemView.findViewById(R.id.tv_pz_number);
            tv_sc_number = itemView.findViewById(R.id.tv_sc_number);
            tv_gys_name = itemView.findViewById(R.id.tv_gys_name);
            tv_sale_count = itemView.findViewById(R.id.tv_sale_count);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
            tv_sp_type = itemView.findViewById(R.id.tv_sp_type);
            tv_nm = itemView.findViewById(R.id.tv_nm);
            layout_more = itemView.findViewById(R.id.layout_more);
            layout_show_more = itemView.findViewById(R.id.layout_show_more);
            layout_chuku_record_list_item = itemView.findViewById(R.id.layout_chuku_record_list_item);
            layout_card = itemView.findViewById(R.id.layout_card);
            tv_xs_type = itemView.findViewById(R.id.tv_xs_type);

        }

        private void bindView(ChuKuRecordBean.DataList bean) {
            tv_ph.setText(bean.getFCodeID());//票号
            tv_xs_date.setText(DateUtil.changeFormat(bean.getFXsDate()));//销售日期
            tv_buy_people.setText(bean.getFBuyName());//购买人
            tv_phone.setText(bean.getFBuyTel());
            tv_qiye_name.setText(bean.getFProductEnterprise());
            tv_normal_name.setText(bean.getFTyName());
            tv_spec.setText(bean.getFGuige());
            tv_sp_name.setText(bean.getFProductName());
            tv_pz_number.setText(bean.getFPzwh());
            tv_sc_number.setText(bean.getFScph());
            tv_gys_name.setText(bean.getFGysmc());
            tv_sale_count.setText(bean.getFXsNum());
            tv_unit.setText(bean.getFDw());
            tv_sale_price.setText(bean.getFDjprice());
            tv_sp_type.setText(bean.getYplx());
            tv_nm.setText(bean.getFNmCode());
            tv_xs_type.setText(bean.getFStatus());

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
            layout_chuku_record_list_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });
        }

    }
}
