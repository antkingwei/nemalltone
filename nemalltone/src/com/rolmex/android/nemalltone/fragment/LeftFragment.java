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
import com.rolmex.android.nemalltone.db.dao.ItemTableDao;
import com.rolmex.android.nemalltone.model.ItemBean;
import com.rolmex.android.nemalltone.widget.MyListView;
import com.rolmex.android.nemalltone.widget.ObservableScrollView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class LeftFragment extends Fragment implements ObservableScrollView.Callbacks{
     private MyListView my_menu_list;
     private ObservableScrollView scroll_view;
     private LinearLayout menu_list_ll;
     
     private LinearLayout menu_all_ll;
     
     private MyListView my_menu_all;
     private TextView place_holder;
     private int screen_height;
     
     private ItemTableDao itemDao;
     

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    DisplayMetrics dm = new DisplayMetrics();
	    getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
	    itemDao = new ItemTableDao(getActivity());
	    screen_height = dm.heightPixels;
	    
		View view = inflater.inflate(R.layout.left, null);
		scroll_view = (ObservableScrollView)view.findViewById(R.id.left_scroll);
		my_menu_list = (MyListView) view.findViewById(R.id.my_menu_list);
		my_menu_all = (MyListView)view.findViewById(R.id.my_menu_all);
		menu_list_ll = (LinearLayout)view.findViewById(R.id.menu_list_ll);
		menu_all_ll = (LinearLayout)view.findViewById(R.id.all_munu_ll);
		place_holder = (TextView)view.findViewById(R.id.left_place_holder);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<ItemBean> list1 = new ArrayList<ItemBean>();
		for(int i=0;i<5;i++){
		    ItemBean bean = new ItemBean("常用菜单："+i,R.drawable.main_icon_dealer+i,"MainActivity");
		    list1.add(bean);
		}
		my_menu_list.AddData(list1);
		List<ItemBean> list = itemDao.getItemList();
		my_menu_all.AddData(list);	
		scroll_view.setCallbacks(this);
		scroll_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	        
	        @Override
	        public void onGlobalLayout() {
	            // TODO Auto-generated method stub
	           onScrollChanged(scroll_view.getScrollY());
	           place_holder.setLayoutParams(new LinearLayout.LayoutParams(menu_list_ll.getWidth(),menu_list_ll.getHeight()));
	        }
		});
		
	}

    @Override
    public void onScrollChanged(int scrollY) {
        // TODO Auto-generated method stub
        if((scrollY+screen_height)<menu_list_ll.getHeight()){
            
        }else{
         
           menu_list_ll.setTranslationY(Math.min(scrollY-menu_list_ll.getHeight()+screen_height,scrollY));
        }
              
        if(scrollY==0){
            menu_all_ll.setTranslationY(menu_list_ll.getHeight());
        }
        
    }

    @Override
    public void onDownMotionEvent() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onUpOrCancelMotionEvent() {
        // TODO Auto-generated method stub
        
    }

}
