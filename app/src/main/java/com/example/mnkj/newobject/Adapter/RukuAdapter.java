package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Activity.RuKuActivity;
import com.example.mnkj.newobject.Bean.ScanInputBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/12.
 */
//入库
public class RukuAdapter extends RecyclerView.Adapter {


    private List<ScanInputBean> mDatas = new LinkedList<>();
    private Context mContext;
    private RuKuActivity.CallBack callBack;
    private static int mHeight = 0;

    public RukuAdapter(Context context, List<ScanInputBean> mDatas, RuKuActivity.CallBack callBack) {
        this.mDatas = mDatas;
        mContext = context;
        this.callBack = callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_ruku_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ScanInputBean bean = mDatas.get(position);
        ((ViewHolder) holder).bindView(bean);
        ((ViewHolder) holder).addClickListener(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onItemClicked(position);
                }
            }
        });


    }

    //添加
    public void addItem(ScanInputBean bean) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        //追溯码唯一判断
        boolean isRepeat = false;
        int repeatPosition = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (bean.getZhuiSuMa().equals(mDatas.get(i).getZhuiSuMa())) {
                isRepeat = true;
                repeatPosition = i + 1;
                break;
            }
        }
        if (!isRepeat) {
            this.mDatas.add(0, bean);
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "输入的追溯码与第" + repeatPosition + "条重复", Toast.LENGTH_SHORT).show();
        }
        judgeHint();
    }

    public List<ScanInputBean> getmDatas() {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    public void clear() {
        this.mDatas.clear();
        notifyDataSetChanged();
        judgeHint();
    }

    public void deleteItem(int position) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        mDatas.remove(position);
        notifyDataSetChanged();
        judgeHint();
    }

    //检查所有数据是否可以上传
    public Boolean checkItem() {
        for (ScanInputBean bean : mDatas) {
            if (!bean.isBemodfiyed()) {
                return false;
            }
        }
        return true;
    }

    //判断是否显示或隐藏提示
    private void judgeHint() {
        if (mDatas.size() == 0) {
            callBack.showHint();
        } else {
            callBack.hideHint();
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_qiye_name;//生产企业名称
        TextView tv_normal_name;//通用名称
        TextView tv_zhuisuma;//追溯码
        TextView tv_wenhao;//批准文号
        TextView tv_producte_riqi;//生产日期
        TextView tv_producte_pihao;//生产批号
        TextView tv_unit;//单位
        TextView tv_sale_price;//销售单价
        TextView tv_count;//销售数量
        TextView tv_youxiao_riqi;//有效期


        TextView tv_splx;//商品类型\
        TextView tv_dljg;//代理机构\
        TextView tv_gys_name;//供应商名称\
        TextView tv_gg;//规格\
        TextView tv_jezj;//金额总计\
        TextView tv_nm;//内码\
        TextView tv_spmc;//商品名称\
        TextView tv_cchjyq;//存储环境要求\
        TextView tv_cfqy;//存放区域\
        TextView tv_cchj;//存放环境\
        TextView tv_buy_price;//采购价格\
        View layout_more;
        View layout_show_more;
        View layout_ruku_item;

        CheckBox cb;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_qiye_name = itemView.findViewById(R.id.tv_qiye_name);
            tv_normal_name = itemView.findViewById(R.id.tv_normal_name);
            tv_zhuisuma = itemView.findViewById(R.id.tv_zhuisuma);
            tv_producte_riqi = itemView.findViewById(R.id.tv_producte_riqi);
            tv_producte_pihao = itemView.findViewById(R.id.tv_producte_pihao);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
            tv_youxiao_riqi = itemView.findViewById(R.id.tv_youxiao_riqi);
            tv_count = itemView.findViewById(R.id.tv_count);
            layout_more = itemView.findViewById(R.id.layout_more);
            layout_show_more = itemView.findViewById(R.id.layout_show_more);
            tv_wenhao = itemView.findViewById(R.id.tv_wenhao);
            layout_ruku_item = itemView.findViewById(R.id.layout_ruku_item);
            tv_splx = itemView.findViewById(R.id.tv_splx);
            tv_dljg = itemView.findViewById(R.id.tv_dljg);
            tv_gg = itemView.findViewById(R.id.tv_gg);
            tv_jezj = itemView.findViewById(R.id.tv_jezj);
            tv_buy_price = itemView.findViewById(R.id.tv_buy_price);
            tv_spmc = itemView.findViewById(R.id.tv_spmc);
            tv_cfqy = itemView.findViewById(R.id.tv_cfqy);
            tv_cchj = itemView.findViewById(R.id.tv_cchj);
            tv_nm = itemView.findViewById(R.id.tv_nm);
            cb = itemView.findViewById(R.id.cb);
        }

        private void bindView(final ScanInputBean bean) {
            tv_zhuisuma.setText(bean.getZhuiSuMa());
            tv_normal_name.setText(bean.getTongYongMing());
            tv_wenhao.setText(bean.getWenHao());
            tv_qiye_name.setText(bean.getName());
            tv_producte_riqi.setText(bean.getProductionDate());
            tv_producte_riqi.setText(bean.getProductionDate());
            tv_youxiao_riqi.setText(bean.getExpiryDate());
            tv_producte_pihao.setText(bean.getProductNumber());
            tv_unit.setText(bean.getUnit());
            tv_sale_price.setText(bean.getPresellPrice());
            tv_count.setText(bean.getCount());

            tv_splx.setText(bean.getYplxname());
            tv_dljg.setText(bean.getFDljg());
            tv_gg.setText(bean.getSpecification());
            tv_jezj.setText(bean.getFJesum());

            tv_buy_price.setText(bean.getPurchasePrice());//采购价格
            tv_spmc.setText(bean.getCpname());//商品名称
            tv_cfqy.setText(bean.getYpArea());//存放区域
            tv_cchj.setText(bean.getFStoreHj());//存放环境
            tv_nm.setText(bean.getNm());//内码

            cb.setChecked(bean.getBechecked());

            if (!bean.isBemodfiyed()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_ruku_item.setBackgroundResource(R.drawable.layout_unmodfiy);
                }
            } else {
                layout_ruku_item.setBackgroundResource(R.drawable.btn_selector);
            }
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bean.setBechecked(b);
                }
            });


        }

        private void addClickListener(final Context mContext, View.OnClickListener listener) {
            layout_ruku_item.setOnClickListener(listener);
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
        }
    }

    public void selectAll() {
        if (mDatas != null && mDatas.size() > 0) {
            boolean isSelect = false;
            for (int i = 0; i < mDatas.size(); i++) {
                //如果所有的都已经是true 那么之后就设置false
                if (!mDatas.get(i).getBechecked()) {
                    isSelect = true;
                    break;
                }
            }
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).setBechecked(isSelect);
            }
        }
        notifyDataSetChanged();
    }

    public void deleteAll() {
        if (mDatas.size() != 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                ScanInputBean bean = mDatas.get(i);
                if (bean.getBechecked() && mDatas.contains(bean)) {
                    mDatas.remove(bean);
                    i--;
                }
            }
            notifyDataSetChanged();
            judgeHint();
        }
    }


    //更新recyclerview中的单个数据
    public void update(ScanInputBean bean) {
        if (mDatas.size() != 0) {
            for (ScanInputBean bean1 : mDatas) {
                if (bean1.getZhuiSuMa().equals(bean.getZhuiSuMa())) {
                    mDatas.set(mDatas.indexOf(bean1), bean);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public Boolean hasCheck() {
        for (ScanInputBean bean : mDatas) {
            if (bean.getBechecked()) {
                return true;
            }
        }
        return false;
    }

    public String getJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String jbr = SPUtils.getInstance().getData(Contance.USERNAME, "", String.class);
        for (ScanInputBean bean : mDatas) {
            JSONObject json = new JSONObject();
            json.put("FProductName", bean.getCpname());
            json.put("FDljg", bean.getFDljg());
            json.put("FJesum", bean.getFJesum());
            json.put("FStoreHj", bean.getFStoreHj());
            json.put("FGuige", bean.getSpecification());
            json.put("YpArea", bean.getYpArea());
            json.put("FBuyNum", bean.getCount());
            json.put("FYxqDate", bean.getExpiryDate());
            json.put("FProductEnterprise", bean.getName());
            json.put("FNmcode", bean.getNm());
            json.put("FLxrdh", bean.getPhone());
            json.put("FHdjg", bean.getPresellPrice());
            json.put("FHdjg", bean.getPresellPrice());
            json.put("FScph", bean.getProductNumber());
            json.put("FScDate", bean.getProductionDate());
            json.put("FCgdj", bean.getPurchasePrice());
            json.put("FTyName", bean.getTongYongMing());
            json.put("FDw", bean.getUnit());
            json.put("Yplx", bean.getYplxname());
            json.put("FSm1", bean.getZhuiSuMa());
            json.put("FGrDate", bean.getRukutime());//购入时间
            json.put("FCodeID", bean.getFCodeID());//订单编号
            json.put("FStatus", bean.getFStatus());//进货状态
            json.put("mytId", bean.getMytId());//上传表主键
            json.put("IsUpload", bean.getIsUpload());//上传状态
            json.put("FJbr", jbr);//经办人
            json.put("FPzwh", bean.getWenHao());//批准文号
            json.put("FTid", "0");//无用
            jsonArray.put(json);
        }
        jsonObject.put("DataList", jsonArray);
        return jsonObject.toString();
    }
}
