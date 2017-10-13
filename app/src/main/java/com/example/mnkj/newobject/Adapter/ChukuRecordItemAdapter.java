package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.ChuKuRecordListItemActivity;
import com.example.mnkj.newobject.Activity.RuKuRecordItemActivity;
import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.Bean.ChuKuRecordListItemBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//出库记录item list
public class ChukuRecordItemAdapter extends RecyclerView.Adapter {
    private List<ChuKuRecordListItemBean> list = new LinkedList<>();
    private Context mContext;

    public ChukuRecordItemAdapter(Context context, List<ChuKuRecordListItemBean> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chu_ku_record_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChuKuRecordListItemBean bean = list.get(position);
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).cuku_record_item_cb.setChecked(bean.getIschecked());
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChuKuRecordListItemActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void selectAll() {
        if (list != null && list.size() > 0) {
            boolean isSelect = false;
            for (int i = 0; i < list.size(); i++) {
                //如果所有的都已经是true 那么之后就设置false
                if (!list.get(i).getIschecked()) {
                    isSelect = true;
                    break;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setIschecked(isSelect);
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tv_name;
        @Bind(R.id.tv_farm_name)
        TextView tv_farm_name;
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_sale_count)
        TextView tv_sale_count;
        @Bind(R.id.cuku_record_item_cb)
        CheckBox cuku_record_item_cb;
        @Bind(R.id.layout_chuku_record_list_item)
        View layout_chuku_record_list_item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(ChuKuRecordListItemBean bean) {
            tv_name.setText(bean.getName());
            tv_farm_name.setText(bean.getFarmname());
            tv_normal_name.setText(bean.getNormalname());
            tv_sale_count.setText(bean.getSalecount());
        }

        private void addClickListener(View.OnClickListener listener) {
            layout_chuku_record_list_item.setOnClickListener(listener);
        }
    }
}
