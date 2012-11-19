package com.android.app.entity;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.app.PicApp;
import com.android.app.utils.ImageUtil;

/**
 * 图片信息 `
 * 
 * 
 * 
 * @author Administrator
 * 
 */
public class Avatar implements Parcelable {
	private String avatarID;
	private String ownerId;
	private double longitude;
	private double latitude;
	private String path;// 文件名从文件路径中截取尾部部分得到
	private String title;
	private ArrayList<Comment> comments;
	private ArrayList<Point> points;
	private long time;

	public SoftReference<Bitmap> mThumbnailRef;
	private int thumbnailW;
	private int thumbnailH;
	public int orientation;

	// //
	private ImageItem imageItem;

	public ImageItem getImageItem() {
		return imageItem;
	}

	public void setImageItem(ImageItem imageItem) {
		this.imageItem = imageItem;
	}

	class Point {
		int pointx;
		int pointy;

		public Point(int x, int y) {
			pointx = x;
			pointy = y;
		}
	}

	public Avatar() {
		thumbnailW = PicApp.getScreenWidth() / 4;
		thumbnailH = thumbnailW;
	}

	public Avatar(Parcel in) {
		this.orientation = in.readInt();
		this.thumbnailH = in.readInt();
		this.thumbnailW = in.readInt();
	}

	public String getAvatarID() {
		return avatarID;
	}

	public void setAvatarID(String avatarID) {
		this.avatarID = avatarID;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Bitmap getThumbnail() {
		return getThumbnail(thumbnailW, thumbnailH);
	}

	public Bitmap getThumbnail(int width, int height) {
		if (mThumbnailRef != null && mThumbnailRef.get() != null) {
			return mThumbnailRef.get();
		}
		if (mThumbnailRef != null) {
			mThumbnailRef.clear();
		}
		Bitmap bitmap = ImageUtil.getThumbnail(this.getPath(), width, height,
				orientation);
		mThumbnailRef = new SoftReference<Bitmap>(bitmap);
		return bitmap;
	}

	public static final Parcelable.Creator<Avatar> CREATOR = new Creator<Avatar>() {

		@Override
		public Avatar[] newArray(int size) {
			return new Avatar[size];
		}

		@Override
		public Avatar createFromParcel(Parcel source) {
			return new Avatar(source);
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(thumbnailH);
		dest.writeInt(thumbnailW);
		// dest.writeParcelable(mThumbnail, flags);
	}

}
