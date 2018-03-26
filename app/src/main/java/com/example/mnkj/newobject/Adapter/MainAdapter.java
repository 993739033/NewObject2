package com.example.mnkj.newobject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.mnkj.newobject.Activity.ChuKuActivity;
import com.example.mnkj.newobject.Activity.ChuKuRecordActivity;
import com.example.mnkj.newobject.Activity.JHTHActivity;
import com.example.mnkj.newobject.Activity.JHTHRecordActivity;
import com.example.mnkj.newobject.Activity.KuCunActivity;
import com.example.mnkj.newobject.Activity.ProfitActivity;
import com.example.mnkj.newobject.Activity.RuKuActivity;
import com.example.mnkj.newobject.Activity.RuKuRecordActivity;
import com.example.mnkj.newobject.Activity.SendMsgActivity;
import com.example.mnkj.newobject.Activity.WebActivity;
import com.example.mnkj.newobject.Bean.ImgBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.ViewHolder.ImageViewHolder;

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
    private static String[] imglist1 = new String[]{"http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg",
            "http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%A6%B9%E5%AD%90%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1572043903,1277450713&os=2675791561,4080459584&simid=3513907018,431268974&pn=377&rn=1&di=13230275410&ln=3910&fr=&fmq=1509098526816_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=14a&hs=2&objurl=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F56e8b5a4a50b1.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1509098563018^3_1588X783%1", "http://img2.3lian.com/2014/f2/37/d/40.jpg"};//轮播图片
    private static final int TYPE_TOP = 1;
    private static final int TYPE_BOTTOM = 2;
    private static Context mContext;

    public MainAdapter(List<ImgBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
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

    static class TopVH extends RecyclerView.ViewHolder implements OnItemClickListener {
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
        //进退货开单
        @Bind(R.id.iv_icon_7)
        LinearLayout iv_icon_7;
        //进退货开单明细
        @Bind(R.id.iv_icon_8)
        LinearLayout iv_icon_8;
        //发短信
        @Bind(R.id.iv_icon_9)
        LinearLayout iv_icon_9;


        public TopVH(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            banner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new ImageViewHolder();
                }
            }, Arrays.asList(imglist1)).setPageIndicator(new int[]{R.drawable.dian1, R.drawable.dian2})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
            banner.startTurning(2000);//轮播时间
            initListener();
        }

        private void initListener() {
            banner.setOnItemClickListener(this);

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
            iv_icon_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), JHTHActivity.class);
                    view.getContext().startActivity(intent);
                }
            });

            iv_icon_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), JHTHRecordActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
            iv_icon_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), SendMsgActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public void onItemClick(int position) {
            String url = imglist1[position];
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra(Contance.DATA, url);
            ((Activity) mContext).startActivity(intent);
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
