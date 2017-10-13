package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.KuCunActivity;
import com.example.mnkj.newobject.Activity.RuKuItemActivity;
import com.example.mnkj.newobject.Activity.RuKuRecordActivity;
import com.example.mnkj.newobject.Activity.RuKuRecordItemActivity;
import com.example.mnkj.newobject.Bean.RuKuBean;
import com.example.mnkj.newobject.Bean.RuKuRecordBean;
import com.example.mnkj.newobject.R;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//入库记录
public class RukuRecordAdapter extends RecyclerView.Adapter {
    private List<RuKuRecordBean> list = new LinkedList<>();
    private Context mContext;

    public RukuRecordAdapter(Context context, List<RuKuRecordBean> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_ruku_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RuKuRecordBean bean = list.get(position);
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RuKuRecordItemActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_buy_date)
        TextView tv_buy_date;
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_gys_name)
        TextView tv_gys_name;
        @Bind(R.id.tv_jh_count)
        TextView tv_jh_count;
        @Bind(R.id.tv_price)
        TextView tv_price;
        @Bind(R.id.layout_ruku_record_item)
        View layout_ruku_record_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(RuKuRecordBean bean) {
            tv_buy_date.setText(bean.getBuydate());
            tv_normal_name.setText(bean.getNormalname());
            tv_gys_name.setText(bean.getGysname());
            tv_jh_count.setText(bean.getJhcount());
            tv_price.setText(bean.getPrice());
        }

        private void addClickListener(View.OnClickListener listener) {
            layout_ruku_record_item.setOnClickListener(listener);
        }
    }
}
