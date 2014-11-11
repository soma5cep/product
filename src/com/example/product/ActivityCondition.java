package com.example.product;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.product.j.OptionDetail;
import com.viewpagerindicator.TabPageIndicator;

public class ActivityCondition extends FragmentActivity {
	FragmentManager fm; //onCreate에서 초기화
	
	static ConditionGroup easy;
	static ConditionGroup hard_jaemu;
	static ConditionGroup hard_sise;
	static ConditionGroup hard_kisool;
	static ConditionGroup hard_pattern;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_condition);		
		
		
		
		/* 프래그먼트 관리 */
		fm = getSupportFragmentManager();
		/* 부모의 id로 fragment를 찾기, fragment가 없으면 추가 */
		if(fm.findFragmentById(R.id.frame) == null) {
			FragmentEasyCondition fg_easy = new FragmentEasyCondition();
			fm.beginTransaction().add(R.id.frame, fg_easy, "fragment_easy_condition").commit();
		}
		
		
		// 조건 데이터 서버에서 받아옴
		if(easy == null) {
			//갱신
			//test
			easy = new ConditionGroup();
			easy.list.add(new ConditionAbstract());			
		}
		if(hard_jaemu == null) {
			//갱신
			hard_jaemu = new ConditionGroup();
			hard_jaemu.list.add(new ConditionAbstract());
			hard_jaemu.list.add(new ConditionAbstract());		
		}
		if(hard_sise == null) {
			//갱신
			hard_sise = new ConditionGroup();
			hard_sise.list.add(new ConditionAbstract());
			hard_sise.list.add(new ConditionAbstract());
			hard_sise.list.add(new ConditionAbstract());
	
		}
		if(hard_kisool == null) {
			//갱신
			hard_kisool = new ConditionGroup();
			hard_kisool.list.add(new ConditionAbstract());
			hard_kisool.list.add(new ConditionAbstract());	
			hard_kisool.list.add(new ConditionAbstract());	
			hard_kisool.list.add(new ConditionAbstract());	
		}
		if(hard_pattern == null) {
			//갱신
			hard_pattern = new ConditionGroup();
			hard_pattern.list.add(new ConditionAbstract());	
			hard_pattern.list.add(new ConditionAbstract());
			hard_pattern.list.add(new ConditionAbstract());
			hard_pattern.list.add(new ConditionAbstract());
			hard_pattern.list.add(new ConditionAbstract());
		}
		
		
	}
	
	public void tabButtonOnClick(View view) {
		
		Fragment fragment = fm.findFragmentById(R.id.frame);
		
		//여기로 진입하면 에러임
		if(fragment == null) {
			FragmentEasyCondition fg_easy = new FragmentEasyCondition();	
			fm.beginTransaction().add(R.id.frame, fg_easy, "fragment_easy_condtion").commit();
			return;
		}
		
		switch(view.getId()) {
			case R.id.easy_condition_bt :
				if(fragment.getTag() == "fagment_easy_condition") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					FragmentEasyCondition fg_easy = new FragmentEasyCondition();
					fm.beginTransaction().replace(R.id.frame, fg_easy, "fragment_easy_condtion").commit();
				}
				
				break;
				
			case R.id.detail_condition_bt :
				if(fragment.getTag() == "fragment_detail_condition") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					
					FragmentDetailCondition fg_detail = new FragmentDetailCondition();
					fm.beginTransaction().replace(R.id.frame, fg_detail, "fragment_detail_condition").commit();
				}
				break;
		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class MyArrayAdapter extends ArrayAdapter<ConditionAbstract> {
		private ArrayList<ConditionAbstract> items;
		private Context context;
		private int resource;

		public MyArrayAdapter(Context context, int viewResourceId, ArrayList<ConditionAbstract> items) {
			super(context, viewResourceId, items);
			this.items = items;
			this.context = context;
			resource = viewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			RelativeLayout v = (RelativeLayout)convertView;
			ViewHolder vh;
			if (v == null) {
				v = (RelativeLayout)View.inflate(context, resource, null);
				vh = new ViewHolder();
				
				vh.image= (ImageView)v.findViewById(R.id.image);	
				vh.type_color = (View)v.findViewById(R.id.type_color);
				vh.cond_name= (TextView)v.findViewById(R.id.condition_name);
				vh.rank = (TextView)v.findViewById(R.id.rank);
				vh.detail = (TextView)v.findViewById(R.id.detail);
				vh.user_cnt = (TextView)v.findViewById(R.id.people_cnt);
				vh.love_cnt = (TextView)v.findViewById(R.id.love_cnt);
				
				v.setTag(vh);
			}
			else {
				vh = (ViewHolder)v.getTag();
			}
			ConditionAbstract data = items.get(position);
			
			/*이미지 변경 코드 작성할 것 */
			
			//이미지 밑에 색을 변경
			if(data.cond_type == ConditionAbstract.EASY) {
				vh.type_color.setBackgroundResource(R.color.condition_type_easy);
			}
			else if(data.cond_type == ConditionAbstract.HARD) {
				vh.type_color.setBackgroundResource(R.color.condition_type_hard);
			}
			
			// 조건 이름 설정
			vh.cond_name.setText(data.cond_name);
			
			// 조건 구성 설정
			vh.detail.setText(data.cond_compose);
			
			// 순위 설정
			vh.rank.setText(Integer.toString(data.rank));
			
			// 사람 숫자 설정
			vh.user_cnt.setText(Integer.toString(data.user_cnt));
			
			// 사랑 숫자 설정
			vh.love_cnt.setText(Integer.toString(data.love_cnt));

			
			return v;
		}
		public class ViewHolder {		
			ImageView image;
			View type_color;
			TextView cond_name;
			TextView rank;
			TextView detail;
			TextView user_cnt;
			TextView love_cnt;
		}

	}
	
	
	
	/***********************************************
	 * 
	 * 간단조건 Fragment
	 * @author Namhoon
	 *
	 ***********************************************/
	public static class FragmentEasyCondition extends ListFragment {
		
		
		
		/*아래 주석은 나중에 검토 하자 아이템을 클릭하였을때 어떻게 할 것인가 하는 부분임 */
		
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
		

		
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);


			setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, easy.list));
			
			// divider 세팅 부분. 주석처리 가능
			ListView mListView = getListView();
			mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			mListView.setDivider(new ColorDrawable(Color.LTGRAY));
			mListView.setDividerHeight(1);		
		}
		
		public void onListItemClick(ListView l, View v, int position, long id) {			
			//mHost.onItemChecked(position);
			/*
			l.setItemChecked(position, true);
			Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
			startActivity(intent);
			*/
			Toast.makeText(getActivity(), position+" is clicked", 0).show();
			

			Intent intent = new Intent(getActivity(), OptionDetail.class);
			
			//인텐트에 클릭된 신호의 조건이름 정보를 전달
			
			ConditionAbstract data = easy.list.get(position);
			intent.putExtra("cond_name", data.cond_name);
			startActivity(intent);
			//Toast.makeText(getActivity(), position+" is clicked", 0).show();		
		}
		

		
	}
	
	
	
	
	
	/***********************************************
	 * 
	 * 일반조건 Fragment
	 * @author Namhoon
	 *
	 ***********************************************/
	
	public static class FragmentDetailCondition extends Fragment {
		private ViewPager mPager;
		private MyPagerAdapter mPagerAdapter;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.view_pager, container, false);
			
			mPager = (ViewPager)root.findViewById(R.id.pager);
			mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
			mPager.setAdapter(mPagerAdapter);

	        TabPageIndicator mIndicator = (TabPageIndicator)root.findViewById(R.id.indicator);
	        mIndicator.setViewPager(mPager);
			
			return root;
		}
		
	/*********************************************************************************************************************
	//
//					뷰페이저 어댑터
	//
	*********************************************************************************************************************/
		private class MyPagerAdapter extends FragmentStatePagerAdapter {
			private static final int NUM_PAGES = 4;
			private final FragmentManager fm;

			public MyPagerAdapter(FragmentManager fm) {
				super(fm);
				this.fm = fm;
			}
			@Override
			public int getCount() {
				return NUM_PAGES;
			}

			@Override
			public Fragment getItem(int position) {
				switch(position) {
					case 0 :
						return new FragmentDetailConditionList((String)getPageTitle(position));
					case 1 :
						return new FragmentDetailConditionList((String)getPageTitle(position));
					case 2 :
						return new FragmentDetailConditionList((String)getPageTitle(position));
					case 3 :
						return new FragmentDetailConditionList((String)getPageTitle(position));				
					default :
						return null;
				}
			}
			
			@Override
			public CharSequence getPageTitle(int position) {
				switch(position) {
					case 0 :
						return "재무";
					case 1 :
						return "시세";
					case 2 :
						return "기술";
					case 3 :
						return "패턴";
					default :
						return "";
				}
			}
			
		}

	/*********************************************************************************************************************
	//
//					일반조건 - List
	//
	*********************************************************************************************************************/	
		/* 전체 */
		public static class FragmentDetailConditionList extends ListFragment {
			String myCategory;
			
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
			
			public FragmentDetailConditionList() {
				super();
			}
			
			public FragmentDetailConditionList(String pageTitle) {
				super();
				myCategory = pageTitle;			
			}
			
			
			public void onActivityCreated(Bundle savedInstanceState) {
				super.onActivityCreated(savedInstanceState);
				
				if(myCategory == "재무") {
					setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, hard_jaemu.list));
				}
				else if(myCategory == "시세") {
					setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, hard_sise.list));
				}
				else if(myCategory == "기술") {
					setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, hard_kisool.list));
				}
				else if(myCategory == "패턴") {
					setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, hard_pattern.list));
				}
				else {
					//default
					setListAdapter(new MyArrayAdapter(getActivity(), R.layout.activity_condition_item, hard_jaemu.list));
				}

				// divider 세팅 부분. 주석처리 가능
				ListView mListView = getListView();
				mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				mListView.setDivider(new ColorDrawable(Color.LTGRAY));
				mListView.setDividerHeight(1);		
			}
			
			public void onListItemClick(ListView l, View v, int position, long id) {			
				//mHost.onItemChecked(position);
				/*
				l.setItemChecked(position, true);
				Intent intent = new Intent(getActivity(), ActivityStockDetail.class);
				startActivity(intent);
				*/
				
				Toast.makeText(getActivity(), position+" is clicked", 0).show();
				

				Intent intent = new Intent(getActivity(), OptionDetail.class);
				
				ConditionAbstract data;				
				//인텐트에 클릭된 신호의 조건이름 정보를 전달
				if(myCategory == "재무") {
					data = hard_jaemu.list.get(position);
				}
				else if(myCategory == "시세") {
					data = hard_sise.list.get(position);
				}
				else if(myCategory == "기술") {
					data = hard_kisool.list.get(position);
				}
				else if(myCategory == "패턴") {
					data = hard_pattern.list.get(position);
				}
				else {
					data = hard_jaemu.list.get(position);
				}
				intent.putExtra("cond_name", data.cond_name);
				startActivity(intent);
			}
		}
	}
}
