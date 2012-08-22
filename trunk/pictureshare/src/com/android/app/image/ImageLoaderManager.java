package com.android.app.image;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.http.HttpEntity;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.app.net.NetService;
import com.android.app.utils.IOUtils;
import com.android.app.utils.LruCache;
import com.android.app.utils.Utils;

/**
 * 每一个ImageLoaderManager对应一个adapter使用 用ImageLoaderManager(Context context,
 * Handler handler, BaseAdapter adapter)构造
 * 
 * 如果是单个imageView加载图片(非list列表中的imageview) 用 ImageLoaderManager(Context context,
 * Handler handler, ImageView imageView)构造
 * 
 */
public class ImageLoaderManager {

	private static final String TAG = "ImageLoaderManager";

	public static final int QUEUE_LENGTH = 15;
	public static final int MAX_TASK_NUM = 5;
	private LinkedList<ImageLoaderTask> taskQueue;
	private LinkedList<ImageLoaderTask> currentExecuteTaskQueue;
	private HashMap<String, Long> faildUrlTimeRecords;

	private Handler handler;
	private BaseAdapter adapter;
	private ImageView imageView;
	private Context context;
	private LruCache<String, Bitmap> bitmapCache;
	private Bitmap defaultBitmap;

	public ImageLoaderManager(Context context, Handler handler, BaseAdapter adapter) {
		super();
		this.adapter = adapter;
		init(context, handler);
	}

	private void init(Context context, Handler handler) {
		this.handler = handler;
		this.context = context;

		final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 8;
		bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
		taskQueue = new LinkedList<ImageLoaderTask>();
		currentExecuteTaskQueue = new LinkedList<ImageLoaderTask>();
		faildUrlTimeRecords = new HashMap<String, Long>();
	}

	public ImageLoaderManager(Context context, Handler handler, ImageView imageView) {
		super();
		this.imageView = imageView;
		init(context, handler);
	}

	public Bitmap getImage(String url, int defaultImageRes) {
		Bitmap bitmap = null;
		try {
			// 从内存缓存中取图片
			bitmap = bitmapCache.get(url);
			if (bitmap != null) {
				return bitmap;
			}
			// 从sd卡缓存中取图片
			if (Utils.isSDCardEnable() && !Utils.isNullOrEmpty(url)) {
				File file = new File(url);
				if (file.exists()) {
					bitmap = BitmapFactory.decodeFile(url);
					if (bitmap != null) {
						bitmapCache.put(url, bitmap);
						return bitmap;
					}
				}
			}

			if (defaultBitmap == null) {
				defaultBitmap = BitmapFactory.decodeResource(context.getResources(), defaultImageRes);
			}
			bitmap = defaultBitmap;

			Long timeL = faildUrlTimeRecords.get(url);
			if (timeL != null) {
				long time = timeL.longValue();
				if (System.currentTimeMillis() - time > 1000 * 2) {
					faildUrlTimeRecords.remove(url);
				} else {
					return bitmap;
				}
			}

			// 缓存中如果没有,添加异步任务到队列
			addTaskToQueue(new ImageLoaderTask(url));
			executeTask();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			addUrlToFaildUrls(url);
			if (defaultBitmap == null) {
				defaultBitmap = BitmapFactory.decodeResource(context.getResources(), defaultImageRes);
			}
			bitmap = defaultBitmap;
			return bitmap;
		}
		return bitmap;
	}

	public static String getCachePath(String url) {
		return Utils.getCacheDir() + URLEncoder.encode(url) + ".cache";
	}

	/**
	 * 将任务从等待队列中移除,并添加到执行队列,
	 */
	private void executeTask() {
		while (currentExecuteTaskQueue.size() < MAX_TASK_NUM && taskQueue.size() > 0) {
			ImageLoaderTask task = taskQueue.removeFirst();
			currentExecuteTaskQueue.addFirst(task);
			new Thread(task).start();
		}
	}

	/**
	 * 添加任务到队列,等待执行
	 * 
	 * @param task
	 */
	private void addTaskToQueue(ImageLoaderTask task) {

		int j = currentExecuteTaskQueue.indexOf(task);
		if (j >= 0) {
			return;
		}
		int i = taskQueue.indexOf(task);
		if (i >= 0) {
			taskQueue.addFirst(taskQueue.remove(i));
			return;
		}
		if (taskQueue.size() + currentExecuteTaskQueue.size() <= QUEUE_LENGTH) {
			taskQueue.addFirst(task);
		} else {
			taskQueue.removeLast();
			taskQueue.addFirst(task);
		}
	}

	private void addUrlToFaildUrls(String url) {
		faildUrlTimeRecords.put(url, System.currentTimeMillis());
	}

	class ImageLoaderTask implements Runnable {
		private String url;

		@Override
		public boolean equals(Object o) {
			if (o instanceof ImageLoaderTask) {
				ImageLoaderTask ilt = (ImageLoaderTask) o;
				if (this.url.equalsIgnoreCase(ilt.url)) {
					return true;
				}
			}
			return false;
		}

		public ImageLoaderTask(String url) {
			super();
			this.url = url;
		}

		@Override
		public void run() {
			String cachePath = getCachePath(url);
			// 从服务端下载
			if (url != null) {
				Log.i(TAG, "down_url=" + url);
				HttpEntity httpEntity = null;
				try {
					httpEntity = NetService.downloadImg(context, url);
					if (httpEntity == null) {
						taskEnd(true, null);
						return;
					}
					InputStream mInStream = httpEntity.getContent();
					long contentLength = httpEntity.getContentLength();
					Log.i(TAG, "contentLength=" + contentLength);
					if (mInStream == null) {
						taskEnd(true, null);
						return;
					}
					Bitmap bitmap = null;
					File file = null;
					if (!Utils.isSDCardEnable()) {
						bitmap = BitmapFactory.decodeStream(mInStream);
					} else {
						file = IOUtils.saveInputStreamToFile(mInStream, cachePath);
						mInStream.close();
						mInStream = null;
						if (file == null || (file != null && contentLength != file.length())) {
							deleteFile(file);
							return;
						}
						bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
					}
					if (bitmap != null) {
						Log.i("TAG", "contentLength is " + contentLength);
						bitmapCache.put(url, (bitmap));
						taskEnd(true, bitmap);
					} else {
						deleteFile(file);
						return;
					}
					return;
				} catch (Exception e) {
					addUrlToFaildUrls(url);
					taskEnd(true, null);
					e.printStackTrace();
				} finally {
					if (httpEntity != null) {
						try {
							httpEntity.consumeContent();
						} catch (Exception e) {
						}
					}
				}
			}
		}

		private void deleteFile(File file) {
			if (file != null) {
				file.delete();
			}
			addUrlToFaildUrls(url);
			taskEnd(true, null);
			return;
		}

		/**
		 * 任务结束后,通知UI更新,并触发新任务
		 */
		private void taskEnd(boolean notify, final Bitmap bitmap) {
			currentExecuteTaskQueue.remove(this);
			executeTask();
			if (notify && handler != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (adapter != null) {
							adapter.notifyDataSetChanged();
						}
						if (imageView != null && bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}
				});
			}
		}
	}
}
