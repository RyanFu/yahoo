package com.example.yahoohackday;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This example uses a FrameLayout to display a menu View and a HorizontalScrollView (HSV).
 * 
 * The HSV has a transparent View as the first child, which means the menu will show through when the HSV is scrolled.
 */
public class HorzScrollWithImageMenu extends Activity {
    MyHorizontalScrollView scrollView;
    View menu;
    View app;
    ImageView btnSlide;
    boolean menuOut = false;
    Handler handler = new Handler();
    int btnWidth;
	private TextView news_content;
	private TextView news_url;
	private TextView news_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = mWinMgr.getDefaultDisplay().getWidth();

        LayoutInflater inflater = LayoutInflater.from(this);
        setContentView(inflater.inflate(R.layout.horz_scroll_with_image_menu, null));

        scrollView = (MyHorizontalScrollView) findViewById(R.id.myScrollView);
        menu = findViewById(R.id.menu);
        app = inflater.inflate(R.layout.horz_scroll_app, null);
        ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);

//        ListView listView = (ListView) app.findViewById(R.id.list);
//        ViewUtils.initListView(this, listView, "Item ", 30, android.R.layout.simple_list_item_1);

        btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
        btnSlide.setOnClickListener(new HorzScrollWithListMenu.ClickListenerForScrolling(scrollView, menu,displayWidth));

        news_content = (TextView)tabBar.findViewById(R.id.news_content);
        news_url = (TextView)tabBar.findViewById(R.id.news_url);
        news_title = (TextView)tabBar.findViewById(R.id.news_title);
        
        Bundle bundle = getIntent().getExtras();
        news_content.setText(bundle.getString("content"));
        news_url.setText(bundle.getString("url"));
        news_title.setText(bundle.getString("title"));
        
        // Create a transparent view that pushes the other views in the HSV to the right.
        // This transparent view allows the menu to be shown when the HSV is scrolled.
        View transparent = new TextView(this);
//        transparent.setBackgroundColor(android.R.color.transparent);

        final View[] children = new View[] { transparent, app };

        // Scroll to app (view[1]) when layout finished.
        int scrollToViewIdx = 1;
        scrollView.initViews(children, scrollToViewIdx, new HorzScrollWithListMenu.SizeCallbackForMenu(btnSlide));
    }
}
