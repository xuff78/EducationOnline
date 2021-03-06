package com.education.online.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avoscloud.leanchatlib.controller.ChatManager;
import com.avoscloud.leanchatlib.utils.LogUtils;
import com.education.online.R;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.ImageUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseFrameAct extends AppCompatActivity {


	public ImageButton btnHome, btnBack;
	private TextView left_text, right_text;
	public LayoutInflater inflater;
	public boolean backflag;
	private RelativeLayout rl;
	private ClickListener listener;
	protected Fragment currentFrg;
	protected ImageLoader loader;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loader=ImageUtil.initImageLoader(this);
	}
	
	
	@Override
	public void setContentView(int layoutResID) {

		super.setContentView(R.layout.app_frame);
		listener = new ClickListener();
		btnHome=(ImageButton) findViewById(R.id.back_home_imagebtn);
		btnHome.setOnClickListener(listener);
		btnBack=(ImageButton)findViewById(R.id.back_imagebtn);
		btnBack.setOnClickListener(listener);
		rl = (RelativeLayout) findViewById(R.id.heder_layout);

		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View content = inflater.inflate(layoutResID, null);

		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.app_frame_content);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);

		frameLayout.addView(content, -1, layoutParams);
	}

	private final class ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.back_home_imagebtn) {
//				ActUtil.showHome(BaseFrameAct.this);
			} else if (id == R.id.back_imagebtn) {
				onBackPressed();
			}
		}
	}

	public void openFragment(int res, Fragment frg) {
		currentFrg=frg;
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		ft.replace(res, frg);
		ft.commit();
	}

	protected boolean filterException(Exception e) {
		if (e != null) {
			e.printStackTrace();
			ToastUtils.displayTextShort(this, e.getMessage());
			return false;
		} else {
			return true;
		}
	}

	protected void _setHeaderTitle(String title) {
		TextView tv = (TextView) findViewById(R.id.header_title_tv);
		tv.setVisibility(View.VISIBLE);
		tv.setText(title);
	}

	protected View _setHeaderGone() {
		rl.setVisibility(View.GONE);
		return rl;
	}

	protected View _setHeaderShown() {
		rl.setVisibility(View.VISIBLE);
		return rl;
	}

	/** 隐藏左边后退按钮 */
	protected void _setLeftBackGone() {
		ImageButton btn = (ImageButton) findViewById(R.id.back_imagebtn);
		btn.setVisibility(View.GONE);
	}

	protected void _setLeftBackListener(OnClickListener listener) {
		ImageButton btn = (ImageButton) findViewById(R.id.back_imagebtn);
		btn.setOnClickListener(listener);
	}

	protected void _setRightHomeListener(OnClickListener listener) {
		ImageButton btn = (ImageButton) findViewById(R.id.back_home_imagebtn);
		btn.setOnClickListener(listener);
	}

	protected void _setRightHome(int res, OnClickListener listener) {
		ImageButton btn = (ImageButton) findViewById(R.id.back_home_imagebtn);
		btn.setVisibility(View.VISIBLE);
		btn.setOnClickListener(listener);
		btn.setImageResource(res);
	}

	/** 隐藏右边主页按钮 */
	protected void _setRightHomeGone() {
		btnHome.setVisibility(View.GONE);
	}

	/** 显示右边主页文字按钮 */
	protected void _setRightHomeText(String name, OnClickListener listener) {
		btnHome.setVisibility(View.GONE);
		TextView right_text= (TextView) findViewById(R.id.right_text);
		right_text.setVisibility(View.VISIBLE);
		right_text.setText(name);
		right_text.setOnClickListener(listener);
	}

	protected void _setRightHomeTextColor(int resourseid){
		TextView right_text= (TextView) findViewById(R.id.right_text);
		right_text.setTextColor(resourseid);
	}

	protected void _setLeftBackText(String name, OnClickListener listener) {
		btnBack.setVisibility(View.GONE);
		TextView left_text= (TextView) findViewById(R.id.left_text);
		left_text.setVisibility(View.VISIBLE);
		left_text.setText(name);
		left_text.setOnClickListener(listener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode && backflag) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (ActUtil.isLogin(this)&&ChatManager.getInstance().getSelfId() == null) {
			ActUtil.initChatUser(this);
		}
//			Intent intent = new Intent(this, MainPage.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.putExtra("Login", true);
//			startActivity(intent);
//		}else
		loader=ImageUtil.initImageLoader(getApplicationContext());
	}

	@Override
	protected void onStop() {
		super.onStop();
		KeyBoardCancle();
	}

	public void KeyBoardCancle() {

		View view = getWindow().peekDecorView();
		if (view != null) {

			InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	
}
