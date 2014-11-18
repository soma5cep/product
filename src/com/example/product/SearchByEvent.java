package com.example.product;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.product.R;

public class SearchByEvent extends Activity {
	ListView lvSearchByEvent;
	EditText txtSearchEventName;
	ArrayList<String> eventName = new ArrayList<String>();
	ArrayList<String> eventNum = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_event);
		eventName.clear();


		eventNum.add("A1111");
		eventNum.add("A1112");
		eventNum.add("A1113");
		eventName.add("Samsung");
		eventName.add("Sambo");
		eventName.add("Apple");
		

		final SearchByEventCustomAdapter adapter = new SearchByEventCustomAdapter((Activity)this,this, eventNum, eventName);
		
		lvSearchByEvent = (ListView)findViewById(R.id.lvSearchByEvent);
		lvSearchByEvent.setAdapter(adapter);

		txtSearchEventName = (EditText)findViewById(R.id.txtSearchByEvent);
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
	}
}
