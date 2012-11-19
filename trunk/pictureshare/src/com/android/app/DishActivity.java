package com.android.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

import com.android.app.entity.Avatar;
import com.android.app.service.PicService;
import com.android.app.utils.Utils;
import com.android.app.view.RotateView;

/**
 * 拍照页面
 * 
 * @author Administrator
 * 
 */
public class DishActivity extends ActivityGroup implements OnTouchListener,
		AnimationListener {
	private static final String TAG = "PicTakeActivity";
	private String filePath;

	private LinearLayout eachLayout;
	private static int screenWidth = -1;
	private int downX = -1;
	private int downY = -1;
	private int orient = -1;//
	private PaddlingAction paddlingAction = PaddlingAction.no;
	private RotateView rotateView;
	private int number;
	private Avatar avatar;

	enum PaddlingAction {
		no, up, down;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dish);

		PicService.allActivity.add(this);

		eachLayout = (LinearLayout) findViewById(R.id.each_layout);
		number = getIntent().getIntExtra("number", -1);

		Intent intent = new Intent(this, PicTakeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("mCurrentFile",
				getIntent().getStringExtra("mCurrentFile"));
		intent.putExtra("number", number);
		eachLayout.addView(getLocalActivityManager().startActivity("contact",
				intent).getDecorView());

		screenWidth = Utils.getScreenWidth(this);
		initComponents();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initComponents() {
		rotateView = (RotateView) findViewById(R.id.rotateView);
		rotateView.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "ACTION_DOWN");
			downX = x;
			downY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			if (downX + screenWidth / 10 < x || downY - screenWidth / 10 > y) {// 往上转一下
				Log.i(TAG, "往上转一下");
				paddlingAction = PaddlingAction.up;
			} else if (downX - screenWidth / 10 > x
					|| downY + screenWidth / 10 < y) {
				Log.i(TAG, "往下转一下----------");
				paddlingAction = PaddlingAction.down;
			}

			if (paddlingAction != PaddlingAction.no) {
				if (paddlingAction == PaddlingAction.up) {
					orient = 1;
					moveUpHandle();
				} else if (paddlingAction == PaddlingAction.down) {
					orient = 2;
					moveDownHandle();
				}
			}
			break;
		case MotionEvent.ACTION_UP:

			downX = -1;
			downY = -1;
			paddlingAction = PaddlingAction.no;

			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 执行逆时针动画
	 * 
	 * (负数from——to正数:顺时针旋转) (负数from——to负数:逆时针旋转) (正数from——to正数:顺时针旋转)
	 * (正数from——to负数:逆时针旋转)
	 */
	private void moveDownHandle() {
		Log.i(TAG, "move down");
		orient = 0;
		// rotateView.refreshView(position);
		rotateView.startAnimation(createRotateAnim(0.f, -38.f, 1f, 1f));
	}

	/**
	 * 执行顺时针动画
	 */
	private void moveUpHandle() {
		Log.i(TAG, "move up");
		orient = 1;
		// rotateView.refreshView(position);
		rotateView.startAnimation(createRotateAnim(0.f, 38.f, 1f, 1f));
	}

	/**
	 * 动画
	 * 
	 * @param fromDegrees
	 *            起始角度
	 * @param toDegrees
	 *            结束角度
	 */
	private RotateAnimation createRotateAnim(float fromDegrees,
			float toDegrees, float pivotXValue, float pivotYValue) {
		// 动画声明
		RotateAnimation anim = new RotateAnimation(fromDegrees, toDegrees,
				Animation.RELATIVE_TO_PARENT, pivotXValue,
				Animation.RELATIVE_TO_PARENT, pivotYValue);
		// 设置动画执行时间
		anim.setDuration(200);
		// 设置动画执行次数
		anim.setRepeatCount(0);
		// 设置动画渲染器
		anim.setInterpolator(AnimationUtils.loadInterpolator(this,
				android.R.anim.accelerate_interpolator));
		// 设置动画结束时停止在结束的位置
		anim.setFillAfter(true);
		// 动画监听
		anim.setAnimationListener(this);
		return anim;
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// rotateView.refreshView(orient);
		rotateView.refreshView(orient);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}
