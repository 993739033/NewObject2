package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.ChuKuRecordItemActivity;
import com.example.mnkj.newobject.Bean.ChuKuRecordBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//出库记录
public class ChukuRecordAdapter extends RecyclerView.Adapter {
    private ChuKuRecordBean bean;
    private Context mContext;
    private HashMap<String, List<ChuKuRecordBean.DataList>> Datas = new HashMap<>();
    private String condition, condition1, condition2;//条件
    private String content;

    public ChuKuRecordBean getBean() {
        return bean;
    }

    public void setBean(ChuKuRecordBean bean) {
        this.bean = bean;
        Datas.clear();
        notifyDataSetChanged();
    }

    public ChukuRecordAdapter(Context context, ChuKuRecordBean list) {
        this.bean = list;
        mContext = context;
    }

    public void search(String condition, String condition1, String condition2, String content) {
        this.condition = condition;
        this.condition1 = condition1;
        this.condition2 = condition2;
        this.content = content;
        Datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chuku_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ChuKuRecordBean.DataList bean = this.bean.getDataList().get(position);
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChuKuRecordItemActivity.class);
                ChuKuRecordBean bean1 = new ChuKuRecordBean();
                bean1.setDataList(Datas.get(bean.getFCodeID()));
                intent.putExtra(Contance.DATA, bean1);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_ph)
        TextView tv_ph;
        @Bind(R.id.tv_xs_date)
        TextView tv_xs_date;
        @Bind(R.id.tv_buy_people)
        TextView tv_buy_people;
        @Bind(R.id.tv_phone)
        TextView tv_phone;
        @Bind(R.id.layout_chuku_record_item)
        View layout_chuku_record_item;
        @Bind(R.id.layout_card)
        View layout_card;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(ChuKuRecordBean.DataList bean) {
            if (checkVisibility(bean)) {
                if (!Datas.containsKey(bean.getFCodeID())) {
                    layout_card.setVisibility(View.VISIBLE);
                    tv_ph.setText(bean.getFCodeID());
                    String date = bean.getFXsDate().substring(0, bean.getFXsDate().indexOf(":") - 1);
                    String s2[] = date.split("/");
                    String date1 = s2[0] + "-" + s2[1] + "-" + s2[2];
                    tv_xs_date.setText(date1);
                    tv_buy_people.setText(bean.getFBuyName());
                    tv_phone.setText(bean.getFBuyTel());
                } else {
                    layout_card.setVisibility(View.GONE);
                }
            } else {
                layout_card.setVisibility(View.GONE);
            }
            List<ChuKuRecordBean.DataList> list = Datas.get(bean.getFCodeID());
            if (list == null) {
                list = new LinkedList<>();
            }
            list.add(bean);
            Datas.put(bean.getFCodeID(), list);
        }

        private void addClickListener(View.OnClickListener listener) {
            layout_chuku_record_item.setOnClickListener(listener);
        }

        //检查是否符合搜索条件
        private Boolean checkVisibility(ChuKuRecordBean.DataList bean) {
            String s = "等于", s1 = "包括";
            if (TextUtils.isEmpty(content)) {

                return true;
            }
            if (!bean.getFStatus().equals(condition2)) {
                return false;
            }
            switch (condition) {
                case "生产企业名称":
                    if (condition1.equals(s)) {
                        if (bean.getFProductEnterprise().equals(content)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFProductEnterprise().contains(content)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    break;
                case "通用名称":
                    if (condition1.equals(s)) {
                        if (bean.getFTyName().equals(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFTyName().contains(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    }
                    break;
                case "规格":
                    if (condition1.equals(s)) {
                        if (bean.getFGuige().equals(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFGuige().contains(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    }
                    break;
                case "商品名称":
                    if (condition1.equals(s)) {
                        if (bean.getFProductName().equals(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFProductName().contains(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    }
                    break;
                case "批准文号":
                    if (condition1.equals(s)) {
                        if (bean.getFPzwh().equals(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFPzwh().contains(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    }
                    break;
                case "企业内码":
                    if (condition1.equals(s)) {
                        if (bean.getFNmCode().equals(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFNmCode().contains(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    }
                    break;
                case "供应商名称":
                    if (condition1.equals(s)) {
                        if (bean.getFGysmc().equals(content)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFGysmc().contains(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    }
                    break;
                case "购买人姓名":
                    if (condition1.equals(s)) {
                        if (bean.getFBuyName().equals(content)) {
                            return true;
                        } else {

                            return false;
                        }
                    } else if (condition1.equals(s1)) {
                        if (bean.getFBuyName().contains(content)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    break;
            }

            return true;
        }
    }


}
