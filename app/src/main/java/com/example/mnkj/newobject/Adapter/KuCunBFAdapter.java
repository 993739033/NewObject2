package com.example.mnkj.newobject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.mnkj.newobject.Activity.KuCunBFItemActivity;
import com.example.mnkj.newobject.Bean.KuCunBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.DateUtil;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mnkj on 2017/9/12.
 */
//库存报废
public class KuCunBFAdapter extends RecyclerView.Adapter {

    private KuCunBean bean;
    private Context mContext;
    private static int mHeight;

    public KuCunBFAdapter(Context context, KuCunBean bean) {
        this.bean = bean;
        mContext = context;
    }

    public KuCunBean getBean() {
        return bean;
    }

    public void setBean(KuCunBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    //更新recyclerview中的单个数据
    public void update(KuCunBean.DataList sbean) {
        if (this.bean.getDataList().size() != 0) {
            for (KuCunBean.DataList bean1 : bean.getDataList()) {
                if (bean1.getFSm1().equals(sbean.getFSm1())) {
                    bean.getDataList().set(bean.getDataList().indexOf(bean1), sbean);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_kucun_bf_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final KuCunBean.DataList item = this.bean.getDataList().get(position);
        ((ViewHolder) holder).bindView(item);
        ((ViewHolder) holder).addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, KuCunBFItemActivity.class);
                intent.putExtra(Contance.DATA, item);
                ((Activity) mContext).startActivityForResult(intent, Contance.RequestCode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }

    public String getJosn() throws JSONException {
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        for (KuCunBean.DataList bean1 : bean.getDataList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("GTid", bean1.getFStId());
            jsonObject.put("FCodeID", "");
            jsonObject.put("FDljg", bean1.getFDljg());
            jsonObject.put("FProductEnterprise", bean1.getFProductEnterprise());
            jsonObject.put("FTyName", bean1.getFTyName());
            jsonObject.put("FGuige", bean1.getFGuige());
            jsonObject.put("FProductName", bean1.getFProductName());
            jsonObject.put("Yplx", bean1.getYplx());
            jsonObject.put("FPzwh", bean1.getFPzwh());
            jsonObject.put("FYxqDate", DateUtil.changeFormat(bean1.getFYxqDate()));
            jsonObject.put("XHNum", bean1.getFBuyNum());
            jsonObject.put("FDw", bean1.getFDw());
            jsonObject.put("FCgdj", bean1.getFDjPrice());
            jsonObject.put("XHsum", bean1.getFJesum());
            jsonObject.put("FNmcode", bean1.getFNmcode());
            jsonObject.put("FStoreHj", bean1.getFStoreHj());
            jsonObject.put("Remark", "");
            jsonObject.put("FStatus", "");
            jsonObject.put("FGysmc", bean1.getFGysmc());
            jsonObject.put("FScph", bean1.getFScph());
            jsonObject.put("FScDate", DateUtil.changeFormat(bean1.getFScDate()));
            jsonArray.put(jsonObject);
        }
        jsonObject1.put("dataList", jsonArray);
        return jsonObject1.toString();
    }

    //检查是否完善数据
    public boolean canUpload() {
        for (KuCunBean.DataList bean1 : bean.getDataList()) {
            if (!bean1.isBeModfiy()) {
                return false;
            }
        }
        return true;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_normal_name;
        TextView tv_bfsl;
        TextView tv_yx_riqi;
        TextView tv_dj;
        TextView tv_sc_date;
        TextView tv_sp_type;
        TextView tv_spec;
        TextView tv_ssje;
        TextView tv_gys_name;
        TextView tv_qy_name;
        TextView tv_pz_number;
        TextView tv_sc_number;
        TextView tv_sp_name;
        TextView tv_nm;
        TextView tv_dljg;

        View layout_more;
        View layout_show_more;
        View layout_kucun_bf_item;
        View layout_card;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_normal_name = itemView.findViewById(R.id.tv_normal_name);
            tv_bfsl = itemView.findViewById(R.id.tv_bfsl);

            tv_yx_riqi = itemView.findViewById(R.id.tv_yx_riqi);
            tv_dj = itemView.findViewById(R.id.tv_dj);
            tv_sc_date = itemView.findViewById(R.id.tv_sc_date);
            tv_sp_type = itemView.findViewById(R.id.tv_sp_type);
            tv_spec = itemView.findViewById(R.id.tv_spec);
            tv_ssje = itemView.findViewById(R.id.tv_ssje);
            tv_gys_name = itemView.findViewById(R.id.tv_gys_name);
            tv_qy_name = itemView.findViewById(R.id.tv_qy_name);
            tv_pz_number = itemView.findViewById(R.id.tv_pz_number);
            tv_sc_number = itemView.findViewById(R.id.tv_sc_number);
            tv_sp_name = itemView.findViewById(R.id.tv_sp_name);
            tv_nm = itemView.findViewById(R.id.tv_nm);

            tv_dljg = itemView.findViewById(R.id.tv_dljg);
            layout_more = itemView.findViewById(R.id.layout_more);
            layout_show_more = itemView.findViewById(R.id.layout_show_more);
            layout_kucun_bf_item = itemView.findViewById(R.id.layout_kucun_item);
            layout_card = itemView.findViewById(R.id.layout_card);
        }

        private void bindView(final KuCunBean.DataList bean) {
            tv_normal_name.setText(bean.getFTyName());

            tv_bfsl.setText(bean.getFBuyNum());//报废数量
            tv_yx_riqi.setText(DateUtil.changeFormat(bean.getFYxqDate()));
            tv_dj.setText(bean.getFDjPrice());
            tv_sc_date.setText(DateUtil.changeFormat(bean.getFScDate()));
            tv_sp_type.setText(bean.getYplx());
            tv_spec.setText(bean.getFGuige());
            tv_ssje.setText(bean.getFJesum());//损失金额
            tv_gys_name.setText(bean.getFGysmc());
            tv_qy_name.setText(bean.getFProductEnterprise());
            tv_pz_number.setText(bean.getFPzwh());
            tv_sc_number.setText(bean.getFScph());
            tv_sp_name.setText(bean.getFProductName());
            tv_nm.setText(bean.getFNmcode());
            tv_dljg.setText(bean.getFDljg());


            if (!bean.isBeModfiy()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_kucun_bf_item.setBackgroundResource(R.drawable.layout_unmodfiy);
                }
            } else {
                layout_kucun_bf_item.setBackgroundResource(R.drawable.btn_selector);
            }

        }

        private void addClickListener(final View.OnClickListener listener) {

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

            layout_kucun_bf_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                }
            });
        }
    }
}
