package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.ChuKuRecordItemActivity;
import com.example.mnkj.newobject.Activity.RuKuRecordItemActivity;
import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.Bean.RuKuRecordBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//出库记录
public class ChukuRecordAdapter extends RecyclerView.Adapter {
    private List<ChuKuRecordBean> list = new LinkedList<>();
    private Context mContext;

    public ChukuRecordAdapter(Context context, List<ChuKuRecordBean> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chuku_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChuKuRecordBean bean = list.get(position);
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChuKuRecordItemActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_ph)
        TextView tv_ph;
        @Bind(R.id.tv_buy_time)
        TextView tv_buy_time;
        @Bind(R.id.tv_buy_people)
        TextView tv_buy_people;
        @Bind(R.id.tv_phone)
        TextView tv_phone;
        @Bind(R.id.tv_nei_ma)
        TextView tv_nei_ma;
        @Bind(R.id.layout_chuku_record_item)
        View layout_chuku_record_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(ChuKuRecordBean bean) {
            tv_ph.setText(bean.getPiaohao());
            tv_buy_time.setText(bean.getBuytime());
            tv_buy_people.setText(bean.getBuypeople());
            tv_phone.setText(bean.getPhone());
            tv_nei_ma.setText(bean.getNeima());
        }

        private void addClickListener(View.OnClickListener listener) {
            layout_chuku_record_item.setOnClickListener(listener);
        }
    }
}
