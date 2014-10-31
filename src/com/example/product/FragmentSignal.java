package com.example.product;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;




public class FragmentSignal extends Fragment {
	private ViewPager mPager;
	private SignalPagerAdapter mPagerAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_signal, container, false);
		
		mPager = (ViewPager)root.findViewById(R.id.pager);
		mPagerAdapter = new SignalPagerAdapter(getChildFragmentManager());
		mPager.setAdapter(mPagerAdapter);

        TabPageIndicator mIndicator = (TabPageIndicator)root.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
		
		return root;
	}
	
	public void change_frag_0(Fragment fragment) {
		mPagerAdapter.switch_fragment(fragment);
	}
	
	
/*********************************************************************************************************************
//
//				뷰페이저 어댑터
//
*********************************************************************************************************************/
	private class SignalPagerAdapter extends FragmentStatePagerAdapter {
		private static final int NUM_PAGES = 3;
		private final FragmentManager fm;
		private Fragment frag_0;
		
		public void switch_fragment(Fragment fragment) {
			if(fragment instanceof FragmentSignalTotal) {

				fm.beginTransaction().remove(frag_0).commit();
				frag_0 = new FragmentSignalTotalSettings(FragmentSignal.this);
				notifyDataSetChanged();						
			}
			else if(fragment instanceof FragmentSignalTotalSettings) {
				fm.beginTransaction().remove(frag_0).commit();
				frag_0 = new FragmentSignalTotal(FragmentSignal.this);
				notifyDataSetChanged();		
			}
		}
		
		public SignalPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}
		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
		@Override
		public int getItemPosition(Object object) {
			if(object instanceof FragmentSignalTotal && frag_0 instanceof FragmentSignalTotalSettings) {
				return POSITION_NONE;				
			}
			else if(object instanceof FragmentSignalTotalSettings && frag_0 instanceof FragmentSignalTotal) {
				return POSITION_NONE;
			}
			return POSITION_UNCHANGED;
		}

		
		@Override
		public Fragment getItem(int position) {
			switch(position) {
				case 0 :
					if(frag_0 == null) {
						frag_0 = new FragmentSignalTotal(FragmentSignal.this);
					}
					return frag_0;
				case 1 :
					return new FragmentSignalStock();
				case 2 :
					return new FragmentSignalSignal();
				default :
					return null;
			}
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			switch(position) {
				case 0 :
					return "전체";
				case 1 :
					return "종목별";
				case 2 :
					return "신호별";
				default :
					return "";
			}
		}
		
	}

