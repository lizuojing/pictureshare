package com.android.app.view;



import java.util.LinkedList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.app.R;

public class FanShapedView extends View {
	private static final String TAG = "MainActivity";
	
	Context context;
	private Paint paint;
	
	private Resources resources;
	
	private Rect allBgRect;
	private Rect firstImgRect;
	private Rect secondImgRect;
	private Rect thirdImgRect;
	private Rect fourImgRect;
	private Rect fiveImgRect;
	private Rect sixtImgRect;
	private Rect onfirstImgRect;
	
	private Bitmap fanshapedBitmap;
	
	private static  int screenWidth = -1;
	private static  int screenHeight = -1;
	private int downX = -1;
	private int downY = -1;
	public static int orient = 1;
	private boolean initPass;
	private boolean autoInitRes = true;
	private boolean actionPass;
	private boolean startActivity;
	
	private PaddlingAction paddlingAction = PaddlingAction.no;
	
	
	private static LinkedList<Bitmap> menuBitmaps = new LinkedList<Bitmap>();
	
	private static LinkedList<String> homeItem = new LinkedList<String>();
	
	public FanShapedView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public FanShapedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	/**
	 * 
	 * <p>Title: </p> 
	 * <p>Description: You can use the constructor to initialize the pictures and text</p> 
	 * @param context
	 * @param paddlingBitmaps Shown in the picture
	 * @param homeItem Shown in the text
	 */
	public FanShapedView(Context context, LinkedList<Bitmap> menuBitmaps, LinkedList<String> homeItem){
		this(context);
		FanShapedView.menuBitmaps = menuBitmaps;
		FanShapedView.homeItem = homeItem;
		autoInitRes = false;
	}
	
	private void init(){
		initPass = true;
		resources = context.getResources();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		
		if(menuBitmaps==null) {
			menuBitmaps = new LinkedList<Bitmap>();
		}
		
		if(homeItem==null) {
			homeItem = new LinkedList<String>();
		}
		
		
		fanshapedBitmap = BitmapFactory.decodeResource(resources, R.drawable.panzi);
		
		if (autoInitRes) {
			Bitmap temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_one);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_two);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_three);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_four);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_five);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_six);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_seven);
			menuBitmaps.add(temp);
			temp = BitmapFactory.decodeResource(resources, R.drawable.turntable_icon_eight);
			menuBitmaps.add(temp);
			
			homeItem.add("contact");
			homeItem.add("im");
			homeItem.add("call");
			homeItem.add("message");
			homeItem.add("usage");
			homeItem.add("money");
			homeItem.add("sns");
			homeItem.add("more");
		}
		
	}
	
	private void initOnDraw(boolean initPass){
		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		Log.i(TAG, "screenWidth="+screenWidth+"  screenHeight="+screenHeight);
		screenWidth = screenWidth < screenHeight ? screenWidth:screenHeight;
		
		allBgRect = new Rect(0, 0, screenWidth, screenWidth);
		
		firstImgRect = new Rect((int)(0.81 * screenWidth), (int)(0.78 * screenWidth), (int)(0.95 * screenWidth), (int)(0.93 * screenWidth));
		onfirstImgRect = new Rect((int)(0.78 * screenWidth), (int)(0.76 * screenWidth), (int)(screenWidth), (int)(screenWidth));
		secondImgRect = new Rect((int)(0.045 * screenWidth), (int)(0.76 * screenWidth), (int)(0.2 * screenWidth), (int)(0.93 * screenWidth));
		thirdImgRect = new Rect((int)(0.14 * screenWidth), (int)(0.48 * screenWidth), (int)(0.29 * screenWidth), (int)(0.66 * screenWidth));
		fourImgRect = new Rect((int)(0.31 * screenWidth), (int)(0.26 * screenWidth), (int)(0.46 * screenWidth), (int)(0.44 * screenWidth));
		fiveImgRect = new Rect((int)(0.53 * screenWidth), (int)(0.11 * screenWidth), (int)(0.675 * screenWidth), (int)(0.285 * screenWidth));
		sixtImgRect = new Rect((int)(0.79 * screenWidth), (int)(0.02 * screenWidth), (int)(0.94 * screenWidth), (int)(0.19 * screenWidth));
		
		initPass = false;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initOnDraw(initPass);
		canvas.drawColor(0x00000000);
		canvas.drawBitmap(fanshapedBitmap, null, allBgRect, paint);
		
//		canvas.drawBitmap(menuBitmaps.getFirst(), null, firstImgRect, null);
//		canvas.save();//绘制中间图标
		
		canvas.rotate(-80);
		canvas.translate(-(0.927f *screenWidth), -(0.5f *screenWidth));
		canvas.drawBitmap(menuBitmaps.get(1), null, secondImgRect, null);
		canvas.restore();//80度方向图标
		
//		canvas.save();
//		canvas.rotate(-60);
//		canvas.translate(-(0.61f *screenWidth), -(0.11f *screenWidth));
//		canvas.drawBitmap(menuBitmaps.get(2), null, thirdImgRect, null);
//		canvas.restore();
		
		Rect bgRect = new Rect((int)(0.10 * screenWidth), (int)(0.23 * screenWidth), (int)(0.65 * screenWidth), (int)(0.50 * screenWidth));
		Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.select_block_blue);
		canvas.save();
		canvas.rotate(-45);//45度方向图标
		canvas.translate(-(0.37f *screenWidth), (0.25f *screenWidth));
		canvas.drawBitmap(bg, null, bgRect, null);
		canvas.restore();
		
		canvas.save();
		canvas.rotate(-45);//45度方向图标
		canvas.translate(-(0.37f *screenWidth), (0.25f *screenWidth));
		canvas.drawBitmap(menuBitmaps.get(2), null, fourImgRect, null);
		canvas.restore();
		
