package com.boosal.smartlibrary.widget.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.boosal.smartlibrary.R;
import com.boosal.smartlibrary.net.entity.local.book.Category;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Created by boosal on 2019/9/6.
 */

public class SelectCategoryPopup extends BasePopupWindow implements View.OnClickListener{

    private TagFlowLayout flowLayout;
    private SelectCategoryListner listner;
    private Context mContext;
    private Integer mSelectedPosition = 0;
    TagAdapter<Category> tagAdapter;

    public interface SelectCategoryListner{
        void selectCategory(int position);
    }

    public SelectCategoryPopup(Context context, SelectCategoryListner listner) {
        super(context);
        setPopupGravity(Gravity.CENTER);
        this.listner = listner;
        this.mContext = context;
        initView();
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
        return createPopupById(R.layout.popup_select_category);
    }

    private void initView(){
        flowLayout = findViewById(R.id.id_flowlayout);
    }

    private void bindEvent() {
        findViewById(R.id.lin_confirm).setOnClickListener(this);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                mSelectedPosition = position;
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_confirm:
                dismiss();
                if(null != listner){
                    listner.selectCategory(mSelectedPosition);
                }
                break;
            default:
                break;
        }
    }

    public void setCategories(List<Category> categories){
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
            tagAdapter = new TagAdapter<Category>(categories) {
                @Override
                public View getView(FlowLayout parent, int position, Category o) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.item_popop_category,
                            flowLayout, false);
                    tv.setText(o.getName());
                    return tv;
                }
        };
        flowLayout.setAdapter(tagAdapter);
        tagAdapter.setSelectedList(0);
    }

    public void setmSelectedPosition(Integer mSelectedPosition) {
        tagAdapter.setSelectedList(mSelectedPosition);
    }
}
