package com.example.yahoohackday;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.sqlite.SQLite;
import com.newsmap.adapter.NewsListAdapter;
import com.newsmap.entity.News;

public class SearchActivity extends Activity {
	private ListView listviewNews;
	private ArrayList<News> newsList;
	private ArrayList<News> arraySort = new ArrayList<News>();
	private EditText edittextSearchBar;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);
        newsList = new ArrayList<News>(10);
        getNewsData();
        findViews();
        setListtener();
        setSearchBar();
	}
	
	private void getNewsData() {
		SQLite sqlite = new SQLite(this);
	    newsList = sqlite.getAllList();
	}
	
	private void findViews() {
		listviewNews = (ListView) findViewById(R.id.listview_newslist);
		View header = View.inflate(this, R.layout.searchbar, null);
		listviewNews.addHeaderView(header);
		listviewNews.setFastScrollEnabled(true);
	    edittextSearchBar = (EditText)findViewById(R.id.edittext_searchbar);
	}
	
	private void setListtener() {
		listviewNews.setAdapter((ListAdapter) new NewsListAdapter(SearchActivity.this, newsList));
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
	
	private void setSearchBar() {
    	edittextSearchBar.addTextChangedListener(
    		new TextWatcher() {
		    	public void afterTextChanged(Editable s) {}
		    	
		    	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		    	
		    	public void onTextChanged(CharSequence s, int start, int before, int count) {
		    		int textlength = edittextSearchBar.getText().length();
		    		arraySort.clear();
			    	for (int i = 0; i < newsList.size(); i++) {
			    		News news;
			    		news = newsList.get(i);
			    		String title = news.getTitle();
			    		if (textlength <= title.length()) {
			    			if(edittextSearchBar.getText().toString().equalsIgnoreCase(title.subSequence(0, textlength).toString())) {
			    				arraySort.add(news);		    				
			    			}
			    		}
			    	}
			    	listviewNews.setAdapter((ListAdapter) new NewsListAdapter(SearchActivity.this, arraySort));
		    	}
			}
		);
    }
}
