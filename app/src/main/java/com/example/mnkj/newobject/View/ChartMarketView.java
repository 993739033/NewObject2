package com.example.mnkj.newobject.View;

import android.content.Context;
import android.widget.TextView;

import com.example.mnkj.newobject.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mnkj on 2017/9/19.
 */

public class ChartMarketView extends MarkerView {
    @Bind(R.id.tv_buy_count)
    TextView tv_buy_count;
    @Bind(R.id.tv_sale_count)
    TextView tv_sale_count;

    public ChartMarketView(Context context, int layoutResource) {
        super(context, layoutResource);
        ButterKnife.bind(this);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tv_buy_count.setText(Utils.formatNumber(e.getX(), 0, true));
        tv_sale_count.setText(Utils.formatNumber(e.getY(), 0, true));
        super.refreshContent(e, highlight);

    }

//    @Override
//    public MPPointF getOffset() {
//        return new MPPointF(-getWidth()/2,-getHeight());
//    }


    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        if (posX > getMeasuredWidth()) {
            return new MPPointF(-getWidth(), 0);//防止popupview出界
        }
        return super.getOffsetForDrawingAtPoint(posX, posY);
    }
}
