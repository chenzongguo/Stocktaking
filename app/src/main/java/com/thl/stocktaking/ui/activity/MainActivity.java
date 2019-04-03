package com.thl.stocktaking.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thl.stocktaking.R;
import com.thl.stocktaking.ui.activity.base.BaseActivity;


public class MainActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
	}
	public void startActivity(View view){
		Intent intent = new Intent(MainActivity.this, QRCodeScanActivity.class);
		startActivityForResult(intent, 101);
	}
}
