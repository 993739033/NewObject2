package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.RuKuRecordBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/27.
 */
//客户选择
public class RuKuRecordAdapter extends RecyclerView.Adapter {
    private RuKuRecordBean bean;
    private Context mContext;
    private String condition, content, condition1;
    private static int mHeight = 0;

    public RuKuRecordBean getBean() {
        return bean;
    }

    public void setBean(RuKuRecordBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public RuKuRecordAdapter(Context context, RuKuRecordBean bean) {
        mContext = context;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_ruku_record_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (bean == null) return;
        final RuKuRecordBean.DataList item = bean.getDataList().get(position);
        ((viewHolder) holder).bindView(item, condition, content);
    }

    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }

    public void search(String condition, String condition1, String content) {
        this.condition = condition;
        this.condition1 = condition1;
        this.content = content;
        notifyDataSetChanged();
    }


    class viewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_gr_date)
        TextView tv_gr_date;
        @Bind(R.id.tv_qiye_name)
        TextView tv_qiye_name;
        @Bind(R.id.tv_normal_name)
        TextView tv_normal_name;
        @Bind(R.id.tv_spec)
        TextView tv_spec;
        @Bind(R.id.tv_sp_name)
        TextView tv_sp_name;
        @Bind(R.id.tv_pz_number)
        TextView tv_pz_number;
        @Bind(R.id.tv_gys_name)
        TextView tv_gys_name;
        @Bind(R.id.tv_sc_number)
        TextView tv_sc_number;
        @Bind(R.id.tv_sc_date)
        TextView tv_sc_date;
        @Bind(R.id.tv_buy_count)
        TextView tv_buy_count;
        @Bind(R.id.tv_unit)
        TextView tv_unit;
        @Bind(R.id.tv_buy_price)
        TextView tv_buy_price;
        @Bind(R.id.tv_hd_price)
        TextView tv_hd_price;
        @Bind(R.id.tv_cfqy)
        TextView tv_cfqy;
        @Bind(R.id.tv_qiye_nm)
        TextView tv_qiye_nm;
        @Bind(R.id.tv_sp_type)
        TextView tv_sp_type;

        @Bind(R.id.tv_dljg)
        TextView tv_dljg;
        @Bind(R.id.tv_xgzsh)
        TextView tv_xgzsh;
        @Bind(R.id.tv_lxr)
        TextView tv_lxr;
        @Bind(R.id.tv_lxrdh)
        TextView tv_lxrdh;
        @Bind(R.id.tv_yxq)
        TextView tv_yxq;
        @Bind(R.id.tv_jezj)
        TextView tv_jezj;
        @Bind(R.id.tv_cchj)
        TextView tv_cchj;

        @Bind(R.id.layout_more)
        View layout_more;
        @Bind(R.id.layout_show_more)
        View layout_show_more;
        @Bind(R.id.layout_ruku_record_item)
        View layout_ruku_record_item;
        @Bind(R.id.layout_card)
        View layout_card;


        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bindView(RuKuRecordBean.DataList bean, String condition, String content) {
            if (bean == null) return;
            String date = bean.getFGrDate().substring(0,bean.getFGrDate().indexOf(":") - 1);
            String s2[] = date.split("/");
            String date1 = s2[0] + "-" + s2[1] + "-" + s2[2];
            tv_gr_date.setText(date1);
            tv_qiye_name.setText(bean.getFProductEnterprise());
            tv_normal_name.setText(bean.getFTyName());
            tv_spec.setText(bean.getFGuige());
            tv_sp_name.setText(bean.getFProductName());
            tv_gys_name.setText(bean.getFGysmc());
            tv_sc_number.setText(bean.getFScph());
            tv_sc_date.setText(bean.getFScDate());
            tv_buy_count.setText(bean.getFBuyNum());
            tv_unit.setText(bean.getFDw());
            tv_buy_price.setText(bean.getFCgdj());
            tv_hd_price.setText(bean.getFHdjg());
            tv_cfqy.setText(bean.getFHdjg());
            tv_qiye_nm.setText(bean.getFNmcode());

            tv_dljg.setText(bean.getFDljg());
            tv_xgzsh.setText(bean.getFXgzsh());
            tv_lxr.setText(bean.getFLxr());
            tv_lxrdh.setText(bean.getFLxrdh());
            tv_yxq.setText(bean.getFYxqDate());
            tv_jezj.setText(bean.getFJesum());
            tv_cchj.setText(bean.getFStoreHj());
            tv_sp_type.setText(bean.getYplx());
            tv_pz_number.setText(bean.getFPzwh());

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
            layout_ruku_record_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });

            if (!TextUtils.isEmpty(content)) {
                String s = "包括", s1 = "等于";
                switch (condition) {
                    case "生产企业名称":
                        if (condition1.equals(s)) {
                            if (bean.getFProductEnterprise().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }

                        } else if (condition1.equals(s1)) {
                            if (bean.getFProductEnterprise().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "通用名称":
                        if (condition1.equals(s)) {
                            if (bean.getFTyName().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        } else if (condition1.equals(s1)) {
                            if (bean.getFTyName().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "规格":
                        if (condition1.equals(s)) {
                            if (bean.getFGuige().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }

                        } else if (condition1.equals(s1)) {
                            if (bean.getFGuige().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "商品名称":
                        if (condition1.equals(s)) {
                            if (bean.getFProductName().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }

                        } else if (condition1.equals(s1)) {
                            if (bean.getFProductName().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "批准文号":
                        if (condition1.equals(s)) {
                            if (bean.getFPzwh().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        } else if (condition1.equals(s1)) {
                            if (bean.getFPzwh().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "企业内码":
                        if (condition1.equals(s)) {
                            if (bean.getFNmcode().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }

                        } else if (condition1.equals(s1)) {
                            if (bean.getFNmcode().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                }
            } else {
                layout_card.setVisibility(View.VISIBLE);
            }


        }

    }
}

