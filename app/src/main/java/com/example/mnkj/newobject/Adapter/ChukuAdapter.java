package com.example.mnkj.newobject.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mnkj.newobject.Activity.ChuKuActivity;
import com.example.mnkj.newobject.Bean.ScanOutputNetworkBean;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.HiddenAnimUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mnkj on 2017/9/12.
 */
//出库
public class ChukuAdapter extends RecyclerView.Adapter {

    private List<ScanOutputNetworkBean> mDatas = new LinkedList<>();
    private Context mContext;
    private ChuKuActivity.CallBack callBack;
    private static int mHeight = 0;

    public ChukuAdapter(Context context, List<ScanOutputNetworkBean> mDatas, ChuKuActivity.CallBack callBack) {
        this.mDatas = mDatas;
        mContext = context;
        this.callBack = callBack;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chuku_item, parent, false);
        return new ChukuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ScanOutputNetworkBean bean = mDatas.get(position);
        ((ChukuAdapter.ViewHolder) holder).bindView(bean);
        ((ChukuAdapter.ViewHolder) holder).addClickListener(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    callBack.onItemClicked(position);
                }
            }
        });


    }

    //添加
    public void addItem(ScanOutputNetworkBean bean) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        //追溯码唯一判断
        boolean isRepeat = false;
        int repeatPosition = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (bean.getDataList().get(0).getFSm1().equals(mDatas.get(i).getDataList().get(0).getFSm1())) {
                isRepeat = true;
                repeatPosition = i + 1;
                break;
            }
        }
        if (!isRepeat) {
            bean.getDataList().get(0).setBeChecked(false);
            bean.getDataList().get(0).setBemodfiyed(false);
            this.mDatas.add(0, bean);
            notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "输入的追溯码与第" + repeatPosition + "条重复", Toast.LENGTH_SHORT).show();
        }
        judgeHint();
    }

    public List<ScanOutputNetworkBean> getmDatas() {
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
        for (ScanOutputNetworkBean bean : mDatas) {
            if (!bean.getDataList().get(0).getBemodfiyed()) {
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
        TextView tv_qiye_name;
        TextView tv_normal_name;
        CheckBox cb;
        TextView tv_zhuisuma;
        TextView tv_wenhao;
        TextView tv_producte_riqi;
        TextView tv_producte_pihao;
        TextView tv_unit;
        TextView tv_buy_price;
        TextView tv_sale_price;
        TextView tv_count;
        TextView tv_youxiao_riqi;


        TextView tv_splx;//商品类型
        TextView tv_dljg;//代理机构
        TextView tv_spmc;//商品名称
        TextView tv_gg;//规格
        TextView tv_jezj;//金额总计
        TextView tv_cfqy;//存放区域
        TextView tv_cchjyq;//存储环境要求
        TextView tv_gys_name;//存储环境
        TextView tv_nm;//内码
        View layout_more;
        View layout_show_more;
        View layout_ruku_item;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_qiye_name = itemView.findViewById(R.id.tv_qiye_name);
            tv_normal_name = itemView.findViewById(R.id.tv_normal_name);
            cb = itemView.findViewById(R.id.cb);
            tv_zhuisuma = itemView.findViewById(R.id.tv_zhuisuma);
            tv_producte_riqi = itemView.findViewById(R.id.tv_producte_riqi);
            tv_producte_pihao = itemView.findViewById(R.id.tv_producte_pihao);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_buy_price = itemView.findViewById(R.id.tv_buy_price);
            tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
            tv_youxiao_riqi = itemView.findViewById(R.id.tv_youxiao_riqi);
            tv_count = itemView.findViewById(R.id.tv_count);
            layout_more = itemView.findViewById(R.id.layout_more);
            layout_show_more = itemView.findViewById(R.id.layout_show_more);
            tv_wenhao = itemView.findViewById(R.id.tv_wenhao);
            layout_ruku_item = itemView.findViewById(R.id.layout_ruku_item);
            tv_splx = itemView.findViewById(R.id.tv_splx);
            tv_dljg = itemView.findViewById(R.id.tv_dljg);
            tv_spmc = itemView.findViewById(R.id.tv_spmc);
            tv_gg = itemView.findViewById(R.id.tv_gg);
            tv_jezj = itemView.findViewById(R.id.tv_jezj);
            tv_cfqy = itemView.findViewById(R.id.tv_cfqy);
            tv_cchjyq = itemView.findViewById(R.id.tv_cchjyq);
            tv_gys_name = itemView.findViewById(R.id.tv_gys_name);
            tv_nm = itemView.findViewById(R.id.tv_nm);
            cb = itemView.findViewById(R.id.cb);
        }

        private void bindView(final ScanOutputNetworkBean bean) {
            if (bean == null) return;
            tv_zhuisuma.setText(bean.getDataList().get(0).getFSm1());
            tv_normal_name.setText(bean.getDataList().get(0).getFTyName());
            tv_wenhao.setText(bean.getDataList().get(0).getFPzwh());
            tv_qiye_name.setText(bean.getDataList().get(0).getFProductEnterprise());

            tv_producte_riqi.setText(bean.getDataList().get(0).getFScDate());
            tv_youxiao_riqi.setText(bean.getDataList().get(0).getFYxqDate());

            tv_producte_pihao.setText(bean.getDataList().get(0).getFScph());
            tv_unit.setText(bean.getDataList().get(0).getFDw());

            tv_sale_price.setText(bean.getDataList().get(0).getFHdjg());
            tv_count.setText(bean.getDataList().get(0).getFBuyNum());

            tv_splx.setText(bean.getDataList().get(0).getYplx());
            tv_dljg.setText(bean.getDataList().get(0).getFDljg());// 代理机构
            tv_gys_name.setText(bean.getDataList().get(0).getFGysmc());
            tv_spmc.setText(bean.getDataList().get(0).getFProductName());
            tv_gg.setText(bean.getDataList().get(0).getFGuige());
            tv_jezj.setText(bean.getDataList().get(0).getFJesum());//金额总计

            tv_nm.setText(bean.getDataList().get(0).getFNmcode());//内码
            tv_cchjyq.setText(bean.getDataList().get(0).getFStoreHj());//存储环境要求


            if (!bean.getDataList().get(0).getBemodfiyed()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_ruku_item.setBackgroundResource(R.drawable.layout_unmodfiy);
                }
            } else {
                layout_ruku_item.setBackgroundResource(R.drawable.btn_selector);
            }
            cb.setChecked(bean.getDataList().get(0).getBeChecked());

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bean.getDataList().get(0).setBeChecked(b);
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
                if (!mDatas.get(i).getDataList().get(0).getBeChecked()) {
                    isSelect = true;
                    break;
                }
            }
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).getDataList().get(0).setBeChecked(isSelect);
            }
        }
        notifyDataSetChanged();
    }

    public void deleteAll() {
        if (mDatas.size() != 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                ScanOutputNetworkBean bean = mDatas.get(i);
                if (bean.getDataList().get(0).getBeChecked() && mDatas.contains(bean)) {
                    mDatas.remove(bean);
                    i--;
                }
            }
            notifyDataSetChanged();
            judgeHint();
        }
    }


    //更新recyclerview中的单个数据
    public void update(ScanOutputNetworkBean bean) {
        if (mDatas.size() != 0) {
            for (ScanOutputNetworkBean bean1 : mDatas) {
                if (bean1.getDataList().get(0).getFSm1().equals(bean.getDataList().get(0).getFSm1())) {
                    mDatas.set(mDatas.indexOf(bean1), bean);
                    notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public Boolean hasCheck() {
        for (ScanOutputNetworkBean bean : mDatas) {
            if (bean.getDataList().get(0).getBeChecked()) {
                return true;
            }
        }
        return false;
    }

    public String getJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (ScanOutputNetworkBean bean : mDatas) {
            JSONObject json = new JSONObject();
            json.put("FTyName", bean.getDataList().get(0).getFTyName());//通用名称
            json.put("FDljg", bean.getDataList().get(0).getFDljg()); //代理机构
            json.put("FProductName", bean.getDataList().get(0).getFProductName()); //商品名称
            json.put("FJesum", bean.getDataList().get(0).getFJesum());//金额总计
            json.put("FGuige", bean.getDataList().get(0).getFGuige());//规格
            json.put("FYxqDate", bean.getDataList().get(0).getFYxqDate());//有效期
            json.put("FProductEnterprise", bean.getDataList().get(0).getFProductEnterprise());//企业名称
            json.put("FNmcode", bean.getDataList().get(0).getFNmcode());//内码
            json.put("FScph", bean.getDataList().get(0).getFScph());//生产批号
            json.put("FScDate", bean.getDataList().get(0).getFScDate());//生产日期
            json.put("FDw", bean.getDataList().get(0).getFDw());//单位
            json.put("Yplx", bean.getDataList().get(0).getYplx());//药品类型
            json.put("FSm1", bean.getDataList().get(0).getFSm1());//追溯码
            json.put("FPzwh", bean.getDataList().get(0).getFPzwh());//批准文号
            json.put("FBuyNum", bean.getDataList().get(0).getFBuyNum());//销售数量
            json.put("FHdjg", bean.getDataList().get(0).getFHdjg());//销售单价
            json.put("FGysmc", bean.getDataList().get(0).getFGysmc());//供应商名称
            json.put("FStoreHj", bean.getDataList().get(0).getFStoreHj());//存储环境要求

            json.put("FGlid", bean.getDataList().get(0).getFStId());//关联ID
            json.put("mytId", "1");//上传表主键
            json.put("IsUpload", "1");//上传状态
            json.put("FTid", "1");//无用

//            json.put("FGrDate", bean.getRukutime());//购入时间
//            json.put("FCodeID", bean.getFCodeID());//订单编号
//            json.put("FStatus", bean.getFStatus());//进货状态
//            json.put("mytId", bean.getMytId());//上传表主键
//            json.put("IsUpload", bean.getIsUpload());//上传状态
            jsonArray.put(json);
        }
        jsonObject.put("DataList", jsonArray);
        return jsonObject.toString();
    }
}
