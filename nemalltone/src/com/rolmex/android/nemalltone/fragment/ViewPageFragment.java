/*
 * Copyright (C) 2012 yueyueniao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rolmex.android.nemalltone.fragment;

import com.rolmex.android.nemalltone.R;
import com.rolmex.android.nemalltone.ui.HomeActivity;
import com.rolmex.android.nemalltone.widget.JazzyViewPager;
import com.rolmex.android.nemalltone.widget.JazzyViewPager.TransitionEffect;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class ViewPageFragment extends Fragment {
    private Button showLeft;

    private Button showRight;

    private MyAdapter mAdapter;

    private JazzyViewPager mPager;

    private ArrayList<Fragment> pagerItemList = new ArrayList<Fragment>();

    private Drawable windowBackground;

    private Context context;

    private RelativeLayout head_layout;

    private HorizontalScrollView hsv;

    private FrameLayout hfl;

    private TextView slidView;

    private int width;

    private LinearLayout table_layout;

    // 主菜单，跟常用菜单
    private TextView home_view, always_view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View mView = inflater.inflate(R.layout.view_main, null);
        
        // 新姿势
        int[] attrs = {
            android.R.attr.windowBackground
        };
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground, outValue, true);
        TypedArray style = context.getTheme().obtainStyledAttributes(outValue.resourceId, attrs);
        windowBackground = style.getDrawable(0);
        style.recycle();

        // head_layout = (RelativeLayout)mView.findViewById(R.id.head_layout);
        // head_layout.setBackground(windowBackground);
        // 获取屏幕宽度
        table_layout = (LinearLayout)mView.findViewById(R.id.table_layout);
//        table_layout.setBackground(windowBackground);
//        table_layout.setBackgroundResource(context.getResources().getColor(R.color.white));
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 将屏幕分割成veiwPager孩子个数（2）分
        width = dm.widthPixels / 2 - 1;
        hsv = (HorizontalScrollView)mView.findViewById(R.id.main_ho_sv);
        hfl = (FrameLayout)mView.findViewById(R.id.main_fl);
        TextView placeView = new TextView(context);
        placeView.setLayoutParams(new FrameLayout.LayoutParams(width * 2, 10));
        placeView.setBackgroundResource(R.color.half_white);
        hfl.addView(placeView);
        slidView = new TextView(context);
        slidView.setLayoutParams(new FrameLayout.LayoutParams(width, 10));
        slidView.setBackgroundResource(R.color.holo_blue);
        hfl.addView(slidView);
        home_view = (TextView)mView.findViewById(R.id.home_mian_view);
        always_view = (TextView)mView.findViewById(R.id.home_always_view);

        showLeft = (Button)mView.findViewById(R.id.showLeft);
        showRight = (Button)mView.findViewById(R.id.showRight);

        mPager = (JazzyViewPager)mView.findViewById(R.id.pager);

        /**
         * 主要部分
         */
        mPager.setTransitionEffect(TransitionEffect.FlipHorizontal);

        PageFragment1 page1 = new PageFragment1();
        PageFragment2 page2 = new PageFragment2();
        pagerItemList.add(page1);
        pagerItemList.add(page2);
        mAdapter = new MyAdapter(getFragmentManager());

        mPager.setAdapter(mAdapter);
        mPager.setPageMargin(30);
      
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                updateTableBg(position);
                slidView.setTranslationX(width * position);
                if (myPageChangeListener != null)
                    myPageChangeListener.onPageSelected(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                if (arg1 > 0.0)
                    slidView.setTranslationX(width * arg1);
            }

            @Override
            public void onPageScrollStateChanged(int position){

            }
        });

        return mView;
    }
    
    private void updateTableBg(int position) {

        switch (position) {
            case 0:
                home_view.setTextColor(context.getResources().getColor(R.color.holo_blue));
                always_view.setTextColor(Color.BLACK);
                break;
            case 1:
                home_view.setTextColor(Color.BLACK);
                always_view.setTextColor(context.getResources().getColor(R.color.holo_blue));
                break;
            default:
                break;
        }

    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        updateTableBg(0);
        home_view.setOnClickListener(buttonListener);
        always_view.setOnClickListener(buttonListener);

        showLeft.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).showLeft();
            }
        });

        showRight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).showRight();
            }
        });
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == home_view) {
                mPager.setCurrentItem(0, true);

            }
            if (v == always_view) {
                mPager.setCurrentItem(1, true);
            }
        }
    };

    public boolean isFirst() {
        if (mPager.getCurrentItem() == 0)
            return true;
        else
            return false;
    }

    public boolean isEnd() {
        if (mPager.getCurrentItem() == pagerItemList.size() - 1)
            return true;
        else
            return false;
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pagerItemList.size();
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;
            if (position < pagerItemList.size())
                fragment = pagerItemList.get(position);
            else
                fragment = pagerItemList.get(0);
            // 必须代码
            mPager.setObjectForPosition(fragment, position);
            return fragment;

        }
    }

    private MyPageChangeListener myPageChangeListener;

    public void setMyPageChangeListener(MyPageChangeListener l) {

        myPageChangeListener = l;

    }

    public interface MyPageChangeListener {
        public void onPageSelected(int position);
    }

}
