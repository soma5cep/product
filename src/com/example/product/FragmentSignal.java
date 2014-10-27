package com.example.product;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
	private static final int NUM_PAGES = 3;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_signal, container, false);
		
		mPager = (ViewPager)root.findViewById(R.id.pager);
		mPagerAdapter = new SignalPagerAdapter(getFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		

        TabPageIndicator mIndicator = (TabPageIndicator)root.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
		
		return root;
	}
	
	
/*********************************************************************************************************************
//
//				뷰페이저 어댑터
//
*********************************************************************************************************************/
	private class SignalPagerAdapter extends FragmentStatePagerAdapter {
		
		
		public SignalPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
		@Override
		public Fragment getItem(int position) {
			switch(position) {
				case 0 :
					return new FragmentSignalTotal();
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

		
		/* ListFragment에 이미 만들어져 있는 onCreateView를 쓴다. 
		 * 따라서 아래 코드는 필요 없다.
		 */		
		/*
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_signal_total, container, false);
			return root;			
		}
		*/
		
		
		/* Item 이 클릭되었을 때, 새로운 Activity를 띄운다(예정)
		 * 이 동작을 Fragment에서 구현하지 않고 정석적인 방법으로 Activity에 Listener 구현을 강제하여 
		 * Activity 차원에서 다양한 처리를 할 수 있게 한다.
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
			Toast.makeText(getActivity(), position+" is clicked", 0).show();
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
			mExpListView = (ExpandableListView)root;
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
			mExpListView = (ExpandableListView)root;
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