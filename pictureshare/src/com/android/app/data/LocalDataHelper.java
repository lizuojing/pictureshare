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

	// table for comment
	private static final String DB_COMMENT_TABLE = "table_comment";
	public static final String KEY_COMMNET_ID = "id";
	public static final String KEY_COMMENT_COMMENTID = "commentid";
	public static final String KEY_COMMENT_AVATARID = "avatarid";// 隶属的feed
	// 回复的父评论Id 注：如果该评论是一条评论，该parentId为0，如果该评论是一条回复，那么parentId为回复的那一条评论commentId
	public static final String KEY_COMMENT_PARENTID = "parentid";
	public static final String KEY_COMMENT_NAME = "name";
	public static final String KEY_COMMENT_PHONE_S = "pnum_s";
	public static final String KEY_COMMENT_NAME_S = "name_s";
	public static final String KEY_COMMENT_AVATAR = "avatar";
	public static final String KEY_COMMENT_CONTENT = "content";
	public static final String KEY_COMMENT_CREATETIME = "createtime";
	
	//sql for create user table
	private static final String CREATE_LOCAL_USER_TABLE = "CREATE TABLE " + DB_Local_USER_Table + 
														 		" (" + KEY_Local_USER_ID + " INTEGER PRIMARY KEY," + 
														 		KEY_Local_USER_NICKNAME + " TEXT," + 
														 		KEY_Local_USER_PHONENUMBER + " TEXT," + 
														 		KEY_Local_USER_SEX + " INTEGER," + 
														 		KEY_Local_USER_DESCRIPTION + " TEXT," + 
														 		KEY_Local_USER_BIRTHDAY + " TEXT)";

	//sql for create avatar table
	private static final String CREATE_AVATAR_TABLE = "CREATE TABLE " + DB_AVATAR_TABLE + 
													" (" + KEY_AVATAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
													KEY_AVATAR_AVATARID + " TEXT," + 
													KEY_AVATAR_AVATAR + " TEXT," +
													KEY_AVATAR_TITLE + " TEXT," +
													KEY_AVATAR_LONGITUDE + " LONG," +
													KEY_AVATAR_LATITUDE + " LONG," +
													KEY_AVATAR_CREATETIME + " TEXT)";
	
	
	//sql for create comment table 
	private static final String CREATE_COMMENT_TABLE = "CREATE TABLE " + DB_COMMENT_TABLE + 
	                                               " (" + KEY_COMMNET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
	                                               KEY_COMMENT_COMMENTID + " INTEGER," +
	                                               KEY_COMMENT_AVATARID + " INTEGER," +
	                                               KEY_COMMENT_PARENTID + " INTEGER," +
	                                               KEY_COMMENT_PHONE_S + " TEXT," +
	                                               KEY_COMMENT_NAME + " TEXT," +
	                                               KEY_COMMENT_NAME_S + " TEXT," +
	                                               KEY_COMMENT_AVATAR + " TEXT," +
	                                               KEY_COMMENT_CONTENT + " TEXT," +
	                                               KEY_COMMENT_CREATETIME + " TEXT)" ;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			 db.execSQL(CREATE_LOCAL_USER_TABLE);
	         db.execSQL(CREATE_AVATAR_TABLE);
	         db.execSQL(CREATE_COMMENT_TABLE);
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
