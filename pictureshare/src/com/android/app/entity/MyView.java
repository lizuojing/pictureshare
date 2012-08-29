package com.android.app.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.app.R;
import com.android.app.utils.Utils;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = "MyView";

	public static int touchLen = 40;

	public static int deflut_int = 0;
	public static int left_top_int = 1;
	public static int left_bottom_int = 2;
	public static int right_bottom_int = 3;
	public static int right_top_int = 4;

	Paint mPaint = new Paint();

	SurfaceHolder holder;
	int tempRectInt;
	Point tempPoint;
	boolean isMove = true;
	Bitmap bmp;
	BitmapShader shader;
	private BitmapShader bitmapShader = null;
	private Bitmap bitmap = null;
	private Bitmap dot = null;
	private ShapeDrawable shapeDrawable = null;

	private int BitmapWidth, BitmapHeight;

	private Thread thread;

	private Rect left_top;

	private Rect left_bottom;

	private Rect right_bottom;

	private Rect right_top;

	public MyView(Context context) {
		super(context);
		holder = this.getHolder();// 获取holder
		holder.addCallback(this);
		setFocusable(true);
		initPoints(context);
		initBitmap();
	}

	private void initPoints(Context context) {
		left_top = new Rect(Utils.dipToPixels(context, 80), 100, touchLen);
		left_bottom = new Rect(Utils.dipToPixels(context, 80), 600, touchLen);
		right_bottom = new Rect(Utils.getScreenWidth(context)
				- Utils.dipToPixels(context, 80), 600, touchLen);
		right_top = new Rect(Utils.getScreenWidth(context)
				- Utils.dipToPixels(context, 80), 100, touchLen);
		dot = BitmapFactory.decodeResource(getResources(), R.drawable.dot);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = this.getHolder();// 获取holder
		holder.addCallback(this);
		setFocusable(true);
		initPoints(context);
		initBitmap();
	}

	public void setBitmap(String filePath) {
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			this.bitmap = bitmap;
			initBitmap();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
	}

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

	public void initBitmap() {
		Log.i(TAG, "initBitmap create");
		if (bitmap == null) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inSampleSize = 2;//压缩处理 或者 使用缩略图
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.a3431587_s, options);
			// ((BitmapDrawable) getResources().getDrawable(
			// R.drawable.a3431587_s)).getBitmap();
		}
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		BitmapWidth = displayMetrics.heightPixels;
		BitmapHeight = displayMetrics.widthPixels;
		// 构造渲染器BitmapShader
		bmp = Bitmap
				.createScaledBitmap(bitmap, BitmapHeight, BitmapWidth, true);
		bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP,
				Shader.TileMode.CLAMP);
		bmp = toGrayscale(bmp);
		isMove = true;
	}

	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		paint.setAlpha(120);
		// ColorMatrix cm = new ColorMatrix();
		// cm.setSaturation((float)0.3);
		// ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		// paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i(TAG, "suface create");
		thread = new Thread(new MyThread());
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.destroyDrawingCache();
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
		Log.i(TAG, "suface Destroyed");
	}

	// 内部类的内部类
	class MyThread implements Runnable {

		@Override
		public void run() {

			while (true) {

				if (isMove) {

					Canvas canvas = null;

					canvas = holder.lockCanvas(null);// 获取画布
					mPaint.setColor(Color.CYAN);
					mPaint.setAntiAlias(true);
					canvas.save();
					canvas.drawBitmap(bmp, new Matrix(), mPaint);
					canvas.restore();
					drawLine(canvas, left_top.getCenterPoint(),
							left_bottom.getCenterPoint());
					drawLine(canvas, left_bottom.getCenterPoint(),
							right_bottom.getCenterPoint());
					drawLine(canvas, right_bottom.getCenterPoint(),
							right_top.getCenterPoint());
					drawLine(canvas, right_top.getCenterPoint(),
							left_top.getCenterPoint());

					Path path = new Path();
					mPaint.setColor(Color.WHITE);
					path.moveTo(left_top.x, left_top.y);
					path.lineTo(left_bottom.x, left_bottom.y);
					path.lineTo(right_bottom.x, right_bottom.y);
					path.lineTo(right_top.x, right_top.y);
					path.close();

					shapeDrawable = new ShapeDrawable(new PathShape(path,
							BitmapWidth, BitmapHeight));

					// 得到画笔并设置渲染器
					shapeDrawable.getPaint().setShader(bitmapShader);
					// 设置显示区域
					shapeDrawable.setBounds(0, 0, BitmapWidth, BitmapHeight);
					// 绘制shapeDrawable
					shapeDrawable.draw(canvas);
					mPaint.setColor(getResources().getColor(R.color.dotcolor));
					mPaint.setAlpha(200);
					drawPointCircle(canvas, left_top);

					drawPointCircle(canvas, left_bottom);

					drawPointCircle(canvas, right_top);

					drawPointCircle(canvas, right_bottom);

					holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
					isMove = false;

				}
				//
				// try {
				// Thread.sleep(10);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
	}

	public void drawLine(Canvas canvas, Point p1, Point p2) {
		canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mPaint);
	};

	public void drawPointCircle(Canvas canvas, Rect rect) {

		canvas.drawCircle(rect.x, rect.y, 20, mPaint);

	}

}
