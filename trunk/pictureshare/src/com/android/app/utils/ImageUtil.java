package com.android.app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.android.app.data.SettingLoader;

public class ImageUtil {

	public static InputStream getRequest(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		if (conn.getResponseCode() == 200) {
			return conn.getInputStream();
		}
		return null;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	public static Drawable loadImageFromUrl(String url) {
		URL m;
		InputStream i = null;
		try {
			m = new URL(url);
			i = (InputStream) m.getContent();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(i, "src");
		return d;
	}

	public static Drawable getDrawableFromUrl(String url) throws Exception {
		return Drawable.createFromStream(getRequest(url), null);
	}

	public static Bitmap getBitmapFromUrl(String url) throws Exception {
		byte[] bytes = getBytesFromUrl(url);
		return byteToBitmap(bytes);
	}

	public static Bitmap getRoundBitmapFromUrl(String url, int pixels)
			throws Exception {
		byte[] bytes = getBytesFromUrl(url);
		Bitmap bitmap = byteToBitmap(bytes);
		return toRoundCorner(bitmap, pixels);
	}

	public static byte[] getBytesFromUrl(String url) throws Exception {
		return readInputStream(getRequest(url));
	}

	public static Bitmap byteToBitmap(byte[] byteArray) {
		if (byteArray.length != 0) {
			return BitmapFactory
					.decodeByteArray(byteArray, 0, byteArray.length);
		} else {
			return null;
		}
	}

	public static Drawable byteToDrawable(byte[] byteArray) {
		ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);
		return Drawable.createFromStream(ins, null);
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = null;
		try {
			bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight(),
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									: Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
					.getIntrinsicHeight());
			drawable.draw(canvas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 图片去色,返回灰度图片
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = null;
		try {
			bmpGrayscale = Bitmap.createBitmap(width, height,
					Bitmap.Config.RGB_565);
			Canvas c = new Canvas(bmpGrayscale);
			Paint paint = new Paint();
			ColorMatrix cm = new ColorMatrix();
			cm.setSaturation(0);
			ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
			paint.setColorFilter(f);
			c.drawBitmap(bmpOriginal, 0, 0, paint);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bmpGrayscale;
	}

	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(toGrayscale(bmpOriginal), pixels);
	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

		Bitmap output = null;
		try {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
					Config.ARGB_8888);
			Canvas canvas = new Canvas(output);

			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			RectF rectF = new RectF(rect);
			float roundPx = pixels;

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return output;
	}

	/**
	 * compress bitmap
	 * 
	 * @param filePath
	 * @param newW
	 * @param newH
	 * @return
	 */
	public static Bitmap getThumbnail(final String filePath, final int newW,
			final int newH, int orientation) {
		Bitmap bitmap = getBitmapFromGalleryThumbnailCache(filePath);
		if (bitmap != null) {
			return bitmap;
		}
		bitmap = scaleBitmap(filePath, newW, newH, orientation);
		// 异步保存图片到缓存
		if (bitmap != null) {
			new AsyncTask<Bitmap, Object, Object>() {
				@Override
				protected Result doInBackground(Bitmap... params) {
					saveBitmapToGalleryThumbnailCache(filePath, params[0]);
					return null;
				}
			}.execute(bitmap);
		}
		return bitmap;
	}

	private static Bitmap scaleBitmap(final String filePath, final int newW,
			final int newH, int orientation) {
		Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(filePath, options);
		int originalH = options.outHeight;
		int originalW = options.outWidth;
		final float scaleWidth = ((float) newW) / originalW;
		final float scaleHeight = ((float) newH) / originalH;
		float scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
		options.inJustDecodeBounds = false;
		options.inSampleSize = (int) (1 / scale);
		options.inDither = true;
		bitmap = BitmapFactory.decodeFile(filePath, options);
		Matrix matrix = new Matrix();
		matrix.postRotate(orientation);
		scale = scale
				/ (1.0f / ((int) (1 / scale) <= 1 ? 1 : (int) (1 / scale)));
		matrix.postScale(scale, scale);
		if (bitmap == null) {
			return bitmap;
		}
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap
				.getHeight(), matrix, true);
		return bitmap;
	}

	private static void saveBitmapToGalleryThumbnailCache(String filePath,
			Bitmap bitmap) {
		String fileName = getFileNameNoExtension(filePath);
		File dir = SettingLoader.getGalleryThumbnailCacheDir();
		if (bitmap == null) {
			return;
		}

		File f = new File(dir, fileName);// 构建文件
		OutputStream os = null;
		try {
			os = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static Bitmap getBitmapFromGalleryThumbnailCache(String filePath) {
		String fileName = getFileNameNoExtension(filePath);
		String thumbnailFilePath = SettingLoader.getGalleryThumbnailCacheDir()
				.getAbsolutePath()
				+ "/" + fileName;
		return BitmapFactory.decodeFile(thumbnailFilePath);
	}

	private static String getFileNameNoExtension(String filePath) {
		if (Utils.isNullOrEmpty(filePath)) {
			return "";
		}
		int begin = filePath.lastIndexOf("/") + 1;
		int end = filePath.lastIndexOf(".");
		int length = filePath.length();
		String fileName = "";
		if (end < length && begin < end) {
			fileName = filePath.substring(begin, end);
		}
		return fileName;
	}

	/**
	 * compress bitmap
	 * 
	 * @param bm
	 * @param newW
	 * @param newH
	 * @param ops
	 * @return
	 */
	public static Bitmap compressBitmap(final Bitmap bm, final int newW,
			final int newH) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		if ((newW < width) || (newH < height)) {
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) newW) / width;
			float scaleHeight = ((float) newH) / height;
			float scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
			matrix.postScale(scale, scale);
			Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
					matrix, true);
			return bitmap;
		} else {
			return bm;
		}
	}

	/**
	 * 保存图片
	 * @param path
	 * @param bitmap
	 * @return
	 */
	public static boolean writeImage(String path, Bitmap bitmap) {
		boolean result = false;

		try { // catches IOException below
			if (!Utils.isNullOrEmpty(path) && bitmap != null) {
				File f = new File(path);
				FileOutputStream osf = new FileOutputStream(f);
				result = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, osf);
				osf.flush();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			result = false;
		}

		return result;
	}

}
