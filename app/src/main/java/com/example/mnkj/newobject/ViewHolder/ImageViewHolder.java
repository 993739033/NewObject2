package com.example.mnkj.newobject.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

/**
 * Created by mnkj on 2017/9/7.
 */

public class ImageViewHolder implements Holder<String> {
    private ImageView imageView;
    private Context mContext;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mContext = context;
        return imageView;
    }
    @Override
    public void UpdateUI(Context context, int position, String data) {
//        imageView.setImageResource(data);
        Glide.with(mContext).load(data).into(imageView);

    }


}
