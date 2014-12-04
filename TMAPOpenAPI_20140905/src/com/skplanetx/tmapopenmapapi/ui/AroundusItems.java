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
			company = obj.getString("company");			//��ü �̸�
			promotion = obj.getString("promotion");		//���� ����
			icon = obj.getString("icon");				//���� ��Ÿ�� thumbnail �̹��� ���
//			banner = obj.getString("banner");			//�ش� ������ Full Banner �̹��� ���
			url = obj.getString("url");					//���� Ŭ�� ���� ��� �̵��� ���� ������ URL
			adClickKey = obj.getString("adClickKey");	//���� Ŭ�� API ȣ�� �� ��������� �����ؾ� �ϴ� Ŭ�� API ��
			distance = obj.getString("distance");		//����ڷ� ���� ���� ��ü�� �������ִ� �Ÿ�
			latitude = obj.getDouble("latitude");		//������ ��ġ�� �ش��ϴ� ������
			longitude = obj.getDouble("longitude");		//������ ��ġ�� �ش��ϴ� �浵��
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
