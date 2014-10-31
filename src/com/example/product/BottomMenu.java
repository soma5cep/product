package com.example.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomMenu {
	
	// ALL, 전체, 개별, 알람 4개 메뉴가 있는 Fragment
	public static class BottomMenu_1 extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.bottom_menu_1, container, false);
			return root;			
		}
	}

}