/*********************************************************************************************************************
//
//				신호 - 전체 FRAGMENT
//
*********************************************************************************************************************/	
	/* 전체 */
	public static class FragmentSignalTotal extends ListFragment {
		private Menu myMenu;
		private FragmentSignal parent;
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			myMenu.clear();			
		}
		
		public FragmentSignalTotal() {
			super();
		}
		public FragmentSignalTotal(FragmentSignal parent) {
			super();
			this.parent = parent;
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_total, container, false);
			
			/*Bottom Menu 에 대한 버튼 클릭 리스너를 등록하자. */
			
			FragmentManager fm=getChildFragmentManager();
			if(fm.findFragmentById(R.id.bottom_menu) == null) {
				BottomMenu.BottomMenu_1 bottom_menu = new BottomMenu.BottomMenu_1();
				fm.beginTransaction().add(R.id.bottom_menu, bottom_menu, "bottom_menu").commit();
			}
			
			return root;			
		}
		
		
		/* Item 이 클릭되었을 때, 새로운 Activity를 띄운다(예정)
		 * 이 동작을 Fragment에서 구현하지 않고 정석적인 방법으로 Activity에 Listener 구현을 강제하여 
		 * Activity 차원에서 다양한 처리를 할 수 있게 한다.
		 * 
		 * 하지만 복잡하므로 그냥 이 Fragment에서 구현
		 */		
		/*
		OnItemClickedListener mHost;
		
		public interface OnItemClickedListener {
			public void onItemClicked(int index);
		}
		
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			
			try {
				mHost = (OnItemClickedListener)activity;				
			} catch (ClassCastException e) {
				throw new ClassCastException("activity must implement OnItemClickedListener");
			}
		}		
		*/
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.actionbarmenu, menu);
			myMenu=menu;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()) {
				case R.id.action_add_alarm :
					Toast.makeText(getActivity(), "action_add_alarm을 클릭함", 0).show();
					// 전체 신호 추가 Activity
					/*
					Intent intent = new Intent(getActivity(), StockDetail.class);
					startActivity(intent);
					*/		
					
					return true;
				case R.id.action_my_settings :		
					parent.change_frag_0(this);
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}
		



		
		/* 이 WORLDS 배열은 테스트용 */
		public static String[] WORDS = {
			"boy", "girl", "school", "Hello", "go"
		};
		

		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			/* test code */
			
			ArrayList<String> dd=new ArrayList<String>();
			dd.add("aaa");
			dd.add("bbb");
			dd.add("ccc");
			dd.add("d");
			dd.add("e");
			dd.add("f");
			

			setListAdapter(new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_item, dd));
			
			ListView mListView = getListView();
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setDivider(new ColorDrawable(Color.LTGRAY));
			mListView.setDividerHeight(1);
			
		}
		
		public void onListItemClick(ListView l, View v, int position, long id) {			
			//mHost.onItemChecked(position);
			l.setItemChecked(position, true);
			Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
			startActivity(intent);
			//Toast.makeText(getActivity(), position+" is clicked", 0).show();
		}
		
		class MyArrayAdapter extends ArrayAdapter<String> {
			private ArrayList<String> items;
			private Context context;
			private int resource;

			public MyArrayAdapter(Context context, int viewResourceId, ArrayList<String> items) {
				super(context, viewResourceId, items);
				this.items = items;
				this.context = context;
				resource = viewResourceId;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				/*
				RelativeLayout v = (RelativeLayout)convertView;
				ViewHolder vh;
				if (v == null) {
					v = (RelativeLayout)View.inflate(context, resource, null);
					vh = new ViewHolder();
					
					vh.signal = (TextView)v.findViewById(R.id.signal);	
					vh.inout = (TextView)v.findViewById(R.id.inout);
					vh.stock_name = (TextView)v.findViewById(R.id.stock_name);
					vh.market_type = (TextView)v.findViewById(R.id.market_type);
					vh.time = (TextView)v.findViewById(R.id.time);
					vh.price_diff_percent = (TextView)v.findViewById(R.id.price_diff_percent);
					vh.price_diff = (TextView)v.findViewById(R.id.price_diff);
					vh.trading_volume = (TextView)v.findViewById(R.id.trading_volume);
					vh.stock_price = (TextView)v.findViewById(R.id.stock_price);
					
					v.setTag(vh);
				}
				else {
					vh = (ViewHolder)v.getTag();
				}
				RTSBox b = items.get(position);
				if(b != null) {
					ConvertRTSBox.convertBoxToRel(b, vh);
				}
				return v;
				*/
				RelativeLayout v = (RelativeLayout)View.inflate(context, resource, null);
				return v;
			}
			
			/*
			public static class ViewHolder {		
				TextView signal;
				TextView inout;
				TextView stock_name;
				TextView market_type;
				TextView time;
				TextView price_diff_percent;
				TextView price_diff;
				TextView trading_volume;
				TextView stock_price;		
			}
			*/
		}
		
	}
	/************************************************
	 * 
	 *  신호 - 전체 - 설정
	 * 
	 * 
	 **************************************************/
	public static class FragmentSignalTotalSettings extends ListFragment {
		private Menu myMenu;
		FragmentSignal parent;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_total_settings, container, false);
			return root;			
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			myMenu.clear();			
		}
		
		public FragmentSignalTotalSettings() {
			super();
		}
		
		public FragmentSignalTotalSettings(FragmentSignal parent) {
			super();
			this.parent = parent;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setHasOptionsMenu(true);
		}
		
	
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			inflater.inflate(R.menu.fragment_signal_total_settings_menu, menu);
			myMenu = menu;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch(item.getItemId()) {
				/* Action_add 동작을 수정하자. 아이콘이랑 이름까지 */
				case R.id.action_alarms :	
					parent.change_frag_0(this);
					return true;
				case R.id.action_add :
					/*
					Intent intent = new Intent(getActivity(), StockDetail.class);
					startActivity(intent);
					*/	
					// 추가하는 동작 수행
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}
		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			/* test code */
			
			ArrayList<String> dd=new ArrayList<String>();
			dd.add("aaa");
			dd.add("bbb");
			dd.add("ccc");
			dd.add("d");
			dd.add("e");
			dd.add("f");
			

			setListAdapter(new MyArrayAdapter(getActivity(), R.layout.fragment_signal_total_settings_item, dd));
			
			
			/*
			ListView mListView = getListView();
			
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setDivider(new ColorDrawable(Color.LTGRAY));
			mListView.setDividerHeight(1);
			*/
			
		}
		
		
		class MyArrayAdapter extends ArrayAdapter<String> {
			private ArrayList<String> items;
			private Context context;
			private int resource;

			public MyArrayAdapter(Context context, int viewResourceId, ArrayList<String> items) {
				super(context, viewResourceId, items);
				this.items = items;
				this.context = context;
				resource = viewResourceId;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				/*
				RelativeLayout v = (RelativeLayout)convertView;
				ViewHolder vh;
				if (v == null) {
					v = (RelativeLayout)View.inflate(context, resource, null);
					vh = new ViewHolder();
					
					vh.signal = (TextView)v.findViewById(R.id.signal);	
					vh.inout = (TextView)v.findViewById(R.id.inout);
					vh.stock_name = (TextView)v.findViewById(R.id.stock_name);
					vh.market_type = (TextView)v.findViewById(R.id.market_type);
					vh.time = (TextView)v.findViewById(R.id.time);
					vh.price_diff_percent = (TextView)v.findViewById(R.id.price_diff_percent);
					vh.price_diff = (TextView)v.findViewById(R.id.price_diff);
					vh.trading_volume = (TextView)v.findViewById(R.id.trading_volume);
					vh.stock_price = (TextView)v.findViewById(R.id.stock_price);
					
					v.setTag(vh);
				}
				else {
					vh = (ViewHolder)v.getTag();
				}
				RTSBox b = items.get(position);
				if(b != null) {
					ConvertRTSBox.convertBoxToRel(b, vh);
				}
				return v;
				*/
				RelativeLayout v = (RelativeLayout)View.inflate(context, resource, null);
				return v;
			}
			
			/*
			public static class ViewHolder {		
				TextView signal;
				TextView inout;
				TextView stock_name;
				TextView market_type;
				TextView time;
				TextView price_diff_percent;
				TextView price_diff;
				TextView trading_volume;
				TextView stock_price;		
			}
			*/
		}
		

		
	}

	
	
