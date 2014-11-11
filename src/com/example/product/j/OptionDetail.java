package com.example.product.j;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.product.R;

public class OptionDetail extends ActionBarActivity {
	ImageView imgLikeNum, imgLikePlus;
	TextView txtOptionName, txtOptionIntroduct, txtOptionDetail, txtLikeNum, txtUseNum;
	View informView;
	Button btnSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_detail);
		imgLikeNum = (ImageView)findViewById(R.id.imgLikeNum);
		imgLikePlus = (ImageView)findViewById(R.id.imgLike);
		txtOptionName = (TextView)findViewById(R.id.txtOptionName);
		txtOptionIntroduct = (TextView)findViewById(R.id.txtInform);
		txtOptionDetail = (TextView)findViewById(R.id.txtDetail);
		txtLikeNum = (TextView)findViewById(R.id.txtLikeNum);
		txtUseNum = (TextView)findViewById(R.id.txtUseNum);
		imgLikePlus.setTag("1");
		
		imgLikePlus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(imgLikePlus.getTag()=="2"){
					imgLikePlus.setImageResource(R.drawable.redheart);
					int num = Integer.parseInt(txtLikeNum.getText().toString());
					num+=1;
					imgLikePlus.setTag("1");
					txtLikeNum.setText(""+(num));
				}else{
					imgLikePlus.setImageResource(R.drawable.grayheart);
					int num = Integer.parseInt(txtLikeNum.getText().toString());
					num-=1;
					imgLikePlus.setTag("2");
					txtLikeNum.setText(""+(num));
				}
				
				// TODO Auto-generated method stub
				
			}
		});
	}
}
