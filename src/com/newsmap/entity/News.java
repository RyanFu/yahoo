package com.newsmap.entity;

public class News {
	private int id;
	private String title;
	private String source;
	private String content;
	private String url;
	private String locationName;
	private double longitude;
	private double latitude;
	private int newsCount;
	
	public News(String locationName, double longitude, double latitude, int newsCount) {
		new News(0, "", "", "", "", locationName, longitude, latitude, newsCount);
	}
	
	public News(int id, String title, String source, String content, String url, String locationName, double longitude, double latitude, int newsCount) {
		this.id = id;
		this.title = title;
		this.source = source;
		this.content = content;
		this.url = url;
		this.locationName = locationName;
		this.longitude = longitude;
		this.latitude = latitude;
		this.newsCount = newsCount;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
	public int getNewsCount() {
		return newsCount;
	}
	public void setNewsCount(int newsCount) {
		this.newsCount = newsCount;
	}
	
}
