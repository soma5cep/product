package com.example.product.j;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.MultiAutoCompleteTextView;

import com.example.product.R;

public class SearchDetail extends Activity {
	ExpandableListView lv;
	Button btnCancel, btnSubmit, btnAdd;
	MultiAutoCompleteTextView searchName;
	ArrayList<Option> option = new ArrayList<Option>();
	String[] productNames={"2014210015 김자현","김자현 2014210015","2014210016 박경수","박경수2014210016","","박경"};
	Option semiOption;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_detail);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		btnAdd = (Button)findViewById(R.id.btnAdd);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		
		searchName = (MultiAutoCompleteTextView) findViewById(R.id.searchName);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, productNames);
		searchName.setAdapter(adapter);
        searchName.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        
		lv = (ExpandableListView)findViewById(R.id.optionList);
		final OptionExpandableCustomAdapter optionCostum = new OptionExpandableCustomAdapter(this,option);
		lv.setAdapter(optionCostum);
        
        // optionIntroduct = new makeOption();
        // makeoption_activity(include layout) 생성
        // 아래 과정들 전부 삭제
		option.add(new Option("성장주","origin_01","장주는 기업의 수익구조가 지속적으로 향상되고 있어 장기간에 걸쳐 주가가 큰 폭으로 상승하고 있는 주식입니다.","매출액 증가율\n\t최근 3년 평균 증감률 50% 이상\n매출액\n\t최근 결산 400억원 이상",0));
		option.add(new Option("골든 크로스","origin_01","장주는 기업의 수익구조가 지속적으로 향상되고 있어 장기간에 걸쳐 주가가 큰 폭으로 상승하고 있는 주식입니다.","매출액 증가율\n\t최근 3년 평균 증감률 50% 이상\n매출액\n\t최근 결산 400억원 이상",1));
		optionCostum.notifyDataSetChanged();
		
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				// TODO Auto-generated method stub
			}
		});
		btnAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
			}
		});
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
			}
		});
		
		//for(int i=0;i<optionCostum.getGroupCount();i++) lv.expandGroup(i);
		
	}
}
