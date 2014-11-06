package com.example.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	FragmentManager fm; //onCreate에서 초기화

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		MyDataBase.initialize(this);

		/* 프래그먼트 관리 */
		fm = getSupportFragmentManager();
		/* 부모의 id로 fragment를 찾기, fragment가 없으면 추가 */
		if(fm.findFragmentById(R.id.frame) == null) {
			FragmentSignal fg_sg = new FragmentSignal();
			fm.beginTransaction().add(R.id.frame, fg_sg, "fragment_signal").commit();
		}
		
	}
	
	public void tabButtonOnClick(View view) {
		
		Fragment fragment = fm.findFragmentById(R.id.frame);
		
		//여기로 진입하면 에러임
		if(fragment == null) {
			FragmentSignal fg_sg = new FragmentSignal();	
			fm.beginTransaction().add(R.id.frame, fg_sg, "fragment_signal").commit();
			return;
		}
		switch(view.getId()) {
			case R.id.signal_bt :
				if(fragment.getTag() == "fragment_signal") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					FragmentSignal fg_sg = new FragmentSignal();
					fm.beginTransaction().replace(R.id.frame, fg_sg, "fragment_signal").commit();
				}
				
				break;
				
				
			/* 검색 activity를 띄우기 */
			case R.id.search_bt :  
				Intent intent = new Intent(this, SearchActivity.class);
				startActivity(intent);
				
				
				
				break;
			
			case R.id.settings_bt :
				if(fragment.getTag() == "fragment_settings") {
					//do nothing
				}
				else {
					/*다른 fragment로 변환 */
					/* fragment를 매번 새로 생성하는데 만들어놓고 Visible만 바꿔서 성능을 향상시키는 것도 고려 */
					
					FragmentSettings fg_st = new FragmentSettings();
					fm.beginTransaction().replace(R.id.frame, fg_st, "fragment_settings").commit();
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
}
