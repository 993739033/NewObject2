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
//供应商新增界面
public class GYSAddAdapter extends RecyclerView.Adapter {
    private GYSBean bean;
    private Context mContext;
    private DeleteDialog deleteDialog;
    private int index = -1;//记录长按的位置
    private ProgressDialog dialog;

    public GYSBean getBean() {
        return bean;
    }

    public void setBean(GYSBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public GYSAddAdapter(Context context, final GYSBean bean) {
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
        HttpRequest.post(Contance.BASE_URL + "DelSupplier.ashx", params, new RequestCallBack<GYSBean>() {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_gys_select_item, parent, false);
        return new GYSAddAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GYSBean.DataList item = bean.getDataList().get(position);
        ((GYSAddAdapter.viewHolder) holder).bindView(item);

        ((GYSAddAdapter.viewHolder) holder).layout_gys_select_item.setOnLongClickListener(new View.OnLongClickListener() {
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
        @Bind(R.id.tv_gys_name)
        TextView tv_gys_name;
        @Bind(R.id.tv_people_name)
        TextView tv_people_name;
        @Bind(R.id.tv_phone)
        TextView tv_phone;
        @Bind(R.id.tv_sy_xkz)
        TextView tv_sy_xkz;
        @Bind(R.id.tv_gmp_zsh)
        TextView tv_gmp_zsh;
        @Bind(R.id.layout_gys_select_item)
        View layout_gys_select_item;

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(GYSBean.DataList bean) {
            tv_gys_name.setText(bean.getGysmc());
            tv_people_name.setText(bean.getLxrName());
            tv_phone.setText(bean.getLxrdh());
            tv_sy_xkz.setText(bean.getXgzsh());
            tv_gmp_zsh.setText(bean.getSyGmp());
        }

    }

}
