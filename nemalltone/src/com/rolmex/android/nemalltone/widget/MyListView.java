package com.rolmex.android.nemalltone.widget;

import com.rolmex.android.nemalltone.R;
import com.rolmex.android.nemalltone.model.ItemBean;
import com.rolmex.android.nemalltone.widget.MyGridView.HolderView;
import com.rolmex.android.nemalltone.widget.MyGridView.OnViewGroupItemClickListener;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyListView extends ViewGroup{
    private LayoutInflater mLayoutInflater;

    private int screen_width;

    private int total = 0;

    private View contentView;

    private Context mContext;

    private List<ItemBean> list;
    private OnViewGroupItemClickListener listener;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.mContext = context;

        mLayoutInflater = LayoutInflater.from(context);
        WindowManager windowManager = ((Activity)context).getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screen_width = display.getWidth()*2/3;

        
    }
    public void AddData(List<ItemBean> list) {
        this.list = list;
        if (null != list) {
            total = list.size();
        }
       
        for (int i = 0; i < total; i++) {
            View view = getView(i, contentView, null);
            this.addView(view, screen_width, ViewGroup.LayoutParams.WRAP_CONTENT);          
        }
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        int totalHeight =0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            
            int measureHeight = childView.getMeasuredHeight();
            int measureWidth = childView.getMeasuredWidth();
            
            childView.layout(l, totalHeight, measureWidth, totalHeight+measureHeight);
            totalHeight += measureHeight;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        //计算所有子空间的大小
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //设置此空间的大小
        int childCount = getChildCount();
        int total = 0;
        for(int i=0;i<childCount;i++){
            View child = this.getChildAt(i);
            total+=child.getMeasuredHeight();
        }
        setMeasuredDimension(screen_width,total);

    }
    
    private int measureWidth(int pWidthMeasureSpec){
        int result =0;
        int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);
        int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);
        
        switch(widthMode){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = widthSize;
                break;
        }
        return result;
        
    }
    private int measureHeight(int pHeightMeasureSpec){
        int result =0;
        int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
        int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);
        
        switch(heightMode){
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = heightSize;
        }
        return result;
    }
    public View getView(int position, View contentView, ViewGroup viewParent) {
        // TODO Auto-generated method stub
        HolderView holderView = null;
        if (contentView == null) {
            contentView = mLayoutInflater.inflate(R.layout.list_menu_item, null);
            holderView = new HolderView();
            holderView.item_textview = (TextView)contentView.findViewById(R.id.menu_item_textview);
            holderView.item_imagview = (ImageView)contentView.findViewById(R.id.menu_item_imageview);
            
            contentView.setTag(holderView);
        } else {
            holderView = (HolderView)contentView.getTag();
        }
        if (position < total) {
            final ItemBean bean = list.get(position);          
            holderView.item_textview.setText(bean.item_name);
            holderView.item_imagview.setImageDrawable(mContext.getResources().getDrawable(bean.item_image));
            contentView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    if(listener!=null){
                        listener.ItemClickListener(bean);
                    }
                }
            });
        }
        return contentView;
    }
    public void setOnViewGroupItemClickListener(OnViewGroupItemClickListener listener){
        this.listener = listener;
    }
    public interface OnViewGroupItemClickListener{
        void ItemClickListener(ItemBean bean);
    }
    static class HolderView {
        ImageView item_imagview;

        TextView item_textview;
    }


}
