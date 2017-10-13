package com.example.mnkj.newobject.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.example.mnkj.newobject.Activity.ChuKuActivity;
import com.example.mnkj.newobject.Activity.ChuKuRecordActivity;
import com.example.mnkj.newobject.Activity.KuCunActivity;
import com.example.mnkj.newobject.Activity.ProfitActivity;
import com.example.mnkj.newobject.Activity.RuKuActivity;
import com.example.mnkj.newobject.Activity.RuKuRecordActivity;
import com.example.mnkj.newobject.Bean.ImgBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.ViewHolder.ImageViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/7.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private List<ImgBean> list = new LinkedList<>();
    private static Integer[] imglist = new Integer[]{R.drawable.banner, R.drawable.banner1, R.drawable.banner2};//轮播图片
    private static final int TYPE_TOP = 1;
    private static final int TYPE_BOTTOM = 2;

    public MainAdapter(List<ImgBean> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position <= 0) {
            return TYPE_TOP;
        } else {
            return TYPE_BOTTOM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_banner, parent, false);
            return new TopVH(view);
        } else if (viewType == TYPE_BOTTOM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recy, parent, false);
            return new BottomVH(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BottomVH) {
            ImgBean bean = list.get(position - 1);
            ((BottomVH) holder).bindView(bean);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    static class TopVH extends RecyclerView.ViewHolder {
        @Bind(R.id.main_banner)
        ConvenientBanner banner;
        //入库
        @Bind(R.id.iv_icon_1)
        LinearLayout iv_icon_1;
        //出库
        @Bind(R.id.iv_icon_2)
        LinearLayout iv_icon_2;
        //库存
        @Bind(R.id.iv_icon_3)
        LinearLayout iv_icon_3;
        //入库记录
        @Bind(R.id.iv_icon_4)
        LinearLayout iv_icon_4;
        //出库记录
        @Bind(R.id.iv_icon_5)
        LinearLayout iv_icon_5;
        //利润
        @Bind(R.id.iv_icon_6)
        LinearLayout iv_icon_6;

        public TopVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new ImageViewHolder();
                }
            }, Arrays.asList(imglist)).setPageIndicator(new int[]{R.drawable.dian1, R.drawable.dian2})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            banner.startTurning(2000);//轮播时间
            initListener();
        }
        private void initListener(){
            iv_icon_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RuKuActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            iv_icon_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ChuKuActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            iv_icon_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), KuCunActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            iv_icon_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RuKuRecordActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            iv_icon_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ChuKuRecordActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            iv_icon_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ProfitActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    static class BottomVH extends RecyclerView.ViewHolder {
        ImageView iv_icon;
        TextView tv_content;
        LinearLayout list_item;

        public BottomVH(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_content = itemView.findViewById(R.id.tv_content);
            list_item = itemView.findViewById(R.id.list_item);
        }

        public void bindView(ImgBean bean) {
            iv_icon.setImageResource(bean.getPath());
            tv_content.setText(bean.getContent());
        }


    }
}
