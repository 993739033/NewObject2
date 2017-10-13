package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.KuCunItemActivity;
import com.example.mnkj.newobject.Activity.RuKuItemActivity;
import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.Bean.RuKuBean;
import com.example.mnkj.newobject.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//库存
public class KuCunAdapter extends RecyclerView.Adapter {
    private List<KuCunBean> list = new LinkedList<>();
    private Context mContext;

    public KuCunAdapter(Context context, List<KuCunBean> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_kucun_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final KuCunBean bean = list.get(position);
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).kucun_cb.setChecked(bean.getSelectd());
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KuCunItemActivity.class);
                mContext.startActivity(intent);
            }
        });
        ((ViewHolder) holder).kucun_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.setSelectd(isChecked);
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
                if (!list.get(i).getSelectd()) {
                    isSelect = true;
                    break;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setSelectd(isSelect);
            }
        }
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_kucun_count)
        TextView tv_kucun_count;
        @Bind(R.id.layout_kucun_item)
        View layout_kucun_item;
        @Bind(R.id.kucun_cb)
        CheckBox kucun_cb;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(KuCunBean bean) {
            tv_normal_name.setText(bean.getNormalname());
            tv_kucun_count.setText(bean.getCount());
        }

        private void addClickListener(View.OnClickListener listener) {
            layout_kucun_item.setOnClickListener(listener);
        }
    }
}
