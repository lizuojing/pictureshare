package com.android.app.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

	public static int touchLen = 40;

	public static int deflut_int = 0;
	public static int left_top_int = 1;
	public static int left_bottom_int = 2;
	public static int right_bottom_int = 3;
	public static int right_top_int = 4;

	public Rect left_top = new Rect(80, 100, touchLen);
	public Rect left_bottom = new Rect(80, 700, touchLen);
	public Rect right_bottom = new Rect(400, 700, touchLen);
	public Rect right_top = new Rect(400, 100, touchLen);
	Paint mPaint = new Paint();

	SurfaceHolder holder;
	int tempRectInt;
	Point tempPoint;
	boolean isMove = true;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			if (left_top.isInArea(event.getX(), event.getY())) {
				tempRectInt = left_top_int;
			} else if (left_bottom.isInArea(event.getX(), event.getY())) {
				tempRectInt = left_bottom_int;
			} else if (right_bottom.isInArea(event.getX(), event.getY())) {
				tempRectInt = right_bottom_int;
			} else if (right_top.isInArea(event.getX(), event.getY())) {
				tempRectInt = right_top_int;
			} else {
				tempRectInt = deflut_int;
			}

			break;

		case MotionEvent.ACTION_MOVE:
			if (tempRectInt != 0) {
				tempPoint = new Point(event.getX(), event.getY());

				isMove = true;
				if (left_top_int == tempRectInt) {
					left_top.changeCenter(tempPoint);
				} else if (left_bottom_int == tempRectInt) {
					left_bottom.changeCenter(tempPoint);
				} else if (right_bottom_int == tempRectInt) {
					right_bottom.changeCenter(tempPoint);
				} else if (right_top_int == tempRectInt) {
					right_top.changeCenter(tempPoint);
				}

			}
			break;
		case MotionEvent.ACTION_UP:
			isMove = false;
			break;
		}
		return true;
	}

	public MyView(Context context) {
		super(context);
		holder = this.getHolder();// 获取holder
		holder.addCallback(this);
		setFocusable(true);

	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = this.getHolder();// 获取holder
		holder.addCallback(this);
		setFocusable(true);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		new Thread(new MyThread()).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	// 内部类的内部类
	class MyThread implements Runnable {

		@Override
		public void run() {

			while (true) {

				if (isMove) {

					Canvas canvas = null;

					canvas = holder.lockCanvas(null);// 获取画布
					canvas.drawColor(Color.BLACK);
					mPaint.setColor(Color.WHITE);
					drawLine(canvas, left_top.getCenterPoint(),
							left_bottom.getCenterPoint());
					drawLine(canvas, left_bottom.getCenterPoint(),
							right_bottom.getCenterPoint());
					drawLine(canvas, right_bottom.getCenterPoint(),
							right_top.getCenterPoint());
					drawLine(canvas, right_top.getCenterPoint(),
							left_top.getCenterPoint());

					drawPointRect(canvas, left_top);
					drawPointRect(canvas, left_bottom);
					drawPointRect(canvas, right_top);
					drawPointRect(canvas, right_bottom);

					holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像

					isMove = false;
				}

				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void drawLine(Canvas canvas, Point p1, Point p2) {
		canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mPaint);
	};

	public void drawPointRect(Canvas canvas, Rect rect) {
		drawLine(canvas, rect.p_CP, rect.p_PP);
		drawLine(canvas, rect.p_PP, rect.p_PC);
		drawLine(canvas, rect.p_PC, rect.p_CC);
		drawLine(canvas, rect.p_CC, rect.p_CP);
	}

}