//		canvas.save();
//		canvas.rotate(-30);
//		canvas.translate(-(0.18f *screenWidth), (0.27f *screenWidth));
//		canvas.drawBitmap(menuBitmaps.get(4), null, fiveImgRect, null);
//		canvas.restore();
//		
		canvas.save();
		canvas.rotate(-10);//10度方向图标
		canvas.translate(-(0.04f *screenWidth), (0.22f *screenWidth));
		canvas.drawBitmap(menuBitmaps.get(3), null, sixtImgRect, null);
		canvas.restore();
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (onfirstImgRect!=null&&onfirstImgRect.contains(x, y)) {//隐藏
				Log.i(TAG, "隐藏");
//				this.setVisibility(GONE);
				downX = -1;
				downY = -1;
				actionPass = false;
				paddlingAction = PaddlingAction.no;
				return this.performClick();
			}
			if (secondImgRect!=null&&secondImgRect.contains(x, y)) {
				moveDownHandle();
			}
			if (thirdImgRect!=null&&thirdImgRect.contains(x, x)) {
				moveDownHandle();
				moveDownHandle();
			}
			if (fourImgRect!=null&&fourImgRect.contains(x, x)) {
				moveDownHandle();
				moveDownHandle();
				moveDownHandle();
			}
			if (fiveImgRect!=null&&fiveImgRect.contains(x, x)) {
				moveDownHandle();
				moveDownHandle();
				moveDownHandle();
				moveDownHandle();
			}
			if (sixtImgRect!=null&&sixtImgRect.contains(x, x)) {
				moveDownHandle();
				moveDownHandle();
				moveDownHandle();
				moveDownHandle();
				moveDownHandle();
			}
			if (allBgRect!=null&&allBgRect.contains(x, y)) {
				Log.i(TAG, "ACTION_DOWN");
				downX = x;
				downY = y;
				actionPass = true;
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			if(actionPass){
				if (downX + screenWidth/10 < x || downY - screenWidth/10 > y) {//往上转一下
					Log.i(TAG, "往上转一下");
					paddlingAction = PaddlingAction.up;
				}else if (downX - screenWidth/10 > x || downY + screenWidth/10 < y) {
					Log.i(TAG, "往下转一下----------");
					paddlingAction = PaddlingAction.down;
				}
				
			}
			break;
		case MotionEvent.ACTION_UP:
			if (paddlingAction != PaddlingAction.no) {
				if (paddlingAction == PaddlingAction.up) {
					orient = 1;
					moveUpHandle();
				}else if (paddlingAction == PaddlingAction.down) {
					orient = 2;
					moveDownHandle();
				}
				refreshView();
			}
			
			downX = -1;
			downY = -1;
			actionPass = false;
			paddlingAction = PaddlingAction.no;
			
			startActivity = true;
			refreshView();
			return this.performClick();
//			break;
		default:
			break;
		}
		return true;
	}
	

	

	/**
	 * 向下
	 */
	public void moveDownHandle(){
		Bitmap temp = menuBitmaps.getFirst();
		menuBitmaps.removeFirst();
		menuBitmaps.addLast(temp);
		String str = homeItem.getFirst();
		homeItem.removeFirst();
		homeItem.addLast(str);
		
	}
	
	/**
	 * 向上
	 */
	public void moveUpHandle(){
		Bitmap temp = menuBitmaps.getLast();
		menuBitmaps.removeLast();
		menuBitmaps.addFirst(temp);
		String str = homeItem.getLast();
		homeItem.removeLast();
		homeItem.addFirst(str);
	}
	
	public Bitmap getFirstBitmap(){
		return menuBitmaps.getFirst();
	}
	
	public String getFirstItem(){
		return homeItem.getFirst();
	}
	
	public boolean isStartActivity() {
		boolean temp = startActivity;
		startActivity = false;
		return temp;
	}
	
	private Handler handler = new Handler();
	
	private void refreshView() {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Log.i(TAG, "refreshView");
				invalidate();
			}
		});
	}
	
    public void recycle() {
		menuBitmaps.remove();
		homeItem.remove();
		menuBitmaps = null;
		homeItem = null;
		screenWidth = -1;
		screenHeight = -1;
    }
    
	enum PaddlingAction{
		no,up,down;
	}
}

