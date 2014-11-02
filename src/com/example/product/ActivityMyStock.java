package com.example.product;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

public class ActivityMyStock extends FragmentActivity{
	
	private int GROUP_CNT = 3;
	private int CHILD_CNT = 5;	
	private ExpandableListView mExpListView;	
	FragmentManager fm; //onCreate에서 초기화

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_expandablelistview);
		
		mExpListView = (ExpandableListView)findViewById(R.id.explist);
		mExpListView.setAdapter(new MyExpAdapter(this));
		
		/*Bottom Menu 에 대한 버튼 클릭 리스너를 등록하자. */
		
		FragmentManager fm=getSupportFragmentManager();
		if(fm.findFragmentById(R.id.bottom_menu) == null) {
			BottomMenu.BottomMenu_2 bottom_menu = new BottomMenu.BottomMenu_2();
			fm.beginTransaction().add(R.id.bottom_menu, bottom_menu, "bottom_menu").commit();
		}
	}
	
	
	class MyExpAdapter extends BaseExpandableListAdapter {
		Context context;
		LayoutInflater inflater;
		/* 뷰홀더 코드 */
		//private ViewHoler viewHolder = null;
		
		public MyExpAdapter(Context context) { //부모그룹이랑 차일드 그룹을 받아야 함.
			super();
			this.context = context;
			inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public String getGroup(int groupPosition) {
			return "Parent";
		}
		
		@Override
		public int getGroupCount() {
			return GROUP_CNT;
		}
		
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
		
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			View v = convertView;
			
			/* View Holder Pattern 을 적용한 예시 */
			/*
			if(v == null){
	            viewHolder = new ViewHolder();
	            v = inflater.inflate(R.layout.list_row, parent, false);
	            viewHolder.tv_groupName = (TextView) v.findViewById(R.id.tv_group);
	            viewHolder.iv_image = (ImageView) v.findViewById(R.id.iv_image);
	            v.setTag(viewHolder);
	        }else{
	            viewHolder = (ViewHolder)v.getTag();
	        }
	         
	        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
	        if(isExpanded){
	            viewHolder.iv_image.setBackgroundColor(Color.GREEN);
	        }else{
	            viewHolder.iv_image.setBackgroundColor(Color.WHITE);
	        }
	         
	        viewHolder.tv_groupName.setText(getGroup(groupPosition));
	        */
			
			/*테스트용으로 그냥 groupview 레이아웃을 inflate 해서 출력 */
			v =inflater.inflate(R.layout.activity_my_stock_item_parent, parent, false);
			return v;
		}
		
		@Override
		public String getChild(int groupPosition, int childPosition) {
			return "child";
		}
		
		@Override
		public int getChildrenCount(int groupPosition) {
	        return CHILD_CNT;
	    }
		
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}
		
		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			View v = convertView;
			/*뷰홀더패턴 코드*/
			/*
			if(v == null){
	            viewHolder = new ViewHolder();
	            v = inflater.inflate(R.layout.list_row, null);
	            viewHolder.tv_childName = (TextView) v.findViewById(R.id.tv_child);
	            v.setTag(viewHolder);
	        }else{
	            viewHolder = (ViewHolder)v.getTag();
	        }
	         
	        viewHolder.tv_childName.setText(getChild(groupPosition, childPosition));
	        */
			v =inflater.inflate(R.layout.activity_my_stock_item_child, null);
			return v;
		}
		
		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}
