package com.example.mnkj.newobject.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mnkj.newobject.R;
import com.example.mnkj.newobject.View.ChartMarketView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
//统计
public class Fragment_Statistics extends Fragment {
    @Bind(R.id.lineChart)
    LineChart lineChart;
    @Bind(R.id.barChart)
    BarChart barChart;
    @Bind(R.id.pieChart)
    PieChart pieChart;

    public Fragment_Statistics() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLineChart();
        initBarChart();
        initPieChart();
    }

    private void initLineChart() {
        List<ILineDataSet> lists = new ArrayList<>();
        List<Entry> entryList = new ArrayList<>();
        entryList.add(new Entry(10, 10));
        entryList.add(new Entry(20, 20));
        entryList.add(new Entry(30, 30));
        entryList.add(new Entry(40, 30));
        entryList.add(new Entry(50, 60));
        LineDataSet dataset = new LineDataSet(entryList, "A");
        lists.add(dataset);
        List<Entry> entryList1 = new ArrayList<>();
        entryList1.add(new Entry(10, 10));
        entryList1.add(new Entry(25, 20));
        entryList1.add(new Entry(30, 40));
        entryList1.add(new Entry(50, 30));
        entryList1.add(new Entry(45, 60));
        LineDataSet dataset1 = new LineDataSet(entryList1, "B");
        lists.add(dataset1);
//        dataset.setColor(Color.BLUE);
        dataset.setColors(Color.BLACK, Color.GRAY, Color.RED, Color.GREEN);
        dataset.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + "个";
            }
        });
        LineData lineData = new LineData(lists);
        lineChart.setData(lineData);
//        lineChart.setBackgroundColor(Color.GRAY);//设置背景颜色
        lineChart.setContentDescription("XXXX表");
        Description description = new Description();  // 这部分是深度定制描述文本，颜色，字体等
        description.setText("××表");
        description.setTextColor(Color.RED);
        lineChart.setDescription(description);
//        lineChart.setScaleXEnabled(false);//x轴无法缩放
        lineChart.setScaleYEnabled(false);//Y轴无法缩放

        XAxis xAxis = lineChart.getXAxis();    // 获取X轴
        YAxis yAxis = lineChart.getAxisLeft();// 获取Y轴

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//x轴的绘制位置 默认为顶部
//        yAxis.setAxisMaximum(50);//最大显示
//        yAxis.setAxisMinimum(20);//最小显示

        ChartMarketView chartMarket = new ChartMarketView(getContext(), R.layout.layout_chart_market_view);//设置点击后的popupview
        lineChart.setMarker(chartMarket);

        lineChart.invalidate();
        xAxis.setAxisLineWidth(2);
        yAxis.setAxisLineWidth(2);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "个";
            }
        });
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "个";
            }
        });
//        lineChart.animateX(3000, Easing.EasingOption.EaseInQuad);
    }

    private void initBarChart() {
        List<IBarDataSet> lists = new ArrayList<>();
        List<BarEntry> entryList = new ArrayList<>();
        entryList.add(new BarEntry(10, 10));
        entryList.add(new BarEntry(20, 20));
        entryList.add(new BarEntry(30, 30));
        entryList.add(new BarEntry(40, 30));
        entryList.add(new BarEntry(50, 60));
        BarDataSet dataset = new BarDataSet(entryList, "A bar");
        lists.add(dataset);
        List<BarEntry> entryList1 = new ArrayList<>();
        entryList1.add(new BarEntry(15, 14));
        entryList1.add(new BarEntry(21, 23));
        entryList1.add(new BarEntry(33, 20));
        entryList1.add(new BarEntry(42, 30));
        entryList1.add(new BarEntry(57, 10));
        BarDataSet dataset1 = new BarDataSet(entryList1, "B bar");
        lists.add(dataset1);

//        dataset.setColor(Color.BLUE);
        dataset.setColors(Color.BLACK, Color.GRAY, Color.RED, Color.GREEN);
        dataset.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + "个";
            }
        });
        BarData barData = new BarData(lists);
        barChart.setData(barData);
//        lineChart.setBackgroundColor(Color.GRAY);//设置背景颜色
        barChart.setContentDescription("XXXX表");
        Description description = new Description();  // 这部分是深度定制描述文本，颜色，字体等
        description.setText("××表");
        description.setTextColor(Color.RED);
        barChart.setDescription(description);

        barChart.setFitBars(true);//两端点留空白

        barChart.setVisibleXRangeMinimum(2);//设置最小缩放的范围

//        lineChart.setScaleXEnabled(false);//x轴无法缩放
        barChart.setScaleYEnabled(false);//Y轴无法缩放
//        barChart.setDrawBarShadow(true);//绘制柱形阴影 性能下降40%

        XAxis xAxis = barChart.getXAxis();    // 获取X轴
        YAxis yAxis = barChart.getAxisLeft();// 获取Y轴

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

//        yAxis.setAxisMaximum(50);//最大显示
//        yAxis.setAxisMinimum(20);//最小显示
        ChartMarketView chartMarket = new ChartMarketView(getContext(), R.layout.layout_chart_market_view);//设置点击后的popupview
        barChart.setMarker(chartMarket);

        barChart.invalidate();
        xAxis.setAxisLineWidth(2);
        yAxis.setAxisLineWidth(2);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "个";
            }
        });
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "个";
            }
        });

    }

    private void initPieChart() {
        List<PieEntry> entryList = new ArrayList<>();
        entryList.add(new PieEntry(10, 10));
        entryList.add(new PieEntry(20, 20));
        entryList.add(new PieEntry(30, 30));
        entryList.add(new PieEntry(15, 15));
        PieDataSet dataset = new PieDataSet(entryList, "A Pie");

//        dataset.setColor(Color.BLUE);
        dataset.setColors(Color.BLUE, Color.GRAY, Color.RED, Color.GREEN, Color.CYAN);
        dataset.setValueTextColor(Color.WHITE);
        dataset.setSliceSpace(5);//块的间隔
        dataset.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + "个";
            }
        });
        PieData barData = new PieData(dataset);
        pieChart.setData(barData);
//        lineChart.setBackgroundColor(Color.GRAY);//设置背景颜色
        pieChart.setContentDescription("XXXX表");
        Description description = new Description();  // 这部分是深度定制描述文本，颜色，字体等
        description.setText("××表");
        description.setTextColor(Color.RED);
        pieChart.setDescription(description);
        pieChart.setCenterText("销售统计");

        ChartMarketView chartMarket = new ChartMarketView(getContext(), R.layout.layout_chart_market_view);//设置点击后的popupview
        pieChart.setMarker(chartMarket);

        pieChart.invalidate();
    }

    //显示时的动画效果
    public void animate() {
        lineChart.animateY(3000, Easing.EasingOption.EaseInOutQuart);
        barChart.animateY(3000, Easing.EasingOption.EaseOutCubic);
        pieChart.animateY(3000, Easing.EasingOption.EaseOutCubic);
    }


}
