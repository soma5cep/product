package com.example.product.j;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.product.R;

public class OptionDetail extends ActionBarActivity {
	ImageView imgLikeNum, imgLikePlus;
	TextView txtOptionName, txtOptionIntroduct, txtOptionDetail, txtLikeNum,
			txtUseNum;
	View informView;
	Button btnSubmit;
	ListView lvParam;
	int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String optionName = intent.getStringExtra("name");
		type = type+0;
		if (type == 0) {
			setContentView(R.layout.activity_option_detail);
			txtOptionDetail = (TextView) findViewById(R.id.txtDetail);
		}else{
			LayoutInflater infalInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			setContentView(R.layout.activity_option_detail2);
			LinearLayout inLayout = (LinearLayout) findViewById(R.id.llChild);
			for (int i = 0; i < 2; i++) {
				LinearLayout route = (LinearLayout) infalInflater.inflate(
						R.layout.expandablelistview_child2_child, null);
				EditText txtParamName1 = (EditText) route.findViewById(R.id.txtParamName1);
				EditText txtParamName2 = (EditText) route.findViewById(R.id.txtParamName2);
				TextView txtParam1 = (TextView) route.findViewById(R.id.txtParam1);
				TextView txtParam2 = (TextView) route.findViewById(R.id.txtParam2);
				txtParam1.setText((char)('A'+2*i)+"");
				txtParam2.setText((char)('A'+2*i)+"");
				txtParamName1.setText("");
				txtParamName2.setText("");
				txtParamName1.setHint("인자 값");
				txtParamName2.setHint("인자 값");
				txtParamName1.setEnabled(false);
				txtParamName2.setEnabled(false);
				inLayout.addView(route);
			}
		}

		imgLikeNum = (ImageView) findViewById(R.id.imgLikeNum);
		imgLikePlus = (ImageView) findViewById(R.id.imgLike);
		txtOptionName = (TextView) findViewById(R.id.txtOptionName);
		txtOptionIntroduct = (TextView) findViewById(R.id.txtInform);
		txtLikeNum = (TextView) findViewById(R.id.txtLikeNum);
		txtUseNum = (TextView) findViewById(R.id.txtUseNum);
		imgLikePlus.setTag("2");
		imgLikePlus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (imgLikePlus.getTag() == "2") {
					imgLikePlus.setImageResource(R.drawable.redheart);
					int num = Integer.parseInt(txtLikeNum.getText()
							.toString());
					num += 1;
					imgLikePlus.setTag("1");
					txtLikeNum.setText("" + (num));
				} else {
					imgLikePlus.setImageResource(R.drawable.grayheart);
					int num = Integer.parseInt(txtLikeNum.getText()
							.toString());
					num -= 1;
					imgLikePlus.setTag("2");
					txtLikeNum.setText("" + (num));
				}

				// TODO Auto-generated method stub

			}
		});
	}
}
