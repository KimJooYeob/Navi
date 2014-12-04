package com.skplanetx.tmapopenmapapi.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.skplanetx.tmapopenmapapi.LogManager;

public class AroundusJsonItem {
	private int mTotalCount;
	private List<AroundusItems> mAroundusItems = new ArrayList<AroundusItems>();
	private static String URL = "http://apis.skplanetx.com/tmap/";
	
	public AroundusJsonItem(StringBuilder url) throws Exception {
		
		JSONObject obj = getDownloadFromUrl(url);
		if (obj != null) {
			JSONObject searchAdInfo = obj.getJSONObject("searchAdInfo");
			mTotalCount = searchAdInfo.getInt("count");
			JSONObject inventories = searchAdInfo.getJSONObject("inventories");
			JSONArray jsonArr = inventories.getJSONArray("inventory");
			for (int i = 0; i < mTotalCount; i++) {
				mAroundusItems.add(new AroundusItems(jsonArr.getJSONObject(i)));
			}
		}
	}

	public static String AroundusClickJsonItem(String adClickKey)throws Exception {
		StringBuilder clickUrl = new StringBuilder();
		clickUrl.append("adClick?version=1");
		clickUrl.append("&adClickKey=").append(adClickKey);
		
		JSONObject obj = getDownloadFromUrl(clickUrl);
		String clickResult = null;
		if (obj != null) {
			JSONObject resclick = obj.getJSONObject("resclick");
			clickResult = resclick.getString("clickResult");
			String clickInfo = resclick.getString("clickInfo");
		}
		return clickResult;
	}
	
	public static JSONObject getDownloadFromUrl(StringBuilder serverUrl) throws Exception {
		URL url = null;
		HttpURLConnection conn = null;
		
		InputStream input = null;
		ByteArrayOutputStream output = null;
		JSONObject jObject = null;
		
		String result = null;
		try {
			StringBuilder uri = new StringBuilder();
			uri.append(URL);		
			uri.append(serverUrl);
					
			url = new URL(uri.toString());

			conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(35000);
			conn.setReadTimeout(35000);
			conn.setRequestProperty("appKey", MainActivity.mApiKey);
			conn.setRequestProperty("Accept", "application/json");

			int response = conn.getResponseCode();

			if (HttpURLConnection.HTTP_OK == response) {

				input = conn.getInputStream();
			    output = new ByteArrayOutputStream();
			    byte[] byteBuffer = new byte[1024];
			    byte[] byteData = null;
			    int nLength = 0;
			    while((nLength = input.read(byteBuffer, 0, byteBuffer.length)) != -1) {
			    	output.write(byteBuffer, 0, nLength);
			    }
			    byteData = output.toByteArray();
			    result = new String(byteData);
			    
				if (result != null) {
					try {
						jObject = new JSONObject(result);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				return jObject;
			}

		} finally {
			try {
				if (output != null) {
					output.flush();
					output.close();
					output = null;
				}
				if (input != null) {
					input.close();
					input = null;
				}
				url = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public int getTotalCount() {
		return mTotalCount;
	}

	public void setTotalCount(int totalCount) {
		this.mTotalCount = totalCount;
	}

	public List<AroundusItems> getPoiItems() {
		return mAroundusItems;
	}

	public void setPoiItems(List<AroundusItems> poiItems) {
		this.mAroundusItems = poiItems;
	}

}
