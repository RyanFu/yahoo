package com.example.yahoohackday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.sqlite.SQLite;
import com.newsmap.adapter.NewsListAdapter;
import com.newsmap.entity.News;

public class HistoryActivity extends Activity {
	private ListView listviewNews;
	private ArrayList<News> newsList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        newsList = new ArrayList<News>(10);
        getNewsData();
        findViews();
        setListtener();
	}
	
	private void getNewsData() {
		SQLite sqlite = new SQLite(this);
	    newsList = sqlite.getAllList();
	}
	
	private void findViews() {
		listviewNews = (ListView) findViewById(R.id.listview_newslist);
	}
	
	private void setListtener() {
		listviewNews.setAdapter((ListAdapter) new NewsListAdapter(HistoryActivity.this, newsList));
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
}
