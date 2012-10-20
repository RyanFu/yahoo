package com.example.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.newsmap.entity.News;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {
	private static final String NewsTable = "news";
	private static final String DB_PATH = "/data/data/com.example.yahoohackday/databases/";
	private static final String DB_NAME = "newsmap.sqlite";	//資料庫名稱
	private static final int DATABASE_VERSION = 1;	//資料庫版本
	private static SQLiteDatabase db;
	
	public SQLite(Activity mActivity) {
		super(mActivity, DB_NAME, null, DATABASE_VERSION);
		checkFileSystem(mActivity);
		
		if(!checkDataBase())
			db = this.getWritableDatabase();
	}
 
	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		createNews();
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + NewsTable);
		Log.d("SQLiteMovieDiary", "Delete old Database");
		onCreate(db);
	}
	
	public SQLiteDatabase GetDB() {
		openDataBase();
		return db;
	}
	
	private static void openDataBase() {
		db = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
	}
	
	public static void closeDB() { 
		if(null != db)
			db.close();  
    }
	
	private boolean checkDataBase(){
		File dbtest = new File(DB_PATH + DB_NAME);
		if(dbtest.exists())
			return true;
		else 
			return false;
    }

	private void checkFileSystem(Activity mActivity){
		//if((new File(DB_PATH + DB_NAME)).exists() == false) {
			// 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
			File f = new File(DB_PATH);
			// 如 database 目录不存在，新建该目录
			if (!f.exists()) {
				f.mkdir();
			}

			try {
				// 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
				InputStream is = mActivity.getBaseContext().getAssets().open(DB_NAME);
				// 输出流
				OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

				// 文件写入
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}

				// 关闭文件流
				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}
	}
	
	
	public static void createNews() {
		openDataBase();
		String DATABASE_CREATE_TABLE =
				"CREATE TABLE IF NOT EXISTS " + NewsTable + " (" +
				" ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
				" TITLE VARCHAR," +
				" SOURCE VARCHAR," +
				" CONTENT VARCHAR" +
				" URL VARCHAR" +
				" LOCNAME VARCHAR" +
				" LONTITUDE REAL" +
				" LATITUDE REAL" +
				" );";
		
		db.execSQL(DATABASE_CREATE_TABLE);
		closeDB();
	}
	
	public long addNews(News news) {
		openDataBase();
		long rowId = insertNews(news);
		closeDB();
		
		return rowId;
	}	
	private static long insertNews(News news) {
		ContentValues args = new ContentValues();
		args.put("ID", news.getID());
		args.put("TITLE", news.getTitle());
		args.put("SOURCE", news.getSource());
		args.put("CONTENT", news.getContent());
		args.put("URL", news.getUrl());
		args.put("LOCNAME", news.getLocationName());
		args.put("LONTITUDE", news.getLongitude());
		args.put("LATITUDE", news.getLatitude());
		return db.insert(NewsTable, null, args);
	}	
	public int deleteNews(long rowId) {
		return db.delete(NewsTable,
		"MAIN_ID = " + rowId,
		null
		);
	}
	public static void addNewsList(ArrayList<News> news_lst) {
		openDataBase();
		Log.d("SQLiteMovieDiary", "News List Size : " + news_lst.size());
		for(int i=0; i<news_lst.size(); i++) {
			News news = news_lst.get(i);
			insertNews(news);
		}
		closeDB();
	}
	public static void deleteNews_lst() {
		openDataBase();
		db.execSQL("DROP TABLE IF EXISTS " + NewsTable);
		closeDB();
	}
	
	public ArrayList<News> getAllList() throws SQLException {
		openDataBase();
		ArrayList<News> news_lst = new ArrayList<News>(10);  
		Cursor cursor = db.rawQuery("SELECT * FROM " + NewsTable, null); 
        while (cursor.moveToNext()){  
            News news = new News(  
		    		cursor.getInt(0),
		            cursor.getString(2),
		            cursor.getString(3),
		            cursor.getString(4),
		            cursor.getString(1),
		            cursor.getString(9),
		            cursor.getFloat(5),
		            cursor.getFloat(6), 0);
            news_lst.add(news);
        }

        cursor.close();
		closeDB();
		return news_lst;
	}
	
	public ArrayList<News> getLocationList() throws SQLException {
		openDataBase();
		ArrayList<News> news_lst = new ArrayList<News>(10);
		//SELECT DISTINCT  location_name, longitude, latitude, COUNT(id) AS count FROM news GROUP BY location_name
		Cursor cursor = db.rawQuery("SELECT location_name, longitude, latitude, COUNT(id) FROM " + NewsTable + " GROUP BY location_name", null); 
        while (cursor.moveToNext()){  
            News news = new News(  
			        0, "", "", "", "",
			        cursor.getString(0),
			        cursor.getFloat(1),
			        cursor.getFloat(2),
			        cursor.getInt(3));
            news_lst.add(news);
        }

        cursor.close();
		closeDB();
		return news_lst;
	}
	public ArrayList<News> getNewsList(String locationName) throws SQLException {
		openDataBase();
		ArrayList<News> news_lst = new ArrayList<News>(10);  
		Cursor cursor = db.rawQuery("SELECT * FROM " + NewsTable + " WHERE location_name = \'" + locationName + "\'", null); 
        while (cursor.moveToNext()){  
            News news = new News(  
            		cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(1),
                    cursor.getString(9),
                    cursor.getFloat(5),
                    cursor.getFloat(6), 0);
            news_lst.add(news);
        }

        cursor.close();
		closeDB();
		return news_lst;
	}
}