/*********************************************************************************************************************
//
//					신호 - 종목별 FRAGMENT
//
*********************************************************************************************************************/	
	/* 종목별 */
	public static class FragmentSignalStock extends Fragment {
		private LayoutInflater inflater;
		private ExpandableListView mExpListView;
		private int GROUP_CNT = 3;
		private int CHILD_CNT = 5;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			this.inflater = inflater;
			View root = inflater.inflate(R.layout.simple_expandablelistview, container, false);
			mExpListView = (ExpandableListView)root.findViewById(R.id.explist);
			mExpListView.setAdapter(new MyExpAdapter(getActivity()));
			
			/* Test 용 임시 listener */
			mExpListView.setOnChildClickListener(new OnChildClickListener() {
				@Override
				public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
					TextView signal = (TextView)v.findViewById(R.id.signal);
					TextView signal_type = (TextView)v.findViewById(R.id.signal_type);
					
					Toast.makeText(getActivity(), "chlid position = " + childPosition +"\n"+
														"signal Top = " + signal.getTop() + "\n" +
														"signal Bottom = " + signal.getBottom() + "\n" +
														"signal Baseline = " + signal.getBaseline() + "\n" +
														"signal_type Top = " + signal_type.getTop() + "\n" +
														"signal_type Bottom = " + signal_type.getBottom() + "\n" +
														"signal_type Baseline = " + signal_type.getBaseline() + "\n", Toast.LENGTH_LONG).show();
					
					return false;
				}
			});
			
			
			/* 하단 메뉴 설정 */
			FragmentManager fm=getChildFragmentManager();
			if(fm.findFragmentById(R.id.bottom_menu) == null) {
				BottomMenu.BottomMenu_1 bottom_menu = new BottomMenu.BottomMenu_1();
				fm.beginTransaction().add(R.id.bottom_menu, bottom_menu, "bottom_menu").commit();
			}
	
			
			return root;			
		}
		
		class MyExpAdapter extends BaseExpandableListAdapter {
			Context context;
			/* 뷰홀더 코드 */
			//private ViewHoler viewHolder = null;
			
			public MyExpAdapter(Context context) { //부모그룹이랑 차일드 그룹을 받아야 함.
				super();
				this.context = context;				
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
				v =inflater.inflate(R.layout.fragment_signal_stock_item_parent, parent, false);
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
				v =inflater.inflate(R.layout.fragment_signal_stock_item_child, null);
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

	
/*********************************************************************************************************************
//
//						신호 - 신호별 FRAGMENT
//
*********************************************************************************************************************/	
	/* 신호별 */
	public static class FragmentSignalSignal extends Fragment {
		private LayoutInflater inflater;
		private ExpandableListView mExpListView;
		private int GROUP_CNT = 3;
		private int CHILD_CNT = 5;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			this.inflater = inflater;
			View root = inflater.inflate(R.layout.simple_expandablelistview, container, false);
			mExpListView = (ExpandableListView)root.findViewById(R.id.explist);
			mExpListView.setAdapter(new MyExpAdapter(getActivity()));
			return root;			
		}
		
		class MyExpAdapter extends BaseExpandableListAdapter {
			Context context;
			/* 뷰홀더 코드 */
			//private ViewHoler viewHolder = null;
			
			public MyExpAdapter(Context context) { //부모그룹이랑 차일드 그룹을 받아야 함.
				super();
				this.context = context;				
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
				v =inflater.inflate(R.layout.fragment_signal_signal_item_parent, parent, false);
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
				v =inflater.inflate(R.layout.fragment_signal_signal_item_child, null);
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
}