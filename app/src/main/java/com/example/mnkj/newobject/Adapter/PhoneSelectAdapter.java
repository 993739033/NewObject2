package com.example.mnkj.newobject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Bean.ContactBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import java.util.List;

/**
 * @author: xp
 * @date: 2017/7/19
 */
//电话选择  返回ContanctBean
public class PhoneSelectAdapter extends RecyclerView.Adapter<PhoneSelectAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<ContactBean> mData;
    private Context mContext;

    public PhoneSelectAdapter(Context context, List<ContactBean> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        this.mContext = context;
    }

    @Override
    public PhoneSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvTag = (TextView) view.findViewById(R.id.tag);
        viewHolder.tvName = (TextView) view.findViewById(R.id.name);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PhoneSelectAdapter.ViewHolder holder, final int position) {
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.tvTag.setVisibility(View.VISIBLE);
            holder.tvTag.setText(mData.get(position).getLetters());
        } else {
            holder.tvTag.setVisibility(View.GONE);
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }

        holder.tvName.setText(this.mData.get(position).toString());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, mData.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = ((Activity) mContext).getIntent();
                intent.putExtra(Contance.DATA, mData.get(position));
                ((Activity) mContext).setResult(Contance.ResultCode, intent);
                ((Activity) mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTag, tvName;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给Activity刷新数据
     *
     * @param list
     */
    public void updateList(List<ContactBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
