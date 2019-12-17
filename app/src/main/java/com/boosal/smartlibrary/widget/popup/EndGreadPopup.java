package com.boosal.smartlibrary.widget.popup;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.boosal.smartlibrary.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by boosal on 2019/9/6.
 */

public class EndGreadPopup extends BasePopupWindow implements View.OnClickListener{

    private TextView tipTv;

    PieChart pieChart;
    protected void onCreate() {


        pieChart = (PieChart) findViewById(R.id.pieChart);

        pieChart.setUsePercentValues(true);//使用百分比
        pieChart.getDescription().setEnabled(false);//是否使用图标描述
        pieChart.setExtraOffsets(1,1,1,1);//设置上下左右边距
        pieChart.setDragDecelerationFrictionCoef(0.9f);//设置饼图转动的摩擦系数
        pieChart.animateY(1000, Easing.EaseInBack);


        //添加饼图的类别，value为数量，饼图会根据value的大小自动分配占比
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(10f,"可回收垃圾"));
        yValues.add(new PieEntry(20f,"有害垃圾"));
        yValues.add(new PieEntry(20f,"厨余垃圾"));
        yValues.add(new PieEntry(20f,"其他垃圾"));
;


        PieDataSet dataSet = new PieDataSet(yValues,"Countries");
        dataSet.setSliceSpace(3f);//设置饼图之间的间隙
        dataSet.setSelectionShift(5f);//设置饼图被选中时的距离变化


        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);//设置饼图每一项的颜色

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);//设置饼图上字体大小
        data.setValueTextColor(Color.YELLOW);;//设置饼图上字体颜色

        pieChart.setData(data);//为图标添加数据

    }
    

    public EndGreadPopup(Context context) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        onCreate();
        bindEvent();
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.endtest);
    }

    private void bindEvent() {
        tipTv = findViewById(R.id.tv_delete_book);

//        findViewById(R.id.lin_cancel).setOnClickListener(this);
        findViewById(R.id.lin_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_confirm:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setComm(String gread){
        String tip = "本次测试结果："+gread;

        tipTv.setText(tip);

    }
}
