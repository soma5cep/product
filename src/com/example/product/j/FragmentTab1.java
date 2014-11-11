package com.example.product.j;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Fragment;
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

	ListView lvSearchByEvent;
	EditText txtSearchEventName;
	ArrayList<String> eventName = new ArrayList<String>();
	ArrayList<String> eventNum = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search_by_event,
				container, false);
		eventName.clear();


		eventName.add("A1111");
		eventName.add("A1112");
		eventName.add("A1113");
		eventNum.add("Samsung");
		eventNum.add("Sambo");
		eventNum.add("Apple");
		

		final SearchByEventCustomAdapter adapter = new SearchByEventCustomAdapter(container.getContext(), eventNum, eventName);
		
		lvSearchByEvent = (ListView) rootView
				.findViewById(R.id.lvSearchByEvent);
		lvSearchByEvent.setAdapter(adapter);

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
