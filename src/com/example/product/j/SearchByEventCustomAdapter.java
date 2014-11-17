package com.example.product.j;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.product.R;

public class SearchByEventCustomAdapter extends BaseAdapter {
	Context mContext;
	Activity aActivity;
	ArrayList<String> eventNum = new ArrayList<String>();
	ArrayList<String> eventName = new ArrayList<String>();
	ArrayList<String> resultEventNum;
	ArrayList<String> resultEventName;
	public SearchByEventCustomAdapter(Activity activity, Context context, ArrayList<String> eventNums, ArrayList<String> eventNames) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.aActivity = activity;
		this.resultEventName = eventNames;
		this.resultEventNum = eventNums;
		for(int i=0;i<resultEventName.size();i++){
			String mid = resultEventName.get(i);
			this.eventName.add(mid);
			mid = resultEventNum.get(i);
			this.eventNum.add(mid);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return resultEventName.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resultEventName.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_search_by_event, null);
		}
		TextView txtEventNum = (TextView)convertView.findViewById(R.id.txtEventNum);
		final TextView txtEventName = (TextView)convertView.findViewById(R.id.txtEventName);
		txtEventNum.setText(resultEventNum.get(position).toString());
		txtEventName.setText(resultEventName.get(position).toString());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("name", txtEventName.getText().toString());
				aActivity.setResult(SearchItem.RESULT_OK, intent);
				// TODO Auto-generated method stub
				aActivity.finish();
			}
		});
		return convertView;
	}
	
	public void Filter(String charText){
		charText = charText.toLowerCase(Locale.getDefault());
		resultEventName.clear();
		resultEventNum.clear();
		if(charText.length()==0){
			resultEventName.addAll(eventName);
			resultEventNum.addAll(eventNum);
		}else{
			int len = eventName.size();
			for(int i=0;i<len;i++){
				if(eventName.get(i).toLowerCase(Locale.getDefault()).contains(charText) || eventNum.get(i).toLowerCase(Locale.getDefault()).contains(charText)){
					resultEventName.add(eventName.get(i));
					resultEventNum.add(eventNum.get(i));
				}
			}
		}
		notifyDataSetChanged();
	}

}
