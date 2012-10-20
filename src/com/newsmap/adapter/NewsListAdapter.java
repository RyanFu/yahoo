package com.newsmap.adapter;

import java.util.ArrayList;

import com.example.yahoohackday.R;
import com.newsmap.entity.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class NewsListAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<News> newsList;
	
	public NewsListAdapter(Context mContext,  ArrayList<News> newsList){
		this.newsList = newsList;
		this.mContext = mContext;
	}

	public int getCount() {
		return newsList.size();
	}

	public Object getItem(int position) {
		return newsList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater myInflater = LayoutInflater.from(mContext);
		View converView = myInflater.inflate(R.layout.listview_news_item, null);
		
		TextView textviewTitle = (TextView) converView.findViewById(R.id.textview_title);
		TextView textviewContentPreview = (TextView) converView.findViewById(R.id.textview_content_preview);
		
		News news = newsList.get(position);
		
		textviewTitle.setText(news.getTitle());
		textviewContentPreview.setText(news.getContent().substring(0, 30));
		
		return converView;
	}
}
