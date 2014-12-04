package com.skplanetx.tmapopenmapapi.ui;

import org.json.JSONObject;

public class AroundusItems {
	private String company;
	private String promotion;
	private String icon;
//	private String banner;
	private String url;
	private String adClickKey;
	private String distance;
	private double latitude;
	private double longitude;
	
	public AroundusItems(JSONObject obj) {
		try {
			company = obj.getString("company");			//업체 이름
			promotion = obj.getString("promotion");		//광고 문구
			icon = obj.getString("icon");				//광고에 나타낼 thumbnail 이미지 경로
//			banner = obj.getString("banner");			//해당 광고의 Full Banner 이미지 경로
			url = obj.getString("url");					//광고를 클릭 했을 경우 이동할 랜딩 페이지 URL
			adClickKey = obj.getString("adClickKey");	//광고 클릭 API 호출 시 어라운더스에 전달해야 하는 클릭 API 값
			distance = obj.getString("distance");		//사용자로 부터 광고 업체가 떨어져있는 거리
			latitude = obj.getDouble("latitude");		//광고주 위치에 해당하는 위도값
			longitude = obj.getDouble("longitude");		//광고주 위치에 해당하는 경도값
		}
		catch(Exception ex) {}
	}
	
	public String getCompany() {
		return company;
	}
	
	public String getPromotion() {
		return promotion;
	}

	public String getIcon() {
		return icon;
	}

	public String getUrl() {
		return url;
	}

	public String getAdClickKey() {
		return adClickKey;
	}

	public String getDistance() {
		return distance;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
