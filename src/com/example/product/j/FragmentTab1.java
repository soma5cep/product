package com.example.product.j;

import java.util.ArrayList;
import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.product.R;

public class FragmentTab1 extends Fragment {
	//Context mContext;
	//Activity aActivity;
	ListView lvSearchByEvent;
	EditText txtSearchEventName;
	ArrayList<String> eventName = new ArrayList<String>();
	ArrayList<String> eventNum = new ArrayList<String>();
	
	
	
	//context와 activity를 받을 필요는 없다.
	//기본 생성자를 사용하기 위해 주석처리
	/*
	public FragmentTab1(Context context,Activity activity) {
		mContext = context;
		aActivity = activity;
		// TODO Auto-generated constructor stub
	}
	*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search_by_event,
				container, false);
		eventName.clear();


		eventNum.add("A1111");
		eventNum.add("A1112");
		eventNum.add("A1113");
		eventName.add("Samsung");
		eventName.add("Sambo");
		eventName.add("Apple");
		

		final SearchByEventCustomAdapter adapter = new SearchByEventCustomAdapter(getActivity(), container.getContext(), eventNum, eventName);
		
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
