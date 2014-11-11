package com.example.product.j;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.product.R;

public class FragmentTab2 extends Fragment {

	ExpandableListView lv;
	Button btnCancel, btnSubmit, btnAdd;
	ArrayList<Option> option = new ArrayList<Option>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_by_option, container, false);
        
        option.clear();
        
        btnCancel = (Button)rootView.findViewById(R.id.btnCancel);
		btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
		btnSubmit = (Button)rootView.findViewById(R.id.btnSubmit);
        
		lv = (ExpandableListView)rootView.findViewById(R.id.optionList);
		final OptionExpandableCustomAdapter optionCostum = new OptionExpandableCustomAdapter(container.getContext(),option);
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
        return rootView;
    }

}
