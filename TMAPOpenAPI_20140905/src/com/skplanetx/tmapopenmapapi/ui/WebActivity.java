package com.skplanetx.tmapopenmapapi.ui;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skplanetx.tmapopenmapapi.R;


public class WebActivity extends Activity {
	private WebView mWebView = null;
	private String mAdClickKey = null;
	private boolean mIsClick = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		 
		super.onCreate(savedInstanceState);	
		super.setContentView(R.layout.web_activity);
		mIsClick = false;
	    
		String url = getIntent().getStringExtra("URL");
		mAdClickKey = getIntent().getStringExtra("ADCLICKKEY");

		mWebView  = (WebView)findViewById(R.id.mainLayout);
		mWebView.setWebViewClient(new WebViewClient() {

			public void onzPageFinished(WebView view, String url) {
				if(!mIsClick) {
					mIsClick = true;

					try {
						AroundusJsonItem.AroundusClickJsonItem(mAdClickKey);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		mWebView.loadUrl(url);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
