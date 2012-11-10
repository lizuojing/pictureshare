package com.android.app.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDataHelper {

	private static final String DB_NAME = "PicApp.db";
	private static final int DB_VERSION = 1;

	private Context mContext;
	private DatabaseHelper mDatabaseHelper;
	private SQLiteDatabase mSQLiteDatabase;

	// 用户表
	private static final String DB_Local_USER_Table = "table_user";
	public static final String KEY_Local_USER_ID = "userid";
	public static final String KEY_Local_USER_NICKNAME = "nickname";
	public static final String KEY_Local_USER_PHONENUMBER = "phone";
	public static final String KEY_Local_USER_SEX = "sex";
	public static final String KEY_Local_USER_BIRTHDAY = "birthday";
	public static final String KEY_Local_USER_DESCRIPTION = "description";

	// 图片表
	public static final String DB_AVATAR_TABLE = "table_avatar";
	private static final String KEY_AVATAR_ID = "id";
	public static final String KEY_AVATAR_AVATARID = "avatarid";
	public static final String KEY_AVATAR_AVATAR = "avatar";// 服务器地址
	public static final String KEY_AVATAR_TITLE = "title";
	public static final String KEY_AVATAR_CREATETIME = "createtime";
	public static final String KEY_AVATAR_LONGITUDE = "longitude";
	public static final String KEY_AVATAR_LATITUDE = "latitude";
	public static final String KEY_AVATAR_XCOORD = "x_coord";
	public static final String KEY_AVATAR_YCOORD = "y_coord";
	

	// 位置表
	public static final String DB_LOCATION_TABLE = "table_location";
	public static final String KEY_LOCATION_ID = "id";
	public static final String KEY_LOCATION_PICID = "picid";
	public static final String KEY_LOCATION_LONGITUDE = "longitude";// 服务器地址
	public static final String KEY_LOCATION_LATITUDE = "latitude";
	public static final String KEY_LOCATION_STATUS = "status";
	public static final String KEY_LOCATION_TYPE = "type";

	// 二级缓存表
	public static final String DB_PICCACHE_TABLE = "table_piccache";
	public static final String KEY_PICCACHE_ID = "picid";
	public static final String KEY_PICCACHE_PARENTID = "parentid";

	// sql for create user table
	private static final String CREATE_LOCAL_USER_TABLE = "CREATE TABLE "
			+ DB_Local_USER_Table + " (" + KEY_Local_USER_ID
			+ " INTEGER PRIMARY KEY," + KEY_Local_USER_NICKNAME + " TEXT,"
			+ KEY_Local_USER_PHONENUMBER + " TEXT," + KEY_Local_USER_SEX
			+ " INTEGER," + KEY_Local_USER_DESCRIPTION + " TEXT,"
			+ KEY_Local_USER_BIRTHDAY + " TEXT)";

	// sql for create avatar table
	private static final String CREATE_AVATAR_TABLE = "CREATE TABLE "
			+ DB_AVATAR_TABLE + " (" + KEY_AVATAR_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_AVATAR_AVATARID
			+ " TEXT," + KEY_AVATAR_AVATAR + " TEXT," + KEY_AVATAR_TITLE
			+ " TEXT," + KEY_AVATAR_LONGITUDE + " LONG," + KEY_AVATAR_LATITUDE
			+ " LONG," + KEY_AVATAR_CREATETIME + " TEXT)";

	// sql for create location table
	private static final String CREATE_LOCATION_TABLE = "CREATE TABLE "
			+ DB_LOCATION_TABLE + " (" + KEY_LOCATION_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LOCATION_PICID
			+ " INTEGER," + KEY_LOCATION_LONGITUDE + " TEXT,"
			+ KEY_LOCATION_LATITUDE + " TEXT," + KEY_LOCATION_STATUS + " LONG,"
			+ KEY_LOCATION_TYPE + " INTEGER )";

	// sql for create picture cache table
	private static final String CREATE_PICCACHE_TABLE = "CREATE TABLE "
			+ DB_PICCACHE_TABLE + " (" + KEY_PICCACHE_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PICCACHE_PARENTID
			+ " INTEGER )";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_LOCAL_USER_TABLE);
			db.execSQL(CREATE_AVATAR_TABLE);
			db.execSQL(CREATE_LOCATION_TABLE);
			db.execSQL(CREATE_PICCACHE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	public LocalDataHelper(Context context) {
		mContext = context;
	}

	public void open() throws SQLException {
		mDatabaseHelper = new DatabaseHelper(mContext);
		mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
	}

	public void close() {
		mDatabaseHelper.close();
	}
}
