package com.example.product.j;

import java.util.ArrayList;

import android.R.drawable;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.product.R;

public class OptionExpandableCustomAdapter extends BaseExpandableListAdapter {
	Context context;
	ArrayList<Option> option;

	public OptionExpandableCustomAdapter(Context contexts,
			ArrayList<Option> options) {
		context = contexts;
		option = options;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return option.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (option.get(groupPosition).getType() >= 2)
			return 0;
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return option.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return option.get(groupPosition).introduct;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return super.areAllItemsEnabled();
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_costum_option_group, parent,
					false);
		}
		ImageView indicator = (ImageView) convertView
				.findViewById(R.id.imgIndicator);
		if (isExpanded)
			indicator.setImageResource(drawable.arrow_up_float);
		else
			indicator.setImageResource(drawable.arrow_down_float);
		indicator.setImageResource(drawable.ic_menu_more);
		Button btnStatus = (Button) convertView.findViewById(R.id.btnDelete);
		TextView txtOptionName = (TextView) convertView
				.findViewById(R.id.txtOptionName);
		TextView txtOptionIntroduct = (TextView) convertView
				.findViewById(R.id.txtOptionIntroduct);
		btnStatus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int now = (Integer) v.getTag();
				option.remove(now);
				OptionExpandableCustomAdapter.this.notifyDataSetChanged();
				// TODO Auto-generated method stub
			}
		});

		// btnStatus.setBackgroundColor(Color.WHITE);
		btnStatus.setBackgroundColor(Color.TRANSPARENT);
		btnStatus.setBackgroundResource(drawable.ic_menu_delete);
		btnStatus.setTag(groupPosition);
		LinearLayout ll = (LinearLayout) convertView
				.findViewById(R.id.llOption);
		if (option.get(groupPosition).getType() == 0) {
			txtOptionIntroduct.setBackgroundColor(Color.parseColor("#F0FFF0"));
			ll.setBackgroundColor(Color.parseColor("#008000"));
		} else {
			txtOptionIntroduct.setBackgroundColor(Color.parseColor("#FFDDFF"));
			ll.setBackgroundColor(Color.parseColor("#660000"));
		}
		txtOptionName.setText(option.get(groupPosition).getName());
		// TODO Auto-generated method stub
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		LayoutInflater infalInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (option.get(groupPosition).getType() == 0) {
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_costum_option_child, parent,
					false);
			convertView.setBackgroundColor(Color.parseColor("#F0FFF0"));
			TextView txtOptionDetail = (TextView) convertView
					.findViewById(R.id.txtOptionDetail);
			txtOptionDetail.setText(option.get(groupPosition).getDetail());
		} else {
			convertView = infalInflater.inflate(
					R.layout.expandablelistview_costum_option_child2, parent,
					false);
			convertView.setBackgroundColor(Color.parseColor("#FFDDFF"));
			LinearLayout inLayout = (LinearLayout) convertView
					.findViewById(R.id.llChild);
			for (int i = 0; i < 2; i++) {
				LinearLayout route = (LinearLayout) infalInflater.inflate(
						R.layout.expandablelistview_child2_child, null);
				EditText txtParamName1 = (EditText) route.findViewById(R.id.txtParamName1);
				EditText txtParamName2 = (EditText) route.findViewById(R.id.txtParamName2);
				txtParamName1.setText("A");
				txtParamName2.setText("B");
				inLayout.addView(route);
			}
		}
		// TODO Auto-generated method stub
		return convertView;
	}

}
