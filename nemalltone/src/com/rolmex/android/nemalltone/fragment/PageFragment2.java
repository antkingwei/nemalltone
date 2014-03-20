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
import com.rolmex.android.nemalltone.widget.MyGridView;
import com.rolmex.android.nemalltone.widget.MyGridView.OnViewGroupItemClickListener;
import com.rolmex.android.nemalltone.widget.ObservableScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PageFragment2 extends Fragment {
    private ObservableScrollView scrollView;
    
    private MyGridView gridView;
    
    private ItemTableDao itemDao;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.page2, null);
		scrollView = (ObservableScrollView)view.findViewById(R.id.pagetwo_scrollview);
		gridView = (MyGridView)view.findViewById(R.id.pagetwo_gridview);
		itemDao = new ItemTableDao(getActivity());
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<ItemBean> list=null;
		list = itemDao.getItemList();
		gridView.AddData(list);
		gridView.setOnViewGroupItemClickListener(new OnViewGroupItemClickListener(){

            @Override
            public void ItemClickListener(ItemBean bean) {
                // TODO Auto-generated method stub
                itemDao.insertIfNot(bean);
                
                Class class1 = null;
                try {
                    class1 = Class.forName("com.rolmex.android.nemalltone."+bean.item_class);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                Intent intent = new Intent(getActivity(),class1);
                startActivity(intent);
            }
		    
		});
	}

}

