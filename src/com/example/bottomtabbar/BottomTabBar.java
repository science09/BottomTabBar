package com.example.bottomtabbar;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class BottomTabBar extends LinearLayout implements View.OnClickListener,
		OnPageChangeListener {

	private Context context;
	private ViewPager mViewPager;
	private ImageView mIndicater;  //指示器tV
	private ImageView mLine;
	private List<TextView> mList;
	private int currIndex = 0;   // 当前显示的index
	private int positions[];     // 存储移动位置
	private int titleCount;      // 标题数量
	private static final int HEIGHT_NAVIGATION_BAR = 45; // 导航栏默认高度，不包括指示器高度，单位是dp
	private static final int HEIGHT_INDICATOR = 3; // 指示器默认高度，单位是dp
	private int darkGreen = android.R.color.black;

	public BottomTabBar(Context context) {
		super(context);
		this.context = context;
	}

	@SuppressLint("InlinedApi")
	public void attachToParent(ViewGroup viewGroup, String[] titles,
			ViewPager viewPager) {
		this.mViewPager = viewPager;
		titleCount = titles.length;
		// 初始化主布局
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				dip2px(HEIGHT_INDICATOR+HEIGHT_NAVIGATION_BAR)));
		setBackgroundColor(getResources().getColor(android.R.color.transparent));
		setOrientation(VERTICAL);	
		mLine = new ImageView(context);
		mLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(1)));
		mLine.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
		this.addView(mLine);
		// 指示器布局  
        LinearLayout ll_indicator = new LinearLayout(context);  
        ll_indicator.setLayoutParams(new LayoutParams(  
                LayoutParams.MATCH_PARENT, dip2px(HEIGHT_INDICATOR)));  
        ll_indicator.setBackgroundColor(getResources().getColor(
                android.R.color.holo_blue_light));  
        ll_indicator.setOrientation(HORIZONTAL);
        // 指示器 
        mIndicater = new ImageView(context);  
        mIndicater.setImageResource(android.R.color.holo_red_dark);  
        mIndicater.setScaleType(ScaleType.MATRIX);  
        mIndicater.setLayoutParams(new LinearLayout.LayoutParams(  
                        getScreenWidth(context) / titleCount,  
                        dip2px(HEIGHT_INDICATOR)));  
        ll_indicator.addView(mIndicater);  
        // 添加指示器  
        this.addView(ll_indicator);
		
		// 导航栏布局  
		LinearLayout ll_bottom = new LinearLayout(context);
		ll_bottom.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				dip2px(HEIGHT_NAVIGATION_BAR)));
		ll_bottom.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
		ll_bottom.setOrientation(HORIZONTAL);
		// 生成导航按钮(TextView)  
		mList = new ArrayList<TextView>();
        for (int i = 0; i < titles.length; i++) {   	
            TextView textView = new TextView(context);  
            textView.setLayoutParams(new LayoutParams(0,  
                    dip2px(HEIGHT_NAVIGATION_BAR), 1));  
            textView.setText(titles[i]);  
            textView.setGravity(Gravity.CENTER);  
            textView.setTextSize(20);  
            textView.setTextColor(getResources().getColor(android.R.color.white));  
            textView.setId(i);  
        	textView.setOnClickListener(this);  
            ll_bottom.addView(textView); 
            mList.add(textView);
        }  
        // 添加导航  
        this.addView(ll_bottom); 
       
        viewGroup.addView(this);  
        viewPager.setOnPageChangeListener(this);  
  
        // 初始化移动位置  
        positions = new int[titleCount];  
        positions[0] = 0; 
        mList.get(positions[0]).setTextColor(getResources().getColor(darkGreen));
        int distance = (int) (getScreenWidth(context) / titleCount);  
        for (int i = 1; i < titleCount; i++) {  
            positions[i] = distance * i;  
        }  
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		 Animation animation = new TranslateAnimation(currIndex * positions[1],  
	                positions[position], 0, 0);  
	        currIndex = position;  
	        animation.setFillAfter(true);  
	        animation.setDuration(300);  
	        mIndicater.startAnimation(animation);  
	        for(int i = 0; i < mList.size() ; i++){
	        	mList.get(i).setTextColor(getResources().getColor(android.R.color.white));
	        }
	        mList.get(position).setTextColor(getResources().getColor(darkGreen));
	}

	@Override
	public void onClick(View v) {
		mViewPager.setCurrentItem(v.getId()); 
	}
	
	// 获取屏幕宽度
	private int getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
	
	private int dip2px(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
