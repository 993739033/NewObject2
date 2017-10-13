package com.example.mnkj.newobject.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mnkj.newobject.Activity.AccountListActivity;
import com.example.mnkj.newobject.Activity.GYSSelectActivity;
import com.example.mnkj.newobject.Bean.GYSBean;
import com.example.mnkj.newobject.Bean.KHBean;
import com.example.mnkj.newobject.Contance;
import com.example.mnkj.newobject.Net.RequestCallBack;
import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.Utils.SPUtils;
import com.example.mnkj.newobject.Utils.ToastUtils;
import com.example.mnkj.newobject.View.DeleteDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by mnkj on 2017/9/27.
 */
//客户新增界面
public class KeHuAddAdapter extends RecyclerView.Adapter {
    private KHBean bean;
    private Context mContext;
    private DeleteDialog deleteDialog;
    private int index = -1;//记录长按的位置
    private ProgressDialog dialog;

    public KHBean getBean() {
        return bean;
    }

    public void setBean(KHBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public KeHuAddAdapter(Context context, final KHBean bean) {
        mContext = context;
        this.bean = bean;
        dialog = new ProgressDialog(context);
        dialog.setMessage("删除中请稍后。。");

        deleteDialog = new DeleteDialog(context, R.style.MyDialog);
        deleteDialog.setCallBack(new DeleteDialog.CallBack() {
            @Override
            public void enter() {
                String FStId = getBean().getDataList().get(index).getFStId();
                requestDel(FStId);
                deleteDialog.cancel();
            }

            @Override
            public void cancel() {
                deleteDialog.cancel();
            }
        });
    }

    //移除单个数据
    private void removeItem(int index) {
        bean.getDataList().remove(index);
        notifyDataSetChanged();
    }

    //请求删除
    private void requestDel(String FStId) {
        dialog.show();
        RequestParams params = new RequestParams();
        params.addFormDataPart("USERID", SPUtils.getInstance().getData(Contance.USERID, "", String.class));
        params.addFormDataPart("FStId", FStId);
        HttpRequest.post(Contance.BASE_URL + "DelFBuyPerson.ashx", params, new RequestCallBack<GYSBean>() {
            @Override
            public void onFailure(Exception e) {
                dialog.cancel();
                ToastUtils.showShort(mContext, "数据删除失败");
            }

            @Override
            public void getData(GYSBean bean) {
                removeItem(index);
                dialog.cancel();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_kh_select_item, parent, false);
        return new KeHuAddAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final KHBean.DataList item = bean.getDataList().get(position);
        ((KeHuAddAdapter.viewHolder) holder).bindView(item);

        ((viewHolder) holder).layout_kh_select_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                index = position;
                deleteDialog.show();
                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return bean.getDataList() == null ? 0 : bean.getDataList().size();
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_gmr_name;
        TextView tv_gmr_sj;
        TextView tv_gmr_dz;
        TextView tv_yzc;
        TextView tv_yzcd;
        View layout_kh_select_item;

        public viewHolder(View itemView) {
            super(itemView);
            tv_gmr_name=itemView.findViewById(R.id.tv_gmr_name);
            tv_gmr_sj=itemView.findViewById(R.id.tv_gmr_sj);
            tv_gmr_dz=itemView.findViewById(R.id.tv_gmr_dz);
            tv_yzc=itemView.findViewById(R.id.tv_yzc);
            tv_yzcd=itemView.findViewById(R.id.tv_yzcd);
            layout_kh_select_item=itemView.findViewById(R.id.layout_kh_select_item);
        }

        public void bindView(KHBean.DataList bean) {
            tv_gmr_name.setText(bean.getFBuyName());
            tv_gmr_sj.setText(bean.getFBuyTel());
            tv_gmr_dz.setText(bean.getFBuyAddress());
            tv_yzc.setText(bean.getFGmyzc());
            tv_yzcd.setText(bean.getFGmyzcd());
        }

    }

}
