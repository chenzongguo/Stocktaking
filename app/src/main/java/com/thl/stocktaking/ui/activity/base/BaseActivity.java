package com.thl.stocktaking.ui.activity.base;


import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


public class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(arg0);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			setTranslucentStatus(true);
//			SystemBarTintManager tintManager = new SystemBarTintManager(this);
//			tintManager.setStatusBarTintEnabled(true);
//			tintManager.setStatusBarTintResource(R.color.blue);//状态栏所需颜色
//		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onStart() {
//		View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//        | View.SYSTEM_UI_FLAG_FULLSCREEN
//        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//        | View.SYSTEM_UI_FLAG_LOW_PROFILE);        
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	return true;
         }
         return super.onKeyDown(keyCode, event);
     }

	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(null != this.getCurrentFocus()){
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super .onTouchEvent(event);
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
