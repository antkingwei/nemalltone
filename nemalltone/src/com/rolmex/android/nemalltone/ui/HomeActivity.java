package com.rolmex.android.nemalltone.ui;

import com.rolmex.android.nemalltone.R;
import com.rolmex.android.nemalltone.fragment.LeftFragment;
import com.rolmex.android.nemalltone.fragment.RightFragment;
import com.rolmex.android.nemalltone.fragment.ViewPageFragment;
import com.rolmex.android.nemalltone.fragment.ViewPageFragment.MyPageChangeListener;
import com.rolmex.android.nemalltone.widget.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Window;

public class HomeActivity extends FragmentActivity{
    
    private SlidingMenu mSlidingMenu;
    private LeftFragment mLeftFragment;
    private RightFragment mRightFragment;
    private ViewPageFragment viewPageFragment;
    private LayoutInflater mLayoutInflater;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_home);
        
        mLayoutInflater = LayoutInflater.from(getApplicationContext());
        init();
        initListener();
        
    }

    private void init() {
        // TODO Auto-generated method stub
        mSlidingMenu = (SlidingMenu)this.findViewById(R.id.slidingMenu);
        mSlidingMenu.setLeftView(mLayoutInflater.inflate(R.layout.left_frame, null));
        mSlidingMenu.setRightView(mLayoutInflater.inflate(R.layout.right_frame, null));
        mSlidingMenu.setCenterView(mLayoutInflater.inflate(R.layout.center_frame, null));
        
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        mLeftFragment = new LeftFragment();
        t.replace(R.id.left_frame, mLeftFragment);
        
        mRightFragment = new RightFragment();
        t.replace(R.id.right_frame, mRightFragment);
        
        viewPageFragment = new ViewPageFragment();
        t.replace(R.id.center_frame, viewPageFragment);
        t.commit();
        
       
        
    }
    private void initListener() {
        viewPageFragment.setMyPageChangeListener(new MyPageChangeListener() {
            
            @Override
            public void onPageSelected(int position) {
                if(viewPageFragment.isFirst()){
                    mSlidingMenu.setCanSliding(true,false);
                }else if(viewPageFragment.isEnd()){
                    mSlidingMenu.setCanSliding(false,true);
                }else{
                    mSlidingMenu.setCanSliding(false,false);
                }
            }
        });
    }
    public void showLeft(){
        mSlidingMenu.showLeftView();
    }
    public void showRight(){
        mSlidingMenu.showRightView();
    }
    

}
