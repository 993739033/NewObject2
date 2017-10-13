package com.example.mnkj.newobject.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by mnkj on 2017/9/7.
 */

public class ImageViewHolder implements Holder<Integer> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }

}
