package com.example.product;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;


public class FragmentTab1 extends Fragment {
	Context mContext;
	Activity aActivity;
	ListView lvSearchByEvent;
	EditText txtSearchEventName;
	ArrayList<String> eventName = new ArrayList<String>();
	ArrayList<String> eventNum = new ArrayList<String>();
	SearchByEventCustomAdapter adapter;
	
	public FragmentTab1(Context context, Activity activity) {
		mContext = context;
		aActivity = activity;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search_by_event,
				container, false);
		
		

		if(MyDataBase.stock_item_list == null) {
			MyDataBase.getStock_list(
					new Response.Listener<stock_items>() {
						@Override
						public void onResponse(stock_items response) {
							MyDataBase.stock_item_list = response.stock_items;
							ArrayList<stock_item> list = response.stock_items;
							eventName.clear();
							eventNum.clear();
							for(int i=0; i<list.size(); ++i) {
								eventName.add(list.get(i).name);
								eventNum.add(list.get(i).short_code);
							}							
							if(adapter != null) {
								adapter.notifyDataSetChanged();				
							}
						}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.e("product", "error!" + error.getMessage());
						}
					});
		}
		else {
			ArrayList<stock_item> list = MyDataBase.stock_item_list;
			eventName.clear();
			eventNum.clear();
			for(int i=0; i<list.size(); ++i) {
				eventName.add(list.get(i).name);
				eventNum.add(list.get(i).short_code);
			}							
			if(adapter != null) {
				adapter.notifyDataSetChanged();				
			}
		}
		
		
		
		
		
		
		
		eventName.clear();

		eventNum.add("A1111");
		eventNum.add("A1112");
		eventNum.add("A1113");
		eventName.add("Samsung");
		eventName.add("Sambo");
		eventName.add("Apple");
		
		

		adapter = new SearchByEventCustomAdapter(aActivity, container.getContext(), eventNum, eventName);
		
		lvSearchByEvent = (ListView) rootView
				.findViewById(R.id.lvSearchByEvent);
		lvSearchByEvent.setAdapter(adapter);
		
		
		//divder가 위아래 에도 표시되게 하기 위해 빈 header와 footer를 삽입	
		lvSearchByEvent.addFooterView(new View(getActivity()), null, true);
		lvSearchByEvent.addHeaderView(new View(getActivity()), null, true);

		txtSearchEventName = (EditText) rootView
				.findViewById(R.id.txtSearchByEvent);
		txtSearchEventName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = txtSearchEventName.getText().toString()
						.toLowerCase(Locale.getDefault());
				adapter.Filter(text);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		return rootView;
	}

}
