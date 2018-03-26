package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mnkj on 2017/9/12.
 */
//库存
public class KuCunAdapter extends RecyclerView.Adapter {

    private KuCunBean bean;
    private Context mContext;
    private static int mHeight;

    private String condition, condition1, content;

    private HashMap<String, String> totalCount = new HashMap<>();//存储通用名称相同的产品总数
    private HashMap<String, String> spcount = new HashMap<>();//存储preference设置的通用名称产品总数


    public KuCunAdapter(Context context, KuCunBean bean) {
        this.bean = bean;
        mContext = context;
    }

    public KuCunBean getBean() {
        return bean;
    }

    public void setBean(KuCunBean bean) {
        this.bean = bean;
        notifyItemAll();
    }

    public void notifyItemAll() {
        try {
            initRemindData(bean);
        } catch (Exception e) {
            ToastUtils.showShort(mContext, "库存提醒设置失败");
            e.printStackTrace();
        }
        totalCount.clear();
        spcount.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_kucun_item, parent, false);
        return new ViewHolder(view);
    }

    public void search(String condition, String condition1, String content) {
        this.condition = condition;
        this.condition1 = condition1;
        this.content = content;
        notifyItemAll();
    }

    //设置不满足提醒
    private void initRemindData(KuCunBean bean) throws Exception {
        if (bean != null) {
            for (KuCunBean.DataList dataList : bean.getDataList()) {
                //获取设置的提醒数量
                String count = SPUtils.getInstance().getData(dataList.getFTyName(), "", String.class);
                if (!TextUtils.isEmpty(count) && !spcount.containsKey(dataList.getFTyName())) {
                    spcount.put(dataList.getFTyName(), count);
                }
                if (!TextUtils.isEmpty(count)) {
                    String total;
                    if (totalCount.containsKey(dataList.getFTyName())) {
                        total = totalCount.get(dataList.getFTyName());
                    } else {
                        total = "0";
                    }
                    int totalc = Integer.parseInt(total) + Integer.parseInt(dataList.getFKcsl());
                    totalCount.put(dataList.getFTyName(), totalc + "");
                }
            }

            for (KuCunBean.DataList dataList : bean.getDataList()) {
                if (spcount.containsKey(dataList.getFTyName())) {
                    if (Integer.parseInt(totalCount.get(dataList.getFTyName())) < Integer.parseInt(spcount.get(dataList.getFTyName()))) {
                        dataList.setNotEnough(false);
                    } else {
                        dataList.setNotEnough(true);
                    }
                } else {
                    dataList.setNotEnough(true);
                }
            }
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final KuCunBean.DataList item = this.bean.getDataList().get(position);
        ((ViewHolder) holder).bindView(item);
        ((ViewHolder) holder).cb.setChecked(item.isSelected());
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ((ViewHolder) holder).cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setSelected(isChecked);
            }
        });

    }

    //获取被选择的item
    public KuCunBean getSelectBean() {
        KuCunBean sbean = new KuCunBean();
        sbean.setDataList(new ArrayList<KuCunBean.DataList>());
        for (KuCunBean.DataList dataList : this.bean.getDataList()) {
            if (dataList.isSelected()) {
                sbean.getDataList().add(dataList);
            }
        }
        return sbean;
    }

    public Boolean isNoEmpty() {
        for (KuCunBean.DataList dataList : this.bean.getDataList()) {
            if (dataList.isSelected()) {
                if (Integer.parseInt(dataList.getFKcsl()) <= 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean hasSelect() {
        for (KuCunBean.DataList dataList : bean.getDataList()) {
            if (dataList.isSelected()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }

    public void selectAll() {
        if (bean.getDataList() != null && bean.getDataList().size() > 0) {
            boolean isSelect = false;
            for (int i = 0; i < bean.getDataList().size(); i++) {
                //如果所有的都已经是true 那么之后就设置false
                if (!bean.getDataList().get(i).isSelected()) {
                    isSelect = true;
                    break;
                }
            }
            for (int i = 0; i < bean.getDataList().size(); i++) {
                bean.getDataList().get(i).setSelected(isSelect);
            }
        }
        notifyDataSetChanged();
    }

    //获取选中的通用名称
    public List<String> getSelectedName() {
        List<String> names = new ArrayList<>();
        if (bean.getDataList() != null && bean.getDataList().size() > 0) {
            for (int i = 0; i < bean.getDataList().size(); i++) {
                if (bean.getDataList().get(i).isSelected()) {
                    names.add(bean.getDataList().get(i).getFTyName());
                }
            }
        }
        return names;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_normal_name;
        TextView tv_kc_count;
        TextView tv_yx_riqi;
        TextView tv_dj;
        TextView tv_sc_date;
        TextView tv_sp_type;
        TextView tv_spec;
        TextView tv_kcje;
        TextView tv_gys_name;
        TextView tv_qy_name;
        TextView tv_pz_number;
        TextView tv_sc_number;
        TextView tv_sp_name;
        TextView tv_nm;
        TextView tv_dljg;
        TextView tv_unit;

        CheckBox cb;

        View layout_more;
        View layout_show_more;
        View layout_kucun_item;
        View layout_card;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_normal_name = itemView.findViewById(R.id.tv_normal_name);
            tv_kc_count = itemView.findViewById(R.id.tv_kc_count);

            tv_yx_riqi = itemView.findViewById(R.id.tv_yx_riqi);
            tv_dj = itemView.findViewById(R.id.tv_dj);
            tv_sc_date = itemView.findViewById(R.id.tv_sc_date);
            tv_sp_type = itemView.findViewById(R.id.tv_sp_type);
            tv_spec = itemView.findViewById(R.id.tv_spec);
            tv_kcje = itemView.findViewById(R.id.tv_ssje);
            tv_gys_name = itemView.findViewById(R.id.tv_gys_name);
            tv_qy_name = itemView.findViewById(R.id.tv_qy_name);
            tv_pz_number = itemView.findViewById(R.id.tv_pz_number);
            tv_sc_number = itemView.findViewById(R.id.tv_sc_number);
            tv_sp_name = itemView.findViewById(R.id.tv_sp_name);
            tv_nm = itemView.findViewById(R.id.tv_nm);

            tv_dljg = itemView.findViewById(R.id.tv_dljg);
            cb = itemView.findViewById(R.id.cb);
            layout_more = itemView.findViewById(R.id.layout_more);
            layout_show_more = itemView.findViewById(R.id.layout_show_more);
            layout_kucun_item = itemView.findViewById(R.id.layout_kucun_item);
            layout_card = itemView.findViewById(R.id.layout_card);

            tv_unit = itemView.findViewById(R.id.tv_unit);
        }

        private void bindView(final KuCunBean.DataList bean) {
            tv_normal_name.setText(bean.getFTyName());

            tv_kc_count.setText(bean.getFKcsl());
            tv_yx_riqi.setText(DateUtil.changeFormat(bean.getFYxqDate()));
            tv_dj.setText(bean.getFDjPrice());
            tv_sc_date.setText(DateUtil.changeFormat(bean.getFScDate()));
            tv_sp_type.setText(bean.getYplx());
            tv_spec.setText(bean.getFGuige());
            tv_kcje.setText(bean.getFSjPrice());
            tv_gys_name.setText(bean.getFGysmc());
            tv_qy_name.setText(bean.getFProductEnterprise());
            tv_pz_number.setText(bean.getFPzwh());
            tv_sc_number.setText(bean.getFScph());
            tv_sp_name.setText(bean.getFProductName());
            tv_nm.setText(bean.getFNmcode());
            tv_dljg.setText(bean.getFDljg());

            tv_unit.setText(bean.getFDw());

            cb.setChecked(bean.isSelected());

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bean.setSelected(b);
                }
            });

            if (!bean.isNotEnough()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_kucun_item.setBackgroundResource(R.drawable.layout_unmodfiy);
                }
            } else {
                layout_kucun_item.setBackgroundResource(R.drawable.btn_selector);
            }
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
                    case "库存数量":
                        if (condition1.equals(s)) {
                            if (bean.getFKcsl().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }

                        } else if (condition1.equals(s1)) {
                            if (bean.getFKcsl().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "单价":
                        if (condition1.equals(s)) {
                            if (bean.getFDjPrice().contains(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }

                        } else if (condition1.equals(s1)) {
                            if (bean.getFDjPrice().equals(content)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case "距有效期(天)":
                        try {
                            if (checkDate(content, DateUtil.changeFormat(bean.getFYxqDate()), condition1)) {
                                layout_card.setVisibility(View.VISIBLE);
                            } else {
                                layout_card.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort(mContext, "请输入整数！");
                        }
                        break;
                }
            } else {
                layout_card.setVisibility(View.VISIBLE);
            }

        }

        //检查时间是否满足搜索条件
        private boolean checkDate(String date, String date1, String condition1) throws Exception {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(date1);
            Date d1 = df.parse(DateUtil.getNowTime());
            long time = (d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
            if (condition1.equals("包括")) {
                if (Integer.parseInt(date) <= time) {
                    return false;
                }
            } else if (condition1.equals("等于")) {
                if (Integer.parseInt(date) != time) {
                    return false;
                }
            }
            return true;
        }

        private void addClickListener(View.OnClickListener listener) {

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

            layout_kucun_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HiddenAnimUtils.newInstance(mContext, layout_more, layout_show_more, mHeight).toggle();
                }
            });
        }
    }
}
