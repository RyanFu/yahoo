package com.example.yahoohackday;

import java.util.ArrayList;

import com.example.sqlite.SQLite;
import com.newsmap.adapter.NewsListAdapter;
import com.newsmap.entity.News;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsListActivity extends Activity {
	
	private ListView listviewNews;
	private ArrayList<News> newsList;
	private String locationName;
	private static final double EARTH_RADIUS = 6378.137;
	private double distance;
	
	private TextView textviewLocation;
	private TextView textviewDistance;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        newsList = new ArrayList<News>(10);
        //setFakeData();
        getNewsData();
        findViews();
        setListtener();
        countDistance();
        
    	textviewLocation.setText(newsList.get(0).getLocationName());
    	
    	java.text.DecimalFormat df= new java.text.DecimalFormat("#0.00");
    	textviewDistance.setText(df.format(distance) + "公里");
	}
	
	public void countDistance () {
		News news = newsList.get(0);
		double newsLong = news.getLongitude();
		double newsLat = news.getLatitude();
		
		LocationManager locMan = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
		Location location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		Location currentLocation = location;
		
		double currentLongitude = currentLocation.getLongitude();
		double currentLatitude = currentLocation.getLatitude();
			
		//getDistance(double lat1, double lng1, double lat2, double lng2)

		distance = getDistance(newsLat, newsLong, currentLatitude, currentLongitude);
		
		distance = distance / 1000;
		
		
		
		
		//distance = df.format(distance);
		//System.out.println(df.format(e));
		
		//Log.d("Ben", "Distance: " + df.format(distance));
	}
	
	private void getNewsData() {
		Bundle bundle = getIntent().getExtras();
	    if(bundle != null) {
	    	locationName = bundle.getString("location_name");
	    	//locationName = bundle.getString("南港展覽館1");
	    	SQLite sqlite = new SQLite(this);
	    	newsList = sqlite.getNewsList(locationName);
	    	
	    	Log.d("Ben", "News Size: " + newsList.size());
	    }
	}
	
	private void setFakeData() {
		//News(String title, String source, String content, String url, String locationName, double longitude, double latitude)
		News news1 = new News(0, "我是第一則南港展館文創博覽會 至21日", "台灣新生報", "由文化部主辦的「二○一二年台灣國際文化創意產業博覽會」，自昨（十八）日至二十一日於世貿南港展覽館盛大舉" +
				"行，本屆展會共設有九大展區，參展廠商及單位，包括國際及中國大陸共有五二五家，攤位數達九五二個，為文博會連續舉辦三屆以來規模最大，四天展會估計可吸引七萬人免費入場參觀，詳情可上" +
				"官網www.iccie.tw為鼓勵民眾共襄盛舉前往參觀，凡至參展攤位單筆消費滿新台幣五百元，即贈抽獎券一張，獎品豐富。展會期間，每日有精采舞台表演活動、藝文沙龍等，歡迎參觀", 
				"http://tw.news.yahoo.com/%E5%8D%97%E6%B8%AF%E5%B1%95%E9%A4%A8%E6%96%87%E5%89%B5%E5%8D%9A%E8%A6%BD%E6%9C%83-%E8%87%B321%E6%97%A5-160438925.html",
				"南港展覽館1", 121.617695, 25.055201, 0);
		News news2 = new News(0, "我是第二則南港展館文創博覽會 至21日", "台灣新生報", "由文化部主辦的「二○一二年台灣國際文化創意產業博覽會」，自昨（十八）日至二十一日於世貿南港展覽館盛大舉" +
				"行，本屆展會共設有九大展區，參展廠商及單位，包括國際及中國大陸共有五二五家，攤位數達九五二個，為文博會連續舉辦三屆以來規模最大，四天展會估計可吸引七萬人免費入場參觀，詳情可上" +
				"官網www.iccie.tw為鼓勵民眾共襄盛舉前往參觀，凡至參展攤位單筆消費滿新台幣五百元，即贈抽獎券一張，獎品豐富。展會期間，每日有精采舞台表演活動、藝文沙龍等，歡迎參觀", 
				"http://tw.news.yahoo.com/%E5%8D%97%E6%B8%AF%E5%B1%95%E9%A4%A8%E6%96%87%E5%89%B5%E5%8D%9A%E8%A6%BD%E6%9C%83-%E8%87%B321%E6%97%A5-160438925.html",
				"南港展覽館1", 121.617695, 25.055201, 0);
		News news3 = new News(0, "我是第三則南港展館文創博覽會 至21日", "台灣新生報", "由文化部主辦的「二○一二年台灣國際文化創意產業博覽會」，自昨（十八）日至二十一日於世貿南港展覽館盛大舉" +
				"行，本屆展會共設有九大展區，參展廠商及單位，包括國際及中國大陸共有五二五家，攤位數達九五二個，為文博會連續舉辦三屆以來規模最大，四天展會估計可吸引七萬人免費入場參觀，詳情可上" +
				"官網www.iccie.tw為鼓勵民眾共襄盛舉前往參觀，凡至參展攤位單筆消費滿新台幣五百元，即贈抽獎券一張，獎品豐富。展會期間，每日有精采舞台表演活動、藝文沙龍等，歡迎參觀", 
				"http://tw.news.yahoo.com/%E5%8D%97%E6%B8%AF%E5%B1%95%E9%A4%A8%E6%96%87%E5%89%B5%E5%8D%9A%E8%A6%BD%E6%9C%83-%E8%87%B321%E6%97%A5-160438925.html",
				"南港展覽館1", 121.617695, 25.055201, 0);
		
		newsList.add(news1);
		newsList.add(news2);
		newsList.add(news3);
	}
	private void findViews() {
		listviewNews = (ListView) findViewById(R.id.listview_newslist);
		textviewLocation = (TextView) findViewById(R.id.textview_locationname);
		textviewDistance = (TextView) findViewById(R.id.textview_distance);
	}
	
	private void setListtener() {
		listviewNews.setAdapter((ListAdapter) new NewsListAdapter(NewsListActivity.this, newsList));
		listviewNews.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				News news = newsList.get(position);
				
				Intent intent = new Intent();
				intent.putExtra("location_name", news.getLocationName());
				intent.putExtra("content", news.getContent());
				intent.putExtra("url", news.getUrl());
				intent.putExtra("title", news.getTitle());
				
				//intent.setClass(NewsListActivity.this, );
	            startActivity(intent);
	            
			}
		});
	}
	
	public double getDistance(double lat1, double lng1, double lat2, double lng2)
	  {
	     double radLat1 = rad(lat1);
	     double radLat2 = rad(lat2);
	     double a = radLat1 - radLat2;
	     double b = rad(lng1) - rad(lng2);
	     double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	      Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	     s = s * EARTH_RADIUS;
	     s = Math.round(s * 10000) / 10000;
	     Log.d("Ben", "Distance: " + s);
	     return s;
	  }
	
	private double rad(double d)
	  {
	     return d * Math.PI / 180.0;
	  }
}
